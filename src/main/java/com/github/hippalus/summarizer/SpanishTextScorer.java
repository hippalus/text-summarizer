package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class SpanishTextScorer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPSpanish;

  public SpanishTextScorer(StanfordCoreNLP stanfordCoreNLPSpanish, StopWordsCache stopWordsCache) {
    super(stopWordsCache.getByLang(Language.ES));
    this.stanfordCoreNLPSpanish = stanfordCoreNLPSpanish;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPSpanish.processToCoreDocument(text);
  }


}