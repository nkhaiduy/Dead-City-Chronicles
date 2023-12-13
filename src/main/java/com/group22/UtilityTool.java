package com.group22;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The UtilityTool class provides utility functions to manipulate images.
 * Currently, it provides a function to scale images to a specified size.
 */
public class UtilityTool {
    /**
     * Scales an image to the specified width and height.
     * @param original The original BufferedImage to be scaled.
     * @param width The desired width to scale the image to.
     * @param height The desired height to scale the image to.
     * @return A new BufferedImage object of the specified width and height with the original image's content scaled accordingly.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Target width and height must be > 0");
        }
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
