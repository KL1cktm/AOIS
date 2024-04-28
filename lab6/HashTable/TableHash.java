package HashTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableHash<K, V> {
    protected class Element<K, V> {
        private K key;
        private V value;

        protected Element(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected K getKey() {
            return this.key;
        }

        protected V getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return "Key: " + key + ", Value: " + value;
        }
    }

    private int size;
    private LinkedList<Element<K, V>>[] hashTable;

    public TableHash(int size) {
        this.size = size;
        this.hashTable = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            this.hashTable[i] = new LinkedList<Element<K, V>>();
        }

    }

    private int getHashCode(K key) {
        String hashKey = key.toString();
        int hash = 0;
        for (int i = 0; i < hashKey.length(); i++) {
            hash += hashKey.charAt(i);
        }
        return hash % this.size;
    }

    public Element<K, V> addElement(K key, V value) {
        Element<K, V> element = new Element<>(key, value);
        int index = getHashCode(key);
        this.hashTable[index].add(element);
        return element;
    }

    public V findElement(K key) {
        int index = getHashCode(key);
        for (int i = 0; i < this.hashTable[index].size(); i++) {
            if (this.hashTable[index].get(i).getKey().equals(key)) {
                System.out.println(this.hashTable[index].get(i));
                return this.hashTable[index].get(i).getValue();
            }
        }
        System.out.println("Element was not found");
        return null;
    }

    public V deleteElement(K key) {
        int index = getHashCode(key);
        for (int i = 0; i < this.hashTable[index].size(); i++) {
            if (this.hashTable[index].get(i).getKey().equals(key)) {
                System.out.println(this.hashTable[index].get(i));
                V value = this.hashTable[index].get(i).getValue();
                this.hashTable[index].remove(i);
                return value;
            }
        }
        System.out.println("The element satisfying the condition was not found");
        return null;
    }

    public List<Element<K, V>> getAllHashTable() {
        List<Element<K, V>> elements = new ArrayList<>();
        for (int i = 0; i < this.hashTable.length; i++) {
            if (this.hashTable[i].size() > 0) {
                for (int j = 0; j < this.hashTable[i].size(); j++) {
                    elements.add(this.hashTable[i].get(j));
                }
            }
        }
        for (Element<K, V> element : elements) {
            System.out.println(element);
        }
        return elements;
    }

    public V updateHashtableElements(K searchKey, K key, V value) {
        V lastValue = null;
        int index = getHashCode(searchKey);
        for (Element<K, V> element : this.hashTable[index]) {
            if (element.getKey().equals(searchKey)) {
                if (searchKey.equals(key)) {
                    System.out.println("Last value: " + element.getValue() + ", new value: " + value);
                    lastValue = element.getValue();
                    element.value = value;
                    return lastValue;
                } else {
                    System.out.println("Element: " + this.hashTable[index] + " was deleted");
                    lastValue = element.getValue();
                    this.hashTable[index].remove(element);
                    break;
                }
            }
        }
        Element<K, V> element = addElement(key, value);
        System.out.println("Element: " + element + " was added");
        return lastValue;
    }
}