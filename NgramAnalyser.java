import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;  
    /** 
     * Analyse the frequency with which distinct n-grams, of length n,
     * appear in an input string. 
     * n-grams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     * @Jesse Carter 22277029 & Nick Walters 22243339
     * @Version 1 25th May '17
     */
public class NgramAnalyser
{
    /** dictionary of all distinct n-grams and their frequencies */
    private HashMap<String,Integer> ngram;

    /** number of distinct characters in the input */
    private int alphabetSize;

    /** n-gram size for this object (new field) */
    private int ngramSize;
    

    /** 
     * Analyse the frequency with which distinct n-grams, of length n,
     * appear in an input string. 
     * n-grams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     */
    public NgramAnalyser(int n, String inp) 
    {
        if ((n <= 0) || (inp == null) || (inp == "") || (n > inp.length()))
        {
            throw new IllegalArgumentException(
              "Invalid Input Parameters");
        } else {
  
                                  
        boolean processed = false;
        ngram = new HashMap<>();
        alphabetSize = 0;
        int nSize = n;
        ngramSize = 0;
        int finalIndex = 0;
        ArrayList<String> tempList = new ArrayList<>();
      
        // add each ngram until reaches point of string-wraparound
        for(int i=0; i<inp.length()-(nSize - 1); i++)
        {
            tempList.add(inp.substring(i,i+nSize));
            if(i == (inp.length()- nSize))
            // if i (the index) has reached the boundary limit ( before it gets an error), then...
            {
                finalIndex = i;
                // The following block of code checks if most of the ngrams are in the array already (processed most of the ngrams)
                // It also adds the remaining ngrams that wrap around to the start, and adds them to the arraylist.
                for(int c=1; c<(nSize); c++)
                {
                    String startString = inp.substring(finalIndex+c, inp.length());
                    String endString = inp.substring(0, c);
                    tempList.add(startString + endString);
                } 
            }
        }

        
       
        // code for counting the ngrams and sorting them
        for(String item: tempList)
        {
            //if key exist increment value by 1, if does not exist return 0
            int itemCount = ngram.getOrDefault(item, 0);
            ngram.put(item, itemCount+1);
            ngramSize += 1;
        }
      
        //convert strings in hash set to char array and hash set, updates alphabetsize
        char[] ngramChars = inp.toCharArray();
        Set<Character> validCharacters = new HashSet<>();
        for(char character: ngramChars)
        {
            validCharacters.add(character);
        }
        alphabetSize = validCharacters.size();
        }
    }

   
    
    /** 
     * Analyses the input text for n-grams of size 1.
     */
    public NgramAnalyser(String inp) 
    {
       this(1, inp); 
    }
    
    /**
     * Default constructor with example values
     */
    public NgramAnalyser()
    {
        this(2, "hello");
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() 
    {
        return alphabetSize;
    }

    /**
     * @return the total number of distinct n-grams appearing
     *         in the input text.
     */
    public int getDistinctNgramCount() 
    {        
        return ngram.size();
    }

    /** 
     * @return Return a set containing all the distinct n-grams
     *         in the input string.
     */
    public Set<String> getDistinctNgrams() 
    {
        Object[] array = ngram.keySet().toArray();
        Set<String> distinctNgrams = new HashSet<>();
        for(Object ngram: array)
        {
            String item = ngram + "";
            distinctNgrams.add(item);
        }
        return distinctNgrams;
    }

    /**
     * @return the total number of n-grams appearing
     *         in the input text (not requiring them to be distinct)
     */
    public int getNgramCount() {
        return ngramSize;
    }

    /** Return the frequency with which a particular n-gram appears
     * in the text. If it does not appear at all, return 0.
     * 
     * @param ngram The n-gram to get the frequency of
     * @return The frequency with which the n-gram appears.
     */
    public int getNgramFrequency(String ngram) 
    {        
        String s = ngram;
        if (this.ngram.get(s) != null){
            return this.ngram.get(s);
        } else {
            return 0;
        }
    }



    /**
     * Generate a summary of the ngrams for this object.
     * @return a string representation of the n-grams in the input text 
     * comprising the ngram size and then each ngram and its frequency
     * where ngrams are presented in alphabetical order.     
     */
    public String toString()
    {       
        String[] toStrArray = new String[ngram.size()];
        Set sets = ngram.entrySet();
        Iterator setsIterator = sets.iterator();
        int i = 0;
        String toStr = ngram.size() + "";
        while(setsIterator.hasNext())
        {
            Map.Entry strMapper = (Map.Entry) setsIterator.next();
            
            toStrArray[i] = strMapper.getKey().toString() + " " + strMapper.getValue();                      
            i++;
        }
        
        Arrays.sort(toStrArray, String.CASE_INSENSITIVE_ORDER);
               
        for(String c : toStrArray)
        {
            toStr += "\n" + c;
        }        
        return toStr;
    }
}

