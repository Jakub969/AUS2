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
        if(this.koren == null) {
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
            int patriDoObdlznika = aktualny.getData().vyhladaj(kluc1, kluc2, poradieKluca);
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
                if (vrchol1.getLavySyn() == null && vrchol1.getPravySyn() == null) {
                    odstranList(vrchol1);
                } else {
                    nahradVrchol(vrchol1);
                }
                pocetVrcholov--;
            }
            return true;
        }
    }

    private void nahradVrchol(Vrchol<T> vrchol) {
        if (vrchol == null) {
            throw new IllegalArgumentException("Vrchol cannot be null");
        }
        int poradieKluca = getHlbkaVrchola(vrchol) % this.pocetKlucov;
        Vrchol<T> nahrada = null;
        if (poradieKluca == 0) {
            nahrada = najdiNajvacsiVrchol(vrchol.getLavySyn(), poradieKluca);
        } else {
            nahrada = najdiNajmensiVrchol(vrchol.getPravySyn(), poradieKluca);
        }
        if (nahrada != null) {
            odstranList(vrchol);
            vloz(nahrada);
        }
    }

    private Vrchol<T> najdiNajmensiVrchol(Vrchol<T> vrchol, int poradieKluca) {
        if (vrchol == null) {
            throw new IllegalArgumentException("Vrchol nemože byť null");
        }
        while (vrchol.getLavySyn() != null) {
            vrchol = vrchol.getLavySyn();
        }
        return vrchol;
    }

    private Vrchol<T> najdiNajvacsiVrchol(Vrchol<T> vrchol, int poradieKluca) {
        if (vrchol == null) {
            throw new IllegalArgumentException("Vrchol nemože byť null");
        }
        while (vrchol.getPravySyn() != null) {
            vrchol = vrchol.getPravySyn();
        }
        return vrchol;
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
