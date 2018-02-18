package com.prosegrinder.cmudict;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmuDict {

  /** Concurrent Hash Map for storing Cmudict lines. **/
  private static final Map<String, String> phonemeStringMap =
      new ConcurrentHashMap<String, String>();

  /** Patterns used to find stressed syllables in cmudict (phonemes that end in a digit). **/
  private static final Pattern cmudictSyllablePattern = Pattern.compile("[012]$");

  private static final Logger logger = LoggerFactory.getLogger(CmuDict.class);


  public CmuDict() {
    if (CmuDict.phonemeStringMap.isEmpty()) {
      logger.info("Loading cmudict.dict.");
      CmuDict.loadPhonemeMap();
    }
  }

  public final Map<String, String> getPhonemeMap() {
    if (phonemeStringMap.isEmpty()) {
      loadPhonemeMap();
    }
    return CmuDict.phonemeStringMap;
  }


  private static final void loadPhonemeMap() {
    ClassLoader classLoader = CmuDict.class.getClassLoader();
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
      logger.error("CMU Dictionary file not found.");
    }
  }


 /**
   * Get cmudict phonemes for a word.
   *
   * @param wordString a single word
   * @return List of Stings representing the phonemes
   * @throws IllegalArgumentException thrown if the word is not in cmudict
   *
   */
  public final List<String> getPhonemes(final String wordString) throws IllegalArgumentException {
    return Arrays.asList(this.getPhonemeString(wordString).split("\\s+"));
  }

  /**
   * Get the number of syllables by looking up the word in the underlying cmudict.
   *
   * @param wordString a single word
   * @return the number of syllables in the word
   * @throws IllegalArgumentException throws if the word is not in the underlying dictionary
   *
   */
  public final String getPhonemeString(final String wordString) throws IllegalArgumentException {
    if (phonemeStringMap.containsKey(wordString)) {
      return phonemeStringMap.get(wordString);
    } else {
      String msg = "cmudict does not contain an entry for " + wordString + ".";
      throw new IllegalArgumentException(msg);
    }
  }

  /**
   * Test if a String is in the underlying cmudict dictionary.
   *
   * @param wordString a string representing a single word
   * @return boolean representing whether the word is found in the underlying dictionary
   *
   */
  public static final Boolean inCmudict(final String wordString) {
      return phonemeStringMap.containsKey(wordString);
  }

}
