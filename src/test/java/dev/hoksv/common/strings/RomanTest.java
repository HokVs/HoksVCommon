package dev.hoksv.common.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RomanTest {

     @Test
     @DisplayName("Test roman numeral translation")
     void numeralOf() {
          assertEquals("III", Roman.numeralOf(3), "Should get the correct roman numeral");
      }

     @Test
     @DisplayName("Test roman numerals to int")
     void getValueOf() {
         assertEquals(4, Roman.getValueOf("IV"), "Should be 4 (IV)");
     }
}