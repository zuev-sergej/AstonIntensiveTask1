public class CustomHashMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.7;
    private final double loadFactor;

    private Node<K, V>[] table;
    private int size;

    public CustomHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Вместимость должна быть больше 0.");
        }
        if (loadFactor <= 0 || Double.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Коэффициент заполнения должен быть положительным числом.");
        }

        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity = capacity * 2;
        }

        this.loadFactor = loadFactor;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.size = 0;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V put(K key, V value) {
        int index = getBucketIndex(hash(key), table.length);
        Node<K, V> head = table[index];

        for (Node<K, V> e = head; e != null; e = e.next) {
            if ((e.key == null && key == null) || (e.key != null && e.key.equals(key))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        table[index] = new Node<>(key, value, head);
        size++;

        if (size > (int) (loadFactor * table.length)) {
            resize();
        }

        return null;
    }

    public V get(Object key) {
        int index = getBucketIndex(hash(key), table.length);

        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if ((e.key == null && key == null)
                    || (e.key != null && e.key.equals(key))) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(Object key) {
        int index = getBucketIndex(hash(key), table.length);

        Node<K, V> head = table[index];
        Node<K, V> prev = null;

        for (Node<K, V> e = head; e != null; e = e.next) {
            if ((e.key == null && key == null)
                    || (e.key != null && e.key.equals(key))) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (int i = 0; i < oldCapacity; i++) {
            Node<K, V> node = table[i];
            while (node != null) {
                Node<K, V> next = node.next;
                int newIndex = getBucketIndex(hash(node.key), newCapacity);
                node.next = newTable[newIndex];
                newTable[newIndex] = node;
                node = next;
            }
        }
        table = newTable;
    }

    private int getBucketIndex(int hash, int length) {
        return hash & (length - 1);
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
