import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public static String generateSHA256Hash(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash;

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            hash = digest.digest();
        }

        return convertToHexString(hash);
    }

    private static String convertToHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length != 1) {
            System.out.println("Usage: java HashGenerator <userNumber>");
            System.exit(1);
        }

        int userNumber = Integer.parseInt(args[0]);
        String filePath = "./mergedFileUser" + userNumber + ".txt";
        
        try {
            String hash = generateSHA256Hash(filePath);
            System.out.println("SHA-256 hash for merged file '" + filePath + "':");
            System.out.println(hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error generating hash: " + e.getMessage());
        }
    }
}
