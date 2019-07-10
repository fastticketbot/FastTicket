package me.kavin.fastticket.utils;

import java.awt.Color;

public class ColorUtils {
	public static Color getRainbowColor(int speed) {
		float hue = (System.currentTimeMillis()) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 1f, 1f);
	}
}
