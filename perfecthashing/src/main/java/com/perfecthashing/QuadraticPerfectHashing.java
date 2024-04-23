package com.perfecthashing;

import java.util.ArrayList;
import java.util.List;

public class QuadraticPerfectHashing<T> implements PerfectHashing<T>{
    private UniversalHashing hashFunction;
    private ArrayList<T> set;
    private List<T> table;
    private int size;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new ArrayList<T>(size);
        initTable();
        set = new ArrayList<>();
    }

    public int insert(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        if(!isRehashing && set.contains(item)){
            return 3;
        }
        set.add(item);
        if(table.get(pos) != null){
            rehash();
            isRehashing = false;
        }
        table.add(pos, item);
       
        return 0;
    } 

    public int delete(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        if(table.get(pos) == null || !table.get(pos).equals(item)){
            return 1;
        }
        table.add(pos, null);

        return 0;
    }

    public boolean search(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        return (table.get(pos) != null && table.get(pos).equals(item));
    }

    private void rehash(){
        isRehashing = true;
        table = new ArrayList<T>(size);
        initTable();
        hashFunction = new UniversalHashing(32, 32);
        for(T x : set){
            insert(x);
        }
    }

    private void initTable(){
        for(int i=0;i<size;i++){
            table.add(null);
        }
    }

    public ArrayList<T> getElements(){
        return set;
    }
}
