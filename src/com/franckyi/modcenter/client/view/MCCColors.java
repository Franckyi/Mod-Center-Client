package com.franckyi.modcenter.client.view;

import com.franckyi.modcenter.api.beans.enums.EnumFileType;

import javafx.scene.paint.Color;

public class MCCColors {

	public static final Color GREEN = Color.web("#8CAF62");
	public static final Color BLUE = Color.web("#7FA5C4");
	public static final Color RED = Color.web("#E49788");

	public static Color getColor(EnumFileType type) {
		if (type.equals(EnumFileType.RELEASE))
			return GREEN;
		if (type.equals(EnumFileType.BETA))
			return BLUE;
		if (type.equals(EnumFileType.ALPHA))
			return RED;
		return Color.WHITE;
	}

}
