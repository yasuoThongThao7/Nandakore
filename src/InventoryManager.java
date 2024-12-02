import java.util.ArrayList;

public interface InventoryManager<T> {
    void add(T item);

    void remove(T item);

    void set(int index, T item);

    ArrayList<T> getList();
}
