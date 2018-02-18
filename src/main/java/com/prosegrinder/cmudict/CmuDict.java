package com.prosegrinder.cmudict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class CmuDict {

  /** Concurrent Hash Map for storing Cmudict lines. **/
  private static final Map<String, String> phonemeStringMap =
      new ConcurrentHashMap<String, String>();

  /** Patterns used to find stressed syllables in cmudict (phonemes that end in a digit). **/
  private static final Pattern cmudictSyllablePattern = Pattern.compile("\\d$");

  private static final Logger logger = LoggerFactory.getLogger(CmuDict.class);


  public static final getDict() {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("cmusphix/cmudict/cmudict.dict");
    try {
      // Updated based on: https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      Stream<String> stream = reader.lines();
      stream.filter(line -> !line.startsWith(";;;")).forEach(line -> {
        String[] parts = line.split("\\s+", 2);
        String wordString = parts[0];
        phonemeStringMap.put(wordString, parts[1]);
      });
    } catch (NullPointerException npe) {
      logger.warn("CMU Dictionary file not found: " + Dictionary2.cmudictFile);
    }
  }

}
