public class GPS {
    char sirka;
    double poziciaSirky;
    char dlzka;
    double poziciaDlzky;

    public GPS(char sirka, double poziciaSirky, char dlzka, double poziciaDlzky) {
        this.sirka = sirka;
        this.poziciaSirky = poziciaSirky;
        this.dlzka = dlzka;
        this.poziciaDlzky = poziciaDlzky;
    }

    public char getSirka() {
        return sirka;
    }

    public double getPoziciaSirky() {
        return poziciaSirky;
    }

    public char getDlzka() {
        return dlzka;
    }

    public double getPoziciaDlzky() {
        return poziciaDlzky;
    }
}
