import com.perfecthashing.Dictionary;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTest {
    
    @Test
    public void InsertionLinearDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("ab", "name", "DATA", "HASH"));
        for (String s : strings) {
            assertEquals(0, dictionary.insert(s));
        }
        assertEquals(3, dictionary.insert("ab"));
    }

    @Test
    public void DeletionLinearDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("abcd", "name", "DATA", "testing", "HASH"));
        for (String s : strings) {
            dictionary.insert(s);
        }
        assertEquals(0, dictionary.delete("DATA")); // deleted
        assertEquals(0, dictionary.delete("testing")); // deleted
        assertEquals(1, dictionary.delete("doesn't exits")); // NOT FOUND
    }

    @Test
    public void SearchingLinearDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 100, 30, 5, 1234, 66, 274, 12, 53243, 111));
        for (int i : integers) {
            dictionary.insert(String.valueOf(i));
        }

        assertFalse(dictionary.search("4"));
        assertTrue(dictionary.search("12"));
    }

    @Test
    public void LargeLinearDictionaryTest(){
        ArrayList<Character> characters = new ArrayList<>();
        Random random = new Random();
        int numberOfElements = 1000;
        for (int i = 0; i < numberOfElements; i++) {
            characters.add((char) ('a' + random.nextInt(26)));
        }
        String fileName = "src/test/resources/characters.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Character c : characters) {
                writer.write(c);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dictionary dictionary = new Dictionary("Linear");
        dictionary.batchInsert(fileName);
        // check size of table (get size)
        assertTrue(dictionary.search(String.valueOf(characters.get(10))));
        assertTrue(dictionary.search(String.valueOf(characters.get(500))));
        assertEquals(0, dictionary.delete(String.valueOf(characters.get(550)))); // deleted
    }

    @Test
    public void InsertionQuadraticDictionaryTest(){
        Dictionary dictionary = new Dictionary("Quadratic");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("ab", "name", "DATA", "HASH"));
        for (String s : strings) {
            assertEquals(0, dictionary.insert(s));
        }
        assertEquals(3, dictionary.insert("ab"));
    }

    @Test
    public void DeletionQuadraticDictionaryTest(){
        Dictionary dictionary = new Dictionary("Quadratic");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("abcd", "name", "DATA", "testing", "HASH"));
        for (String s : strings) {
            dictionary.insert(s);
        }
        assertEquals(0, dictionary.delete("DATA")); // deleted
        assertEquals(0, dictionary.delete("testing")); // deleted
        assertEquals(1, dictionary.delete("doesn't exits")); // NOT FOUND
    }

    @Test
    public void SearchingQuadraticDictionaryTest(){
        Dictionary dictionary = new Dictionary("Quadratic");
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 100, 30, 5, 1234, 66, 274, 12, 53243, 111));
        for (int i : integers) {
            dictionary.insert(String.valueOf(i));
        }
        assertTrue(dictionary.search("274"));
        assertFalse(dictionary.search("4"));
    }

    @Test
    public void LargeQuadraticDictionaryTest(){
        ArrayList<Character> characters = new ArrayList<>();
        Random random = new Random();
        int numberOfElements = 1000;
        for (int i = 0; i < numberOfElements; i++) {
            characters.add((char) ('a' + random.nextInt(26)));
        }
        String fileName = "src/test/resources/characters.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Character c : characters) {
                writer.write(c);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dictionary dictionary = new Dictionary("Quadratic");
        dictionary.batchInsert(fileName);
        // check size of table (get size)
        assertTrue(dictionary.search(String.valueOf(characters.get(10))));
        assertTrue(dictionary.search(String.valueOf(characters.get(500))));
        assertEquals(0, dictionary.delete(String.valueOf(characters.get(550)))); // deleted
    }

    @Test
    public void GenericDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int i : integers) {
            dictionary.insert(String.valueOf(i));
        }
        assertEquals(3, dictionary.insert("1"));
        assertEquals(0, dictionary.delete("1"));
        assertFalse(dictionary.search("1"));
    }

    @Test
    public void BatchInsertionLinearDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("ab", "name", "DATA", "HASH"));
        for (String s : strings) {
            assertEquals(0, dictionary.insert(s));
        }
        ArrayList<Integer> result = dictionary.batchInsert("src/test/resources/batchInsert.txt");
        assertEquals(4, result.size());
        assertEquals(0, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(0, result.get(2));
        assertEquals(3, result.get(3));
    }

    @Test
    public void BatchDeletionLinearDictionaryTest(){
        Dictionary dictionary = new Dictionary("Linear");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("abcd", "name", "DATA", "testing", "HASH"));
        for (String s : strings) {
            dictionary.insert(s);
        }
        ArrayList<Integer> result = dictionary.batchDelete("src/test/resources/batchDelete.txt");
        assertEquals(3, result.size());
        assertEquals(0, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(1, result.get(2));
    }

    @Test
    public void BatchInsertionQuadraticDictionaryTest(){
        Dictionary dictionary = new Dictionary("Quadratic");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("ab", "name", "DATA", "HASH"));
        for (String s : strings) {
            assertEquals(0, dictionary.insert(s));
        }
        ArrayList<Integer> result = dictionary.batchInsert("src/test/resources/batchInsert.txt");
        assertEquals(4, result.size());
        assertEquals(0, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(0, result.get(2));
        assertEquals(3, result.get(3));
    }

    @Test
    public void BatchDeletionQuadraticDictionaryTest(){
        Dictionary dictionary = new Dictionary("Quadratic");
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("abcd", "name", "DATA", "testing", "HASH"));
        for (String s : strings) {
            dictionary.insert(s);
        }
        ArrayList<Integer> result = dictionary.batchDelete("src/test/resources/batchDelete.txt");
        assertEquals(3, result.size());
        assertEquals(0, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(1, result.get(2));
    }

}
