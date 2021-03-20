import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final String ALPHABETS = "^[a-zA-Z]*$";
    Scanner scan = new Scanner(System.in);

    public String getName() {

        System.out.println("Insert your name:");
        boolean check = true;
        String name = "";

        do {
            name = scan.next();
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) >= '0' && name.charAt(i) <= '9') {
                    System.out.println("Invalid name, insert your name with alphabets:");
                    check = true;
                    break;
                }
                else {
                    check = false;
                }
            }
        } while(check);

        return name;
    }

    public int getId() {
        System.out.println("Insert your id:");
        boolean check = true;
        String id = "";

        do {
            id = scan.next();
            if (id.matches("[0-9]+")) {
                check = false;
            } else {
                System.out.println("Invalid id, insert your id with digits: ");
            }
        } while(check);

        return Integer.parseInt(id);
    }

    public boolean getExit() {
        System.out.println("To continue playing press (C), other key will exit game");
        String answer = scan.next();
        return !answer.equals("C") && !answer.equals("c");
    }

    public void showInsults(ArrayList<String> insults) {
        System.out.println("Insert the number of insult:");
        for (int i = 0; i <= insults.size() - 1; i++) {
            System.out.println(i + 1 + ". " + insults.get(i));
        }
    }

    public void showComebacks(ArrayList<String> comebacks) {
        System.out.println("Insert the number of comeback:");
        for (int i = 0; i <= comebacks.size() - 1; i++) {
            System.out.println(i + 1 + ". " + comebacks.get(i));
        }
    }

    public int getOption(ArrayList<String> list) {
        int option;

        do {
            option = scan.nextInt();
            if (option >= 0 && option <= list.size()){
                break;
            }else{
                System.out.println("Invalid option, try again: ");
            }
        } while(true);

        return option - 1;
    }
}
