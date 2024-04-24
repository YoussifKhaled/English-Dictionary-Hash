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
            secondLevelTables.add(new QuadraticPerfectHashing<>(1)); // Initialize with size 1
        }
        size = N;
        set = new ArrayList<>();
        rehashCount = 0;
    }

    public int insert(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        if(!set.contains(item)){
            set.add(item);
            QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
            if (secondLevelTable == null) {
                secondLevelTable = new QuadraticPerfectHashing<>(1); // Initialize with size 1
                secondLevelTables.set(firstLevelHash, secondLevelTable);
            }
            int result = secondLevelTable.insert(item);
            if (result == 3) {
                // If a collision occurs, rehash the second level table with size equal to the number of collisions
                secondLevelTable = new QuadraticPerfectHashing<>(secondLevelTable.getElements().size());
                secondLevelTables.set(firstLevelHash, secondLevelTable);
                for (T element : set) {
                    secondLevelTable.insert(element); // Re-insert the items
                }
            }
            return 0;
        }
        return 1;
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
        if (secondLevelTable != null && secondLevelHashFunction != null) {
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
            if (secondLevelTable != null) {
                int secondLevelSize = secondLevelTable.getElements().size();
                tableSizes.put(i + 1, secondLevelSize);  // size of each second-level table
            } else {
                tableSizes.put(i + 1, 0);  // If second level table is null, set size to 0
            }
        }
        return tableSizes;
    }

    public ArrayList<T> getElements(){
        return set;
    }

    
}