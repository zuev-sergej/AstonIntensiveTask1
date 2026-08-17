import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomHashMapTest {

    @Test
    void putAndGet_shouldReturnValue() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Java", 100);
        assertEquals(100, map.get("Java"));
    }

    @Test
    void size_shouldIncreaseAfterPut() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Java", 100);
        map.put("Pyton", 200);
        assertEquals(2, map.size());
    }

    @Test
    void remove_shouldRemoveElement() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Java", 100);
        map.remove("Java");
        assertNull(map.get("Java"));
    }

    @Test
    void put_shouldOverwriteValue() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Java", 100);
        map.put("Java", 200);
        assertEquals(200, map.get("Java"));
    }

    @Test
    void shouldHandleWithCollision() {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();
        map.put(1, "first");
        map.put(17, "second");
        assertEquals("first", map.get(1));
        assertEquals("second", map.get(17));
        assertEquals(2, map.size());
    }

    @Test
    void shouldResizeAndPreserveWithCollision() {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();
        map.put(1, "first");
        map.put(17, "second");

        for (int i = 2; i < 12; i++) {
            map.put(i, "value" + i);
        }

        assertEquals("first", map.get(1));
        assertEquals("second", map.get(17));
        assertEquals(12, map.size());
    }
}
