package triedy;

import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import rozhrania.IKluc;

import java.util.ArrayList;

public class OperacieGeografickychObjektov<T extends IKluc<T>> {
    private final KdStrom<T> strom;

    public OperacieGeografickychObjektov(KdStrom<T> kdStrom) {
        this.strom = kdStrom;
    }

    public ArrayList<Vrchol<T>> metodaVkladania(T objekt1, T objekt2) {
        if (objekt1 instanceof Nehnutelnost dataNehnutelnost1 && objekt2 instanceof Nehnutelnost dataNehnutelnost2) {
            dataNehnutelnost1.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost2);
            dataNehnutelnost2.setReferenciaNaRovnakuNehnutelnostSInymiGPS(dataNehnutelnost1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
            vrcholy.add(vrchol1);
            vrcholy.add(vrchol2);
            return vrcholy;
        } else if (objekt1 instanceof Parcela dataParcela1 && objekt2 instanceof Parcela dataParcela2) {
            dataParcela1.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela2);
            dataParcela2.setReferenciaNaRovnakuParceluSInymiGPS(dataParcela1);
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
            vrcholy.add(vrchol1);
            vrcholy.add(vrchol2);
            return vrcholy;
        } else if (objekt1 instanceof GeografickyObjekt dataGeografickyObjekt1 && objekt2 instanceof GeografickyObjekt dataGeografickyObjekt2) {
            Vrchol<T> vrchol1 = new Vrchol<>(objekt1);
            this.strom.vloz(vrchol1);
            Vrchol<T> vrchol2 = new Vrchol<>(objekt2);
            this.strom.vloz(vrchol2);
            ArrayList<Vrchol<T>> vrcholy = new ArrayList<>();
            vrcholy.add(vrchol1);
            vrcholy.add(vrchol2);
            return vrcholy;
        } else {
            return null;
        }
    }
}
