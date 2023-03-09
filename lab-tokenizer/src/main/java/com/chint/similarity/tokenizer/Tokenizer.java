package com.chint.similarity.tokenizer;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer {
    private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);

    public static List<Word> segment(String sentence) {
        List<Word> results = new ArrayList<>();
        List<Term> termList = HanLP.segment(sentence);
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.word, term.nature.toString()))
                .collect(Collectors.toList())
        );
        return results;
    }
}
