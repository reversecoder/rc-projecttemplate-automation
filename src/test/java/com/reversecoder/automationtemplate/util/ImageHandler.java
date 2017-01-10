package com.reversecoder.automationtemplate.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.Point;

public class ImageHandler {

	public class Color {
		public int red;
		public int green;
		public int blue;

		public Color(int r, int g, int b) {
			red = r;
			green = g;
			blue = b;
		}

		public Boolean isWithinRed(int r, Color deviation) {
			if (this.red >= (r - deviation.red)
					&& this.red <= (r + deviation.red))
				return true;
			else
				return false;
		}

		public Boolean isWithinGreen(int g, Color deviation) {
			if (this.green >= (g - deviation.green)
					&& this.green <= (g + deviation.green))
				return true;
			else
				return false;
		}

		public Boolean isWithinBlue(int b, Color deviation) {
			if (this.blue >= (b - deviation.blue)
					&& this.blue <= (b + deviation.blue))
				return true;
			else
				return false;
		}
	}

	private final Color green = new Color(98, 188, 26);
	private final Color orange = new Color(255, 134, 36);
	private final Color blue = new Color(23, 125, 180);
	private final Color deviation = new Color(5, 5, 5);
	private final int whiteRGBValue = 0xFFFFFFFF;

	public Boolean isGreenCircle(ScreenShot screenshot, int x, int y)
			throws IOException {
		int rgb = getRGBValueFromImagePixel(screenshot, x, y);
		return green.isWithinRed(getRedValue(rgb), deviation)
				&& green.isWithinGreen(getGreenValue(rgb), deviation)
				&& green.isWithinBlue(getBlueValue(rgb), deviation);
	}

	public Boolean isOrangeCircle(ScreenShot screenshot, int x, int y)
			throws IOException {
		int rgb = getRGBValueFromImagePixel(screenshot, x, y);
		return orange.isWithinRed(getRedValue(rgb), deviation)
				&& orange.isWithinGreen(getGreenValue(rgb), deviation)
				&& orange.isWithinBlue(getBlueValue(rgb), deviation);
	}

	public Boolean isBluePixel(int rgb)
			throws IOException {
		return blue.isWithinRed(getRedValue(rgb), deviation)
				&& blue.isWithinGreen(getGreenValue(rgb), deviation)
				&& blue.isWithinBlue(getBlueValue(rgb), deviation);
	}

	public Boolean isBluePixel(Color color)
			throws IOException {
		return ((color.blue - color.red) > 50) ? true : false;
	}

	public Boolean isWhitePoint(ScreenShot screenshot, int x, int y)
			throws IOException {
		return whiteRGBValue == getRGBValueFromImagePixel(screenshot, x, y);
	}

	public int getRGBValueFromImagePixel(ScreenShot screenshot, int x, int y)
			throws IOException {
		BufferedImage bi = ImageIO.read(screenshot.getFile());
		return bi.getRGB(x, y);
	}

	public int getRedValue(int rgb) {
		return (rgb >> 16) & 0xFF;
	}

	public int getGreenValue(int rgb) {
		return (rgb >> 8) & 0xFF;
	}

	public int getBlueValue(int rgb) {
		return rgb & 0xFF;
	}

	public Point getLinkTappingPoint(ScreenShot screenshot) throws IOException {
		BufferedImage bi = ImageIO.read(screenshot.getFile());
		for (int x = 0; x < bi.getWidth(); x++)
			for (int y = 0; y < bi.getHeight(); y++) {
				int rgb = bi.getRGB(x, y);
//				if(getBlueValue(rgb) > 150 && getBlueValue(rgb) < 210)
//					System.out.println("[" + x + ", " + y + "] = " + getRedValue(rgb) + ", " + getGreenValue(rgb) + ", " + getBlueValue(rgb));
				Color color = new Color(getRedValue(rgb), getGreenValue(rgb), getBlueValue(rgb));
				if(isBluePixel(color)) return new Point(x, y);
			}
		return new Point(0, 0);
	}

	public Point getLinkTappingPointOnOmronWellnessScreen(ScreenShot screenshot, int fraction) throws IOException {
		BufferedImage bi = ImageIO.read(screenshot.getFile());
		for (int y = ((bi.getHeight() -1) / fraction); y > 0; y--)
			for (int x = 0; x < bi.getWidth(); x++) {
				int rgb = bi.getRGB(x, y);
				Color color = new Color(getRedValue(rgb), getGreenValue(rgb), getBlueValue(rgb));
//				if((color.blue - color.red) > 50)
//					System.out.println("[" + x + ", " + y + "] = " + getRedValue(rgb) + ", " + getGreenValue(rgb) + ", " + getBlueValue(rgb));
				if(isBluePixel(color)) return new Point(x, y);
			}
		return new Point(0, 0);
	}

	public static void main(String args[]) throws IOException {
		System.out.println(new ImageHandler().getLinkTappingPoint(new ScreenShot("a")));
	}
}
