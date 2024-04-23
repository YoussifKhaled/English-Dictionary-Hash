package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LinearPerfectHashing<T> {
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<UniversalHashing> secondLevelHashFunctions;
    private ArrayList<HashSet<T>> secondLevelTables;
    private Set<T> set;
    private int size;

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
        }
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
        LinearPerfectHashing<Integer> hashing = new LinearPerfectHashing<>(10);
        for (int i = 1; i <= 10; i++) {
            hashing.insert(i);
        }
        for (int i = 1; i <= 10; i++) {
            System.out.println("Searching for " + i + ": " + hashing.search(i));
        }
        System.out.println("Searching for 11: " + hashing.search(11));
    }
}