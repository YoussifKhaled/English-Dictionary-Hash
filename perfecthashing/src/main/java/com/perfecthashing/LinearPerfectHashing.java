package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LinearPerfectHashing<T> implements PerfectHashing<T> {
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<ArrayList<T>> secondLevelTables;
    private int size;
    private int rehashCount;

    public LinearPerfectHashing(int N) {
        firstLevelHashFunction = new UniversalHashing(32, 32);
        secondLevelTables = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            secondLevelTables.add(new ArrayList<>());
        }
        size = N;
        rehashCount = 0;
    }

    public int insert(T item) {
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        if (!secondLevelTable.contains(item)) {
            secondLevelTable.add(item);
            return 0;
        }
        return 1;
    }

    public int delete(T item) {
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        if (secondLevelTable.remove(item)) {
            return 0;
        }
        return 1;
    }

    public boolean search(T item) {
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        return secondLevelTable.contains(item);
    }

    public int getRehashCount() {
        return rehashCount;
    }

    public Map<Integer, Integer> getTableSizes() {
        Map<Integer, Integer> tableSizes = new HashMap<>();
        tableSizes.put(0, size);  // size of the first-level table
        for (int i = 0; i < size; i++) {
            tableSizes.put(i + 1, secondLevelTables.get(i).size());
        }
        return tableSizes;
    }

    @Override
    public ArrayList<T> getElements() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getElements'");
    }
}
