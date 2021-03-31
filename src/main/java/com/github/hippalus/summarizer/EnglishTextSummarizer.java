package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class EnglishTextSummarizer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPEnglish;

  public EnglishTextSummarizer(StanfordCoreNLP stanfordCoreNLPEnglish, StopWordsCache stopWordsCache) {
    super(stopWordsCache.getByLang(Language.EN));
    this.stanfordCoreNLPEnglish = stanfordCoreNLPEnglish;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPEnglish.processToCoreDocument(text);
  }

}