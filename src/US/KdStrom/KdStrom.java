package US.KdStrom;


import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.Stack;

public class KdStrom<T extends IKluc<T>> {
    private Vrchol<T> koren;
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
            this.koren = vrchol;
            pocetVrcholov++;
            return;
        }
        Vrchol<T> aktualny = this.koren;
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
                    int porovnanieDuplicity = vrchol.getData().porovnaj(aktualny.getData(), (poradieKluca + 1) % this.pocetKlucov);
                    if (porovnanieDuplicity == 0) {
                        aktualny.addDuplicitu(vrchol);
                        return;
                    }
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
        if (lavy) {
            rodic.setLavySyn(vrchol);
        } else {
            rodic.setPravySyn(vrchol);
        }
        pocetVrcholov++;

        // aktualizacia hlbky stromu
        if (lokalnaHlbka > this.hlbka) {
            this.hlbka = lokalnaHlbka;
        }
    }

    private ArrayList<Vrchol<T>> vyhladaj(Vrchol<T> kluc) {
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        if (this.koren == null) {
            return vrcholy;
        }
        Stack<Vrchol<T>> vrcholyPrehladavania = new Stack<>();
        vrcholyPrehladavania.push(this.koren);

        int hlbka = 0;

        while (!vrcholyPrehladavania.isEmpty()) {
            Vrchol<T> aktualny = vrcholyPrehladavania.pop();
            int poradieKluca = hlbka % this.pocetKlucov;
            int porovnanie = kluc.getData().porovnaj(aktualny.getData(), poradieKluca);
            if (porovnanie == 0 && aktualny.getData().porovnaj(kluc.getData(), (poradieKluca + 1) % this.pocetKlucov) == 0) {
                vrcholy.add(aktualny);
                vrcholy.addAll(aktualny.getDuplicity());
                return vrcholy;
            }
            if (porovnanie <= 0 && aktualny.getLavySyn() != null) {
                vrcholyPrehladavania.push(aktualny.getLavySyn());
            }
            if (porovnanie > 0 && aktualny.getPravySyn() != null) {
                vrcholyPrehladavania.push(aktualny.getPravySyn());
            }
            hlbka++;
        }
        return vrcholy;
    }

    public ArrayList<Vrchol<T>> bodoveVyhladavanie(ArrayList<Vrchol<T>> kluce) {
        if (kluce.size() != this.pocetKlucov) {
            throw new IllegalArgumentException("Nespravny pocet klucov");
        }

        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        for (Vrchol<T> vrchol : kluce) {
            vrcholy.addAll(vyhladaj(vrchol));
        }

        return vrcholy;
    }


    public boolean vyrad(Vrchol<T> vrchol) {
        ArrayList<Vrchol<T>> vrcholy = this.vyhladaj(vrchol);
        if (vrcholy.isEmpty()) {
            return false;
        } else {
            for (Vrchol<T> vrchol1 : vrcholy) {
                if (vrchol1.getLavySyn() == null && vrchol1.getPravySyn() == null) {
                    odstranList(vrchol1, vrchol);
                } else {
                    nahradVrchol(vrchol1);
                    odstranVrchol(vrchol1, vrchol);
                }
            }
            return true;
        }
    }

    private void odstranVrchol(Vrchol<T> vrchol, Vrchol<T> kluc) {
        if (vrchol == null) return;


        vrchol.setRodic(null);
        vrchol.setLavySyn(null);
        vrchol.setPravySyn(null);
        pocetVrcholov--;
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
        // Nahradíme mazaný vrchol
        if (nahrada != null) {
            opravPrepojenia(vrchol, nahrada);
        }
    }

    private Vrchol<T> nahradNahradu(Stack<Vrchol<T>> zasobnik, Vrchol<T> nahrada) {
        Vrchol<T> vrchol = zasobnik.pop();
        opravPrepojenia(vrchol, nahrada);
        return vrchol;
    }

    private void opravPrepojenia(Vrchol<T> vrchol, Vrchol<T> nahrada) {
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
            this.koren = nahrada; // Ak nemá rodiča, nahrada sa stáva novým koreňom
            this.koren.setRodic(null);
        }
        // Nastavenie synov pre novú náhradu
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

    private Vrchol<T> vyhladajNahradu(Vrchol<T> vrchol, int poradieKluca) {
        Vrchol<T> nahrada;
        // Pokúsi sa nájsť náhradu vrchola podľa aktuálneho poradia kľúča
        if (vrchol.getPravySyn() != null) {
            nahrada = najdiNajmensiVrchol(vrchol.getPravySyn(), poradieKluca);
        } else if (vrchol.getLavySyn() != null) {
            nahrada = najdiNajvacsiVrchol(vrchol.getLavySyn(), poradieKluca);
        } else {
            return null;
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

    private void odstranList(Vrchol<T> vrchol, Vrchol<T> kluc) {
        Vrchol<T> rodic = vrchol.getRodic();
        ArrayList<Vrchol<T>> duplicity = vrchol.getDuplicity();
        Vrchol<T> mazanyVrchol = null;
        if (vrchol.getData().zhodneUuid(kluc.getData())) {
            mazanyVrchol = vrchol;
        } else if (!duplicity.isEmpty()) {
            for (Vrchol<T> tVrchol : duplicity) {
                if (tVrchol.getData().zhodneUuid(kluc.getData())) {
                    mazanyVrchol = tVrchol;
                    break;
                }
            }
            duplicity.remove(mazanyVrchol);
        }
        if (mazanyVrchol != vrchol) {
            if (rodic == null) {
                this.koren = duplicity.removeFirst();
                this.koren.setDuplicity(duplicity);
            } else if (rodic.getLavySyn() == vrchol) {
                Vrchol<T> nahrada = duplicity.removeFirst();
                rodic.setLavySyn(nahrada);
                nahrada.setRodic(rodic);
                nahrada.setDuplicity(duplicity);
                nahrada.setPravySyn(vrchol.getPravySyn());
                nahrada.setLavySyn(vrchol.getLavySyn());
            } else {
                Vrchol<T> nahrada = duplicity.removeFirst();
                rodic.setPravySyn(nahrada);
                nahrada.setRodic(rodic);
                nahrada.setDuplicity(duplicity);
                nahrada.setPravySyn(vrchol.getPravySyn());
                nahrada.setLavySyn(vrchol.getLavySyn());
            }
            if (mazanyVrchol != null) {
                mazanyVrchol.setRodic(null);
            }
        } else {
            if (rodic == null) {
                this.koren = null;
            } else if (rodic.getLavySyn() == vrchol) {
                rodic.setLavySyn(null);
            } else {
                rodic.setPravySyn(null);
            }
        }
        pocetVrcholov--;
    }

    public ArrayList<Vrchol<T>> inOrderPrehliadka() {
        Vrchol<T> aktualny = this.koren;
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

    public Vrchol<T> getKoren() {
        return koren;
    }

    public int getHlbka() {
        return hlbka;
    }

    public int getPocetVrcholov() {
        return pocetVrcholov;
    }
}
