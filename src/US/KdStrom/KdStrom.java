package US.KdStrom;


import java.util.ArrayList;
import java.util.Stack;

public class KdStrom<T extends Comparable<T>> {
    private Vrchol<T> koren;
    private int hlbka;
    private final int pocetKlucov;
    private int pocetVrcholov;

    /**
     * Konštruktor triedy KdStrom
     * @param pocetKlucov počet klúčov, podľa ktorých sa bude strom deliť
     * */
    public KdStrom(int pocetKlucov) {
        this.koren = null;
        this.pocetKlucov = pocetKlucov;
        this.hlbka = 0;
        this.pocetVrcholov = 0;
    }

    /**
     * Metóda na vloženie vrchola do stromu
     * @param vrchol vrchol, ktorý chceme vložiť do stromu
     * */
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
            int porovnanie = vrchol.getKluce().porovnaj(aktualny.getKluce(), poradieKluca);

            // porovnanie klucov, ak je vrchol mensi alebo rovnaky, ideme dolava, inak doprava
            if (porovnanie == -1 || porovnanie == 0) {
                if (porovnanie == 0) {
                    //ak sa kluce zhoduju, pridame vrchol do zoznamu duplicit
                    boolean zhoda = true;
                    for (int i = 0; i < this.pocetKlucov; i++) {
                        if (vrchol.getKluce().porovnaj(aktualny.getKluce(), i) != 0) {
                            zhoda = false;
                            break;
                        }
                    }
                    if (zhoda) {
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

    /**
     * Metóda na vyhľadanie vrchola v strome
     * @param kluc kluc, podla ktoreho vyhladavame vrchol
     * @return vrchol, ktory sme vyhladali
     * */
    public Vrchol<T> vyhladaj(Vrchol<T> kluc) {
        if (this.koren == null) {
            return null;
        }
        Vrchol<T> aktualny = this.koren;
        int hlbka = 0;

        while (aktualny != null) {
            int poradieKluca = hlbka % this.pocetKlucov;
            int porovnanie = kluc.getKluce().porovnaj(aktualny.getKluce(), poradieKluca);
            boolean zhoda = true;
            for (int i = 0; i < this.pocetKlucov; i++) {
                if (kluc.getKluce().porovnaj(aktualny.getKluce(), i) != 0) {
                    zhoda = false;
                    break;
                }
            }
            if (zhoda) {
                return aktualny;
            }
            if (porovnanie <= 0 && aktualny.getLavySyn() != null) {
                aktualny = aktualny.getLavySyn();
            }
            else if (porovnanie > 0 && aktualny.getPravySyn() != null) {
                aktualny = aktualny.getPravySyn();
            } else {
                return null;
            }
            hlbka++;
        }
        return aktualny;
    }

    /**
     * Metóda na vyhľadanie všetkých vrcholov v strome podľa zoznamu klúčov
     * @param kluce zoznam klucov, podla ktorych vyhladavame vrcholy
     * @return zoznam vrcholov v strome
     * */
    public ArrayList<Vrchol<T>> bodoveVyhladavanie(ArrayList<Vrchol<T>> kluce) {
        ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
        for (Vrchol<T> vrchol : kluce) {
            vrcholy.add(vyhladaj(vrchol));
        }

        return vrcholy;
    }


    /**
     * Metóda na vyradenie vrchola zo stromu
     * @param vrchol vrchol, ktorý chceme vyradiť zo stromu
     * */
    public void vyrad(Vrchol<T> vrchol) {
        Vrchol<T> vyhladanyVrchol = this.vyhladaj(vrchol);
        if (vyhladanyVrchol != null) {
            if (vyhladanyVrchol.getLavySyn() == null && vyhladanyVrchol.getPravySyn() == null) {
                odstranVrchol(vyhladanyVrchol, vrchol, true, false);
            } else {
                if (!vyhladanyVrchol.getDuplicity().isEmpty()) {
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
            } else if (nahrada.getRodic().getPravySyn() == nahrada) {
                nahrada.getRodic().setPravySyn(null);
            }
        }

        // Nastavenie nového rodiča pre náhradu
        if (rodicVrchola != null) {
            if (rodicVrchola.getLavySyn() == vrchol) {
                rodicVrchola.setLavySyn(nahrada);
            } else if (rodicVrchola.getPravySyn() == vrchol) {
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
                znovuVlozVrcholy(pravySyn, nahrada);
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
            int aktualnePoradieKluca = getHlbkaVrchola(vrchol) % this.pocetKlucov;
            if (vrchol.getKluce().porovnaj(nahrada.getKluce(), poradieKluca) == 0) {
                vrcholyNaZnovuVlozenie.add(vrchol);
            }
            prehladajPodstrom(poradieKluca, zasobnik, vrchol, aktualnePoradieKluca);
        }

        for (Vrchol<T> vrchol : vrcholyNaZnovuVlozenie) {
            nahradVrchol(vrchol);
            odstranVrchol(vrchol, vrchol, vrchol.getLavySyn() == null && vrchol.getPravySyn() == null, true);
            vloz(vrchol);
        }
    }

    private void prehladajPodstrom(int poradieKluca, Stack<Vrchol<T>> zasobnik, Vrchol<T> vrchol, int aktualnePoradieKluca) {
        if (aktualnePoradieKluca == poradieKluca) {
            if (vrchol.getLavySyn() != null) {
                zasobnik.push(vrchol.getLavySyn());
            }
        } else {
            if (vrchol.getLavySyn() != null) {
                zasobnik.push(vrchol.getLavySyn());
            }
            if (vrchol.getPravySyn() != null) {
                zasobnik.push(vrchol.getPravySyn());
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
        Stack<Vrchol<T>> zasobnik = new Stack<>();
        zasobnik.push(vrchol);
        while (!zasobnik.isEmpty()) {
            Vrchol<T> aktualny = zasobnik.pop();
            int aktualnePoradieKluca = getHlbkaVrchola(aktualny) % this.pocetKlucov;
            if (aktualny.getKluce().porovnaj(najmensi.getKluce(), poradieKluca) <= 0) {
                najmensi = aktualny;
            }
            prehladajPodstrom(poradieKluca, zasobnik, aktualny, aktualnePoradieKluca);
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
            if (aktualny.getKluce().porovnaj(najvacsi.getKluce(), poradieKluca) >= 0) {
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
        if (vrchol.getData().compareTo(kluc.getData()) == 0) {
            mazanyVrchol = vrchol;
        } else if (!duplicity.isEmpty()) {
            for (Vrchol<T> tVrchol : duplicity) {
                if (tVrchol.getData().compareTo(kluc.getData()) == 0) {
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
                this.koren.setRodic(null);
                this.koren.setJeDuplicita(false);
                this.koren.setLavySyn(vrchol.getLavySyn());
                this.koren.setPravySyn(vrchol.getPravySyn());
                if (this.koren.getLavySyn() != null) {
                    this.koren.getLavySyn().setRodic(this.koren);
                }
                if (this.koren.getPravySyn() != null) {
                    this.koren.getPravySyn().setRodic(this.koren);
                }
            } else if (rodic.getLavySyn() == vrchol) {
                Vrchol<T> nahrada = duplicity.removeFirst();
                rodic.setLavySyn(nahrada);
                opravDuplicity(vrchol, rodic, duplicity, nahrada);
            } else if (rodic.getPravySyn() == vrchol) {
                Vrchol<T> nahrada = duplicity.removeFirst();
                rodic.setPravySyn(nahrada);
                opravDuplicity(vrchol, rodic, duplicity, nahrada);
            }
            mazanyVrchol.setRodic(null);
        } else {
            if (jeList) {
                if (rodic == null) {
                    this.koren = null;
                } else if (rodic.getLavySyn() == vrchol) {
                    rodic.setLavySyn(null);
                } else if (rodic.getPravySyn() == vrchol) {
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

    private void opravDuplicity(Vrchol<T> vrchol, Vrchol<T> rodic, ArrayList<Vrchol<T>> duplicity, Vrchol<T> nahrada) {
        nahrada.setRodic(rodic);
        nahrada.setDuplicity(duplicity);
        nahrada.setPravySyn(vrchol.getPravySyn());
        nahrada.setLavySyn(vrchol.getLavySyn());
        if (nahrada.getLavySyn() != null) {
            nahrada.getLavySyn().setRodic(nahrada);
        }
        if (nahrada.getPravySyn() != null) {
            nahrada.getPravySyn().setRodic(nahrada);
        }
        nahrada.setJeDuplicita(false);
    }

    /**
     * Metóda na in-order prehliadku stromu
     * @return zoznam vrcholov v strome
     * */
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

    /**
     * Metóda na pre-order prehliadku stromu
     * @return zoznam vrcholov v strome
     * */
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

    /**
     * Getter pre atribút koren
     * @return kořen stromu
     * */
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
