import java.util.Arrays;
import java.util.ArrayList;

import com.perfecthashing.LinearPerfectHashing;
import com.perfecthashing.QuadraticPerfectHashing;

public class ReportComparison{
    static boolean isPrime(int n){
        if(n<=1) return false;
        if(n<=3) return true;
        if(n%2==0 || n%3==0) return false;
        for(int i=5;i*i<=n;i+=6){
            if(n%i==0 || n%(i+2)==0) return false;
        }
        return true;
    }
    public static void main(String[] args) {

        ArrayList<Integer> sizes = new ArrayList<>(Arrays.asList(10, 100, 1000, 10000, 15000, 20000, 30000));
        ArrayList<Integer> linearMeanTimes = new ArrayList<>();
        ArrayList<Integer> quadraticMeanTimes = new ArrayList<>();
        
        for(int size : sizes){
            int quadraticSearchMean = 0, quadraticInsertMean = 0;
            int linearSearchMean = 0, linearInsertMean = 0;

            System.out.println("("+size+")");
            
            System.out.println("Linear o(n):");
            for(int i=0;i<10;i++){
                LinearPerfectHashing<String> linearPerfectHashing = new LinearPerfectHashing<>(size);
                    
                long startTime = System.currentTimeMillis();
                for(int j=1;j<=size;j++){
                    if(isPrime(j))
                        linearPerfectHashing.insert(Integer.toString(j-1));
                    else 
                        linearPerfectHashing.insert(Integer.toString(j));
                }
                long endTime = System.currentTimeMillis();
                
                linearInsertMean += (int)(endTime-startTime);

                startTime = System.currentTimeMillis();
                for(int j=1;j<=size;j++){
                    linearPerfectHashing.search(Integer.toString(j));
                }
                endTime = System.currentTimeMillis();

                linearSearchMean += (int)(endTime-startTime);

            }
            System.out.println("insertion mean time: "+linearInsertMean/10+" ms");
            linearMeanTimes.add(linearInsertMean/10);
            System.out.println("search mean time: "+linearSearchMean/10+" ms");
            linearMeanTimes.add(linearSearchMean/10);

            System.out.println("Quadratic o(n^2):");
            for(int i=0;i<10;i++){
                QuadraticPerfectHashing<String> QuadraticPerfectHashing = new QuadraticPerfectHashing<>(size);
                    
                long startTime = System.currentTimeMillis();
                for(int j=1;j<=size;j++){
                    if(isPrime(j))
                        QuadraticPerfectHashing.insert(Integer.toString(j-1));
                    else 
                        QuadraticPerfectHashing.insert(Integer.toString(j));
                }
                long endTime = System.currentTimeMillis();
                
                quadraticInsertMean += (int)(endTime-startTime);

                startTime = System.currentTimeMillis();
                for(int j=1;j<=size;j++){
                    QuadraticPerfectHashing.search(Integer.toString(j));
                }
                endTime = System.currentTimeMillis();

                quadraticSearchMean += (int)(endTime-startTime);

            }
            System.out.println("insertion mean time: "+quadraticInsertMean/10+" ms");
            quadraticMeanTimes.add(quadraticInsertMean/10);
            System.out.println("search mean time: "+quadraticSearchMean/10+" ms");
            quadraticMeanTimes.add(quadraticSearchMean/10);
        }
    }
}