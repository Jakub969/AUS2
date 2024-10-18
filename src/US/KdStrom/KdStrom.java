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

    public ArrayList<Vrchol<T>> vyhladaj(GPS kluc1, GPS kluc2) {
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

            boolean patriDoObdlznika = aktualny.getData().vyhladaj(kluc1, kluc2);
            if (patriDoObdlznika) {
                vrcholy.add(aktualny);
            }

            int dimenzia = hlbka % 2; // 0 pre šírku, 1 pre dĺžku
            if (dimenzia == 0) {
                if (aktualny.getData() instanceof Nehnutelnost instanciaNehnutelnosti) {
                    if (kluc1.getPoziciaSirky() <= instanciaNehnutelnosti.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getLavySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                    if (kluc2.getPoziciaSirky() >= instanciaNehnutelnosti.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getPravySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                } else if (aktualny.getData() instanceof Parcela instanciaParcely) {
                    if (kluc1.getPoziciaSirky() <= instanciaParcely.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getLavySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                    if (kluc2.getPoziciaSirky() >= instanciaParcely.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getPravySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                }
            } else {
                if (aktualny.getData() instanceof Nehnutelnost instanciaNehnutelnosti) {
                    if (kluc1.getPoziciaDlzky() <= instanciaNehnutelnosti.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getLavySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                    if (kluc2.getPoziciaDlzky() >= instanciaNehnutelnosti.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getPravySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                } else if (aktualny.getData() instanceof Parcela instanciaParcely) {
                    if (kluc1.getPoziciaDlzky() <= instanciaParcely.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getLavySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                    if (kluc2.getPoziciaDlzky() >= instanciaParcely.getGPSsuradnice().getPoziciaSirky()) {
                        stack.push(aktualny.getPravySyn());
                        hlbkaStack.push(hlbka + 1);
                    }
                }
            }
        }

        return vrcholy;
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
