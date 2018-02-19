package com.prosegrinder.cmudict;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CmuDictTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  // @Test
  // public final void testGetPhonemes() {
  //   CmuDict cmuDict = new CmuDict();
  //   List<String> frowningPhonemes = Arrays.asList("F", "R", "AW1", "N", "IH0", "NG");
  //   assertEquals("frowning:", frowningPhonemes, cmuDict.getPhonemes("frowning"));
  //   List<String> zurkuhlenPhonemes = Arrays.asList("Z", "ER0", "K", "Y", "UW1", "L", "AH0", "N");
  //   assertEquals("zurkuhlen:", zurkuhlenPhonemes, cmuDict.getPhonemes("zurkuhlen"));
  //   List<String> cafePhonemes = Arrays.asList("K", "AH0", "F", "EY1");
  //   assertEquals("cafe:", cafePhonemes, cmuDict.getPhonemes("cafe"));
  // }

  @Test
  public final void testDict() {
    int EXPECTED_SIZE = 135086;
    Map<String, String> dict = CmuDict.getDict();
    assertEquals(EXPECTED_SIZE, dict.size());
    int EXPECTED_LENGTH = 3615935;
    String dictString = CmuDict.getDictString();
    assertEquals(EXPECTED_LENGTH, dictString.length());
  }

  @Test
  public final void testPhones() {
    int EXPECTED_ENTRIES = 39;
    Map<String, String> phones = CmuDict.getPhones();
    assertEquals(EXPECTED_ENTRIES, phones.size());
    int EXPECTED_LENGTH = 381;
    String phonesString = CmuDict.getPhonesString();
    assertEquals(EXPECTED_LENGTH, phonesString.length());
  }

  @Test
  public final void testSymbols() {
    int EXPECTED_SIZE = 84;
    List<String> symbols = CmuDict.getSymbols();
    assertEquals(EXPECTED_SIZE, symbols.size());
    int EXPECTED_LENGTH = 280;
    String symbolsString = CmuDict.getSymbolsString();
    assertEquals(EXPECTED_LENGTH, symbolsString.length());
  }

  @Test
  public final void testVp() {
    int EXPECTED_SIZE = 54;
    Map<String, String> vp = CmuDict.getVp();
    assertEquals(EXPECTED_SIZE, vp.size());
    int EXPECTED_LENGTH = 1746;
    String vpString = CmuDict.getVpString();
    assertEquals(EXPECTED_LENGTH, vpString.length());
  }

  @Test
  public final void testLicense() {
    int EXPECTED_LENGTH = 1753;
    String license = CmuDict.getLicense();
    assertEquals(EXPECTED_LENGTH, license.length());
  }

}
