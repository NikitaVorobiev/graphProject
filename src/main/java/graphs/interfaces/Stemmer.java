package graphs.interfaces;

/**
 * Стеммер слов
 */
public interface Stemmer {

    /**
     * Метод стемминга
     *
     * @param word входное слово
     * @return вывод после обработки слова
     */
    String stem(String word);
}
