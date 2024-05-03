package graphs.interfaces;

/**
 * Интерфейс для стратегии метода очистки от стоп-слов
 */
public interface StopWords {

    /**
     * Метод для очистки ввода от стоп-слов
     *
     * @param input Входная строка
     * @return true если слово значащее, false если оно в стоп-листе
     */
    boolean clean(String input);
}
