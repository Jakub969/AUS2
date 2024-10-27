package US.KdStrom;


import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.Stack;

public class KdStrom<T extends IKluc<T>> {
    private Koren<T> koren;
    private int hlbka;
    private final int pocetKlucov;
    private int pocetVrcholov;

    public KdStrom(int pocetKlucov) {
        this.koren = null;
        this.pocetKlucov = pocetKlucov;
        this.hlbka = 0;
        this.pocetVrcholov = 0;
    }

    public void vloz(Vrchol<T> vrchol) {
        // ak je strom prazdny, vlozime vrchol ako koren
        if (this.koren == null) {
            this.koren = new Koren<>(vrchol);
            pocetVrcholov++;
            return;
        }
        Vrchol<T> aktualny = this.koren.getKoren();
        Vrchol<T> rodic = null;
        boolean lavy = true;
        int lokalnaHlbka = 0;
        // prechadzame strom, kym nenajdeme vhodne miesto pre vlozenie vrchola
        while (aktualny != null) {
            rodic = aktualny;
            int poradieKluca = lokalnaHlbka % this.pocetKlucov;
            int porovnanie = vrchol.getData().porovnaj(aktualny.getData(), poradieKluca);

            // porovnanie klucov, ak je vrchol mensi alebo rovnaky, ideme dolava, inak doprava
            if (porovnanie == -1 || porovnanie == 0) {
                if (porovnanie == 0) {
                    aktualny.addDuplicitu(vrchol);
                }
                aktualny = aktualny.getLavySyn();
                lavy = true;
            } else if (porovnanie == 1) {
                aktualny = aktualny.getPravySyn();
                lavy = false;
            } else {
                throw new IllegalStateException("Porovnanie klucov zlyhalo");
            }
            lokalnaHlbka++;
        }
        // nastavime rodica vrchola
        vrchol.setRodic(rodic);

        // vkladanie vrchola do stromu ako lavy alebo pravy syn
        if (rodic == null) {
            this.koren = new Koren<>(vrchol);
            pocetVrcholov++;
        } else if (lavy) {
            rodic.setLavySyn(vrchol);
            pocetVrcholov++;
        } else {
            rodic.setPravySyn(vrchol);
            pocetVrcholov++;
        }

        // aktualizacia hlbky stromu
        if (lokalnaHlbka > this.hlbka) {
            this.hlbka = lokalnaHlbka;
        }
    }

    public ArrayList<Vrchol<T>> vyhladaj(T kluc1, T kluc2) {
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        Stack<Vrchol<T>> stack = new Stack<>();
        Stack<Integer> hlbkaStack = new Stack<>();

        stack.push(this.koren.getKoren());
        hlbkaStack.push(0);

        while (!stack.isEmpty()) {
            Vrchol<T> aktualny = stack.pop();
            int hlbka = hlbkaStack.pop();

            if (aktualny == null) {
                continue;
            }

            int poradieKluca = hlbka % 2;
            // Zabezpečenie, že kluc1 má menšie hodnoty ako kluc2
            if (kluc1.porovnaj(kluc2, poradieKluca) == 1) {
                T temp = kluc1;
                kluc1 = kluc2;
                kluc2 = temp;
            }
            int patriDoObdlznika = aktualny.getData().vyhladaj(kluc1, kluc2);
            if (patriDoObdlznika == 0) {
                vrcholy.add(aktualny);
            }

            if (aktualny.getData().porovnaj(kluc1, poradieKluca) >= 0) {
                stack.push(aktualny.getLavySyn());
                hlbkaStack.push(hlbka + 1);
            }
            if (aktualny.getData().porovnaj(kluc2, poradieKluca) <= 0) {
                stack.push(aktualny.getPravySyn());
                hlbkaStack.push(hlbka + 1);
            }
        }

        return vrcholy;
    }


    public boolean vyrad(Vrchol<T> vrchol) {
        ArrayList<Vrchol<T>> vrcholy = this.vyhladaj(vrchol.getData(), vrchol.getData());
        if (vrcholy.isEmpty()) {
            return false;
        } else {
            for (Vrchol<T> vrchol1 : vrcholy) {
                if (vrchol1.getData().vyrad(vrchol.getData())) {
                    if (vrchol1.getLavySyn() == null && vrchol1.getPravySyn() == null) {
                        odstranList(vrchol1);
                    } else {
                        nahradVrchol(vrchol1);
                        odstranVrchol(vrchol1);
                    }
                    pocetVrcholov--;
                }
            }
            return true;
        }
    }

