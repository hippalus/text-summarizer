package com.github.hippalus.summarizer;


import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractStanfordCoreNLPScorer extends AbstractTextSummarizer<CoreDocument> {

  protected final ConcurrentHashSet<String> stopWords;

  protected AbstractStanfordCoreNLPScorer(ConcurrentHashSet<String> stopWords) {
    super();
    this.stopWords = stopWords;
  }

  @Override
  protected Map<String, Double> calculateWordFrequencies(CoreDocument document) {
    Map<String, Double> wordFrequencies = new HashMap<>();
    document.tokens().stream()
        .map(CoreLabel::word)
        .filter(Objects::nonNull)
        .filter(this::notContainsStopWords)
        .forEach(word -> calculateWordFrequencies(wordFrequencies, word));

    Double maxFrequency = Collections.max(wordFrequencies.values());
    wordFrequencies.replaceAll((word, frequency) -> wordFrequencies.get(word) / maxFrequency);
    return wordFrequencies;
  }

  @Override
  protected Map<String, Double> calculateSentenceScores(CoreDocument document) {
    Map<String, Double> wordFrequencies = calculateWordFrequencies(document);
    Map<String, Double> sentenceScores = new HashMap<>();
    List<CoreSentence> sentences = document.sentences();
    for (CoreSentence sentence : sentences) {
      sentence.tokens()
          .stream()
          .map(CoreLabel::word)
          .filter(Objects::nonNull)
          .filter(wordFrequencies::containsKey)
          .filter(word -> sentence.text().split(" ").length < 30)
          .forEach(word -> calculateSentenceScore(wordFrequencies, sentenceScores, sentence.text(), word));
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