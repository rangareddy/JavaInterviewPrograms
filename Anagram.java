Definition: An anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
Example: The word silent can be rearranged into listen.

public class Anagram {

    public static void main(String[] args) {

        String[][] input = {{"abc", "bac"}, {"bac", "bacd"}, {"abc", null}, {"listen", "silent"}};

        for (int i = 0; i < input.length; i++) {
            String inputs[] = input[i];
            String str1 = inputs[0];
            String str2 = inputs[1];
            boolean isAnagram = checkAnagram(str1, str2);
            if (isAnagram) {
                System.out.println("Both '" + str1 + "', '" + str2 + "' are Anagram(s)");
            } else {
                System.out.println("Both '" + str1 + "', '" + str2 + "' are not Anagram(s)");
            }
        }
    }

    private static boolean checkAnagram(String str1, String str2) {

        if (str1 == null || str2 == null) {
            return false;
        }
        int len1 = str1.length();
        int len2 = str2.length();
        if (len1 != len2) {
            return false;
        }

        int value = 0;
        for (int i = 0; i < len1; i++) {
            value = value ^ (int) str1.charAt(i);
            value = value ^ (int) str2.charAt(i);
        }
        return value == 0;
    }
}
