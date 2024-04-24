package com.perfecthashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class QuadraticPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing hashFunction;
    private mySet<T> set;
    private List<T> table;
    private int size;
    private boolean isRehashing = false;
    private int rehashedSize;
    private int deletedItems;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new ArrayList<T>(size);
        initTable();
        set = new mySet<T>();
        rehashedSize = 0;
        deletedItems = 0;
    }

    public int insert(T item){
        int hash = hashFunction.hash(item.hashCode());
        int pos = hash%size;
        if(!isRehashing && set.contains(item)){
            return 3;
        }
        set.add(item);
        if(table.get(pos) != null){
            rehash();
        }
        table.set(pos, item);
       
        return 0;
    } 

    public int delete(T item){
        int hash = hashFunction.hash(item.hashCode());
        int pos = hash%size;
        if(table.get(pos) == null || !table.get(pos).equals(item)){
            return 1;
        }
        table.set(pos, null);
        set.remove(item);
        deletedItems++;

        return 0;
    }

    public boolean search(T item){
        int hash = hashFunction.hash(item.hashCode());
        int pos = hash%size;
        return (table.get(pos) != null && table.get(pos).equals(item));
    }

    private void rehash(){
        rehashedSize++;
        isRehashing = true;
        initTable();
        hashFunction = new UniversalHashing(32, 32);
        for(T x : set){
            insert(x);
        }
        isRehashing = false;
    }

    private void initTable(){
        table = new ArrayList<>(Collections.nCopies(size, null));
    }

    public ArrayList<T> getElements(){
        return (ArrayList<T>) set.getList();
    }

    public int getRehashedSize() {
        return rehashedSize;
    }

    public int getDeletedItems(){
        return deletedItems;
    }

    public int getHasedItems(){
        return set.size();
    }


    public static void main(String[] args) {
        QuadraticPerfectHashing m = new QuadraticPerfectHashing(1000);
        for (int i = 1; i <= 1000; i++) {
            m.insert(i);
            System.out.println(i);
        }

        for (int i = 1; i <= 500; i++) {
            m.delete(i);
        }
        int sum = 0;
        for (int i = 1; i <= 1000; i++) {
            if (m.search(i)) sum++;
        }
        System.out.println(sum);
    }
    
    
    //return 1 ==> not exist in table
    //return 3 ==> already in table

}