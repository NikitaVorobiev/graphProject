package calculations.classes;

import calculations.interfaces.CalculateClosest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculateClosestImpl implements CalculateClosest {

    @Override
    public double calculate(List<String> input, Map<String, Map<String, Double>> graph) {

        return (double) calculateVertex(input, graph) / input.size();
    }

    @Override
    public double calculate(String input, Map<String, Map<String, Double>> graph) {
        List<String> inputList = Arrays.stream(input.split(" ")).toList();

        return (double) calculateVertex(inputList, graph) / inputList.size();
    }

    @Override
    public boolean isForwardDirection(String wordFirst, String wordSecond, Map<String, Double> singleString) {

        if (!singleString.containsKey(wordFirst)) return true;
        if (!singleString.containsKey(wordSecond)) return false;

        return singleString.get(wordFirst) < singleString.get(wordSecond);
    }

    @Override
    public int calculateVertex(List<String> input, Map<String, Map<String, Double>> graph) {

        int count = 0;
        for (String vertex : input) {
            Map<String, Double> singleString = graph.get(vertex);

            int localCount = calculateFullDirection(input, singleString);
            if (localCount > count)
                count = localCount;
        }

        return count;
    }

    @Override
    public int calculateFullDirection(List<String> input, Map<String, Double> singleString) {

        String[] localInput = input.toArray(new String[0]);

        if (singleString == null || singleString.isEmpty()) return 0;

        int count = 0;
        int localCount = 0;
        for (int i = 0; i < localInput.length - 1; i++) {
        //for (int i = 1; i < localInput.length-1; i++) {
            if (isForwardDirection(localInput[i], localInput[i + 1], singleString))
                localCount++;
            else{
                if (localCount > count)
                    count = localCount;
                localCount = 0;
            }
        }

        return count;
    }
}
