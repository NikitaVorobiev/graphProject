package com.example.graph_project;

import graphs.interfaces.Stemmer;
import graphs.interfaces.StopWords;
import org.junit.jupiter.api.Test;
import graphs.utils.StemmerPorterRU;
import graphs.utils.StopWordsRu;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    void StemmerRuTest(){
        Stemmer stemmer = new StemmerPorterRU();

        assertEquals("идт", stemmer.stem("идти"));
        assertEquals("летет", stemmer.stem("лететь"));
        assertEquals("чрезвычайн", stemmer.stem("чрезвычайный"));
    }

    @Test
    void StopWordsRuTest() throws FileNotFoundException {
        StopWords stopWords = new StopWordsRu();

        stopWords.clean("");
    }
}
