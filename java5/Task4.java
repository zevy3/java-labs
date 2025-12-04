package java5;

import java.util.Scanner;
import java.util.regex.*;

public class Task4 {
	public static void main(String[] args) {
		String ip;
        System.out.println("Введите IP адрес: ");
		if (args.length > 0) {
			ip = String.join(" ", args).trim();
		} else {
			try (Scanner scanner = new Scanner(System.in)) {
				if (!scanner.hasNextLine()) {
					return;
				}
				ip = scanner.nextLine().trim();
			}
		}

		boolean ok = isValidIP(ip);
		System.out.println("IP: " + ip);
		System.out.println(ok ? "Корректный IP-адрес" : "Некорректный IP-адрес");
	}

	private static boolean isValidIP(String ip) {
		try {
			String ipPattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
			Pattern pattern = Pattern.compile(ipPattern);
			return pattern.matcher(ip).matches();
		} catch (PatternSyntaxException e) {
			System.err.println("Ошибка в регулярном выражении: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Ошибка при проверке: " + e.getMessage());
			return false;
		}
	}
}