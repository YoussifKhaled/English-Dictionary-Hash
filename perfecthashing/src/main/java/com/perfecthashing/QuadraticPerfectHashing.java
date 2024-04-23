package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadraticPerfectHashing<T> {
    private UniversalHashing hashFunction;
    private Set<T> set;
    private List<T> table;
    private int size;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new ArrayList<T>(size);
        initTable();
        set = new HashSet<T>();
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
        if(!table.get(pos).equals(item)){
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


    public static void main(String[] args) {
        QuadraticPerfectHashing m = new QuadraticPerfectHashing(4);
        System.out.println(m.table.size());
        System.out.println(m.insert("hii"));
        System.out.println(m.insert("hii"));
        System.out.println(m.insert("kool"));
        System.out.println(m.delete("kool"));
        //System.out.println(m.delete("hii"));
        System.out.println(m.search("hii"));
        System.out.println(m.search("bye"));
        System.out.println(m.search("kool"));
    }
    
    //return 1 ==> not exist in table
    //return 3 ==> already in table

}
