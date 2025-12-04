package java5;

import java.util.Scanner;
import java.util.regex.*;

public class Task3 {
	public static void main(String[] args) {
		String text;

        System.out.println("Введите строку для проверки:");
		if (args.length > 0) {
			text = String.join(" ", args);
		} else {
			try (Scanner scanner = new Scanner(System.in)) {
				if (!scanner.hasNextLine()) {
					return;
				}
				text = scanner.nextLine();
			}
		}

		highlightLowerToUpper(text);
	}

	private static void highlightLowerToUpper(String text) {
		Pattern pattern = Pattern.compile("([a-zа-яё])([A-ZА-ЯЁ])");
		Matcher matcher = pattern.matcher(text);

		StringBuffer result = new StringBuffer();
		int count = 0;

		while (matcher.find()) {
			count++;
			matcher.appendReplacement(result, " *** " + matcher.group(1) + matcher.group(2) + " *** ");
		}
		matcher.appendTail(result);

		System.out.println("Исходный: " + text);
		System.out.println("Результат: " + result.toString());
		System.out.println("Найдено: " + count);
	}
}