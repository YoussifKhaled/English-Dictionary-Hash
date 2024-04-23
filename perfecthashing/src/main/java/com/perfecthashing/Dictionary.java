package com.perfecthashing;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private String TypeOfHashing;
    private PerfectHashing<String> HashingType;
    
    public Dictionary(String HashingType){
        this.TypeOfHashing = HashingType;
        if(HashingType.equals("Linear")){
            this.HashingType = new LinearPerfectHashing<>(10);
        } else if(HashingType.equals("Quadratic")){
            this.HashingType = new QuadraticPerfectHashing<>(10);
        }
        System.out.println("Dictionary created with " + HashingType + " hashing type.");
    }

    public int insert(String item){
        return HashingType.insert(item);
    }

    public int delete(String item){
        return HashingType.delete(item);
    }

    public boolean search(String item){
        return HashingType.search(item);
    }

    public ArrayList<Integer> batchInsert(String filePath){
        ArrayList<String> oldItems = HashingType.getElements();
        ArrayList<String> newItems = new ArrayList<>();
        
        File file = new File(filePath);
        Scanner sc;        
        try{
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                newItems.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        if(this.TypeOfHashing.equals("Linear")){
            this.HashingType = new LinearPerfectHashing<>(oldItems.size() + newItems.size());
        } else if(this.TypeOfHashing.equals("Quadratic")){
            this.HashingType = new QuadraticPerfectHashing<>(oldItems.size() + newItems.size());
        }
        
        for(String item : oldItems){
            insert(item);
        }
        ArrayList<Integer> result = new ArrayList<>();
        for(String item : newItems){
            result.add(insert(item));
        }
        
        return result;
    }

    public ArrayList<Integer> batchDelete(String filePath){
        ArrayList<String> items = new ArrayList<>();
        
        File file = new File(filePath);
        Scanner sc;        
        try{
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                items.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        ArrayList<Integer> result = new ArrayList<>();
        for(String item : items){
            result.add(delete(item));
        }
        return result;
    }
}
