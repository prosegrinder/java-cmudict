package com.prosegrinder.cmudict;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class CmuDictTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public final void testGetPhonemes() {
    CmuDict cmuDict = new CmuDict();
    List<String> frowningPhonemes = Arrays.asList("F", "R", "AW1", "N", "IH0", "NG");
    assertEquals("frowning:", frowningPhonemes, cmuDict.getPhonemes("frowning"));
    List<String> zurkuhlenPhonemes = Arrays.asList("Z", "ER0", "K", "Y", "UW1", "L", "AH0", "N");
    assertEquals("zurkuhlen:", zurkuhlenPhonemes, cmuDict.getPhonemes("zurkuhlen"));
    List<String> cafePhonemes = Arrays.asList("K", "AH0", "F", "EY1");
    assertEquals("cafe:", cafePhonemes, cmuDict.getPhonemes("cafe"));
  }

}
