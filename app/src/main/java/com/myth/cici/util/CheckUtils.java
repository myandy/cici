package com.myth.cici.util;

import android.R.integer;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.EditText;

import com.myth.cici.db.YunDatabaseHelper;

public class CheckUtils {

    public static void checkEditText(EditText edittext, String s) {
        String test = edittext.getEditableText().toString();
        int pos = -1;
        s = s.replaceAll("\\D", "");
        for (int i = 0; i < test.length(); i++) {
            if (OthersUtils.isChinese(test.charAt(i))) {
                pos++;
                if (pos >= s.length()) {
                    break;
                }
                int check = checkPingze(test.charAt(i), s.charAt(pos));
                if (check == 0) {
                    // 设置删除线
                    SpannableString ss = new SpannableString(edittext.getText());
                    ss.setSpan(new StrikethroughSpan() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.RED);
                        }
                    }, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    edittext.setText(ss);
                } else if (check == 30) {
                    SpannableString ss = new SpannableString(edittext.getText());
                    ss.setSpan(new ForegroundColorSpan(Color.GREEN), i, i + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    edittext.setText(ss);
                }
            }
        }
    }

    /**
     * 1验证成功 0验证失败 30 多音
     * 
     * @param hanzi
     * @param code
     * @return
     */
    private static int checkPingze(char hanzi, char code) {
        int intCode = Integer.parseInt(code + "");
        while (intCode > 3) {
            intCode -= 3;
        }
        if (intCode == 1 || intCode == 3) {
            int wordStone = YunDatabaseHelper.getWordStone(hanzi + "");

            if (wordStone == 30) {
                return 30;
            } else if (wordStone == 10 && intCode == 1) {
                return 1;
            } else if (wordStone == 20 && intCode == 3) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    public static String[] getCodeFormPingze(String[] list) {
        if (list != null && list.length > 0) {
            int size = list.length;
            if (TextUtils.isEmpty(list[size - 1].trim())) {
                size--;
            }
            String[] codes = new String[size];
            for (int i = 0; i < size; i++) {
                codes[i] = "";
                for (int j = 0; j < list[i].length(); j++) {
                    if (list[i].charAt(j) == '平') {
                        codes[i] += "1";
                    } else if (list[i].charAt(j) == '中') {
                        codes[i] += "2";
                    } else if (list[i].charAt(j) == '仄') {
                        codes[i] += "3";
                    } else if (list[i].charAt(j) == '（'
                            && list[i].charAt(j + 1) == '韵'
                            && list[i].charAt(j + 2) == '）') {
                        int x = Integer.parseInt(codes[i].charAt(codes[i]
                                .length() - 1) + "") + 3;
                        codes[i] = codes[i].substring(0, codes[i].length() - 1)
                                + x;
                    } else if (list[i].charAt(j) == '（'
                            && list[i].charAt(j + 1) == '增'
                            && list[i].charAt(j + 2) == '韵'
                            && list[i].charAt(j + 3) == '）') {
                        int x = Integer.parseInt(codes[i].charAt(codes[i]
                                .length() - 1) + "") + 6;
                        codes[i] = codes[i].substring(0, codes[i].length() - 1)
                                + x;
                    } else if (list[i].charAt(j) == '（'
                            || list[i].charAt(j) == '韵'
                            || list[i].charAt(j) == '增'
                            || list[i].charAt(j) == '）') {

                    } else {
                        codes[i] += list[i].charAt(j);
                    }
                }

            }
            return codes;
        }
        return null;
    }
}
