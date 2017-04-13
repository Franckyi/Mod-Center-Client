package com.franckyi.mpb.core.curse;

public enum SortFilter {

	MONTHLY_DL("Monthly Downloads", 1), LIKED("Most Liked", 2), UPDATED("Recently Updated", 3), NEWEST("Newest",
			4), TOTAL_DL("Total Downloads", 5), GAME_TITLE("Game Title", 6);

	public String text;
	public int value;

	SortFilter(String text, int value) {
		this.text = text;
		this.value = value;
	}

	public String toString() {
		return text;
	}

}
