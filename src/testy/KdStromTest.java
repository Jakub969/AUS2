package testy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import triedy.GPS;
import triedy.Nehnutelnost;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KdStromTest {

    private KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private Vrchol<Nehnutelnost> koren;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Order(1)
    @org.junit.jupiter.api.Test
    void vloz() {
        this.kdStromNehnutelnosti.vloz(new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3))));
        int[] supisneCisloRodica = {3, 0, 1, 2, 3, 4};
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('N', i, 'E', i)));
            this.kdStromNehnutelnosti.vloz(vrchol1);
            if (vrchol1.getRodic() != null) {
                assertEquals(supisneCisloRodica[i], vrchol1.getRodic().getData().getSupisneCislo());
            }
        }
    }

    @Order(2)
    @org.junit.jupiter.api.Test
    void inOrderPrehliadka() {
        this.kdStromNehnutelnosti.vloz(new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3))));
        int[] supisneCisla = {0, 1, 2, 3, 4, 5};
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null ,new GPS('N', i, 'E', i)));
            this.kdStromNehnutelnosti.vloz(vrchol1);
        }
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = this.kdStromNehnutelnosti.inOrderPrehliadka();
        for (int i = 0; i <= 5; i++) {

            assertEquals(supisneCisla[i], vrcholy.get(i).getData().getSupisneCislo());
        }
    }

    @org.junit.jupiter.api.Test
    void getKoren() {
        this.koren = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3)));
        this.kdStromNehnutelnosti.vloz(this.koren);
        assertEquals(this.koren, this.kdStromNehnutelnosti.getKoren());
    }

    @org.junit.jupiter.api.Test
    void getHlbka() {
        this.kdStromNehnutelnosti.vloz(new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3))));
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('N', i, 'E', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        assertEquals(3, kdStromNehnutelnosti.getHlbka());
    }

    @org.junit.jupiter.api.Test
    void getPocetVrcholov() {
        this.kdStromNehnutelnosti.vloz(new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3))));
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('N', i, 'E', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        assertEquals(6, kdStromNehnutelnosti.getPocetVrcholov());
    }

    @org.junit.jupiter.api.Test
    void vyhladaj() {
        /*
        * GPS suradnice: 2.0 3.0 a 5.0 3.0
          GPS suradnice: 0.0 4.0 a 7.0 8.0
          GPS suradnice: 6.0 6.0 a 6.0 8.0
          GPS suradnice: 7.0 2.0 a 5.0 7.0
          GPS suradnice: 4.0 3.0 a 1.0 3.0
          GPS suradnice: 1.0 9.0 a 3.0 9.0
          GPS suradnice: 4.0 4.0 a 4.0 5.0
          GPS suradnice: 8.0 3.0 a 2.0 8.0
          GPS suradnice: 6.0 7.0 a 2.0 8.0
          GPS suradnice: 4.0 9.0 a 8.0 1.0
        * */
        Vrchol<Nehnutelnost> vrchol00 = new Vrchol<>(new Nehnutelnost(0, "popis", null, null, new GPS('N', 3.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol01 = new Vrchol<>(new Nehnutelnost(0, "popis", null, null, new GPS('N', 3.0, 'E', 5.0)));
        Vrchol<Nehnutelnost> vrchol10 = new Vrchol<>(new Nehnutelnost(1, "popis", null, null, new GPS('N', 4.0, 'E', 0.0)));
        Vrchol<Nehnutelnost> vrchol11 = new Vrchol<>(new Nehnutelnost(1, "popis", null, null, new GPS('N', 8.0, 'E', 7.0)));
        Vrchol<Nehnutelnost> vrchol20 = new Vrchol<>(new Nehnutelnost(2, "popis", null, null, new GPS('N', 6.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol21 = new Vrchol<>(new Nehnutelnost(2, "popis", null, null, new GPS('N', 8.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol30 = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 2.0, 'E', 7.0)));
        Vrchol<Nehnutelnost> vrchol31 = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 7.0, 'E', 5.0)));
        Vrchol<Nehnutelnost> vrchol40 = new Vrchol<>(new Nehnutelnost(4, "popis", null, null, new GPS('N', 3.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol41 = new Vrchol<>(new Nehnutelnost(4, "popis", null, null, new GPS('N', 3.0, 'E', 1.0)));
        Vrchol<Nehnutelnost> vrchol50 = new Vrchol<>(new Nehnutelnost(5, "popis", null, null, new GPS('N', 9.0, 'E', 1.0)));
        Vrchol<Nehnutelnost> vrchol51 = new Vrchol<>(new Nehnutelnost(5, "popis", null, null, new GPS('N', 9.0, 'E', 3.0)));
        Vrchol<Nehnutelnost> vrchol60 = new Vrchol<>(new Nehnutelnost(6, "popis", null, null, new GPS('N', 4.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol61 = new Vrchol<>(new Nehnutelnost(6, "popis", null, null, new GPS('N', 5.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol70 = new Vrchol<>(new Nehnutelnost(7, "popis", null, null, new GPS('N', 3.0, 'E', 8.0)));
        Vrchol<Nehnutelnost> vrchol71 = new Vrchol<>(new Nehnutelnost(7, "popis", null, null, new GPS('N', 8.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol80 = new Vrchol<>(new Nehnutelnost(8, "popis", null, null, new GPS('N', 7.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol81 = new Vrchol<>(new Nehnutelnost(8, "popis", null, null, new GPS('N', 8.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol90 = new Vrchol<>(new Nehnutelnost(9, "popis", null, null, new GPS('N', 9.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol91 = new Vrchol<>(new Nehnutelnost(9, "popis", null, null, new GPS('N', 1.0, 'E', 8.0)));
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = new ArrayList<>();
        vrcholy.add(vrchol00);
        vrcholy.add(vrchol01);
        vrcholy.add(vrchol10);
        vrcholy.add(vrchol11);
        vrcholy.add(vrchol20);
        vrcholy.add(vrchol21);
        vrcholy.add(vrchol30);
        vrcholy.add(vrchol31);
        vrcholy.add(vrchol40);
        vrcholy.add(vrchol41);
        vrcholy.add(vrchol50);
        vrcholy.add(vrchol51);
        vrcholy.add(vrchol60);
        vrcholy.add(vrchol61);
        vrcholy.add(vrchol70);
        vrcholy.add(vrchol71);
        vrcholy.add(vrchol80);
        vrcholy.add(vrchol81);
        vrcholy.add(vrchol90);
        vrcholy.add(vrchol91);
        for (int i = 0; i < 20; i++) {
            this.kdStromNehnutelnosti.vloz(vrcholy.get(i));
        }
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrchol40);
        kluce.add(vrchol41);
        kluce.add(vrchol30);
        kluce.add(vrchol31);
        kluce.add(vrchol60);
        kluce.add(vrchol61);
        kluce.add(vrchol50);
        kluce.add(vrchol51);
        kluce.add(vrchol90);
        kluce.add(vrchol91);
        int[] pocetVrcholov = {2,2,2,2,2};
        for (int i = 0; i < 5; i++) {
            int index = i * 2; // Každý pár je na indexoch 0-1, 2-3, 4-5, atď.
            ArrayList<Vrchol<Nehnutelnost>> kluceVyhladavania = new ArrayList<>();
            kluceVyhladavania.add(kluce.get(index));
            kluceVyhladavania.add(kluce.get(index + 1));
            ArrayList<Vrchol<Nehnutelnost>> vysledok = this.kdStromNehnutelnosti.bodoveVyhladavanie(kluceVyhladavania);

            System.out.println("Vyhľadávam v rozsahu: x(" + kluce.get(index).getData().getGPSsuradnice().getPoziciaDlzky() + " ; " + kluce.get(index + 1).getData().getGPSsuradnice().getPoziciaDlzky() + ") a y(" + kluce.get(index).getData().getGPSsuradnice().getPoziciaSirky() + " ; " + kluce.get(index + 1).getData().getGPSsuradnice().getPoziciaSirky() + ")");

            for (Vrchol<Nehnutelnost> vrchol : vysledok) {
                System.out.println("Vrchol: " + vrchol.getData().getSupisneCislo() + " ; " + vrchol.getData().toString() + " bol nájdený");
            }

            System.out.println("Počet nájdených vrcholov: " + vysledok.size());
            assertEquals(pocetVrcholov[i], vysledok.size());
        }
    }

    @org.junit.jupiter.api.Test
    void vyrad() {
        Vrchol<Nehnutelnost> vrchol00 = new Vrchol<>(new Nehnutelnost(0, "popis", null, null, new GPS('N', 3.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol01 = new Vrchol<>(new Nehnutelnost(0, "popis", null, null, new GPS('N', 3.0, 'E', 5.0)));
        Vrchol<Nehnutelnost> vrchol10 = new Vrchol<>(new Nehnutelnost(1, "popis", null, null, new GPS('N', 4.0, 'E', 0.0)));
        Vrchol<Nehnutelnost> vrchol11 = new Vrchol<>(new Nehnutelnost(1, "popis", null, null, new GPS('N', 8.0, 'E', 7.0)));
        Vrchol<Nehnutelnost> vrchol20 = new Vrchol<>(new Nehnutelnost(2, "popis", null, null, new GPS('N', 6.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol21 = new Vrchol<>(new Nehnutelnost(2, "popis", null, null, new GPS('N', 8.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol30 = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 2.0, 'E', 7.0)));
        Vrchol<Nehnutelnost> vrchol31 = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 7.0, 'E', 5.0)));
        Vrchol<Nehnutelnost> vrchol40 = new Vrchol<>(new Nehnutelnost(4, "popis", null, null, new GPS('N', 3.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol41 = new Vrchol<>(new Nehnutelnost(4, "popis", null, null, new GPS('N', 3.0, 'E', 1.0)));
        Vrchol<Nehnutelnost> vrchol50 = new Vrchol<>(new Nehnutelnost(5, "popis", null, null, new GPS('N', 9.0, 'E', 1.0)));
        Vrchol<Nehnutelnost> vrchol51 = new Vrchol<>(new Nehnutelnost(5, "popis", null, null, new GPS('N', 9.0, 'E', 3.0)));
        Vrchol<Nehnutelnost> vrchol60 = new Vrchol<>(new Nehnutelnost(6, "popis", null, null, new GPS('N', 4.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol61 = new Vrchol<>(new Nehnutelnost(6, "popis", null, null, new GPS('N', 5.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol70 = new Vrchol<>(new Nehnutelnost(7, "popis", null, null, new GPS('N', 3.0, 'E', 8.0)));
        Vrchol<Nehnutelnost> vrchol71 = new Vrchol<>(new Nehnutelnost(7, "popis", null, null, new GPS('N', 8.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol80 = new Vrchol<>(new Nehnutelnost(8, "popis", null, null, new GPS('N', 7.0, 'E', 6.0)));
        Vrchol<Nehnutelnost> vrchol81 = new Vrchol<>(new Nehnutelnost(8, "popis", null, null, new GPS('N', 8.0, 'E', 2.0)));
        Vrchol<Nehnutelnost> vrchol90 = new Vrchol<>(new Nehnutelnost(9, "popis", null, null, new GPS('N', 9.0, 'E', 4.0)));
        Vrchol<Nehnutelnost> vrchol91 = new Vrchol<>(new Nehnutelnost(9, "popis", null, null, new GPS('N', 1.0, 'E', 8.0)));
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = new ArrayList<>();
        vrcholy.add(vrchol00);
        vrcholy.add(vrchol01);
        vrcholy.add(vrchol10);
        vrcholy.add(vrchol11);
        vrcholy.add(vrchol20);
        vrcholy.add(vrchol21);
        vrcholy.add(vrchol30);
        vrcholy.add(vrchol31);
        vrcholy.add(vrchol40);
        vrcholy.add(vrchol41);
        vrcholy.add(vrchol50);
        vrcholy.add(vrchol51);
        vrcholy.add(vrchol60);
        vrcholy.add(vrchol61);
        vrcholy.add(vrchol70);
        vrcholy.add(vrchol71);
        vrcholy.add(vrchol80);
        vrcholy.add(vrchol81);
        vrcholy.add(vrchol90);
        vrcholy.add(vrchol91);
        for (int i = 0; i < 20; i++) {
            this.kdStromNehnutelnosti.vloz(vrcholy.get(i));
        }
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrchol40);
        kluce.add(vrchol41);
        kluce.add(vrchol30);
        kluce.add(vrchol31);
        kluce.add(vrchol60);
        kluce.add(vrchol61);
        kluce.add(vrchol50);
        kluce.add(vrchol51);
        kluce.add(vrchol90);
        kluce.add(vrchol91);
        kluce.add(vrchol00);
        kluce.add(vrchol01);
        kluce.add(vrchol10);
        kluce.add(vrchol11);
        kluce.add(vrchol20);
        kluce.add(vrchol21);
        kluce.add(vrchol70);
        kluce.add(vrchol71);
        kluce.add(vrchol80);
        int[] pocetVrcholov = {14,11,5,2,1};
        int[] pocetMazanychVrcholov = {5, 3, 6, 3, 2};
        int zaciatok = 0;
        for (int i = 0; i < 5; i++) {
            int koniec = zaciatok + pocetMazanychVrcholov[i];
            for (int j = zaciatok; j < koniec; j++) {
                System.out.println("Mazaný vrchol: " + kluce.get(j).getData().getSupisneCislo() + " ; " + kluce.get(j).getData().toString());
                this.kdStromNehnutelnosti.vyrad(kluce.get(j));
                System.out.println("Počet vrcholov po mazaní: " + this.kdStromNehnutelnosti.getPocetVrcholov());
                ArrayList<Vrchol<Nehnutelnost>> vysledok = this.kdStromNehnutelnosti.inOrderPrehliadka();
                System.out.println("InOrder prehliadka po mazaní: " + vysledok.size());
                if (false) { //j == 9
                    for (Vrchol<Nehnutelnost> nehnutelnostVrchol : vysledok) {
                        System.out.println(nehnutelnostVrchol.getData().toString());
                        if (nehnutelnostVrchol.getLavySyn() != null) {
                            System.out.println("Lavy syn: " + nehnutelnostVrchol.getLavySyn().getData().toString());
                        }
                        if (nehnutelnostVrchol.getPravySyn() != null) {
                            System.out.println("Pravy syn: " + nehnutelnostVrchol.getPravySyn().getData().toString());
                        }
                    }
                }
                for (Vrchol<Nehnutelnost> nehnutelnostVrchol : vysledok) {
                    System.out.print("Vrchol: " + nehnutelnostVrchol.getData().getSupisneCislo() + ", ");
                }
                System.out.println();
            }
            zaciatok += pocetMazanychVrcholov[i];
            ArrayList<Vrchol<Nehnutelnost>> vysledok = this.kdStromNehnutelnosti.inOrderPrehliadka();
            assertEquals(pocetVrcholov[i], vysledok.size());
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------------");
        }
    }
}