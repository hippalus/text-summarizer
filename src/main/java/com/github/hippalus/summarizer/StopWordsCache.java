package com.github.hippalus.summarizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class StopWordsCache {

  private final ConcurrentMap<Language, ConcurrentHashSet<String>> stopWordsMap = new ConcurrentHashMap<>();

  public StopWordsCache() {
    loadCache();
  }

  private void loadCache() {
    for (Language language : Language.values()) {
      if (language.isKnown()) {
        String pathName = String.format("stopwords-%s.txt", language.getValue());
        try {
          stopWordsMap.put(language, readFileLines(pathName));
          log.info("{} language stop words loaded from {}", language, pathName);
        } catch (Exception ex) {
          log.error("Stop words cache could not be loaded", ex);
          throw new RuntimeException("Stop words cache could not be loaded", ex);
        }
      }
    }
  }

  @NotNull
  @SneakyThrows
  public ConcurrentHashSet<String> readFileLines(@NotNull String path) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(findURLInClassLoader(path).openStream()))) {
      return reader.lines().map(String::toLowerCase).collect(Collectors.toCollection(ConcurrentHashSet::new));
    }
  }

  public ConcurrentHashSet<String> getByLang(Language language) {
    return stopWordsMap.getOrDefault(language, new ConcurrentHashSet<>());
  }

  public static URL findURLInClassLoader(String name) {
    URL url = ClassLoader.getSystemResource(name);
    if (url != null) {
      return url;
    }

    url = ClassLoader.getSystemResource(name.replaceAll("\\\\", "/"));
    if (url != null) {
      return url;
    }

    url = ClassLoader.getSystemResource(name.replaceAll("\\\\", "/").replaceAll("/+", "/"));
    if (url != null) {
      return url;
    }

    url = StopWordsCache.class.getClassLoader().getResource(name);
    if (url != null) {
      return url;
    }

    url = StopWordsCache.class.getClassLoader().getResource(name.replaceAll("\\\\", "/"));
    if (url != null) {
      return url;
    }

    url = StopWordsCache.class.getClassLoader().getResource(name.replaceAll("\\\\", "/").replaceAll("/+", "/"));
    return url;
  }

}