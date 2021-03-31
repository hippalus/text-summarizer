package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class FrenchTextSummarizer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPFrench;

  public FrenchTextSummarizer(StanfordCoreNLP stanfordCoreNLPFrench, StopWordsCache stopWordsCache) {
    super(stopWordsCache.getByLang(Language.FR));
    this.stanfordCoreNLPFrench = stanfordCoreNLPFrench;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPFrench.processToCoreDocument(text);
  }
}
