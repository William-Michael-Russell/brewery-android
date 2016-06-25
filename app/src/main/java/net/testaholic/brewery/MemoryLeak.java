package net.testaholic.brewery;

import java.util.Map;

/**
 * Created by williamrussell on 6/16/16.
 */
public class MemoryLeak {
    // no hashCode or equals();
    public final String key;

    public MemoryLeak(String key) {
        this.key = key;
    }

    public void getMap(String key, String value){
        Map map = System.getProperties();
        map.put(new MemoryLeak(key), value);
    }
}
