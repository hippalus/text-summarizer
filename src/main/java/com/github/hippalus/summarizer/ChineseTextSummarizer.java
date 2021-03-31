package com.github.hippalus.summarizer;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.jetbrains.annotations.NotNull;

public class ChineseTextSummarizer extends AbstractStanfordCoreNLPScorer {

  private final StanfordCoreNLP stanfordCoreNLPChinese;

  public ChineseTextSummarizer(StanfordCoreNLP stanfordCoreNLPChinese, StopWordsCache stopWords) {
    super(stopWords.getByLang(Language.ZH));
    this.stanfordCoreNLPChinese = stanfordCoreNLPChinese;
  }

  @Override
  protected CoreDocument getBoundedSource(@NotNull String text) {
    return stanfordCoreNLPChinese.processToCoreDocument(text);
  }
}
