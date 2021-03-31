package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class GermanTextSummarizer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPGerman;

  public GermanTextSummarizer(StanfordCoreNLP stanfordCoreNLPGerman, StopWordsCache stopWordsCache) {
    super(stopWordsCache.getByLang(Language.DE));
    this.stanfordCoreNLPGerman = stanfordCoreNLPGerman;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPGerman.processToCoreDocument(text);
  }
}
