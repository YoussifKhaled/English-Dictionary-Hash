package com.perfecthashing;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int input;
        System.out.println("Welcome to the English Dictionary, please select a hashing method:");
        System.out.println("1. Linear Perfect Hashing (O(N))");
        System.out.println("2. Quadratic Perfect Hashing (O(N^2))");

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();
        
        Dictionary dictionary = null;
        if(input == 1){
            dictionary = new Dictionary("Linear");
        } else if(input == 2){
            dictionary = new Dictionary("Quadratic");
        } else {
            System.out.println("Invalid input, please try again.");
        }

        while(input != 6){
            System.out.println("Please select an option:");
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("4. Batch Insert");
            System.out.println("5. Batch Delete");
            System.out.println("6. Exit");

            input = scanner.nextInt();
            switch(input){
                case 1:
                    System.out.println("Enter the word you want to insert:");
                    String word = scanner.next();
                    int result1 = dictionary.insert(word);
                    if(result1 == 0){
                        System.out.println("Word inserted successfully.");
                    } else if(result1 == 3){
                        System.out.println("Word already exists.");
                    }
                    break;
                case 2:
                    System.out.println("Enter the word you want to delete:");
                    word = scanner.next();
                    int result2 = dictionary.delete(word);
                    if(result2 == 0){
                        System.out.println("Word deleted successfully.");
                    }else if(result2 == 1){
                        System.out.println("Word not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter the word you want to search for:");
                    word = scanner.next();
                    if(dictionary.search(word)){
                        System.out.println("Word found.");
                    }else{
                        System.out.println("Word not found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter the path to the file you want to insert:");
                    String path = scanner.next();
                    ArrayList<Integer> result3 = dictionary.batchInsert(path);
                    int existsCount = 0;
                    for(int i : result3){
                        if(i == 3){
                            existsCount++;
                        }
                    }
                    System.out.println("Words inserted successfully: " + (result3.size() - existsCount));
                    System.out.println("Words already existing: " + existsCount);
                    break;
                case 5:
                    System.out.println("Enter the path to the file you want to delete:");
                    path = scanner.next();
                    ArrayList<Integer> result4 = dictionary.batchDelete(path);
                    int notFoundCount = 0;
                    for(int i : result4){
                        if(i == 1){
                            notFoundCount++;
                        }
                    }
                    System.out.println("Words not found: " + notFoundCount);
                    System.out.println("Words deleted successfully: " + (result4.size() - notFoundCount));
                    break;
                default:
                    break;
            }
        }

        scanner.close();
    }
}