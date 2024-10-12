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
        kdStromNehnutelnosti = new KdStrom<>(2);
        Koren<Nehnutelnost> koren = new Koren<>(new Vrchol<>(new Nehnutelnost(3, "popis", null, new GPS('E', 3.0, 'S', 3.0))));
        kdStromNehnutelnosti.vloz(koren);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Order(1)
    @org.junit.jupiter.api.Test
    void vloz() {
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
            //assertEquals(koren, vrchol1.getRodic());
            assertEquals(i, vrchol1.getData().getSupisneCislo());
        }
    }

    @Order(2)
    @org.junit.jupiter.api.Test
    void inOrderPrehliadka() {
        int[] supisneCisla = {0, 1, 2, 3, 3, 4, 5};
        for (int i = 0; i <= 5; i++) {
            Vrchol<Nehnutelnost> vrchol1 = new Vrchol<>(new Nehnutelnost(i, "popis", null, new GPS('E', i, 'S', i)));
            kdStromNehnutelnosti.vloz(vrchol1);
        }
        ArrayList<Vrchol<Nehnutelnost>> vrcholy = kdStromNehnutelnosti.inOrderPrehliadka();
        for (int i = 0; i <= 6; i++) {

            assertEquals(supisneCisla[i], vrcholy.get(i).getData().getSupisneCislo());
        }
    }

    @org.junit.jupiter.api.Test
    void getKoren() {
    }

    @org.junit.jupiter.api.Test
    void getHlbka() {
    }

    @org.junit.jupiter.api.Test
    void getPocetVrcholov() {
    }
}