import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class HashMap<K, V> {

    public static final int INITIAL_CAPACITY = 11;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The key and/or value are null");
        }
        double check = size + 1;
        if ((check / table.length) >= MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int k = key.hashCode();
        k = k % table.length;
        MapEntry<K, V> temp = table[k];
        V ret = null;
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                ret = temp.getValue();
                temp.setValue(value);
                return ret;
            } else {
                temp = temp.getNext();
            }
        }
        MapEntry<K, V> place = new MapEntry<K, V>(key, value);
        place.setNext(table[k]);
        table[k] = place;
        size++;
        return ret;
    }

    public void resizeBackingTable(int length) {
        if (length < 0 || length < size) {
            throw new IllegalArgumentException("The length is not valid");
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                MapEntry<K, V> tab = table[i];
                K key = tab.getKey();
                int k = key.hashCode();
                k = k % length;
                MapEntry<K, V> tem;
                if (temp[k] != null) {
                    tem = temp[k];
                    while (tem.getNext() != null) {
                        tem = tem.getNext();
                    }
                    tem.setNext(tab);
                } else {
                    temp[k] = tab;
                    tem = temp[k];
                }
                tab = tab.getNext();
                while (tab != null) {
                    tem.setNext(tab);
                    tab = tab.getNext();
                }
            }
        }

        table = temp;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }

        int k = key.hashCode() % table.length;

        if (table[k] == null) {
            throw new NoSuchElementException("The key does not exist");
        }
        size--;
        MapEntry<K, V> temp = table[k];
        V ret;
        if (temp.getKey().equals(key)) {
            ret = temp.getValue();
            table[k] = temp.getNext();
            return ret;
        }
        while (temp.getNext() != null) {
            if (temp.getNext().getKey().equals(key)) {
                ret = temp.getNext().getValue();
                temp.setNext(temp.getNext().getNext());
                return ret;
            }
            temp = temp.getNext();
        }
        throw new NoSuchElementException("The key does not exist");
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int k = key.hashCode() % table.length;
        if (table[k] == null) {
            throw new NoSuchElementException("The key is not in the map");
        }

        MapEntry<K, V> temp = table[k];
        V ret;
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                ret = temp.getValue();
                return ret;
            }
            temp = temp.getNext();
        }

        throw new NoSuchElementException("The key is not in the map");
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }

        int k = key.hashCode() % table.length;
        if (table[k] == null) {
            return false;
        }

        MapEntry<K, V> temp = table[k];
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    public Set<K> keySet() {
        HashSet<K> ret = new HashSet<K>();

        for (MapEntry<K, V> temp : table) {
            if (temp != null) {
                ret.add(temp.getKey());
                while (temp.getNext() != null) {
                    ret.add(temp.getNext().getKey());
                    temp = temp.getNext();
                }
            }
        }
        return ret;
    }

    public List<V> values() {
        ArrayList<V> ret = new ArrayList<V>();
        for (MapEntry<K, V> temp : table) {
            if (temp != null) {
                ret.add(temp.getValue());
                while (temp.getNext() != null) {
                    ret.add(temp.getNext().getValue());
                    temp = temp.getNext();
                }
            }
        }
        return ret;
    }

    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public MapEntry<K, V>[] getTable() {
        return table;
    }

}
