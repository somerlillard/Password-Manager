package assn07;


import java.util.*;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> passwordManager = new PasswordManager<>();
        // Checking master password
        System.out.println("Enter Master Password");
        String master = scanner.nextLine();
        while (passwordManager.checkMasterPassword(master) == false) {
            System.out.println("Enter Master Password");
            master = scanner.nextLine();
        }
       // System.out.println("Enter Command: ");
        String command = scanner.nextLine();
        while (!command.equals("Exit")) {
            switch (command) {
                case "New password": {
                String website = scanner.nextLine();
                String password = scanner.nextLine();
                passwordManager.put(website, password);
                System.out.println("New password added");
                break;
                }
                case "Get password": {
                String website = scanner.nextLine();
                if (passwordManager.get(website) == null) {
                    System.out.println("Account does not exist");
                }
                else {
                    System.out.println(passwordManager.get(website));
                }
                break;
                }
                case "Delete account": {
                String website = scanner.nextLine();
                if (passwordManager.remove(website) == null) {
                    System.out.println("Account does not exist");
                }
                else {
                    passwordManager.remove(website);
                    System.out.println("Account deleted");
                }
                break;
                }
                case "Check duplicate password": {
                String website = scanner.nextLine();
                if (!passwordManager.checkDuplicate(website).isEmpty()) {
                    System.out.println("Websites using that password:");
                    List duplicates = passwordManager.checkDuplicate(website);
                    Iterator itr = duplicates.iterator();
                    while (itr.hasNext()) {
                        System.out.println(itr.next());
                    }
                }
                else {
                    System.out.println("No account uses that password");
                }
                break;
                }
                case "Get accounts": {
                    System.out.println("Your accounts:");
                    Set accounts = passwordManager.keySet();
                    Iterator itr = accounts.iterator();
                    while (itr.hasNext()) {
                        System.out.println(itr.next());
                    }
                    break;
                }
                case "Generate random password": {
                int length = scanner.nextInt();
                System.out.println(passwordManager.generateRandomPassword(length));
                break;
                }
                default: {
                    System.out.println("Command not found");
                }
            }
            command = scanner.nextLine();
        }
    }
}
