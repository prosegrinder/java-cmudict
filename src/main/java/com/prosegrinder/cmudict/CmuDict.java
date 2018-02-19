package com.prosegrinder.cmudict;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmuDict {

  private static final Logger logger =
      LoggerFactory.getLogger(CmuDict.class);

  public static final Map<String, String> getDict() {
    Stream<String> stream = CmuDict.getDictStream();
    Map<String, String> dictMap = new HashMap<String, String>();
    stream.forEach(line -> {
      String[] parts = line.split("\\s+", 2);
      dictMap.put(parts[0], parts[1]);
    });
    return dictMap;
  }

  public static final Stream<String> getDictStream() {
    Stream<String> dictStream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.dict");
    return dictStream;
  }

  public static final String getDictString() {
    String dictString = CmuDict.resourceAsString("cmusphinx/cmudict/cmudict.dict");
    return dictString;
  }

  public static final Map<String, String> getPhones() {
    logger.info("Loading cmudict.phones");
    Stream<String> stream = CmuDict.getPhonesStream();
    Map<String, String> phonesMap = new HashMap<String, String>();
    stream.forEach(line -> {
      String[] parts = line.split("\\s+", 2);
      phonesMap.put(parts[0], parts[1]);
    });
    return phonesMap;
  }

  public static final Stream<String> getPhonesStream() {
    Stream<String> phonesStream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.phones");
    return phonesStream;
  }

  public static final String getPhonesString() {
    String phonesString = CmuDict.resourceAsString("cmusphinx/cmudict/cmudict.phones");
    return phonesString;
  }

  public static final List<String> getSymbols() {
    logger.info("Loading cmudict.phones");
    Stream<String> stream = CmuDict.getPhonesStream();
    List<String> symbolsList = new ArrayList<String>();
    stream.forEach(line -> {
      symbolsList.add(line);
    });
    return symbolsList;
  }

  public static final Stream<String> getSymbolsStream() {
    Stream<String> symbolsStream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.symbols");
    return symbolsStream;
  }

  public static final String getSymbolsString() {
    String symbolsString = CmuDict.resourceAsString("cmusphinx/cmudict/cmudict.symbols");
    return symbolsString;
  }

  public static final Map<String, String> getVp() {
    Stream<String> stream = CmuDict.getVpStream();
    Map<String, String> vpMap = new HashMap<String, String>();
    stream.forEach(line -> {
      String[] parts = line.split("\\s+", 2);
      String wordString = parts[0];
      vpMap.put(wordString, parts[1]);
    });
    return vpMap;
  }

  public static final Stream<String> getVpStream() {
    Stream<String> vpStream = CmuDict.resourceAsStream("cmusphinx/cmudict/cmudict.vp");
    return vpStream;
  }

  public static final String getVpString() {
    String vpString = CmuDict.resourceAsString("cmusphinx/cmudict/cmudict.vp");
    return vpString;
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

  private static final Stream<String> resourceAsStream(final String resourceName) {
    ClassLoader classLoader = CmuDict.class.getClassLoader();
    InputStream in = classLoader.getResourceAsStream(
          resourceName);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    Stream<String> resourceStream = reader.lines();
    return resourceStream;
  }

  private static final String resourceAsString(final String resourceName) {
    String resourceString = new String();
    Stream<String> stream = CmuDict.resourceAsStream(resourceName);
    stream.forEach(line -> {
      resourceString.concat(line);
    });
    return resourceString;
  }

}
