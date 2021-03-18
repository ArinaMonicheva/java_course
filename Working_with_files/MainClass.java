import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class MainClass {  // demonstration

    public static void main(String []args) {
        Scanner input = new Scanner(System.in);
        MyFileReader newReader = new MyFileReader();
        String fileName = "";
        boolean success = false;
        System.out.println("Enter the name of a file");
        HashMap<Character, Integer> first = new HashMap<>();
        HashMap<Character, Integer> second = new HashMap<>();
        while (!success) {
            fileName = input.next();
            try {
                first = newReader.countLettersVar1(fileName);
                second = newReader.countLettersVar2(fileName);
                success = true;
            }
            catch(FileNotFoundException e) {
                System.out.println("Wrong filename, try again");
            }
            catch (IOException e) {
                System.out.println("Error");
            }
        }

        System.out.println(first);
        System.out.println(second);

        try { // choose one  or each to try
            System.out.println(newReader.writeResultsToFileVar1(first));
            // System.out.println(newReader.writeResultsToFileVar1(second));
            // System.out.println(newReader.writeResultsToFileVar2(first));
            // System.out.println(newReader.writeResultsToFileVar2(second));
        }
        catch(IllegalArgumentException e) {
            System.out.println("Empty hashset");
        }
        catch (IOException e) {
            System.out.println("Error");
        }
    }
}
