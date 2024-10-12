package rozhrania;
import triedy.GPS;

public interface IKluc<T> {
    void vyhladaj(GPS GPSsuradnice);
    void pridaj(T objekt);
    void edituj(GPS GPSsuradnice);
    void vyrad(GPS GPSsuradnice);
    int porovnaj(T data, int poradieKluca);
}
