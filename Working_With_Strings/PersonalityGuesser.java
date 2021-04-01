import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class PersonalityGuesser {
    private Date date;
    private String name;
    private String secondName;
    private String patronymic;
    private final Scanner input;
    private SimpleDateFormat currentFormat;

    PersonalityGuesser() {
        date = new Date();
        name = "";
        secondName = "";
        patronymic = "";
        input = new Scanner(System.in);
        currentFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentFormat.setLenient(false);
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public String toString() {
        return  name + " " + secondName + " " +
                patronymic + " " + date.toString();
    }

    private String check(String toCheck) { // the user can set any format of the names,
        // even X Æ A-12 if he wishes that, but it cannot start with a number
        if (toCheck.isEmpty() || Character.isDigit(toCheck.charAt(0))) {
            System.out.println("Name should not start with a digit or be empty");
            return "";
        }
        return toCheck.replaceFirst("" + Character.toString(toCheck.charAt(0)),
                "" + Character.toUpperCase(toCheck.charAt(0)));
    }

    public boolean setName(String name) {
        String toCatch = check(name);
        if (!toCatch.isEmpty()) {
            this.name = toCatch;
            return true;
        }
        return false;
    }

    public boolean setSecondName(String secondName) {
        String toCatch = check(secondName);
        if (!toCatch.isEmpty()) {
            this.secondName = toCatch;
            return true;
        }
        return false;
    }

    public boolean setPatronymic(String patronymic) {
        String toCatch = check(patronymic);
        if (!toCatch.isEmpty()) {
            this.patronymic = toCatch;
            return true;
        }
        return false;
    }

    private boolean isValidDate(String date, SimpleDateFormat dateFormat) { // the message of
        // incorrect date is typed when the format is wrong for this certain format,
        // but for the next format it may work, so you can guess what format is valid for input date
        try {
            this.date = dateFormat.parse(date);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean isValidDateCheck(String date, SimpleDateFormat dateFormat) { // for user's checks
        try {
            dateFormat.parse(date);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean setDate(String date) throws IllegalArgumentException {
        if (date.contains("-")) {
            date = date.replaceAll("-", ".");
        }
        else if (date.contains("/")) {
            date = date.replaceAll("/", ".");
        }
        else if (!date.contains(".")){
            throw new IllegalArgumentException("Illegal date format");
        }

        SimpleDateFormat isValid = new SimpleDateFormat("dd.MM.yyyy");
        isValid.setLenient(false);
        SimpleDateFormat isValid2 = new SimpleDateFormat("MM.dd.yyyy");
        isValid2.setLenient(false);
        SimpleDateFormat isValid3 = new SimpleDateFormat("yyyy.MM.dd");
        isValid3.setLenient(false);

        if (isValidDate(date, isValid)) {
            currentFormat = isValid;
            return true;
        }
        else if (isValidDate(date, isValid2)) {
            currentFormat = isValid2;
            return true;
        }
        else if (isValidDate(date, isValid3)) {
            currentFormat = isValid3;
            return true;
        }
        else {
            throw new IllegalArgumentException("Illegal date format");
        }
    }

    public boolean setAllInfo() {
        System.out.println("Enter all data in format:\n" +
                "Name SecondName Patronymic(unnecessary field) data(dd.mm.yyyy)");
        String temp = input.nextLine();
        String[] forCheck = {};

        if (temp.contains(", ")) {
            forCheck = temp.split(", ");
        }
        else if (temp.contains("; ")) {
            forCheck = temp.split("; ");
        }
        else if (temp.contains(" ")) {
            forCheck = temp.split(" ");
        }
        else {
            System.out.println("Can't find the proper separator");
            return false;
        }

        if (forCheck.length < 3) {
            System.out.println("At least 2 fields of name and date field are required");
        }
        else if (forCheck.length > 4) {
            System.out.println("Too much info. Check for typos or change the separator");
        }
        else if (forCheck.length == 4) {
            Date reservedDate = date;
            String reservedName = name;
            String reservedSName = secondName;
            String reservedPatronymic = patronymic;
            try {
                if (setName(forCheck[0]) && setSecondName(forCheck[1]) &&
                        setPatronymic(forCheck[2]) && setDate(forCheck[3])) {
                    return true;
                }
                else {
                    date = reservedDate;
                    name = reservedName;
                    secondName = reservedSName;
                    patronymic = reservedPatronymic;
                }
            }
            catch (IllegalArgumentException e) {
                date = reservedDate;
                name = reservedName;
                secondName = reservedSName;
                patronymic = reservedPatronymic;
                return false;
            }
        }
        else {
            Date reservedDate = date;
            String reservedName = name;
            String reservedSName = secondName;
            try {
                if (setName(forCheck[0]) && setSecondName(forCheck[1])
                        && setDate(forCheck[2])) {
                    return true;
                }
                else {
                    date = reservedDate;
                    name = reservedName;
                    secondName = reservedSName;
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
                date = reservedDate;
                name = reservedName;
                secondName = reservedSName;
                return false;
            }
        }

        return false;
    }

    public String getInitials() {
        String temp = "";
        if (!name.isEmpty() && !secondName.isEmpty()) {
            temp += secondName + " " + name.charAt(0) + '.';
        }
        else {
            return "";
        }

        if (!patronymic.isEmpty()) {
            temp = temp + " " + patronymic.charAt(0) + '.';
        }

        return temp;
    }

    public int getAge() {
        Date currentDate = new Date();
        long difference = currentDate.getTime() - date.getTime();
        int days = (int)(difference / (24 * 60 * 60 * 1000));
        return days / 365;
    }

    public String guessSex() {
        if (!patronymic.isEmpty()) { // lets suppose that only russian people have patronymics
            if (patronymic.endsWith("vna") || patronymic.endsWith("вна")) {
                return "female";
            }
            else {
                return "male";
            }
        }
        else if (!secondName.isEmpty()) {
            if (secondName.endsWith("ova") || secondName.endsWith("ова") ||
                secondName.endsWith("eva") || secondName.endsWith("ева")) {
                return "female";
            }
            else if (secondName.endsWith("ov") || secondName.endsWith("ов") ||
                    secondName.endsWith("ev") || secondName.endsWith("ев")) {
                return "male";
            }
            else {
                return "unknown";
            }
        }
        else {
            return "unknown";
        }
    }
}
