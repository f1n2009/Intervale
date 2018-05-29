import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class Main {

    private Converter calculator = new Calculator();

    private Main(String[] files){
        this.readerWriter(files);
    }

    public static void main (String[] args) {
        new Main(new String[]{"input_1.txt", "input_2.txt", "input_3.txt"});
    }

    //чтение из файла и запись в файл
    private void readerWriter(String[] files) {
        for (String file : files) {
            List<String> mass = new ArrayList<>();
            BufferedReader readFromFile = null;
            try {
                readFromFile = new BufferedReader(new FileReader(file));
                String line;
                while ((line = readFromFile.readLine()) != null) {
                    mass.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (readFromFile != null)
                    try {
                        readFromFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            BufferedWriter writeFromFile = null;
            try {
                String newFile = file.replace("in", "out");
                writeFromFile = new BufferedWriter(new FileWriter(newFile));
                for (String mas : mass) {
                    List<String> expression = calculator.parse(mas);
                    try {
                        writeFromFile.write(mas + " = " + format(calculator.calc(expression)) + '\n');
                    }
                    catch (ArithmeticException e){
                    writeFromFile.write(e.getMessage() + '\n');}
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writeFromFile != null)
                    try {
                        writeFromFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    //формат вывода ответа
    private String format (Double num){
        String format;
        if (num%1==0){
            format = String.valueOf(Math.round(num));
        }
        else {
            format = String.valueOf(new Formatter().format(Locale.ENGLISH,"%.5f", num));
        }
        return format;
    }
}
