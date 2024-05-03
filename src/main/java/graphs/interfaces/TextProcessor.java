package graphs.interfaces;

import java.util.List;

/**
 * Интерфейс для утилитарных действий в структуре данных
 */
public interface TextProcessor {

    /*
     * Текст перекидывается в стек слов
     * В цикле:
     *  Для каждого слова текст бьется на массивы по слову из стека
     *  Массив делится пополам, по его длине рассчитывается удаление процента по факториалу
     *  Создается матрица где каждому слову сопоставляется процент, следующее вхождение его увеличивает
     */

    /**
     * Для каждого слова текст бьется на массивы по слову из стека
     *
     * @param inputText входной текст
     * @param splitterWord слово для разделения
     * @return массив слов из стека
     */
    List<List<String>> unicSplitter(List<String> inputText, String splitterWord);

    /**
     * Массив делится пополам, по его длине рассчитывается удаление процента по факториалу
     * создается массив процентов, соответствующих словам
     *
     * @param inputString входная строка, разбитая на слова
     * @return массив процентов, соответствующий строке
     */
    double[] weightCalculator(List<String> inputString);

    /**
     * Очистка текстов от знаков препинания
     *
     * @param inputString входная строка
     * @return строка без знаков препинания
     */
    String clean(String inputString);

    /**
     * Стемминг слов
     *
     * @param input входное слово
     * @return основа слова
     */
    String stem(String input);

    /**
     * Сеттер метода рассчета весов
     *
     * @param weightCalculator Стратегия рассчета веса
     */
    void setWeightCalculator(WeightCalculator weightCalculator);

    /**
     * Сеттер флага использования стеммера
     *
     * @param needUse true если стеммер нужно использовать, false если не нужно
     */
    void setUsingStemmer(Boolean needUse);
}
