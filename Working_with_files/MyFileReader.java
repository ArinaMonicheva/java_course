import java.io.*;
import java.util.*;

public class MyFileReader {
    public HashMap<Character, Integer> createBlank() {
        HashMap<Character, Integer> letters = new HashMap<Character, Integer>();
        for (char i = 'a'; i <= 'z'; i++) {
            letters.put(i, 0);
        }
        return letters;
    }

    public boolean doesExist(String fileName) {
        File toOpen = new File(fileName);
        if (!toOpen.exists()) {
            File secondTry = new File(new File(".").getAbsolutePath());
            String temp = secondTry.getPath();
            toOpen = new File(temp.substring(0, temp.length() - 1) + fileName);
        }
        return toOpen.exists();
    }

    public HashMap<Character, Integer> countLettersVar1(String fileName) throws FileNotFoundException {
        if (!doesExist(fileName) || fileName.isEmpty()) {
            throw new FileNotFoundException();
        }
        File toOpen = new File(fileName);
        Scanner fileReader = new Scanner(toOpen);
        HashMap<Character, Integer> letters = createBlank();
        String temp;
        while (fileReader.hasNext()) {
            temp = fileReader.next();
            temp = temp.toLowerCase();
            for (int i = 0; i < temp.length(); i++) {
                char c = temp.charAt(i);
                if (letters.containsKey(c)) {
                    letters.put(c, letters.get(c) + 1);
                }
            }
        }

        return letters;
    }

    public HashMap<Character, Integer> countLettersVar2(String fileName) throws FileNotFoundException, IOException {
        if (!doesExist(fileName) || fileName.isEmpty()) {
            throw new FileNotFoundException();
        }
        HashMap<Character, Integer> letters = createBlank();
        try (FileReader fileReader = new FileReader(fileName)) {
            int c = fileReader.read();
            while (c != -1) {
                String temp = "" + (char)c;
                temp = temp.toLowerCase();
                if (letters.containsKey(temp.charAt(0))) {
                    letters.put(temp.charAt(0), letters.get(temp.charAt(0)) + 1);
                }
                c = fileReader.read();
            }
            fileReader.close();
        }
        catch (IOException e) {
            throw new IOException();
        }

        return letters;
    }

    public String writeResultsToFileVar1(HashMap<Character, Integer> toWrite) throws IOException,
            IllegalArgumentException {
        if (toWrite.isEmpty()) {
            throw new IllegalArgumentException("Empty HashMap");
        }
        String outFileName = "out.txt";
        File outFile = new File(outFileName);
        if (!doesExist(outFileName)) {
            outFile.createNewFile();
        }
        Set <Character> keys = toWrite.keySet();

        try (FileWriter fileWriter = new FileWriter(outFile)) {
            for (Character i : keys) {
                String temp = i + " - " + String.valueOf(toWrite.get(i)) + "\n";
                fileWriter.write(temp);
            }
            fileWriter.close();
        }
        catch (IOException e) {
            throw new IOException();
        }

        return outFileName;
    }

    public String writeResultsToFileVar2(HashMap<Character, Integer> toWrite) throws IOException,
            IllegalArgumentException {
        if (toWrite.isEmpty()) {
            throw new IllegalArgumentException("Empty HashMap");
        }
        String outFileName = "out.txt";
        Formatter formatter = new Formatter(outFileName);
        Set <Character> keys = toWrite.keySet();

        for (Character i : keys) {
            formatter.format(i + " - " + String.valueOf(toWrite.get(i)) + "\n");
        }

        formatter.close();

        return outFileName;
    }

}
