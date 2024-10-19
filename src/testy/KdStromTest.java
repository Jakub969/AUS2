package testy;

import US.KdStrom.KdStrom;
import US.KdStrom.Koren;
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
        int[] supisneCisloRodica = {3, 0, 1, 2, 3 ,4};
        this.kdStromNehnutelnosti.vloz(new Vrchol<>(new Nehnutelnost(3, "popis", null,null, new GPS('N', 3, 'E', 3))));
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('N', i, 'E', i)));
            this.kdStromNehnutelnosti.vloz(vrchol1);
            assertEquals(supisneCisloRodica[i], vrchol1.getRodic().getData().getSupisneCislo());
        }
    }

    @Order(2)
    @org.junit.jupiter.api.Test
    void inOrderPrehliadka() {
        int[] supisneCisla = {0, 1, 2, 3, 3, 4, 5};
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null ,new GPS('N', i, 'E', i)));
            this.kdStromNehnutelnosti.vloz(vrchol1);
        }
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = this.kdStromNehnutelnosti.inOrderPrehliadka();
        for (int i = 0; i <= 6; i++) {

            assertEquals(supisneCisla[i], vrcholy.get(i).getData().getSupisneCislo());
        }
    }

    @org.junit.jupiter.api.Test
    void getKoren() {
        Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(3, "popis", null, null, new GPS('N', 3, 'E', 3)));
        this.kdStromNehnutelnosti.vloz(vrchol1);
        assertEquals(vrchol1.getData().getSupisneCislo(), this.kdStromNehnutelnosti.getKoren().getKoren().getData().getSupisneCislo());
    }

    @org.junit.jupiter.api.Test
    void getHlbka() {
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        assertEquals(4, kdStromNehnutelnosti.getHlbka());
    }

    @org.junit.jupiter.api.Test
    void getPocetVrcholov() {
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        assertEquals(7, kdStromNehnutelnosti.getPocetVrcholov());
    }
}