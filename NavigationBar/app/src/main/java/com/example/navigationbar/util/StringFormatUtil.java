package com.example.navigationbar.util;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatUtil {

        public static SpannableString getHighLightKeyWord(String color, String text, String keyword) {
            SpannableString s = new SpannableString(text);
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return s;
        }


}
