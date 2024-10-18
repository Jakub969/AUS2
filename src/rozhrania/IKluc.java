package rozhrania;
import triedy.GPS;

public interface IKluc<T> {
    boolean vyhladaj(GPS GPSsuradnice1, GPS GPSsuradnice2);
    void pridaj(T objekt);
    void edituj(GPS GPSsuradnice);
    void vyrad(GPS GPSsuradnice);
    int porovnaj(T data, int poradieKluca);
}
