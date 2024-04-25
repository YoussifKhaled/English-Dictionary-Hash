package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LinearPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing firstLevelHashFunction;
    private ArrayList<UniversalHashing> secondLevelHashFunctions;
    private ArrayList<ArrayList<T>> secondLevelTables;
    private int size;
    private int rehashCount;
    
    public LinearPerfectHashing(int N){
        firstLevelHashFunction = new UniversalHashing(32, 32);
        secondLevelHashFunctions = new ArrayList<>(N);
        secondLevelTables = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            UniversalHashing secondLevelHashFunction = new UniversalHashing(32, 32);
            secondLevelHashFunctions.add(secondLevelHashFunction);
            secondLevelTables.add(new ArrayList<>());
            for (int j = 0; j < 2; j++) {
                secondLevelTables.get(i).add(null);
            }
        }
        size = N;
        rehashCount = 0;
    }

    public int insert(T item){
        int firstLevelHash = firstLevelHashFunction.hash(item.hashCode()) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        int secondLevelTableSize = secondLevelTable.size();
        int secondLevelHash = secondLevelHashFunctions.get(firstLevelHash).hash(item.hashCode()) % secondLevelTableSize;
        if (secondLevelTable.get(secondLevelHash) == null) {//No collision & not inserted before
            secondLevelTable.set(secondLevelHash, item);
            return 0;
        } else if (!item.equals(secondLevelTable.get(secondLevelHash))) {//collision happened => Rehash
            ArrayList<T> tmp = new ArrayList<>();
            for (T x: secondLevelTable) {
                if (x != null) tmp.add(x);
            }
            tmp.add(item);
            int newSecondLevelSize = tmp.size()* tmp.size();// square size of existing elements
            UniversalHashing secondLevelHashFunction = new UniversalHashing(32, 32);
            secondLevelHashFunctions.set(firstLevelHash, secondLevelHashFunction);
            secondLevelTable = new ArrayList<>();
            for (int i = 0; i < newSecondLevelSize; i++) {
                secondLevelTable.add(null);
            }
            for (T x : tmp) {
                secondLevelHash = secondLevelHashFunction.hash(x.hashCode()) % newSecondLevelSize;
                secondLevelTable.set(secondLevelHash, x);
            }
            rehashCount++;
        }
        return 3; //inserted before
    }

    public int delete(T item){
        int firstLevelHash = firstLevelHashFunction.hash(item.hashCode()) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        UniversalHashing secondLevelHashFunction = secondLevelHashFunctions.get(firstLevelHash);
        int secondLevelTableSize=secondLevelTable.size();
        int secondLevelHash = secondLevelHashFunction.hash(item.hashCode()) % secondLevelTableSize;
        if(item.equals(secondLevelTable.get(secondLevelHash))) {
            secondLevelTable.set(secondLevelHash, null);
            return 0;
        }
        return 1; //not found
    }
    
    public boolean search(T item){
        int firstLevelHash = firstLevelHashFunction.hash(StringUtls.getStringKey(item.toString())) % size;
        ArrayList<T> secondLevelTable = secondLevelTables.get(firstLevelHash);
        UniversalHashing secondLevelHashFunction = secondLevelHashFunctions.get(firstLevelHash);
        int secondLevelTableSize=secondLevelTable.size();
        int index=secondLevelHashFunction.hash(item.hashCode()) % secondLevelTableSize;
        return item.equals(secondLevelTable.get(index));
    }

    public int getRehashCount() {
        return rehashCount;
    }

    public Map<Integer, Integer> getTableSizes() {
        Map<Integer, Integer> tableSizes = new HashMap<>();
        tableSizes.put(0, size);  // size of the first-level table
        System.out.println("Size of first-level table: " + size);
        for (int i = 0; i < size; i++) {
            //QuadraticPerfectHashing<T> secondLevelTable = secondLevelTables.get(i);
            //int secondLevelSize = secondLevelTable.getSize();
            //tableSizes.put(i + 1, secondLevelSize);  // size of each second-level table
            //System.out.println("Size of second-level table " + (i + 1) + ": " + secondLevelSize);
        }
        return tableSizes;
    }

    public ArrayList<T> getElements(){
        ArrayList<T> elements=new ArrayList<>() ;
        for (ArrayList<T> tArrayList:secondLevelTables) {
             for (T x:tArrayList)
                 if (x != null)  elements.add(x);
        }
        return elements;
    }
}