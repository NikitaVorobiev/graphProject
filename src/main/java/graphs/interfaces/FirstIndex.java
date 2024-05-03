package graphs.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Структура данных для хранения прямого индекса
 */
public interface FirstIndex {

    /**
     * Загружает текст для обработки
     *
     * @param input входной текст
     * @return true если загрузка успешная, false если не успешная
     */
    Boolean inputText(String input);

    /**
     * Получить список всех уникальных слов индекса
     *
     * @return список уникальных слов индекса
     */
    List<String> getUnicWords();

    /**
     * Получить весь контекст по слову
     *
     * @param word слово, по которому получается контекст
     * @return мапа с контекстом слова
     */
    Map<String, Double> getWeights(String word);

    /**
     * Получить значение веса для конкретной единицы контекста для конкретного слова
     *
     * @param mainWord основное слово
     * @param associatedWord слово в его контексте
     * @return значение веса
     */
    double getOneWeight(String mainWord, String associatedWord);

    /**
     * Обновляет веса в матрице
     * Матрица представляет собой массив мап, ключ-слово / значение-вес. Матрице соответствует массив, обозначающий
     * основные слова, для которых записывается контекст.
     *
     * @param word слово, для которого по данным обновляются веса
     * @param inputString входные слова, для которых нужно обновить веса
     * @param newWeights веса, которые соответствуют входным словам
     */
    void updateWeights(String word, List<String> inputString, double[] newWeights);

    /**
     * Получить лист всех слов текста
     *
     * @return список всех слов
     */
    List<String> getWords();

    /**
     * Получить индекс
     * Ключ - уникальное слово, значение - карта из слов и весов
     * Слова составляют контекст, веса - отдаление (вероятность перехода)
     *
     * @return индекс
     */
    Map<String, Map<String, Double>> getIndex();
    //ConcurrentHashMap<String, Map<String, Double>> getIndex();
}
