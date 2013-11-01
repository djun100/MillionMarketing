package com.yameng.bean;

import com.yamtz.millionmarketing.R;

public class Expressions {

	public static int[] expressionImgs = new int[] { R.drawable.f000,
			R.drawable.f001, R.drawable.f002, R.drawable.f003, R.drawable.f004,
			R.drawable.f005, R.drawable.f006, R.drawable.f007, R.drawable.f008,
			R.drawable.f009, R.drawable.f010, R.drawable.f011, R.drawable.f012,
			R.drawable.f013, R.drawable.f014, R.drawable.f015, R.drawable.f016,
			R.drawable.f017, R.drawable.f018, R.drawable.f019, R.drawable.f020,
			R.drawable.f021, R.drawable.f022, R.drawable.f023 };

	public static String[] expressionImgNames = new String[] { "[f000]",
			"[f001]", "[f002]", "[f003]", "[f004]", "[f005]", "[f006]",
			"[f007]", "[f008]", "[f009]", "[f010]", "[f011]", "[f012]",
			"[f013]", "[f014]", "[f015]", "[f016]", "[f017]", "[f018]",
			"[f019]", "[f020]", "[f021]", "[f022]", "[f023]" };
	public static String[] expressionRegImgNames = new String[] {
			"\\U0001F601", "f0asd01", "f00asd2", "fasd003", "f0gf04", "f00fg5",
			"f0gfdh06", "fhjgh007", "f0gh08", "ffgh009", "f010", "f011",
			"f012", "f013", "f014", "f015", "f016", "f017", "f05err18",
			"f045fd19", "f0234sdf20", "fsdfg021", "f0jjjh22", "f0hjh23" };

	public static String[] replaceStrings(String[] str, String[] str2) {
		String newStr[] = new String[str.length - 1];
		for (int i = 0; i < str.length; i++) {
			newStr[i] = str[i].replace(str[i], str2[i]);
		}
		return newStr;
	}

}
