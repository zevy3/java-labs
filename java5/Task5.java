package java5;

import java.util.Scanner;
import java.util.regex.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Task5 {
    public static void main(String[] args) {
        String letterInput;
        String text;

        if (args.length >= 2) {
            letterInput = args[0];
            text = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        } else {
            System.out.println("Введите букву для поиска:");
            try (Scanner scanner = new Scanner(System.in)) {
                if (!scanner.hasNextLine()) {
                    return;
                }
                System.out.println("Введите текст для анализа:");
                letterInput = scanner.nextLine().trim();
                if (!scanner.hasNextLine()) {
                    return;
                }
                text = scanner.nextLine();
            }
        }

        if (letterInput.isEmpty()) {
            System.err.println("Буква не указана");
            return;
        }

        char letter = letterInput.charAt(0);
        findWordsByLetter(text, letter);
    }

    private static void findWordsByLetter(String text, char letter) {
        try {
            System.out.println("Текст: " + text);
            System.out.println("Буква: " + letter);

            String escapedLetter = Pattern.quote(String.valueOf(letter));
            String regex = "\\b" + escapedLetter + "\\p{L}*";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(text);

            Set<String> found = new LinkedHashSet<>();
            while (matcher.find()) {
                found.add(matcher.group());
            }

            if (found.isEmpty()) {
                System.out.println("Слова не найдены");
            } else {
                for (String w : found) {
                    System.out.println(w);
                }
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Неправильное регулярное выражение: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при поиске: " + e.getMessage());
        }
    }
}