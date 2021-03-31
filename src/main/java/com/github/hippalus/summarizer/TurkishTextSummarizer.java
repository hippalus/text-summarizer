package com.github.hippalus.summarizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import zemberek.tokenization.Token;
import zemberek.tokenization.TurkishSentenceExtractor;
import zemberek.tokenization.TurkishTokenizer;

public class TurkishTextSummarizer extends AbstractTextSummarizer<String> {

  private final TurkishTokenizer turkishTokenizer;
  private final TurkishSentenceExtractor turkishSentenceExtractor;
  private final ConcurrentHashSet<String> stopWords;

  public TurkishTextSummarizer(StopWordsCache stopWordsCache, TurkishTokenizer turkishTokenizer,
      TurkishSentenceExtractor turkishSentenceExtractor) {
    this.turkishTokenizer = turkishTokenizer;
    this.turkishSentenceExtractor = turkishSentenceExtractor;
    this.stopWords = stopWordsCache.getByLang(Language.TR);
  }

  @Override
  protected String getBoundedSource(@NotNull String text) {
    return text;
  }

  @Override
  protected Map<String, Double> calculateWordFrequencies(String text) {
    Map<String, Double> wordFrequencies = new HashMap<>();
    turkishTokenizer.tokenize(text).stream()
        .map(Token::getText)
        .filter(Objects::nonNull)
        .filter(this::notContainsStopWords)
        .forEach(word -> calculateWordFrequencies(wordFrequencies, word));

    Double maxFrequency = Collections.max(wordFrequencies.values());
    wordFrequencies.replaceAll((word, frequency) -> wordFrequencies.get(word) / maxFrequency);
    return wordFrequencies;
  }


  @NotNull
  protected Map<String, Double> calculateSentenceScores(String fullText) {
    Map<String, Double> wordFrequencies = calculateWordFrequencies(fullText);
    Map<String, Double> sentenceScores = new HashMap<>();
    List<String> sentenceList = turkishSentenceExtractor.fromParagraph(fullText);
    for (String sentence : sentenceList) {
      turkishTokenizer.tokenize(sentence.toLowerCase())
          .stream()
          .map(Token::getText)
          .filter(Objects::nonNull)
          .filter(wordFrequencies::containsKey)
          .filter(word -> sentence.split(" ").length < 30)
          .forEach(word -> calculateSentenceScore(wordFrequencies, sentenceScores, sentence, word));
    }
    return sentenceScores;
  }

  private boolean containsStopWords(String word) {
    return StringUtils.isNotBlank(word) && stopWords.contains(word.toLowerCase());
  }

  private boolean notContainsStopWords(String word) {
    return !containsStopWords(word);
  }

}