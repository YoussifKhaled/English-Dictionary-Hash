package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LinearPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<T> set;
    private ArrayList<UniversalHashing> secondLevelHashFunctions;
    private ArrayList<QuadraticPerfectHashing<T>> secondLevelTables;
    private int size;
    private int rehashCount;
    
    public LinearPerfectHashing(int N){
        firstLevelHashFunction = new UniversalHashing(32, 32);
        secondLevelHashFunctions = new ArrayList<>(N);
        secondLevelTables = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            secondLevelHashFunctions.add(null);
            secondLevelTables.add(new QuadraticPerfectHashing<>(1));
        }
        size = N;
        set = new ArrayList<>();
        rehashCount = 0;
    }

    public int insert(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if (!set.contains(item)) {
            set.add(item);
            QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            if (secondLevelTable.search(item)) {
                // Only rehash when a collision occurs
                int secondLevelSize = secondLevelTable.getSize() * secondLevelTable.getSize();
                UniversalHashing secondLevelHashFunction = new UniversalHashing(32, secondLevelSize);
                secondLevelHashFunctions.set(firstLevelHash, secondLevelHashFunction);
                rehashCount++;
            }
            secondLevelTable.insert(item);
            return 0;
        }
        return 3;
    }

    public int delete(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if(set.contains(item)){
            set.remove(item);
            QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            secondLevelTable.delete(item);
            return 0;
        }
        return 1;
    }
    
    public boolean search(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        UniversalHashing secondLevelHashFunction = secondLevelHashFunctions.get(firstLevelHash);
        if (secondLevelHashFunction != null) {
            return secondLevelTable.search(item);
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
            QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(i);
            int secondLevelSize = secondLevelTable.getSize();
            tableSizes.put(i + 1, secondLevelSize);  // size of each second-level table
            //System.out.println("Size of second-level table " + (i + 1) + ": " + secondLevelSize);
        }
        return tableSizes;
    }

    public ArrayList<T> getElements(){
        return set;
    }
}