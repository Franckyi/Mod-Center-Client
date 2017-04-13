package com.franckyi.mpb.core.curse;

public enum MCVersion {

	ALL("All", ""), MC1_11_2("1.11.2"), MC1_11("1.11"), MC1_10_2("1.10.2"), MC1_10("1.10"), MC1_9_4("1.9.4"), MC1_9(
			"1.9"), MC1_8_9("1.8.9"), MC1_8_8("1.8.8"), MC1_8_7("1.8.7"), MC1_8("1.8"), MC1_7_10(
					"1.7.10"), MC1_7_2("1.7.2"), MC1_6_4("1.6.4"), MC1_6_2("1.6.2"), MC1_5_2("1.5.2"), MC1_4_7("1.4.7");

	public String displayText, urlText;

	private MCVersion(String displayText, String urlText) {
		this.displayText = displayText;
		this.urlText = urlText;
	}

	private MCVersion(String text) {
		this.displayText = this.urlText = text;
	}

	public String toString() {
		return displayText;
	}

	public static MCVersion fromString(String text) {
		for (MCVersion v : values())
			if (v.urlText.equals(text))
				return v;
		return null;
	}

}
