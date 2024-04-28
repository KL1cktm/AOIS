package HashTable;

public class Main {
    public static void main(String[] args) {
        TableHash<String,String> hashTable = new TableHash<>(20);
        hashTable.addElement("Eugene","Yurhilevich");
        hashTable.addElement("Dmitriy","Yurhilevich");
        hashTable.addElement("Ksenia","Petrenko");
        hashTable.addElement("Ivan","Sytsevich");
        hashTable.findElement("Dmitriy");
        hashTable.getAllHashTable();
        hashTable.updateHashtableElements("Eugene","Unknown","Incognito");
        hashTable.getAllHashTable();
    }
}
