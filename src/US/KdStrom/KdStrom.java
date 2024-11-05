package US.KdStrom;


import rozhrania.IKluc;

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
                        vrchol.setJeDuplicita(true);
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

    public Vrchol<T> vyhladaj(Vrchol<T> kluc) {
        if (this.koren == null) {
            return null;
        }
        Vrchol<T> aktualny = this.koren;
        int hlbka = 0;

        while (aktualny != null) {
            int poradieKluca = hlbka % this.pocetKlucov;
            int porovnanie = kluc.getData().porovnaj(aktualny.getData(), poradieKluca);
            if (porovnanie == 0 && aktualny.getData().porovnaj(kluc.getData(), (poradieKluca + 1) % this.pocetKlucov) == 0) {
                return aktualny;
            }
            if (porovnanie <= 0 && aktualny.getLavySyn() != null) {
                aktualny = aktualny.getLavySyn();
            }
            else if (porovnanie > 0 && aktualny.getPravySyn() != null) {
                aktualny = aktualny.getPravySyn();
            }
            hlbka++;
        }
        return aktualny;
    }

    public ArrayList<Vrchol<T>> bodoveVyhladavanie(ArrayList<Vrchol<T>> kluce) {
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        for (Vrchol<T> vrchol : kluce) {
            vrcholy.add(vyhladaj(vrchol));
        }

        return vrcholy;
    }


    public void vyrad(Vrchol<T> vrchol) {
        Vrchol<T> vyhladanyVrchol = this.vyhladaj(vrchol);
        if (vyhladanyVrchol != null) {
            if (vyhladanyVrchol.getLavySyn() == null && vyhladanyVrchol.getPravySyn() == null) {
                odstranVrchol(vyhladanyVrchol, vrchol, true, false);
            } else {
                if (!vyhladanyVrchol.getDuplicity().isEmpty() || vyhladanyVrchol.getDuplicity() == null) {
                    odstranVrchol(vyhladanyVrchol, vrchol, false, false);
                } else {
                    nahradVrchol(vyhladanyVrchol);
                    odstranVrchol(vyhladanyVrchol, vrchol, false, false);
                }
            }
        }
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
        if (!zasobnik.isEmpty() && nahrada != null) {
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
        /*„Ak sa pri mazaní v strome presúva na nové miesto prvok, ktorého kľúč sa podľa dimenzie nového
            umiestnenia prvku v pravom podstrome nového umiestnenia prvku už nachádza, musíme následne
            všetky takéto prvky zo stromu tiež odobrať a opätovne vložiť do stromu."*/

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
                if (nahrada.getData().porovnaj(pravySyn.getData(), getHlbkaVrchola(nahrada) % this.pocetKlucov) == 0) {
                    znovuVlozVrcholy(pravySyn, nahrada);
                }
            }
        }
    }

    private void znovuVlozVrcholy(Vrchol<T> pravySyn, Vrchol<T> nahrada) {
        int poradieKluca = getHlbkaVrchola(nahrada) % this.pocetKlucov;
        ArrayList<Vrchol<T>> vrcholyNaZnovuVlozenie = new ArrayList<>();
        Stack<Vrchol<T>> zasobnik = new Stack<>();
        zasobnik.push(pravySyn);

        while (!zasobnik.isEmpty()) {
            Vrchol<T> vrchol = zasobnik.pop();
            if (vrchol.getData().porovnaj(nahrada.getData(), poradieKluca) == 0) {
                vrcholyNaZnovuVlozenie.add(vrchol);
            }
            if (vrchol.getLavySyn() != null && vrchol.getLavySyn().getData().porovnaj(nahrada.getData(), poradieKluca) == 0) {
                zasobnik.push(vrchol.getLavySyn());
            }
            if (vrchol.getPravySyn() != null && vrchol.getPravySyn().getData().porovnaj(nahrada.getData(), poradieKluca) == 0) {
                zasobnik.push(vrchol.getPravySyn());
            }
        }

        for (Vrchol<T> vrchol : vrcholyNaZnovuVlozenie) {
            nahradVrchol(vrchol);
            odstranVrchol(vrchol, vrchol, vrchol.getLavySyn() == null && vrchol.getPravySyn() == null, true);
            vloz(vrchol);
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
        Stack<Vrchol<T>> zasobnik = new Stack<>();
        zasobnik.push(vrchol);
        while (!zasobnik.isEmpty()) {
            Vrchol<T> aktualny = zasobnik.pop();
            int aktualnePoradieKluca = getHlbkaVrchola(aktualny) % this.pocetKlucov;
            if (aktualny.getData().porovnaj(najmensi.getData(), poradieKluca) <= 0) {
                najmensi = aktualny;
            }
            if (aktualnePoradieKluca == poradieKluca) {
                if (aktualny.getLavySyn() != null) {
                    zasobnik.push(aktualny.getLavySyn());
                }
            } else {
                if (aktualny.getLavySyn() != null) {
                    zasobnik.push(aktualny.getLavySyn());
                }
                if (aktualny.getPravySyn() != null) {
                    zasobnik.push(aktualny.getPravySyn());
                }
            }
        }
        return najmensi;
    }

    private Vrchol<T> najdiNajvacsiVrchol(Vrchol<T> vrchol, int poradieKluca) {
        Vrchol<T> najvacsi = vrchol;
        Stack<Vrchol<T>> zasobnik = new Stack<>();
        zasobnik.push(vrchol);
        while (!zasobnik.isEmpty()) {
            Vrchol<T> aktualny = zasobnik.pop();
            int aktualnePoradieKluca = getHlbkaVrchola(aktualny) % this.pocetKlucov;
            if (aktualny.getData().porovnaj(najvacsi.getData(), poradieKluca) >= 0) {
                najvacsi = aktualny;
            }
            if (aktualnePoradieKluca == poradieKluca) {
                if (aktualny.getPravySyn() != null) {
                    zasobnik.push(aktualny.getPravySyn());
                }
            } else {
                if (aktualny.getLavySyn() != null) {
                    zasobnik.push(aktualny.getLavySyn());
                }
                if (aktualny.getPravySyn() != null) {
                    zasobnik.push(aktualny.getPravySyn());
                }
            }
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

    private void odstranVrchol(Vrchol<T> vrchol, Vrchol<T> kluc, boolean jeList, boolean znovuVkladanieVrchola) {
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
            return;
        }
        if (!duplicity.isEmpty() && !znovuVkladanieVrchola) {
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
                nahrada.setJeDuplicita(false);
            } else {
                Vrchol<T> nahrada = duplicity.removeFirst();
                rodic.setPravySyn(nahrada);
                nahrada.setRodic(rodic);
                nahrada.setDuplicity(duplicity);
                nahrada.setPravySyn(vrchol.getPravySyn());
                nahrada.setLavySyn(vrchol.getLavySyn());
                nahrada.setJeDuplicita(false);
            }
            mazanyVrchol.setRodic(null);
        } else {
            if (jeList) {
                if (rodic == null) {
                    this.koren = null;
                } else if (rodic.getLavySyn() == vrchol) {
                    rodic.setLavySyn(null);
                } else {
                    rodic.setPravySyn(null);
                }
            } else {
                vrchol.setRodic(null);
                vrchol.setLavySyn(null);
                vrchol.setPravySyn(null);
            }
            pocetVrcholov--;
        }
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

    public ArrayList<Vrchol<T>> preOrderPrehliadka() {
        Vrchol<T> aktualny = this.koren;
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        Stack<Vrchol<T>> zasobnik = new Stack<>();
        zasobnik.push(aktualny);

        while (!zasobnik.isEmpty()) {
            aktualny = zasobnik.pop();
            vrcholy.add(aktualny);

            if (aktualny.getPravySyn() != null) {
                zasobnik.push(aktualny.getPravySyn());
            }
            if (aktualny.getLavySyn() != null) {
                zasobnik.push(aktualny.getLavySyn());
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
