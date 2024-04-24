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
            isRehashing = false;
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
        List<T> newTable = new ArrayList<>(size);
        initTable();
        hashFunction = new UniversalHashing(32, 32);
        for (T x : set) {
            insert(x);
        }
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
