import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;
import java.lang.Math;
import java.io.*;

/** Create and manipulate Markov models and model matchers for lists of training data 
 * a test data String and generate output from it for convenient display.
 * 
 * @author Nicholas Walters & Jesse Carter, 22243339 and 22277029
 * @version (30/05/2017)
 *
 */
public class MatcherController {

    /** list of training data string used to generate markov models */
    ArrayList<String> trainingDataList;
    /** test data to be matched with the models */
    String testData;
    /** order of the markov models*/
    int k;
    /** generated list of markov models for the given training data*/
    ArrayList<MarkovModel> modelList;
    /** generated list of matchers for the given markov models and test data*/
    ArrayList<ModelMatcher> matcherList;
   

    /** Generate models for analysis
     * @param k order of the markov models to be used
     * @param testData String to check against different models
     * @throw unchecked exceptions if the input order or data inputs are invalid
     */
    public MatcherController(int k, ArrayList<String> trainingDataList, String testData) 
    {
      
      this.trainingDataList = trainingDataList;
      this.testData = testData;
      this.k = k;
      
      modelList = new ArrayList<>();
      matcherList = new ArrayList<>();
      
      // generate markovModel and ModelMatcher from string in array
      for (String s : trainingDataList)
      {
          MarkovModel model1 = new MarkovModel(k, s);
          modelList.add(model1);
          
          ModelMatcher matcher1 = new ModelMatcher(model1, testData);
          matcherList.add(matcher1);       
      }
    }
    
    /*
     * Constructor accepting filename rather than preconstructed String array
     */
    public MatcherController(int k, String filename, String testData)
    {
        
        String fileString = getFileContents(filename);
        
        // filter each line from text file into new array
        trainingDataList = new ArrayList<>(Arrays.asList(fileString.split("\n")));
        
        this.testData = testData;
        this.k = k;
        
        modelList = new ArrayList<>();
        matcherList = new ArrayList<>();
        
        for (String s : trainingDataList)
        {
          MarkovModel model1 = new MarkovModel(k, s);
          modelList.add(model1);
          
          ModelMatcher matcher1 = new ModelMatcher(model1, testData);
          matcherList.add(matcher1);       
        }
    }
    


    /** @return a string containing all lines from a file 
     * ff file contents can be got, otherwise null
     * This method should process any exceptions that arise.
     */
    private  static String getFileContents(String filename) 
    {
        String fileContents = "";
        try {
            ArrayList<String> lineArray = FileIO.readFile(filename);
            for (String s: lineArray)
            {
            fileContents = fileContents + s + "\n";
            }
        } catch (java.io.FileNotFoundException e){
            System.out.println("Filename not found"); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileContents);
        
        return fileContents;
    }

 

    /**
     * @return the ModelMatcher object that has the highest average loglikelihood
     * (where all candidates are trained for the same test string
     */
    public ModelMatcher getBestMatch(ArrayList<ModelMatcher> candidates) 
    {
        ModelMatcher highestModelMatcher = candidates.get(0);
        double highestAverage = candidates.get(0).getAverageLogLikelihood();
        // for each ModelMatcher...
        for(ModelMatcher model: candidates)
        {
            // get the average...
            double average = model.getAverageLogLikelihood();
            // if higher than the current highest...
            if(average < highestAverage)
            {
                highestAverage = average;
                highestModelMatcher = model;
            }
        }
        return highestModelMatcher;
    }

    /** @return String an *explanation* of
     * why the test string is the match from the candidate models
     */
    public String explainBestMatch(ModelMatcher best) {
        String explanation = "The best match for the test string recorded a value of " + getBestMatch(matcherList).getAverageLogLikelihood() 
            + ", which is the highest out of all the candidate strings. This implies a strong connection between the test string and chosen model";
        return explanation;
    }

    /** Display an error to the user in a manner appropriate
     * for the interface being used.
     * 
     * @param message
     */
    public void displayError(String message) {
        // LEAVE THIS METHOD EMPTY
    }

}