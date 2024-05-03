package graphs.classes;

import graphs.interfaces.FirstIndex;
import graphs.interfaces.StopWords;
import graphs.interfaces.TextProcessor;
import graphs.utils.StopWordsRu;

import java.util.*;

public class FirstIndexImpl implements FirstIndex {

    /**
     * Экземпляр текстового процессора
     */
    TextProcessor textProcessor;

    /**
     * Текст для обработки
     */
    List<String> workText;

    /**
     * Лист уникальных слов
     */
    Set<String> unicWords;

    /**
     * Мапа, содержащая результаты работы
     * Ключ - слово, по нему получаются пары слово-вес
     */
    Map<String, Map<String, Double>> index;

    /**
     * Делегирование очистки от стоп-слов
     */
    StopWords stopWords;

    /**
     * Количество потоков
     */
    int parts = 8;

    /**
     * ОН ПОЛНОСВЯЗНЫЙ
     */

    public FirstIndexImpl(TextProcessor textProcessor){
        this.textProcessor = textProcessor;
        index = new HashMap<>();
        workText = new ArrayList<>();
        stopWords = new StopWordsRu();
    }

    public FirstIndexImpl(TextProcessor textProcessor, String text){
        this.textProcessor = textProcessor;
        index = new HashMap<>();
        workText = new ArrayList<>();
        stopWords = new StopWordsRu();
        inputText(text);
    }

    public FirstIndexImpl(TextProcessor textProcessor, String text, StopWords stopWords){
        this.textProcessor = textProcessor;
        index = new HashMap<>();
        workText = new ArrayList<>();
        this.stopWords = stopWords;
        inputText(text);
    }

    @Override
    public Boolean inputText(String input) {
        String inputClean = textProcessor.clean(input); //ввод очищается от знаков препинания и переводится в нижний
        String[] inputSplit = inputClean.split(" "); //ввод бьется на слова и выделяются основы
        List<Set<String>> unicWordsSplitted;
        List<Runnable> threads = new ArrayList<>();

        for (String word : inputSplit){ //сохранение слов в список слов текста
            if (!word.equals("") && stopWords.clean(word))
                workText.add(textProcessor.stem(word));
        }

        unicWords = new HashSet<>(workText); // сохранение списка уникальных слов текста
        unicWordsSplitted = setSplitter(unicWords, parts);

        /*int tmp = 1;
        for (String word : unicWords){ // для каждого уникального слова разбивается текст и обновляются веса
            List<List<String>> splittedWords = textProcessor.unicSplitter(workText, word);

            for (List<String> singleString : splittedWords){
                double[] weights = textProcessor.weightCalculator(singleString);
                updateWeights(word, singleString, weights);
            }

            System.out.println(tmp++ + " / " + unicWords.size());
        }*/

        for (Set<String> unicWord : unicWordsSplitted){
            threads.add(() -> {
                //System.out.println("ПОТОК ОДИН СТАРТ");
                for (String word : unicWord){ // для каждого уникального слова разбивается текст и обновляются веса
                    List<List<String>> splittedWords = textProcessor.unicSplitter(workText, word);

                    for (List<String> singleString : splittedWords){
                        double[] weights = textProcessor.weightCalculator(singleString);
                        updateWeights(word, singleString, weights);
                    }
                }
                //System.out.println("ПОТОК ОДИН ФИНИШ");
            });
        }

        threads.stream().parallel().forEach(Runnable::run);

        System.out.println("ПОТОКИ ОДИН ОТРАБОТАЛИ");

        System.out.println(index);
        return true;
    }

    @Override
    public List<String> getUnicWords() {

        return unicWords.stream().toList();
    }

    @Override
    public Map<String, Double> getWeights(String word) {
        if (!index.containsKey(word))
            return null;

        return index.get(word);
    }

    @Override
    public double getOneWeight(String mainWord, String associatedWord) {
        if (!index.containsKey(mainWord))
            return 0;
        if (!index.get(mainWord).containsKey(associatedWord))
            return 0;

        return index.get(mainWord).get(associatedWord);
    }

    @Override
    public void updateWeights(String word, List<String> inputString, double[] newWeights) {
        if (!index.containsKey(word)){
            index.put(word, new HashMap<>());
        }
        int count = 0;
        Map<String, Double> locMap = index.get(word);

        for (String input : inputString){
            if (locMap.containsKey(input)) {
                double locWeight = locMap.get(input) + newWeights[count];

                locMap.replace(input, locWeight);
            } else locMap.put(input, newWeights[count]);

            count++;
        }

        //System.out.println(index);
    }

    @Override
    public List<String> getWords() {

        //System.out.println(workText);
        return workText;
    }

    @Override
    public Map<String, Map<String, Double>> getIndex() {

        return index;
    }

    private List<Set<String>> setSplitter(Set<String> input, int parts){
        List<Set<String>> result = new ArrayList<>();
        for (int i = 0; i < parts; i++)
            result.add(new HashSet<>());

        int locPart = 0;
        for (String word : input){
            if (locPart == parts)
                locPart = 0;
            result.get(locPart).add(word);
            locPart++;
        }

        return result;
    }
}
