package triedy;

import rozhrania.IKluc;

import java.util.ArrayList;

public class Nehnutelnost implements IKluc<Nehnutelnost> {
    private int supisneCislo;
    private String popis;
    private ArrayList<Parcela> zoznamParciel;
    private Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS;
    private final GPS GPSsuradnice1;
    private final GPS GPSsuradnice2;
    private final String uuid;

    /**
     * Konstruktor triedy Nehnutelnost
     * @param supisneCislo supisne cislo nehnutelnosti
     * @param popis popis nehnutelnosti
     * @param zoznamParcel zoznam parciel na nehnutelnosti
     * @param referenciaNaRovnakuNehnutelnostSInymiGPS referencia na rovnaku nehnutelnost s inymi GPS
     * @param GPSsuradnice1 GPS suradnice nehnutelnosti
     * @param GPSsuradnice2 GPS suradnice nehnutelnosti
     */
    public Nehnutelnost(int supisneCislo, String popis, ArrayList<Parcela> zoznamParcel, Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS, GPS GPSsuradnice1, GPS GPSsuradnice2) {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.zoznamParciel = zoznamParcel;
        if (zoznamParcel == null) {
            this.zoznamParciel = new ArrayList<>();
        }
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
        this.GPSsuradnice1 = GPSsuradnice1;
        this.GPSsuradnice2 = GPSsuradnice2;
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public int getSupisneCislo() {
        return supisneCislo;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Parcela> getZoznamParciel() {
        return zoznamParciel;
    }

    public GPS getGPSsuradnice1() {
        return GPSsuradnice1;
    }

    public GPS getGPSsuradnice2() {
        return GPSsuradnice2;
    }

    public Nehnutelnost getReferenciaNaRovnakuNehnutelnostSInymiGPS() {
        return referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    public void setSupisneCislo(int supisneCislo) {
        this.supisneCislo = supisneCislo;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setReferenciaNaRovnakuNehnutelnostSInymiGPS(Nehnutelnost referenciaNaRovnakuNehnutelnostSInymiGPS) {
        this.referenciaNaRovnakuNehnutelnostSInymiGPS = referenciaNaRovnakuNehnutelnostSInymiGPS;
    }

    public void addParcela(Parcela parcela) {
        this.zoznamParciel.add(parcela);
    }

    public void removeParcela(Parcela parcela) {
        this.zoznamParciel.remove(parcela);
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * Metoda porovnania nehnutelnosti
     * @param objekt objekt, s ktorym sa ma porovnat
     * @param poradieKluca poradie kluca, podla ktoreho sa ma porovnat
     * @return int - vysledok porovnania 0 ak su rovnake, -1 ak je mensie, 1 ak je vacsie
     */
    @Override
    public int porovnaj(Nehnutelnost objekt, int poradieKluca) {
        double tolerancia = 0.000001;
        if (objekt instanceof Nehnutelnost dataNehnutelnost) {
            if (poradieKluca == 0) { //TODO opravenie aby jedna trieda mala 2 GPS suradnice
                if (Math.abs(this.GPSsuradnice.getPoziciaDlzky() - dataNehnutelnost.getGPSsuradnice1().getPoziciaDlzky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaDlzky(), dataNehnutelnost.getGPSsuradnice1().getPoziciaDlzky());
            } else {
                if (Math.abs(this.GPSsuradnice.getPoziciaSirky() - dataNehnutelnost.getGPSsuradnice1().getPoziciaSirky()) <= tolerancia) {
                    return 0;
                }
                return Double.compare(this.GPSsuradnice.getPoziciaSirky(), dataNehnutelnost.getGPSsuradnice1().getPoziciaSirky());
            }
        } else {
            return -2;
        }
    }

    /**
     * Metoda na zistenie, ci sa dve nehnutelnosti zhoduju podla UUID
     * @param objekt nehnutelnost, s ktorou sa ma porovnat
     * @return boolean - true, ak sa zhoduju, inak false
     */
    @Override
    public boolean zhodneUuid(Nehnutelnost objekt) {
        return this.uuid.equals(objekt.getUuid());
    }

    @Override
    public String toString() {
        return "Nehnutelnost{" +
                "supisneCislo=" + supisneCislo +
                ", popis='" + popis + '\'' +
                ", GPSsuradnice=" + GPSsuradnice.getDlzka() + ": " + GPSsuradnice.getPoziciaDlzky() +  " | " + GPSsuradnice.getSirka() + ": " + GPSsuradnice.getPoziciaSirky() +
                '}';
    }
}
