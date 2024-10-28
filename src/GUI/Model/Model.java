package GUI.Model;

import US.KdStrom.KdStrom;
import triedy.Nehnutelnost;
import triedy.Parcela;

public class Model {
    private int counter;
    private KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private KdStrom<Parcela> kdStromParcel;
    //private KdStrom<UzemnaJednotka> kdStromUzemnychJednotiek;


    public Model() {
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        counter++;
    }
}
