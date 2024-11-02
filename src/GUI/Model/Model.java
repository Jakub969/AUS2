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
    OperaciaGeografickychObjektov<Nehnutelnost> operacieNehnutelnosti;
    OperaciaGeografickychObjektov<Parcela> operacieParciel;
    OperaciaGeografickychObjektov<GeografickyObjekt> operacieGeografickychObjektov;

    public Model() {
        this.kdStromNehnutelnosti = new KdStrom<>(2);
        this.operacieNehnutelnosti = new OperaciaGeografickychObjektov<>(this.kdStromNehnutelnosti);
        this.kdStromParciel = new KdStrom<>(2);
        this.operacieParciel = new OperaciaGeografickychObjektov<>(this.kdStromParciel);
        this.kdStromGeografickychObjektov = new KdStrom<>(2);
        this.operacieGeografickychObjektov = new OperaciaGeografickychObjektov<>(this.kdStromGeografickychObjektov);
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
        Nehnutelnost newNehnutelnost1 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getFirst());
        Nehnutelnost newNehnutelnost2 = new Nehnutelnost(supisneCislo, popis, null, null, gpsPositions.getLast());
        ArrayList<Vrchol<Nehnutelnost>> vysledok = operacieNehnutelnosti.metodaVkladania(newNehnutelnost1, newNehnutelnost2);
        GeografickyObjekt newGeografickyObjekt1 = new GeografickyObjekt(gpsPositions.getFirst(), vysledok.getFirst().getData(), null);
        GeografickyObjekt newGeografickyObjekt2 = new GeografickyObjekt(gpsPositions.getLast(), vysledok.getLast().getData(), null);
        operacieGeografickychObjektov.metodaVkladania(newGeografickyObjekt1, newGeografickyObjekt2);
    }

    public void addParcela(int cisloParcely, String popis, List<GPS> gpsPositions) {
        Parcela newParcela = new Parcela(cisloParcely, popis, null, null, gpsPositions.get(0));

        Vrchol<Parcela> newVrchol = new Vrchol<>(newParcela);
        kdStromParciel.vloz(newVrchol);
    }

    public List<Parcela>  searchParcela(double dlzka, double sirka) {
        // Create a temporary `Parcela` for comparison purposes
        GPS searchGPS = new GPS('N', sirka, 'E', dlzka);
        Parcela searchParcela = new Parcela(0, "", null, null, searchGPS);

        // Perform the KD-tree search based on the initial GPS position
        Vrchol<Parcela> vrcholVyhladavania = new Vrchol<>(searchParcela);
        ArrayList<Vrchol<Parcela>> kluce = new ArrayList<>();
        kluce.add(vrcholVyhladavania);
        ArrayList<Vrchol<Parcela>> results = kdStromParciel.bodoveVyhladavanie(kluce);

        // Filter results by using the `porovnaj` method to check if they match within tolerance
        return results.stream()
                .map(Vrchol::getData)
                .filter(parcela -> {
                    // Use `porovnaj` for both longitude (poradieKluca 0) and latitude (poradieKluca 1)
                    return searchParcela.porovnaj(parcela, 0) == 0 &&
                            searchParcela.porovnaj(parcela, 1) == 0;
                })
                .collect(Collectors.toList());
    }
}
