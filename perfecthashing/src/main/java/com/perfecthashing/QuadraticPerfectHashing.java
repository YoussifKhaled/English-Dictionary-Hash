package com.perfecthashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuadraticPerfectHashing {
    private UniversalHashing hashFunction;
    private Set<String> set;
    private int[] table;
    private int size;
    private boolean isRehashing = false;

    public QuadraticPerfectHashing(int N){
        hashFunction = new UniversalHashing(32, 32);
        size = N*N;
        table = new int[size];
        set = new HashSet<String>();
    }

    public int insert(String s){
        int hash = hashFunction.hash(StringUtls.getStringKey(s));
        int pos = hash%size;
        if(!isRehashing && set.contains(s)){
            return 3;
        }
        set.add(s);
        if(table[pos] != 0){
            rehash();
            isRehashing = false;
        }
        table[pos] = 1;
       
        return 0;
    } 

    public int delete(String s){
        int hash = hashFunction.hash(StringUtls.getStringKey(s));
        int pos = hash%size;
        if(table[pos] == 0){
            return 1;
        }
        table[pos] = 0;

        return 0;
    }

    public boolean search(String s){
        int hash = hashFunction.hash(StringUtls.getStringKey(s));
        int pos = hash%size;
        return (table[pos] != 0);
    }

    private void rehash(){
        isRehashing = true;
        table = new int[size];
        hashFunction = new UniversalHashing(32, 32);
        for(String x : set){
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

}
