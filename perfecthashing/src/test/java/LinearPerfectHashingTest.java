import com.perfecthashing.LinearPerfectHashing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LinearPerfectHashingTest {
    @Test
    public  void InsertionLinearHashingTest(){
        int numberOfElements=5;
        LinearPerfectHashing<String> linearPerfectHashing=new LinearPerfectHashing<>(numberOfElements);
        ArrayList<String> strings=new ArrayList<>(Arrays.asList("ab","name","DATA","HASH"));
        for (String s:strings){
            assertEquals(0,linearPerfectHashing.insert(s));
        }
        assertEquals(3,linearPerfectHashing.insert("ab"));
    }
    @Test
    public void DeletionLinearHashingTest(){
        int numberOfElements=5;
        LinearPerfectHashing<String> linearPerfectHashing=new LinearPerfectHashing<>(numberOfElements);
        ArrayList<String> strings=new ArrayList<>(Arrays.asList("abcd","name","DATA","testing","HASH"));
        for (String s:strings){
            linearPerfectHashing.insert(s);
        }
        assertEquals(0,linearPerfectHashing.delete("DATA"));//deleted
        assertEquals(0,linearPerfectHashing.delete("testing"));//deleted
        assertEquals(1,linearPerfectHashing.delete("doesn't exits"));//NOT FOUND
    }
    @Test
    public void SearchingLinearHashingTest(){
        int numberOfElements=10;
        LinearPerfectHashing<Integer> linearPerfectHashing=new LinearPerfectHashing<>(numberOfElements);
        ArrayList<Integer> integers=new ArrayList<>(Arrays.asList(1,100,30,5,1234,66,274,12,53243,111));
        for (int i:integers){
            linearPerfectHashing.insert(i);
        }
        assertFalse(linearPerfectHashing.search(4));
        // assertTrue(linearPerfectHashing.search(274));
    }
    @Test
    public void LargeLinearHashingTest(){
        ArrayList<Character> characters=new ArrayList<>();
        Random random=new Random();
        int numberOfElements=1000;
        for (int i=0;i<numberOfElements;i++){
            characters.add((char)('a'+random.nextInt(26)));
        }
        LinearPerfectHashing<Character> linearPerfectHashing=new LinearPerfectHashing<>(numberOfElements);
        for (char c:characters){
            linearPerfectHashing.insert(c);
        }
        //check size of table (get size)
        assertTrue(linearPerfectHashing.search(characters.get(10)));
        assertTrue(linearPerfectHashing.search(characters.get(500)));
        assertEquals(0,linearPerfectHashing.delete(characters.get(550)));//deleted
        assertFalse(linearPerfectHashing.search(characters.get(550))); //search after deleted
    }

   
}
