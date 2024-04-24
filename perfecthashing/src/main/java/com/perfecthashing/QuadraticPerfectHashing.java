package com.perfecthashing;

import java.util.ArrayList;
import java.util.List;


public class QuadraticPerfectHashing<T> implements PerfectHashing<T> {
    private UniversalHashing hashFunction;
    private ArrayList<T> set;
    private List<T> table;
    private int size;
    private int rehashCount;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N) {
        hashFunction = new UniversalHashing(32, 32);
        size = N * N;
        table = new ArrayList<>(size);
        initTable();
        set = new ArrayList<>();
        rehashCount = 0;
    }

    public int insert(T item) {
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash % size;
        if (!isRehashing && set.contains(item)) {
            return 3;
        }
        set.add(item);
        if (table.get(pos) != null) {
            rehash();
        }
        table.set(pos, item);
        return 0;
    }

    public int delete(T item) {
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash % size;
        if (table.get(pos) == null || !table.get(pos).equals(item)) {
            return 1;
        }
        table.set(pos, null);
        return 0;
    }

    public boolean search(T item) {
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash % size;
        return (table.get(pos) != null && table.get(pos).equals(item));
    }

    private void rehash() {
        rehashCount++;
        isRehashing = true;
        int newSize = size * 2; // Double the size for rehashing
        List<T> newTable = new ArrayList<>(newSize);
        for (int i = 0; i < newSize; i++) {
            newTable.add(null);
        }
        for (T x : set) {
            int hash = hashFunction.hash(StringUtls.getStringKey(x.toString()));
            int pos = hash % newSize;
            while (newTable.get(pos) != null) {
                pos = (pos + 1) % newSize; // Linear probing to resolve collisions
            }
            newTable.set(pos, x);
        }
        table = newTable;
        size = newSize;
        isRehashing = false;
    }

    private void initTable() {
        for (int i = 0; i < size; i++) {
            table.add(null);
        }
    }

    public ArrayList<T> getElements() {
        return set;
    }

    public int getRehashCount() {
        return rehashCount;
    }

    public int getSize() {
        return size;
    }

    public List<T> getTable() {
        return table;
    }
}
