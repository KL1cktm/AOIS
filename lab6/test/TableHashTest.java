package test;

import static org.junit.Assert.*;

import HashTable.TableHash;
import org.junit.Test;

public class TableHashTest {
    @Test
    public void testAddElement() {
        TableHash<Integer, String> table = new TableHash<>(10);
        table.addElement(1, "value1");
        assertEquals("value1", table.findElement(1));
    }

    @Test
    public void testDeleteElement() {
        TableHash<String, Integer> table = new TableHash<>(10);
        table.addElement("key1", 10);
        assertEquals(10, (int) table.deleteElement("key1"));
    }

    @Test
    public void testDeleteUnnecessaryElement() {
        TableHash<String, Double> table = new TableHash<>(10);
        table.addElement("apple", 10.4);
        table.addElement("banan", 20.2);
        table.addElement("orange", 24.8);
        assertEquals(null,table.deleteElement("blackberry"));
    }
    @Test
    public void testUpdateHashtableElements() {
        TableHash<Double, String> table = new TableHash<>(10);
        table.addElement(1.5, "value1");
        assertEquals("value1", table.updateHashtableElements(1.5, 1.5, "value2"));
    }

    @Test
    public void testUpdateHashtableDifferenceElements() {
        TableHash<Character, Integer> table = new TableHash<>(10);
        table.addElement('a', 10);
        table.addElement('b', 20);
        table.addElement('c', 30);
        assertEquals(20,(int) table.updateHashtableElements('b', 'd', 40));
    }
    @Test
    public void testGetAllHashTable() {
        TableHash<Character, Integer> table = new TableHash<>(10);
        table.addElement('a', 10);
        table.addElement('b', 20);
        table.addElement('c', 30);
        assertEquals(3, table.getAllHashTable().size());
    }
}
