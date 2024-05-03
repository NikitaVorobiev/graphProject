package graphs.utils;

import graphs.interfaces.WeightCalculator;

import java.util.List;

public class WeightCalculatorByPercent implements WeightCalculator {

    @Override
    public double[] calculate(List<String> inputString) {
        double basePercent = 0.5; // 50 процентов распределяются до середины и после середины
        double minCount; // базовый процент, минимальный для середины
        double[] result = new double[inputString.size()];
        int size = inputString.size();

        int count = 0;
        if (size % 2 == 0){
            minCount = basePercent / calculateSize(size / 2);
            for (int i = size / 2; i > 0; i--) {
                result[count] = minCount * i;
                count++;
            }
            for (int i = 1; i <= size / 2; i++) {
                result[count] = minCount * i;
                count++;
            }
        } else {
            minCount = basePercent / calculateSize(size / 2 + 1);
            for (int i = size / 2; i > 0; i--) {
                result[count] = minCount * (i + 1);
                count++;
            }
            result[count] = minCount * 2; //TODO("Костыль, надо будет доделать")
            count++;
            for (int i = 1; i <= size / 2; i++) {
                result[count] = minCount * (i + 1);
                count++;
            }
        }

        //System.out.println(Arrays.toString(result));
        return result;
    }

    /**
     * Рассчитывает на сколько частей делить процент
     *
     * @param size размер массива
     * @return количество долей
     */
    private int calculateSize(int size){
        if (size == 1)
            return 1;

        return size + calculateSize(size - 1);
    }
}
