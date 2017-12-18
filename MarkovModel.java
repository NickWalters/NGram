import java.util.Set;
/**
 * Construct a Markov model of order /k/ based on an input string.
 * 
 * @Jesse Carter 22277029 & Nick Walters 22243339
 * @Version 1 25th May '17
 */
public class MarkovModel
{

    /** Markov model order parameter */
    int k; 
    /** ngram model of order k */
    NgramAnalyser ngram; 
    /** ngram model of order k+1 */
    NgramAnalyser n1gram; 

    /**
     * Construct an order-k Markov model from string s
     * @param k int order of the Markov model
     * @param s String input to be modelled
     */
    public MarkovModel(int k, String s) 
    {
        this.k = k;        
        ngram = new NgramAnalyser(k, s);
        n1gram = new NgramAnalyser(k+1, s);
    }
    
    public MarkovModel()
    {
        this(2, "aabcabaacaac");
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        return k;
    }

    /** Estimate the probability of a sequence appearing in the text 
     * using simple estimate of freq seq / frequency front(seq).
     * @param sequence String of length k+1
     * @return double probability of the last letter occuring in the 
     * context of the first ones or 0 if front(seq) does not occur.
     */
    public double simpleEstimate(String sequence) 
    {
       if ((sequence.length() != k+1) || (sequence.equals("")) || sequence == null) {
            throw new IllegalArgumentException(
                "Invalid Input Parameters, Please enter a sequence of greater length");
       } else {
        double probability = 0.0;
        
        // find frequency of specific sequence and separately the last character's preceding characters        
        int sequenceFreq = n1gram.getNgramFrequency(sequence);       
        int preFreq = ngram.getNgramFrequency(sequence.substring(0,sequence.length()-1));
                
        probability = (double) sequenceFreq / (double) preFreq;
                
        return probability;
       }
    }
    
    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @input sequence String of length k+1
     */
    public double laplaceEstimate(String sequence) 
    { 
        double probability = 0.0;
        int sequenceFreq = n1gram.getNgramFrequency(sequence);
        int preFreq = ngram.getNgramFrequency(sequence.substring(0,sequence.length()-1));
        
        probability = ((double) sequenceFreq + 1) / ((double) preFreq + ngram.getAlphabetSize());
                
        return probability;
    }

    /**
     * @return String representing this Markov model
     */
    public String toString()
    {
       String toStr = k + "\n" + ngram.getAlphabetSize() + "\n" + ngram.toString() + "\n" + n1gram.toString();
       return toStr;
    }
    

}

