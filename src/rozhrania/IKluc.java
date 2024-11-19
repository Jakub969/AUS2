package rozhrania;
import triedy.GPS;

public interface IKluc<T extends Comparable<T>> {
    int porovnaj(T objekt, int poradieKluca);
}
