import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class MainClass {
    public static void main(String []args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in, "Cp866"));

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "Cp866"), true);
            Scanner input = new Scanner(System.in);
            PersonalityGuesser test = new PersonalityGuesser();
            test.setAllInfo();
            System.out.println(test.getInitials());
            System.out.println(test.getAge());
            System.out.println(test.guessSex());
            System.out.println(test);
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
