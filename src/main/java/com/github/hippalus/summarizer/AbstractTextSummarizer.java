package com.github.hippalus.summarizer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTextSummarizer<T> implements TextSummarizer {

  protected AbstractTextSummarizer() {
  }

  @Override
  public final String extractSummary(@NotNull String fullText) {
    if (StringUtils.isBlank(fullText)) {
      return "";
    }
    var formattedText = preprocess(fullText);
    T boundedSource = getBoundedSource(formattedText);
    Map<String, Double> sentenceScores = calculateSentenceScores(boundedSource);
    int selectCount = sentenceScores.size() * 40 / 100;
    return joinMaxScoredText(sentenceScores, selectCount);
  }

  protected abstract T getBoundedSource(@NotNull String text);

  protected abstract Map<String, Double> calculateWordFrequencies(T type);

  protected abstract Map<String, Double> calculateSentenceScores(T type);

  @NotNull
  protected final String joinMaxScoredText(Map<String, Double> sentenceScores, int sentenceCount) {
    return sortByValue(sentenceScores)
        .keySet()
        .stream()
        .limit(sentenceCount)
        .collect(Collectors.joining(" "));
  }

  protected final void calculateWordFrequencies(Map<String, Double> wordFrequencies, String word) {
    if (!wordFrequencies.containsKey(word)) {
      wordFrequencies.put(word, 1.0);
    } else {
      double newFrequency = wordFrequencies.get(word) + 1.0;
      wordFrequencies.put(word, newFrequency);
    }
  }

  protected final void calculateSentenceScore(Map<String, Double> wordFrequencies, Map<String, Double> sentenceScores,
      String sentence, String word) {
    if (!sentenceScores.containsKey(sentence)) {
      sentenceScores.put(sentence, wordFrequencies.get(word));
    } else {
      Double sent = sentenceScores.get(sentence);
      Double freq = wordFrequencies.get(word);
      sentenceScores.put(sentence, sent + freq);
    }
  }


  public static Map<String, Double> sortByValue(final Map<String, Double> wordScores) {
    return wordScores.entrySet()
        .stream()
        .sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

}