    private void odstranVrchol(Vrchol<T> vrchol) {
        if (vrchol == null) return;

        vrchol.setRodic(null);
        vrchol.setLavySyn(null);
        vrchol.setPravySyn(null);
    }

    private void nahradVrchol(Vrchol<T> vrchol) {
        int poradieKluca = getHlbkaVrchola(vrchol) % this.pocetKlucov;
        Vrchol<T> nahrada = vyhladajNahradu(vrchol, poradieKluca);
        Stack<Vrchol<T>> zasobnik = new Stack<>();

        // Prechádzame až po najnižší vhodný vrchol pre náhradu
        while (nahrada != null && (nahrada.getLavySyn() != null || nahrada.getPravySyn() != null)) {
            zasobnik.push(nahrada);
            poradieKluca = getHlbkaVrchola(nahrada) % this.pocetKlucov;
            nahrada = vyhladajNahradu(nahrada, poradieKluca);
        }
        if (zasobnik.size() > 1 && nahrada != null) {
            Vrchol<T> vrcholNaNahradenie;
            while (!zasobnik.isEmpty()) {
                vrcholNaNahradenie = nahradNahradu(zasobnik, nahrada);
                nahrada = vrcholNaNahradenie;
            }
        }
        // Ak existuje najnižšia možná náhrada, pristúpime k výmene
        if (nahrada != null) {
            Vrchol<T> rodicVrchola = vrchol.getRodic();
            Vrchol<T> lavySyn = vrchol.getLavySyn();
            Vrchol<T> pravySyn = vrchol.getPravySyn();

            // Odstránenie referencie syna pre rodiča náhrady
            if (nahrada.getRodic() != null) {
                if (nahrada.getRodic().getLavySyn() == nahrada) {
                    nahrada.getRodic().setLavySyn(null);
                } else {
                    nahrada.getRodic().setPravySyn(null);
                }
            }

            // Nastavenie nového rodiča pre náhradu
            if (rodicVrchola != null) {
                if (rodicVrchola.getLavySyn() == vrchol) {
                    rodicVrchola.setLavySyn(nahrada);
                } else {
                    rodicVrchola.setPravySyn(nahrada);
                }
                nahrada.setRodic(rodicVrchola);
            } else {
                //this.koren = nahrada; // Ak nemá rodiča, nahrada sa stáva novým koreňom
            }

            // Nastavenie detí pre novú náhradu
            if (lavySyn != nahrada) {
                nahrada.setLavySyn(lavySyn);
                if (lavySyn != null) {
                    lavySyn.setRodic(nahrada);
                }
            }
            if (pravySyn != nahrada) {
                nahrada.setPravySyn(pravySyn);
                if (pravySyn != null) {
                    pravySyn.setRodic(nahrada);
                }
            }
        }
    }

    private Vrchol<T> nahradNahradu(Stack<Vrchol<T>> zasobnik, Vrchol<T> nahrada) {
        Vrchol<T> vrchol = zasobnik.pop();
        Vrchol<T> rodicVrchola = vrchol.getRodic();
        Vrchol<T> lavySyn = vrchol.getLavySyn();
        Vrchol<T> pravySyn = vrchol.getPravySyn();

        // Odstránenie referencie syna pre rodiča náhrady
        if (nahrada.getRodic() != null) {
            if (nahrada.getRodic().getLavySyn() == nahrada) {
                nahrada.getRodic().setLavySyn(null);
            } else {
                nahrada.getRodic().setPravySyn(null);
            }
        }

        // Nastavenie nového rodiča pre náhradu
        if (rodicVrchola != null) {
            if (rodicVrchola.getLavySyn() == vrchol) {
                rodicVrchola.setLavySyn(nahrada);
            } else {
                rodicVrchola.setPravySyn(nahrada);
            }
            nahrada.setRodic(rodicVrchola);
        }
        // Nastavenie detí pre novú náhradu
        if (lavySyn != nahrada) {
            nahrada.setLavySyn(lavySyn);
            if (lavySyn != null) {
                lavySyn.setRodic(nahrada);
            }
        }
        if (pravySyn != nahrada) {
            nahrada.setPravySyn(pravySyn);
            if (pravySyn != null) {
                pravySyn.setRodic(nahrada);
            }
        }
        return vrchol;
    }


