package calculations.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Класс для расчета ближайшего графа
 */
public interface CalculateClosest {

    /**
     * Метод для расчета приближения ввода к контекстному графу методом наибольшего общего подграфа
     *
     * @param input список слов ввода пользователя
     * @param graph контекстный граф
     * @return значение приближения от 0 до 1
     */
    double calculate(List<String> input, Map<String, Map<String, Double>> graph);

    /**
     * Метод для определения направления в контекстом графе между двумя словами
     *
     * @param wordFirst первое слово
     * @param wordSecond второе слово
     * @param graph контекстный граф
     * @return true если направление прямое (слова в графе идут последовательно), false если обратное
     */
    boolean isForwardDirection(String wordFirst, String wordSecond, Map<String, Map<String, Double>> graph);

    /**
     * Метод для расчета числа вершин наибольшего общего подграфа
     *
     * @param input список слов ввода пользователя
     * @param graph контекстный граф
     * @return число вершин графа
     */
    int calculateVertex(List<String> input, Map<String, Map<String, Double>> graph);

}
