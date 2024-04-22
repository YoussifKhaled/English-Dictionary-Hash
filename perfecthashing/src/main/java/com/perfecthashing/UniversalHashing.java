package com.perfecthashing;

import java.util.Random;

public class UniversalHashing {
    private int[][] matrix;
    private int b;
    private int u;
    private int M;

    public UniversalHashing(int b, int u){
        this.b = b;
        this.u = u;
        this.M = 1 << b;
        this.matrix = this.generateRandomMatrix();
    }

    private int[][] generateRandomMatrix(){

        int[][] randMatrix = new int[b][u];
        Random rand = new Random();

        for(int i=0;i<b;i++){
            for(int j=0;j<u;j++){
                randMatrix[i][j] = rand.nextInt(2);
            }
        }

        return randMatrix;
    }

    public int hash(int key){

        int[] keyBinary = new int[u];
        for(int i=u-1;i>=0;i--){
            keyBinary[i] = key&1;
            key >>= 1;
        }

        int[] hashX = new int[b];
        for(int i=0;i<b;i++){
            int sum=0;
            for(int j=0;j<u;j++){
                sum += matrix[i][j] * keyBinary[j]; 
                sum = sum%2;
            }
            hashX[i] = sum;
        }

        int hash=0;
        for(int i=b-1;i>=0;i--){
            hash <<= 1;
            hash |= hashX[i];
        }

        return hash;
    }

}

