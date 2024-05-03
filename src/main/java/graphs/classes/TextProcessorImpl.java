package graphs.classes;

import graphs.interfaces.Stemmer;
import graphs.interfaces.TextProcessor;
import graphs.interfaces.WeightCalculator;

import java.util.ArrayList;
import java.util.List;

public class TextProcessorImpl implements TextProcessor {

    Stemmer stemmer;

    WeightCalculator weightCalculator;

    Boolean needStem = false;

    /*public TextProcessorImpl(Stemmer stemmer){
        this.stemmer = stemmer;
    }*/

    public TextProcessorImpl(Stemmer stemmer, WeightCalculator weightCalculator){
        this.stemmer = stemmer;
        this.weightCalculator = weightCalculator;
    }

    @Override
    public List<List<String>> unicSplitter(List<String> inputText, String splitterWord) {
        List<List<String>> result = new ArrayList<>();
        List<String> locList = new ArrayList<>();

        for (String word : inputText){
            if (!word.equals(splitterWord))
                locList.add(word);
            else if (!locList.isEmpty()) {
                result.add(locList);
                locList = new ArrayList<>();
            }
        }
        if (!locList.isEmpty())
            result.add(locList);

        //for (List<String> list : result)
        //    System.out.println(list);

        return result;
    }

    @Override
    public double[] weightCalculator(List<String> inputString) {
        return weightCalculator.calculate(inputString);
    }

    @Override
    public String clean(String inputString) {
        //String pattern = "(?U)[\\pP\\s]";
        String pattern = "\\p{Punct}|«»|\n";

        return inputString.toLowerCase().replaceAll(pattern, "");
    }

    @Override
    public String stem(String input) {
        if (needStem) return stemmer.stem(input);
        return input.toLowerCase();
    }

    @Override
    public void setWeightCalculator(WeightCalculator weightCalculator) {
        this.weightCalculator = weightCalculator;
    }

    @Override
    public void setUsingStemmer(Boolean needUse) {
        needStem = needUse;
    }

}
