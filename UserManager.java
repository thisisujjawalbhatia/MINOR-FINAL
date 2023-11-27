
import java.io.*;
import java.util.*;

public class UserManager {

    private HashMap<String, String> userCredentials = new HashMap<>();
    private String dataFilePath = "user_credentials.txt";

    public void registerUser(String username, String password) throws IOException {
        if (!userCredentials.containsKey(username)) {
            userCredentials.put(username, password);
            saveData();
            System.out.println("User registration successful.");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    public boolean loginUser(String username, String password) throws IOException {
        if (userCredentials.containsKey(username)) {
            String storedPassword = userCredentials.get(username);
            if (password.equals(storedPassword)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Incorrect password.");
                return false;
            }
        } else {
            System.out.println("User not found. Please register or try again with a different username.");
            return false;
        }
    }

    private void saveData() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath, true))) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        }
    }

    private void loadData() throws IOException {
        userCredentials.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                userCredentials.put(parts[0], parts[1]);
            }
        }
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        try {
            userManager.loadData();

            System.out.println("Enter 'register' to register a new user or 'login' to log in:");
            String choice = scanner.nextLine();

            if (choice.equals("register")) {
                System.out.println("Enter username:");
                String username = scanner.nextLine();

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                userManager.registerUser(username, password);
            } else if (choice.equals("login")) {
                System.out.println("Enter username:");
                String username = scanner.nextLine();

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                if (userManager.loginUser(username, password)) {
                    // User is logged in. Perform authorized actions
                } else {
                    // User login failed
                }
            } else {
                System.out.println("Invalid choice. Please enter 'register' or 'login'.");
            }
        } catch (IOException e) {
            System.out.println("Error accessing user data file.");
            e.printStackTrace();
        }
    }
}
