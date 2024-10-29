package GUI.Model;

import testy.VkladanieGeografickychObjektov;
import US.KdStrom.KdStrom;
import US.KdStrom.Vrchol;
import triedy.GPS;
import triedy.GeografickyObjekt;
import triedy.Nehnutelnost;
import triedy.Parcela;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private KdStrom<Nehnutelnost> kdStromNehnutelnosti;
    private KdStrom<Parcela> kdStromParciel;
    private KdStrom<GeografickyObjekt> kdStromGeografickychObjektov;

    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        VkladanieGeografickychObjektov<Nehnutelnost> vkladanieGeografickychObjektov = new VkladanieGeografickychObjektov<>(this.kdStromNehnutelnosti);
    }

    public List<Nehnutelnost> searchNehnutelnosti(double dlzka, double sirka) {
        GPS searchGPS = new GPS('N', sirka, 'E', dlzka);
        Nehnutelnost nehnutelnostVyhladavania = new Nehnutelnost(0, "", null, null, searchGPS);
        Vrchol<Nehnutelnost> vrcholVyhladavania = new Vrchol<>(nehnutelnostVyhladavania);
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania);
        ArrayList<Vrchol<Nehnutelnost>> results = kdStromNehnutelnosti.bodoveVyhladavanie(kluce);
        return results.stream()
                .map(Vrchol::getData)
                .filter(nehnutelnost -> {
                    double tolerancia = 0.000001;
                    return Math.abs(nehnutelnost.getGPSsuradnice().getPoziciaDlzky() - dlzka) < tolerancia &&
                            Math.abs(nehnutelnost.getGPSsuradnice().getPoziciaSirky() - sirka) < tolerancia;
                })
                .collect(Collectors.toList());
    }

    public void addNehnutelnost(int supisneCislo, String popis, List<GPS> gpsPositions) {
        Nehnutelnost newNehnutelnost = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.get(0));
        Vrchol<Nehnutelnost> newVrchol = new Vrchol<>(newNehnutelnost);
        kdStromNehnutelnosti.vloz(newVrchol);
    }

}
