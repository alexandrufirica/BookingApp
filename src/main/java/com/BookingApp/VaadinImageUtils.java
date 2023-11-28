package com.BookingApp;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VaadinImageUtils {

    public static Image convertBufferedImageToImage(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return new Image(); // Return an empty image if the input is null
        }

        // Convert BufferedImage to byte array
        byte[] imageBytes = convertBufferedImageToBytes(bufferedImage);

        // Create a StreamResource from the byte array
        StreamResource resource = new StreamResource("image.png", () -> new ByteArrayInputStream(imageBytes));

        // Create the Vaadin Image component
        Image vaadinImage = new Image(resource, "alt text");

        return vaadinImage;
    }

    private static byte[] convertBufferedImageToBytes(BufferedImage bufferedImage) {
        // Implementation for converting BufferedImage to byte array
        // This may involve using ImageIO or other methods depending on your needs
        // Here's a simple example using ImageIO
        // Note: Exception handling should be added as needed
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
