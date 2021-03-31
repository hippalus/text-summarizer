package com.github.hippalus.summarizer;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public interface TextSummarizer {

  TextSummarizer DEFAULT = fullText -> "";

  String extractSummary(@NotNull String fullText);

  default String preprocess(String text) {
    return StringUtils.isBlank(text) ? "" : removeSpecialCharacters(text);
  }

  static String removeSpecialCharacters(String text) {
    var txt = Objects.requireNonNull(text);
    return txt.replaceAll("\\[[0-9]*]", " ").replaceAll("\\s+", " ");
  }

}
