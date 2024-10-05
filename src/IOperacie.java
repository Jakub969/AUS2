public interface IOperacie<T> {
    void vyhladaj(GPS GPSsuradnice);
    void pridaj(T objekt);
    void edituj(T objekt);
    void vyrad(T objekt);
}
