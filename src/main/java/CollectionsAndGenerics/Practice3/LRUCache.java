package CollectionsAndGenerics.Practice3;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache <K,V> extends LinkedHashMap<K,V> {
    private final int capacity;
    public LRUCache(int size) {
        super(size, 0.75F, true);
        this.capacity = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
