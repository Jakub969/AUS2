package US.KdStrom;

import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorOperacii<T extends IKluc<T>> {
    private final int pocetVlozeni;
    private final int pocetMazani;
    private final int pocetHladani;
    private final int maxRozsah;
    private ArrayList<Vrchol<T>> zoznamVlozenychVrcholov;
    private final KdStrom<T> strom;
    private final Class<T> instanciaTriedy;

    public GeneratorOperacii(int pocetVlozeni, int pocetMazani, int pocetHladani, int maxRozsah, Class<T> trieda) {
        this.pocetVlozeni = pocetVlozeni;
        this.pocetMazani = pocetMazani;
        this.pocetHladani = pocetHladani;
        this.maxRozsah = maxRozsah;
        this.strom = new KdStrom<>(2);
        this.instanciaTriedy = trieda;
        this.zoznamVlozenychVrcholov = new ArrayList<>(this.pocetVlozeni * 2);
        metodaVkladania();
        metodaMazania();
        metodaVyhladavania();
    }

    private void metodaVkladania() {
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < this.pocetVlozeni; i++) {
            if (i % 100 == 0) {
                random.setSeed(System.nanoTime());
            }
            double x1 = random.nextDouble() * this.maxRozsah;
            double y1 = random.nextDouble() * this.maxRozsah;
            double x2 = random.nextDouble() * this.maxRozsah;
            double y2 = random.nextDouble() * this.maxRozsah;
            GPS gps1 = new GPS('N', x1, 'E', y1);
            GPS gps2 = new GPS('N', x2, 'E', y2);
            T data1;
            T data2;
            try {
                if (instanciaTriedy == Nehnutelnost.class) {
                    Nehnutelnost nehnutelnost1 = new Nehnutelnost(i, "popis", null, null, gps1);
                    Nehnutelnost nehnutelnost2 = new Nehnutelnost(i, "popis", null, nehnutelnost1, gps2);
                    nehnutelnost1.setReferenciaNaRovnakuNehnutelnostSInymiGPS(nehnutelnost2);
                    data1 = instanciaTriedy.cast(nehnutelnost1);
                    data2 = instanciaTriedy.cast(nehnutelnost2);
                } else if (instanciaTriedy == Parcela.class) {
                    Parcela parcela1 = new Parcela(i, "popis", null, null, gps1);
                    Parcela parcela2 = new Parcela(i, "popis", null, parcela1, gps2);
                    parcela1.setReferenciaNaRovnakuParceluSInymiGPS(parcela2);
                    data1 = instanciaTriedy.cast(parcela1);
                    data2 = instanciaTriedy.cast(parcela2);
                } else {
                    throw new IllegalArgumentException("Nepodporovaný typ triedy");
                }
                Vrchol<T> vrchol1 = new Vrchol<>(data1);
                Vrchol<T> vrchol2 = new Vrchol<>(data2);
                if (false) {
                    System.out.println("GPS suradnice: " + gps1.getPoziciaDlzky() + " " + gps1.getPoziciaSirky() + " a " + gps2.getPoziciaDlzky() + " " + gps2.getPoziciaSirky());
                }
                this.strom.vloz(vrchol1);
                this.strom.vloz(vrchol2);
                this.zoznamVlozenychVrcholov.add(vrchol1);
                this.zoznamVlozenychVrcholov.add(vrchol2);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Error pri pretypovani", e);
            }
        }
    }

    public void metodaMazania() {
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < this.pocetMazani; i++) {
            if (i % 100 == 0) {
                random.setSeed(System.nanoTime());
            }
            int index = random.nextInt(this.zoznamVlozenychVrcholov.size());
            Vrchol<T> vrchol = this.zoznamVlozenychVrcholov.get(index);
            this.strom.vyrad(vrchol);
            this.zoznamVlozenychVrcholov.remove(index);
            System.out.println("Vrchol s kľúčom: " + vrchol.getData().toString() + " bol vymazaný");
        }
    }

    private void metodaVyhladavania() {
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < this.pocetHladani; i++) {
            if (i % 100 == 0) {
                random.setSeed(System.nanoTime());
            }
            int index = random.nextInt(this.zoznamVlozenychVrcholov.size());
            Vrchol<T> vrchol1 = this.zoznamVlozenychVrcholov.get(index);
            this.zoznamVlozenychVrcholov.remove(index);
            ArrayList<Vrchol<T>> vysledok;
            if (vrchol1.getData() instanceof Nehnutelnost nehnutelnost) {
                Nehnutelnost nehnutelnost1 = nehnutelnost.getReferenciaNaRovnakuNehnutelnostSInymiGPS();
                Vrchol<Nehnutelnost> vrchol2;
                vrchol2 = new Vrchol<>(nehnutelnost1);
                vysledok = this.strom.vyhladaj(vrchol1.getData(), (T) vrchol2.getData());
                System.out.println("vyhladávam v rozsahu: x(" + nehnutelnost.getGPSsuradnice().getPoziciaDlzky() + " ; " + nehnutelnost1.getGPSsuradnice().getPoziciaDlzky() + ") a y(" + nehnutelnost.getGPSsuradnice().getPoziciaSirky() + " ; " + nehnutelnost1.getGPSsuradnice().getPoziciaSirky() + ")");
            } else if (vrchol1.getData() instanceof Parcela parcela) {
                Parcela parcela1 = parcela.getReferenciaNaRovnakuParceluSInymiGPS();
                Vrchol<Parcela> vrchol2;
                vrchol2 = new Vrchol<>(parcela1);
                vysledok = this.strom.vyhladaj(vrchol1.getData(), (T) vrchol2.getData());
            } else {
                throw new IllegalArgumentException("Nepodporovaný typ triedy");
            }
            if (0 == 0) {
                int pocetNajdenychVrcholov = vysledok.size();
                for (Vrchol<T> tVrchol : vysledok) {
                    System.out.println("Vrchol s kľúčom: " + tVrchol.getData().toString() + " bol nájdený");
                }
                System.out.println("Počet nájdených vrcholov: " + pocetNajdenychVrcholov);
                System.out.println("----------------------------------------------------------------------");
            }
        }
    }

    public void vypisVrcholy() {
        ArrayList<Vrchol<T>> vrcholy = this.strom.inOrderPrehliadka();
        for (Vrchol<T> vrchol : vrcholy) {
            T data = vrchol.getData();
            if (data instanceof Nehnutelnost nehnutelnost) {
                if (vrchol.getRodic() == null) {
                    System.out.println("Vrchol zo súpisným číslom " + nehnutelnost.getSupisneCislo() + " je koreňom: " + vrchol.getData().toString());
                } else {
                    System.out.println("Vrchol zo supisným číslom: " + nehnutelnost.getSupisneCislo() + " má rodiča: " + vrchol.getRodic().getData().toString());
                }
                if (vrchol.getPravySyn() != null) {
                    System.out.println("Pravý syn: " + vrchol.getPravySyn().getData().toString());
                }
                if (vrchol.getLavySyn() != null) {
                    System.out.println("Ľavý syn: " + vrchol.getLavySyn().getData().toString());
                }
            } else if (data instanceof Parcela parcela) {
                if (vrchol.getRodic() == null) {
                    System.out.println("Vrchol z číslom parcely " + parcela.getCisloParcely() + " je koreňom " + vrchol.getData().toString());
                } else {
                    System.out.println("Vrchol z číslom parcely: " + parcela.getCisloParcely() + " má rodiča: " + vrchol.getRodic().getData().toString());
                }
                if (vrchol.getPravySyn() != null) {
                    System.out.println("Pravý syn: " + vrchol.getPravySyn().getData().toString());
                }
                if (vrchol.getLavySyn() != null) {
                    System.out.println("Ľavý syn: " + vrchol.getLavySyn().getData().toString());
                }
            } else {
                throw new IllegalArgumentException("Nepodporovaný typ");
            }
        }
    }

    public void vypisVrcholy2() {
        ArrayList<Vrchol<T>> vrcholy = this.strom.inOrderPrehliadka();
        System.out.println("Počet vrcholov: " + vrcholy.size());
        for (Vrchol<T> vrchol : vrcholy) {
            T data = vrchol.getData();
            if (data instanceof Nehnutelnost nehnutelnost) {
                System.out.printf("Vrchol: %s, ", nehnutelnost.getSupisneCislo());
            }
        }
    }

    public int getHlbka() {
        return this.strom.getHlbka();
    }

    public int getPocetVrcholov() {
        return this.strom.getPocetVrcholov();
    }
}
