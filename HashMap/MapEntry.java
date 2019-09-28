public class MapEntry<K, V> {
    private K key;
    private V value;
    private MapEntry<K, V> next;

    public MapEntry(K key, V value) {
        this(key, value, null);
    }

    public MapEntry(K key, V value, MapEntry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public MapEntry<K, V> getNext() {
        return next;
    }

    public void setNext(MapEntry<K, V> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        } else {
            MapEntry<K, V> that = (MapEntry<K, V>) o;
            return that.getKey().equals(key) && that.getValue().equals(value);
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s", key.toString(), value.toString());
    }
}
