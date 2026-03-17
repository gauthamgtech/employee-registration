package com.example.demo.util;

import java.util.Locale;
import com.ibm.icu.text.RuleBasedNumberFormat;

public class NumberToWordsUtil {

    public static String convertToWords(int amount) {

        RuleBasedNumberFormat formatter =
                new RuleBasedNumberFormat(Locale.ENGLISH,
                RuleBasedNumberFormat.SPELLOUT);

        return formatter.format(amount) + " Only";
    }
}