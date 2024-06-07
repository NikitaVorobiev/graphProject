package com.example.graph_project;

import calculations.classes.CalculateClosestImpl;
import calculations.interfaces.CalculateClosest;
import graphs.classes.FirstIndexImpl;
import graphs.classes.TextProcessorImpl;
import graphs.interfaces.FirstIndex;
import graphs.interfaces.Stemmer;
import graphs.interfaces.TextProcessor;
import graphs.interfaces.WeightCalculator;
import graphs.utils.StemmerPorterRU;
import graphs.utils.WeightCalculatorByPercent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class GraphProjectApplication {

    public static synchronized void main(String[] args) throws IOException {

        Stemmer st = new StemmerPorterRU();
        WeightCalculator wc = new WeightCalculatorByPercent();
        CalculateClosest calculator = new CalculateClosestImpl();

        TextProcessor tp = new TextProcessorImpl(st, wc);
        FirstIndex fi = new FirstIndexImpl(tp);

        File file = new File("src/main/resources/dataset_1.txt");
        String inputText = Files.readString(file.toPath());

        SpringApplication.run(GraphProjectApplication.class, args);
        //System.out.println(inputText);

        fi.inputText(inputText);
        Map<String, Map<String, Double>> index = fi.getIndex();

        double result = calculator.calculate("учреждения ограничиваются вебприложений", index);

        System.out.println(result);
    }
}
