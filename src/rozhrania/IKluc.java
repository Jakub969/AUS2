package rozhrania;
import triedy.GPS;

public interface IKluc<T> {
    int vyhladaj(T objekt1, T objekt2, int poradieKluca);
    void pridaj(T objekt);
    void edituj(T objekt);
    void vyrad(T objekt);
    int porovnaj(T objekt, int poradieKluca);
}
