package com.example.graph_project;

import graphs.classes.FirstIndexImpl;
import graphs.classes.TextProcessorImpl;
import graphs.interfaces.FirstIndex;
import graphs.interfaces.Stemmer;
import graphs.interfaces.TextProcessor;
import graphs.interfaces.WeightCalculator;
import org.junit.jupiter.api.Test;
import graphs.utils.StemmerPorterRU;
import graphs.utils.WeightCalculatorByPercent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstIndexImplTest {

    private Stemmer st = new StemmerPorterRU();
    private WeightCalculator wc = new WeightCalculatorByPercent();
    TextProcessor tp = new TextProcessorImpl(st, wc);

    @Test
    void inputTextTest(){
        FirstIndex fi = new FirstIndexImpl(tp);

        String trueText = "Why would you want to predict upcoming words, or assign probabilities to sentences?";

        assertTrue(fi.inputText(trueText));
    }

    @Test
    void getUnicWordsTest() {
        FirstIndex fi = new FirstIndexImpl(tp);
        String trueText = "Привет, Олег, Олег, мир";
        String[] expectedResult = new String[2];

        fi.inputText(trueText);
        List<String> result = fi.getUnicWords();
        System.out.println(result);

        assertEquals(expectedResult.length, result.size());
    }

    @Test
    void getWeightsTest() {
        FirstIndex fi = new FirstIndexImpl(tp);
        String mainWord = "мы";

        List<String> inputString = new ArrayList<>();
        inputString.add("привет");
        inputString.add("мир");
        inputString.add("дом");
        double[] inputWeight = new double[3];
        inputWeight[0] = 0.1;
        inputWeight[1] = 0.2;
        inputWeight[2] = 0.3;

        Map<String, Double> result = new HashMap<>();
        result.put("привет", 0.1);
        result.put("мир", 0.2);
        result.put("дом", 0.3);

        fi.updateWeights(mainWord, inputString, inputWeight);
        assertEquals(fi.getWeights(mainWord), result);
    }

    @Test
    void getOneWeightTest() {
        FirstIndex fi = new FirstIndexImpl(tp);
        String mainWord = "мы";

        List<String> inputString = new ArrayList<>();
        inputString.add("привет");
        inputString.add("мир");
        inputString.add("дом");
        double[] inputWeight = new double[3];
        inputWeight[0] = 0.1;
        inputWeight[1] = 0.2;
        inputWeight[2] = 0.3;

        double result = 0.2;

        fi.updateWeights(mainWord, inputString, inputWeight);
        assertEquals(fi.getOneWeight(mainWord, "мир"), result);
    }

    @Test
    void updateWeightsTest() {
        FirstIndex fi = new FirstIndexImpl(tp);
        String mainWord = "мы";

        List<String> inputString = new ArrayList<>();
        inputString.add("привет");
        inputString.add("мир");
        inputString.add("дом");
        double[] inputWeight = new double[3];
        inputWeight[0] = 0.1;
        inputWeight[1] = 0.2;
        inputWeight[2] = 0.3;

        Double resultBefore = 0.2;
        Double resultAfter = 0.5;

        fi.updateWeights(mainWord, inputString, inputWeight);
        assertEquals(fi.getIndex().get(mainWord).get("мир"), resultBefore);

        inputString = new ArrayList<>();
        inputString.add("привет");
        inputWeight = new double[1];
        inputWeight[0] = 0.4;

        fi.updateWeights(mainWord, inputString, inputWeight);
        assertEquals(fi.getIndex().get(mainWord).get("привет"), resultAfter);
    }

    @Test
    void getWordsTest(){
        FirstIndex fi = new FirstIndexImpl(tp);
        String input = "Мы любим животных. И стараемся поддерживать тех из них, кому не посчастливилось иметь - ласковых хозяев и тёплый кров.";
        List<String> expected = new ArrayList<>();

        fi.inputText(input);
        //expected.add("мы");

        assertEquals(9, fi.getWords().size());
    }
}
