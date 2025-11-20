import java.io.*;

public class CopyFileV1 {
    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream("input.txt");
            FileOutputStream fos = new FileOutputStream("output.txt");

            int b;
            while ((b = fis.read()) != -1) {

                //if (b == 'X') { // при встрече буквы X — кидаем ошибку
                //    throw new IOException("Искусственная ошибка записи");
                //}

                fos.write(b);
            }

            fis.close();
            fos.close();

            System.out.println("Файл успешно скопирован!");

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка открытия файла: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка закрытия файла: " + e.getMessage());
        }
    }
}
