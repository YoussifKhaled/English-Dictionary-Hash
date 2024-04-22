package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuadraticPerfectHashing<T> {
    private UniversalHashing hashFunction;
    private Set<T> set;
    private int[] table;
    private int size;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new int[size];
        set = new HashSet<T>();
    }

    public int insert(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        if(!isRehashing && set.contains(item)){
            return 3;
        }
        set.add(item);
        if(table[pos] != 0){
            rehash();
            isRehashing = false;
        }
        table[pos] = 1;
       
        return 0;
    } 

    public int delete(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        if(table[pos] == 0){
            return 1;
        }
        table[pos] = 0;

        return 0;
    }

    public boolean search(T item){
        int hash = hashFunction.hash(StringUtls.getStringKey(item.toString()));
        int pos = hash%size;
        return (table[pos] != 0);
    }

    private void rehash(){
        isRehashing = true;
        table = new int[size];
        hashFunction = new UniversalHashing(32, 32);
        for(T x : set){
            insert(x);
        }
    }


    public static void main(String[] args) {
        QuadraticPerfectHashing m = new QuadraticPerfectHashing(4);
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
