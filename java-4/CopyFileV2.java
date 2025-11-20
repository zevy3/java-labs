import java.io.*;

public class CopyFileV2 {
    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream("input.txt");
            FileOutputStream fos = new FileOutputStream("output.txt");

            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
                // if (b > 0) fos.close();
            }

            fis.close();
            fos.close();

            System.out.println("Файл успешно скопирован!");

        } catch (IOException e) {
            System.out.println("Ошибка чтения/записи: " + e.getMessage());
        }
    }
}
