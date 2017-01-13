package com.reversecoder.automationtemplate.util;

import io.appium.java_client.AppiumDriver;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.PNGDecodeParam;
import com.sun.media.jai.codec.SeekableStream;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ScreenshotHandler {
    public ScreenShot takeScreenShot(ScreenShot screenshot, AppiumDriver driver) {
        return takeScreenShot(screenshot, driver, null);
    }

    public ScreenShot takeScreenShot(ScreenShot screenshot, AppiumDriver driver, WebElement paramWebElement) {
        try {
            if (driver == null)
                return screenshot;
            TakesScreenshot temp = (TakesScreenshot) driver;
            File file = temp.getScreenshotAs(OutputType.FILE);
            if (file.exists()) {
                File saveFile = new File(getScreenShotsDirectory() + "/" + screenshot.getScreenShotFile() + ".png");
                if (saveFile.exists()) {
                    saveFile.delete();
                }
                if (paramWebElement != null) {
                    writeElementImage(file, paramWebElement, saveFile);
                } else {
                    FileUtils.copyFile(file, saveFile);
                }
                if (saveFile.exists())
                    screenshot.setFile(saveFile);
            }
        } catch (Exception e) {
        }
        return screenshot;
    }

    private String getScreenShotsDirectory() {
        File file = new File(Constants.SCREENSHOT_LOCATION);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    private void writeElementImage(File srcFile, WebElement paramWebElement, File dstFile) throws Exception {
        SeekableStream seekableStream = new FileSeekableStream(srcFile);
        PNGDecodeParam pngParams = new PNGDecodeParam();
        ImageDecoder dec = ImageCodec.createImageDecoder("png", seekableStream, pngParams);
        RenderedImage pngImage = dec.decodeAsRenderedImage();
        BufferedImage inputBufferedImage = convertRenderedImage(pngImage);
        Point localPoint = paramWebElement.getLocation();
        int paramWidth = paramWebElement.getSize().getWidth();
        int paramHeight = paramWebElement.getSize().getHeight();
        BufferedImage outputBufferedImage = inputBufferedImage.getSubimage(localPoint.getX(), localPoint.getY(),
                paramWidth, paramHeight);
        ImageIO.write(outputBufferedImage, "png", dstFile);
    }

    private BufferedImage convertRenderedImage(RenderedImage img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable<String, Object> properties = new Hashtable<>();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                properties.put(keys[i], img.getProperty(keys[i]));
            }
        }
        BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
        img.copyData(raster);
        return result;
    }
}
