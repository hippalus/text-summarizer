package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class ArabicTextSummarizer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPArabic;

  public ArabicTextSummarizer(StanfordCoreNLP stanfordCoreNLPArabic, StopWordsCache stopWordsCache) {
    super(stopWordsCache.getByLang(Language.AR));
    this.stanfordCoreNLPArabic = stanfordCoreNLPArabic;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPArabic.processToCoreDocument(text);
  }
}
