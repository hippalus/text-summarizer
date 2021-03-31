package com.github.hippalus.summarizer;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum Language {
  UNKNOWN("unknown"),
  AR("ar"),
  BG("bg"),
  CS("cs"),
  DA("da"),
  DE("de"),
  EN("en"),
  ES("es"),
  FI("fi"),
  FR("fr"),
  HU("hu"),
  ID("id"),
  IT("it"),
  KO("ko"),
  NB("nb"),
  NL("nl"),
  NO("no"),
  PL("pl"),
  PT("pt"),
  RU("ru"),
  SV("sv"),
  TH("th"),
  TR("tr"),
  ZH("zh");

  @Getter
  private final String value;

  Language(String language) {
    this.value = language;
  }

  public static Set<String> getValuesSet() {
    return Stream.of(Language.values()).map(Language::getValue).collect(Collectors.toSet());
  }

  public static Language from(String language) {
    return getValuesSet().contains(language) ? Language.valueOf(language.toUpperCase()) : Language.UNKNOWN;
  }

  public static Set<Language> from(@NotNull Set<String> language) {
    return language.stream().map(Language::from).collect(Collectors.toSet());
  }

  public boolean isUnknown() {
    return this == UNKNOWN;
  }

  public boolean isKnown() {
    return !isUnknown();
  }


}