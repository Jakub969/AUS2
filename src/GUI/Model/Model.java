package GUI.Model;

import testy.OperaciaGeografickychObjektov;
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
        OperaciaGeografickychObjektov<Nehnutelnost> operacieNehnutelnosti = new OperaciaGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        OperaciaGeografickychObjektov<Parcela> operacieParciel = new OperaciaGeografickychObjektov<>(this.kdStromParciel);
    }

    public List<Nehnutelnost> searchNehnutelnosti(double dlzka, double sirka) {
        // Create a temporary `Nehnutelnost` for comparison purposes
        GPS searchGPS = new GPS('N', sirka, 'E', dlzka);
        Nehnutelnost searchNehnutelnost = new Nehnutelnost(0, "", null, null, searchGPS);

        // Perform the KD-tree search based on the initial GPS position
        Vrchol<Nehnutelnost> vrcholVyhladavania = new Vrchol<>(searchNehnutelnost);
        ArrayList<Vrchol<Nehnutelnost>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania);
        ArrayList<Vrchol<Nehnutelnost>> results = kdStromNehnutelnosti.bodoveVyhladavanie(kluce);

        // Filter results by using the `porovnaj` method to check if they match within tolerance
        return results.stream()
                .map(Vrchol::getData)
                .filter(nehnutelnost -> {
                    // Use `porovnaj` for both longitude (poradieKluca 0) and latitude (poradieKluca 1)
                    return searchNehnutelnost.porovnaj(nehnutelnost, 0) == 0 &&
                            searchNehnutelnost.porovnaj(nehnutelnost, 1) == 0;
                })
                .collect(Collectors.toList());
    }


    public void addNehnutelnost(int supisneCislo, String popis, List<GPS> gpsPositions) {
        // Modify Nehnutelnost to store all GPS positions if needed
        Nehnutelnost newNehnutelnost = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.get(0));

        Vrchol<Nehnutelnost> newVrchol = new Vrchol<>(newNehnutelnost);
        kdStromNehnutelnosti.vloz(newVrchol);
    }
}
