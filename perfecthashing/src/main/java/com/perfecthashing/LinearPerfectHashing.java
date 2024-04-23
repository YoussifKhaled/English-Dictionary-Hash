package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinearPerfectHashing<T> {
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<UniversalHashing> secondLevelHashFunctions;
    private ArrayList<HashSet<T>> secondLevelTables;
    private Set<T> set;
    private int size;
    private int rehashCount;

    public LinearPerfectHashing(int N){
        firstLevelHashFunction = new UniversalHashing(32, 32);
        secondLevelHashFunctions = new ArrayList<>(N);
        secondLevelTables = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            secondLevelHashFunctions.add(null);
            secondLevelTables.add(new HashSet<>());
        }
        size = N;
        set = new HashSet<>();
        rehashCount = 0;
    }

    public void insert(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if (!set.contains(item)) {
            set.add(item);
            HashSet<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            secondLevelTable.add(item);
            int secondLevelSize = secondLevelTable.size() * secondLevelTable.size();
            UniversalHashing secondLevelHashFunction = new UniversalHashing(32, secondLevelSize);
            secondLevelHashFunctions.set(firstLevelHash, secondLevelHashFunction);
            rehashCount++;
        }
    }

    public boolean delete(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if (set.contains(item)) {
            set.remove(item);
            HashSet<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            secondLevelTable.remove(item);
            return true;
        }
        return false;
    }

    public int getRehashCount() {
        return rehashCount;
    }

    public Map<Integer, Integer> getTableSizes() {
        Map<Integer, Integer> tableSizes = new HashMap<>();
        tableSizes.put(0, size);  // size of the first-level table
        System.out.println("Size of first-level table: " + size);
        for (int i = 0; i < size; i++) {
            HashSet<T> secondLevelTable = secondLevelTables.get(i);
            int secondLevelSize = secondLevelTable.size();
            tableSizes.put(i + 1, secondLevelSize);  // size of each second-level table
            //System.out.println("Size of second-level table " + (i + 1) + ": " + secondLevelSize);
        }
        return tableSizes;
    }

    public boolean search(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        HashSet<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        UniversalHashing secondLevelHashFunction = secondLevelHashFunctions.get(firstLevelHash);
        if (secondLevelHashFunction != null) {
            return secondLevelTable.contains(item);
        }
        return false;
        }
    public static void main(String[] args) {
        // Create an instance of the class
        int n = 40;
        LinearPerfectHashing<String> lph = new LinearPerfectHashing<>(n);

        // Insert some values
        lph.insert("apple");
        lph.insert("banana");
        lph.insert("cherry");

        // Check if the values exist
        System.out.println("Contains apple: " + lph.search("apple"));
        System.out.println("Contains banana: " + lph.search("banana"));
        System.out.println("Contains cherry: " + lph.search("cherry"));

        // Delete some values
        lph.delete("apple");
        lph.delete("cherry");

        // Check if the values exist after deletion
        System.out.println("Contains apple: " + lph.search("apple"));
        System.out.println("Contains banana: " + lph.search("banana"));
        System.out.println("Contains cherry: " + lph.search("cherry"));

        // Print the size of the hash table and the number of rehashes

        System.out.println("Size of hash table: " + lph.getTableSizes());
        System.out.println("Number of rehashes: " + lph.getRehashCount());
    }
}