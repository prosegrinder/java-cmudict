package com.prosegrinder.cmudict;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmuDict {

  /** Concurrent Hash Map for storing cmudict.dict. **/
  private static final Map<String, String> phonemeStringMap =
      new ConcurrentHashMap<String, String>();

  /** Concurrent Hash Map for storing cmudict.phones. **/
  private static final Map<String, String> phonesStringMap =
      new ConcurrentHashMap<String, String>();

  /** Concurrent Hash Map for storing cmudict.symbols. **/
  private static final List<String> symbolsList =
      new CopyOnWriteArrayList<String>();

  /** Concurrent Hash Map for storing cmudict.symbols. **/
  private static final Map<String, String> vpStringMap =
      new ConcurrentHashMap<String, String>();

  /**
   * Patterns used to find stressed syllables
   * in cmudict (phonemes that end in a digit).
   * **/
  private static final Pattern cmudictSyllablePattern =
      Pattern.compile("[012]$");

  private static final Logger logger =
      LoggerFactory.getLogger(CmuDict.class);


  public CmuDict() {
    if (CmuDict.phonemeStringMap.isEmpty()) {
      CmuDict.loadDict();
    }
    if (CmuDict.phonesStringMap.isEmpty()) {
      CmuDict.loadPhones();
    }
    if (CmuDict.symbolsList.isEmpty()) {
      CmuDict.loadSymbols();
    }
    if (CmuDict.vpStringMap.isEmpty()) {
      CmuDict.loadVp();
    }
  }

  public final Map<String, String> getPhonemeMap() {
    if (phonemeStringMap.isEmpty()) {
      loadDict();
    }
    return CmuDict.phonemeStringMap;
  }

  private static final void loadDict() {
    logger.info("Loading cmudict.dict");
    // ClassLoader classLoader = CmuDict.class.getClassLoader();
    // InputStream in = classLoader.getResourceAsStream(
    //       "cmusphinx/cmudict/cmudict.dict");
    // try {
    //   BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      Stream<String> stream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.dict");
      stream.forEach(line -> {
        String[] parts = line.split("\\s+", 2);
        String wordString = parts[0];
        phonemeStringMap.put(wordString, parts[1]);
      });
    // } catch (NullPointerException npe) {
    //   logger.error("CMU Dictionary file not found.");
    // }
  }

  private static final void loadPhones() {
    logger.info("Loading cmudict.phones");
    Stream<String> stream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.phones");
    stream.forEach(line -> {
      String[] parts = line.split("\\s+", 2);
      String phone = parts[0];
      phonesStringMap.put(phone, parts[1]);
    });
  }

  private static final void loadSymbols() {
    logger.info("Loading cmudict.symbols");
    Stream<String> stream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.symbols");
    stream.forEach(line -> {
      symbolsList.add(line);
    });
  }

  private static final void loadVp() {
    logger.info("Loading cmudict.vp");
    Stream<String> stream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.vp");
    stream.forEach(line -> {
      String[] parts = line.split("\\s+", 2);
      String punctuation = parts[0];
      phonemeStringMap.put(punctuation, parts[1]);
    });
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

  private static final String resourceAsString(final String resourceName) {
    String resourceString = new String();
    Stream<String> stream = CmuDict.resourceAsStream(resourceName);
    stream.forEach(line -> {
      resourceString.concat(line);
    });
    return resourceString;
  }

  private static final Stream<String> resourceAsStream(final String resourceName) {
    ClassLoader classLoader = CmuDict.class.getClassLoader();
    InputStream in = classLoader.getResourceAsStream(
          resourceName);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    Stream<String> resourceStream = reader.lines();
    return resourceStream;
  }

  /**
   * Get The CMU Dictionary license as a string.
   *
   * @return string of The CMU Dictionary license file
   *
   */
  public static final String getLicense() {
    return CmuDict.resourceAsString("cmusphinx/cmudict/LICENSE");
  }

}
