package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LinearPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<T> set;
    private ArrayList<UniversalHashing> secondLevelHashFunctions;
    private ArrayList<HashSet<T>> secondLevelTables;
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
        set = new ArrayList<>();
        rehashCount = 0;
    }

    public int insert(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if (!set.contains(item)) {
            set.add(item);
            HashSet<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            secondLevelTable.add(item);
            int secondLevelSize = secondLevelTable.size() * secondLevelTable.size();
            UniversalHashing secondLevelHashFunction = new UniversalHashing(32, secondLevelSize);
            secondLevelHashFunctions.set(firstLevelHash, secondLevelHashFunction);
            rehashCount++;
            return 0;
        }
        return 3;
    }

    public int delete(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if(set.contains(item)){
            set.remove(item);
            HashSet<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            secondLevelTable.remove(item);
            return 0;
        }
        return 1;
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

    public ArrayList<T> getElements(){
        return set;
    }
}