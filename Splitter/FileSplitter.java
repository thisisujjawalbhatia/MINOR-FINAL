package Splitter;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileSplitter {
    public static void splitAndStoreUserDetails(String userId, String[] outputFolders) {
        for (String folderPath : outputFolders) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        String inputFile = "personalDetails" + userId + ".txt";
        int totalParts = outputFolders.length + 1; // Including the original file

        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            long fileSize = fileChannel.size();
            long partSize = fileSize / totalParts;

            // Store the original file (part 1)
            String originalFilePath = outputFolders[0] + File.separator + "User" + userId + "part1.txt";
            try (FileOutputStream originalFileOutputStream = new FileOutputStream(originalFilePath);
                 FileChannel originalFileChannel = originalFileOutputStream.getChannel()) {

                long position = 0;
                long remainingBytes = partSize;
                fileChannel.transferTo(position, remainingBytes, originalFileChannel);
            }

            // Store the remaining parts (part 2 to part totalParts)
            for (int i = 1; i < totalParts; i++) {
                String outputFilePath = outputFolders[i - 1] + File.separator + "User" + userId + "part" + (i + 1) + ".txt";
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                     FileChannel outputChannel = fileOutputStream.getChannel()) {

                    long position = (i - 1) * partSize;
                    long remainingBytes = (i == totalParts - 1) ? (fileSize - position) : partSize;

                    fileChannel.transferTo(position, remainingBytes, outputChannel);
                }
            }

            System.out.println("User details divided into parts and stored in different folders successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Main {
    public static void main(String[] args) {
        String userId = "User1"; // Replace with the actual user ID
        String[] outputFolders = {
                "DestinationFolder1",
                "DestinationFolder2",
                "DestinationFolder3",
                "DestinationFolder4",
                "DestinationFolder5"
        };

        FileSplitter.splitAndStoreUserDetails(userId, outputFolders);
    }
}
