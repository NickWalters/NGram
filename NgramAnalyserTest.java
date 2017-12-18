import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.Test;

/**
 * Unit tests for the utility class MarkovModel.
 * Do not change this file.  Add new tests to the ProjectTest class.
 *
 * @author  Arran Stewart
 * @version May 2017
 */
public class NgramAnalyserTest
{
    /** tolerance for double comparisons */
    static final double tolerance = 0.0001; 

    /** Use reflection to extract the ngram map, even though it's private.
    */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Integer> extractMap(NgramAnalyser analyser) {
        try {
            Field ngramField = analyser.getClass().getDeclaredField("ngram");
            ngramField.setAccessible(true);
            return (HashMap<String, Integer>) ngramField.get(analyser);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test(timeout=1000)
    public void testSimpleOneGram() {
        //default value for n should be 1
        NgramAnalyser analyser = new NgramAnalyser("abc"); 
        assertEquals(3,analyser.getAlphabetSize());

        HashMap<String, Integer> ngram = extractMap(analyser);

        assertEquals(3,analyser.getDistinctNgramCount());
        assertEquals(1,(int)ngram.get("a"));
        assertEquals(1,(int)ngram.get("b"));
        assertEquals(1,(int)ngram.get("c"));
    }

    @Test(timeout=1000)
    public void testOneGram() {
        //default value for n should be 1
        NgramAnalyser analyser = new NgramAnalyser("aabcabaacaac"); 
        assertEquals(3,analyser.getAlphabetSize());

        HashMap<String, Integer> ngram = extractMap(analyser);

        assertEquals(3,analyser.getDistinctNgramCount());
        assertEquals(7,(int)ngram.get("a"));
        assertEquals(2,(int)ngram.get("b"));
        assertEquals(3,(int)ngram.get("c"));
    }

    @Test(timeout=1000,expected = IllegalArgumentException.class) //TODO exception catch
    public void testNullString()
    {
        NgramAnalyser analyser = new NgramAnalyser(1, null);
    }

    @Test(timeout=1000,expected = IllegalArgumentException.class) //TODO exception catch
    public void testEmptyString()
    {
        NgramAnalyser analyser = new NgramAnalyser(3, "");
    }

    @Test(timeout=1000,expected = IllegalArgumentException.class) //TODO exception catch
    public void testInvalidNtooLow()
    {
        NgramAnalyser analyser = new NgramAnalyser(0, "test");
    }

    @Test(timeout=1000,expected = IllegalArgumentException.class) //TODO exception catch
    public void testInvalidNtooHigh()
    {
        NgramAnalyser analyser = new NgramAnalyser(5, "test");
    }

    @Test(timeout=1000)
    public void testBiGrams()
    {
        NgramAnalyser analyser = new NgramAnalyser(2, "aabcabaacaac");
        assertEquals(3,analyser.getAlphabetSize());
        assertEquals(6,analyser.getDistinctNgramCount() );
        HashMap<String, Integer> ngram = extractMap(analyser);

        assertEquals(3,(int) ngram.get("aa"));
        assertEquals(2,(int) ngram.get("ab"));
        assertEquals(2,(int) ngram.get("ac"));
        assertEquals(1,(int) ngram.get("ba"));
        assertEquals(1,(int) ngram.get("bc"));
        assertEquals(3,(int) ngram.get("ca"));

    }

    @Test(timeout=1000)
    public void testTriGrams() {
        NgramAnalyser analyser = new NgramAnalyser(3,"aabcabaacaac");

        assertEquals(3,analyser.getAlphabetSize());
        assertEquals(9,analyser.getDistinctNgramCount() );

        HashMap<String, Integer> ngram = extractMap(analyser);

        assertEquals(1,(int)ngram.get("aab"));  
        assertEquals(2,(int)ngram.get("aac"));
        assertEquals(1,(int)ngram.get("aba"));
        assertEquals(1,(int)ngram.get("abc"));
        assertEquals(2,(int)ngram.get("aca"));
        assertEquals(1,(int)ngram.get("baa"));
        assertEquals(1,(int)ngram.get("bca"));
        assertEquals(2,(int)ngram.get("caa"));
        assertEquals(1,(int)ngram.get("cab"));
    }

    @Test(timeout=1000)
    public void testNgramCount() {
        NgramAnalyser analyser;
        String[] inputTexts = { "WXyZ", "abc", "aabcabaacaac" };
        int[] ngramSizes = {1, 2, 3}; 

        for (String inputText : inputTexts) {
            for (int n : ngramSizes) {
                analyser = new NgramAnalyser(n,inputText);
                assertEquals( inputText.length(), analyser.getNgramCount());
            }
        }
    }

    @Test(timeout=1000)
    public void fieldsUnaltered() {
        NgramAnalyser analyser = new NgramAnalyser(1,"aa");
        Class<? extends NgramAnalyser> clazz = analyser.getClass();
        assertEquals("should be no public fields", 0, clazz.getFields().length );
        int nf = clazz.getDeclaredFields().length;
        assertTrue("should be at least 2 fields", nf >= 2);
        //assertEquals("should be 2 fields total", 2, clazz.getDeclaredFields().length );
        //allow more than this

        try {
            Field ngramField = clazz.getDeclaredField("ngram");
            Field alphField = clazz.getDeclaredField("alphabetSize");
        } catch (NoSuchFieldException |  SecurityException e) {
            throw new RuntimeException(e);
        }
    }

   
 }

