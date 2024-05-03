package graphs.interfaces;

import java.util.List;

/**
 * Интерфейс для делигирования метода рассчета весов
 */
public interface WeightCalculator {

    double[] calculate(List<String> inputString);
}