    private Vrchol<T> vyhladajNahradu(Vrchol<T> vrchol, int poradieKluca) {
        Vrchol<T> nahrada;


        // Pokúsi sa nájsť náhradu vrchola podľa aktuálneho poradia kľúča
        if (vrchol.getPravySyn() != null) {
            nahrada = najdiNajmensiVrchol(vrchol.getPravySyn(), poradieKluca);
        } else if (vrchol.getLavySyn() != null) {
            nahrada = najdiNajvacsiVrchol(vrchol.getLavySyn(), poradieKluca);
        } else {
            return null; // Ak nemá ani jeden podstrom, žiadna náhrada nie je potrebná
        }
        return nahrada;
    }


    private Vrchol<T> najdiNajmensiVrchol(Vrchol<T> vrchol, int poradieKluca) {
        Vrchol<T> najmensi = vrchol;
        while (vrchol.getLavySyn() != null) {
            if (vrchol.getLavySyn().getData().porovnaj(najmensi.getData(), poradieKluca) <= 0) {
                najmensi = vrchol.getLavySyn();
            }
            vrchol = vrchol.getLavySyn();
        }
        return najmensi;
    }

    private Vrchol<T> najdiNajvacsiVrchol(Vrchol<T> vrchol, int poradieKluca) {
        Vrchol<T> najvacsi = vrchol;
        while (vrchol.getPravySyn() != null) {
            if (vrchol.getPravySyn().getData().porovnaj(najvacsi.getData(), poradieKluca) >= 0) {
                najvacsi = vrchol.getPravySyn();
            }
            vrchol = vrchol.getPravySyn();
        }
        return najvacsi;
    }

    private int getHlbkaVrchola(Vrchol<T> vrchol) {
        int lokalnaHlbka = 0;
        while (vrchol.getRodic() != null) {
            vrchol = vrchol.getRodic();
            lokalnaHlbka++;
        }
        return lokalnaHlbka;
    }

    private void odstranList(Vrchol<T> vrchol) {
        Vrchol<T> rodic = vrchol.getRodic();
        if (rodic == null) {
            this.koren = null;
        } else if (rodic.getLavySyn() == vrchol) {
            rodic.setLavySyn(null);
        } else {
            rodic.setPravySyn(null);
        }
    }

    public ArrayList<Vrchol<T>> inOrderPrehliadka() {
        Vrchol<T> aktualny = this.koren.getKoren();
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();

        // Morrisov algoritmus pre in-order prehliadku stromu zdroj: https://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/
        while (aktualny != null) {
            // ak vrchol nema laveho syna, pridame ho do zoznamu vrcholov a posunieme sa na praveho syna
            if (aktualny.getLavySyn() == null) {
                vrcholy.add(aktualny);
                aktualny = aktualny.getPravySyn();
            } else {
                // najdeme predchodcu vrchola
                Vrchol<T> predchodca = aktualny.getLavySyn();
                while (predchodca.getPravySyn() != null && predchodca.getPravySyn() != aktualny) {
                    predchodca = predchodca.getPravySyn();
                }

                // nastavime predchodcovi praveho syna na aktualny vrchol
                if (predchodca.getPravySyn() == null) {
                    predchodca.setPravySyn(aktualny);
                    aktualny = aktualny.getLavySyn();
                } else {
                    // vratime zmeny v strome do povodneho stavu
                    predchodca.setPravySyn(null);
                    vrcholy.add(aktualny);
                    aktualny = aktualny.getPravySyn();
                }
            }
        }
        return vrcholy;
    }

    public Koren<T> getKoren() {
        return koren;
    }

    public int getHlbka() {
        return hlbka;
    }

    public int getPocetVrcholov() {
        return pocetVrcholov;
    }
}
