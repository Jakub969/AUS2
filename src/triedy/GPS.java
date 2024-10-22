package triedy;

public class GPS {

    private char dlzka;
    private double poziciaDlzky;
    private char sirka;
    private double poziciaSirky;

    public GPS(char sirka, double poziciaSirky, char dlzka, double poziciaDlzky) {
        if (sirka != 'N' && sirka != 'S') {
            throw new IllegalArgumentException("Sirka musí byť 'N' alebo 'S'");
        }
        this.sirka = sirka;
        this.poziciaSirky = poziciaSirky;
        if (dlzka != 'E' && dlzka != 'W') {
            throw new IllegalArgumentException("Dlzka musí byť 'E' alebo 'W'");
        }
        this.dlzka = dlzka;
        this.poziciaDlzky = poziciaDlzky;
    }

    public char getSirka() {
        return sirka;
    }

    public double getPoziciaSirky() {
        if (sirka == 'N') {
            return poziciaSirky;
        } else {
            return -poziciaSirky;
        }
    }

    public char getDlzka() {
        return dlzka;
    }

    public double getPoziciaDlzky() {
        if (dlzka == 'E') {
            return poziciaDlzky;
        } else {
            return -poziciaDlzky;
        }
    }
}
