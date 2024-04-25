import com.perfecthashing.LinearPerfectHashing;
import com.perfecthashing.QuadraticPerfectHashing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuadraticPerfectHashingTest {
    @Test
    public  void InsertionQuadraticHashingTest(){
        int numberOfElements=5;
        QuadraticPerfectHashing<String> quadraticPerfectHashing=new QuadraticPerfectHashing<>(numberOfElements);
        ArrayList<String> strings=new ArrayList<>(Arrays.asList("ab","name","DATA","HASH"));
        for (String s:strings){
            assertEquals(0,quadraticPerfectHashing.insert(s));
        }
        assertEquals(3,quadraticPerfectHashing.insert("ab"));
    }
    @Test
    public void DeletionQuadraticHashingTest(){
        int numberOfElements=5;
        QuadraticPerfectHashing<String> quadraticPerfectHashing=new QuadraticPerfectHashing<>(numberOfElements);
        ArrayList<String> strings=new ArrayList<>(Arrays.asList("abcd","name","DATA","testing","HASH"));
        for (String s:strings){
            quadraticPerfectHashing.insert(s);
        }
        assertEquals(0,quadraticPerfectHashing.delete("DATA"));//deleted
        assertEquals(0,quadraticPerfectHashing.delete("testing"));//deleted
        assertEquals(1,quadraticPerfectHashing.delete("doesn't exits"));//NOT FOUND
    }
    @Test
    public void SearchingQuadraticHashingTest(){
        int numberOfElements=10;
        QuadraticPerfectHashing<Integer> quadraticPerfectHashing=new QuadraticPerfectHashing<>(numberOfElements);
        ArrayList<Integer> integers=new ArrayList<>(Arrays.asList(1,100,30,5,1234,66,274,12,53243,111));
        for (int i:integers){
            quadraticPerfectHashing.insert(i);
        }
        assertTrue(quadraticPerfectHashing.search(274));
        assertFalse(quadraticPerfectHashing.search(4));
    }
    @Test
    public void LargeQuadraticHashingTest(){
        ArrayList<Character> characters=new ArrayList<>();
        Random random=new Random();
        int numberOfElements=1000;
        for (int i=0;i<numberOfElements;i++){
            characters.add((char)('a'+random.nextInt(26)));
        }
        QuadraticPerfectHashing<Character> quadraticPerfectHashing=new QuadraticPerfectHashing<>(numberOfElements);
        for (char c:characters){
            quadraticPerfectHashing.insert(c);
        }
        //check size of table (get size)
        assertTrue(quadraticPerfectHashing.search(characters.get(10)));
        assertTrue(quadraticPerfectHashing.search(characters.get(500)));
        assertEquals(0,quadraticPerfectHashing.delete(characters.get(550)));//deleted
        assertFalse(quadraticPerfectHashing.search(characters.get(550))); //search after deleted
    }

    @Test
    public void GenericQuadraticHashingTest(){
        QuadraticPerfectHashing<Integer> quadraticPerfectHashing=new QuadraticPerfectHashing<>(10);
        List<Integer> integers= Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        for (int i:integers){
            assertEquals(0,quadraticPerfectHashing.insert(i));
        }
        assertEquals(3,quadraticPerfectHashing.insert(1));
        assertEquals(0,quadraticPerfectHashing.delete(1));
        assertFalse(quadraticPerfectHashing.search(1));
    }

    
    @Test
    public void rehashCountRandomIntegerTest(){
        Random random=new Random();
        int numberOfElements=5000;
        QuadraticPerfectHashing<Integer> quadraticPerfectHashing
                =new QuadraticPerfectHashing<>(5000);
        ArrayList<Integer>integers=new ArrayList<>();
        for (int i=0;i<numberOfElements;i++){
            integers.add(random.nextInt());
        }
        long startTime=System.currentTimeMillis();
        for (int i:integers){
            quadraticPerfectHashing.insert(i);
        }
        long endTime=System.currentTimeMillis();
        System.out.println("time "+(endTime-startTime));
        System.out.println("rehash count "+quadraticPerfectHashing.getRehashCount());

    }
}
