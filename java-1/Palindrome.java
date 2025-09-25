public class Palindrome{
    public static void main(String[] args) {
        for (int i = 0; i< args.length; i++) {
            String s = args[i];
            if (isPalindrome(s)) {
                System.out.println(s + " ПАЛИНДРОМ");
            }
            else {
                System.out.println(s + " НЕ ПАЛИНДРОМ");
            }
        }

    }
    public static String reverseString(String s) {

        String reversed = "";

        for (int i = s.length()-1; i>=0; i--) {
            reversed += s.charAt(i);
        }

        return reversed;

    }
    public static boolean isPalindrome(String s) {
        String reversed = reverseString(s);
        return s.equals(reversed);
    }
}

