package com.nhat.structurizebe.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Base64;

public class ImageUtil {

    public static String encodeImageToBase64(String imagePath) throws IOException {
        // Read the image from the file
        BufferedImage image = ImageIO.read(new File(imagePath));

        // Convert the image to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Encode the byte array to a Base64 string
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Main method to test the ImageUtil class
    public static void main(String[] args) {
        String path = "D:\\Dev\\Workspace\\Java\\TextureEncode\\WIP";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        StringBuilder base64Images = new StringBuilder();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        String base64String = encodeImageToBase64(file.getPath());
                        base64Images.append(file.getName()).append(": ").append(base64String).append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Write the Base64 strings to a text file
        try (FileWriter writer = new FileWriter(path + "/base64Images.txt")) {
            writer.write(base64Images.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}