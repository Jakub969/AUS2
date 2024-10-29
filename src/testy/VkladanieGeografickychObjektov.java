package testy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import rozhrania.IKluc;
import triedy.GPS;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.Random;

public class VkladanieGeografickychObjektov<T extends IKluc<T>> {
    private final KdStrom<T> strom;

    public VkladanieGeografickychObjektov(KdStrom<T> kdStrom) {
        this.strom = kdStrom;
    }

    public void metodaVkladania(T objekt1, T objekt2) {
        if (objekt1 instanceof Nehnutelnost dataNehnutelnost1 && objekt2 instanceof Nehnutelnost dataNehnutelnost2) {
            dataNehnutelnost1.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost2);
            dataNehnutelnost2.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
        } else if (objekt1 instanceof Parcela dataParcela1 && objekt2 instanceof Parcela dataParcela2) {
            dataParcela1.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela2);
            dataParcela2.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
        }
    }
}
