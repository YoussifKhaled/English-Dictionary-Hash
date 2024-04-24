package com.perfecthashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuadraticPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing hashFunction;
    private mySet<T> set;
    private List<T> table;
    private int size;
    private int rehashCount;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new ArrayList<T>(size);
        initTable();
        set = new mySet<T>();
    }
/* 
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
*/

    public int insert(T item){
        int hash = hashFunction.hash(item.hashCode());
        int pos = hash%size;
        if(!isRehashing && set.contains(item)){
            return 3;
        }
        set.add(item);
        if(table.get(pos) != null && !isRehashing){
            rehash();
            // After rehashing, we need to insert the item again
            return insert(item);
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

        return 0;
    }

    public boolean search(T item){
        int hash = hashFunction.hash(item.hashCode());
        int pos = hash%size;
        return (table.get(pos) != null && table.get(pos).equals(item));
    }

    private void rehash(){
        isRehashing = true;
        initTable();
        hashFunction = new UniversalHashing(32, 32);
        for(T x : set){
            insert(x);
        }
        isRehashing = false;
        rehashCount++;
    }

    private void initTable(){
        table = new ArrayList<>(Collections.nCopies(size, null));
    }

    public ArrayList<T> getElements(){
        return (ArrayList<T>) set.getList();
    }

    public int getSize() {
        return size;
    }

    public int getRehashCount() {
        return rehashCount;
    }
    //return 1 ==> not exist in table
    //return 3 ==> already in table

}