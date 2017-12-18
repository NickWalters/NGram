

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ProjectTest for student test cases.
 * Add all new test cases to this task.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ProjectTest
{
    /**
     * Default constructor for test class ProjectTest
     */
    public ProjectTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    //TODO add new test cases from here include brief documentation
    
     @Test(timeout=1000)
    public void testSensibleToStringSize() {
        NgramAnalyser testGram;
        testGram = new NgramAnalyser(2, "abbcdefg");
        
        assertTrue(testGram.getAlphabetSize() + 1 < testGram.toString().split("\n").length);
    }

   
    @Test(timeout=1000)
    public void testGetDistinctNgrams() {
         assertEquals(0,1); //TODO replace with test code
    }
    
    @Test(timeout=1000)
    public void testLaplaceExample() {
        MarkovModel markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.5, markovMo1.laplaceEstimate("aac"), 0.1);
        assertEquals(0.166666666666, markovMo1.laplaceEstimate("aaa"), 0.1);
        assertEquals(0.3333333333333, markovMo1.laplaceEstimate("aab"), 0.1);
    }
    
    @Test(timeout=1000)
    public void testSimpleExample() {
        MarkovModel markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.666666666666, markovMo1.simpleEstimate("aac"), 0.1);
        assertEquals(0.0, markovMo1.simpleEstimate("aaa"), 0.1);
        assertEquals(0.3333333333, markovMo1.simpleEstimate("aab"), 0.1);
    }


    @Test
    public void testTask3example() 
    {
        MarkovModel model = new MarkovModel(2,"aabcabaacaac");
        ModelMatcher match = new ModelMatcher(model,"aabbcaac");
        assertEquals(match.getAverageLogLikelihood(), -0.3849, 0.0001);
    }
}
