package com.example.graph_project;

import graphs.classes.TextProcessorImpl;
import graphs.interfaces.Stemmer;
import graphs.interfaces.TextProcessor;
import graphs.interfaces.WeightCalculator;
import org.junit.jupiter.api.Test;
import graphs.utils.StemmerPorterRU;
import graphs.utils.WeightCalculatorByPercent;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextProcessorImplTest {

    private Stemmer st = new StemmerPorterRU();
    private WeightCalculator weightCalculator = new WeightCalculatorByPercent();
    private TextProcessor tp = new TextProcessorImpl(st, weightCalculator);

    @Test
    void unicSplitterTest() {
        List<String> trueText = new ArrayList<>();
        String splitterWord = "меня";
        List<List<String>> expectedResult = new ArrayList<>();

        trueText.add("меня");
        trueText.add("привет");
        trueText.add("меня");
        trueText.add("зовут");
        trueText.add("олег");
        trueText.add("меня");
        trueText.add("работать");
        trueText.add("зовут");

        List<String> firstArr = new ArrayList<>();
        firstArr.add("привет");
        List<String> secArr = new ArrayList<>();
        secArr.add("зовут");
        secArr.add("олег");
        List<String> thirdArr = new ArrayList<>();
        thirdArr.add("работать");
        thirdArr.add("зовут");

        expectedResult.add(firstArr);
        expectedResult.add(secArr);
        expectedResult.add(thirdArr);

        assertEquals(expectedResult, tp.unicSplitter(trueText, splitterWord));
    }

    @Test
    void weightCalculatorTest() {
        List<String> input1 = new ArrayList<>();
        List<String> input2 = new ArrayList<>();

        double[] expectedResult1 = new double[6];
        expectedResult1[0] = 0.25;
        expectedResult1[1] = 0.16666666666666666;
        expectedResult1[2] = 0.08333333333333333;
        expectedResult1[3] = 0.08333333333333333;
        expectedResult1[4] = 0.16666666666666666;
        expectedResult1[5] = 0.25;
        double[] expectedResult2 = new double[5];
        expectedResult2[0] = 0.25;
        expectedResult2[1] = 0.16666666666666666;
        expectedResult2[2] = 0.16666666666666666;
        expectedResult2[3] = 0.16666666666666666;
        expectedResult2[4] = 0.25;

        input1.add("");
        input1.add("");
        input1.add("");
        input1.add("");
        input1.add("");
        input1.add("");

        input2.add("");
        input2.add("");
        input2.add("");
        input2.add("");
        input2.add("");

        assertEquals(expectedResult1[0], tp.weightCalculator(input1)[0]);
        assertEquals(expectedResult2[2], tp.weightCalculator(input2)[2]);
    }

    @Test
    void cleanTest() {
        String input1 = "Why would you want to predict upcoming words, or assign probabilities to sentences?";
        String input2 = "heLLo, privEt!23,_ %@()\".;@ kl \n wow";

        String expectedResult1 = "why would you want to predict upcoming words or assign probabilities to sentences";
        String expectedResult2 = "hello privet23  kl  wow";

        assertEquals(expectedResult1, tp.clean(input1));
        assertEquals(expectedResult2, tp.clean(input2));
    }

}
