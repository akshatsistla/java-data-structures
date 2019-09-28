import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatching {

    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0
                || text == null || comparator == null) {
            throw new IllegalArgumentException("Pattern is invalid");
        }

        int[] failureTable = buildFailureTable(pattern, comparator);
        ArrayList<Integer> matches = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    matches.add(i);
                }
                int nextAlignment = failureTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
        return matches;
    }

    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern is invalid");
        }
        if (pattern.length() == 0) {
            return new int[0];
        }
        int[] table = new int[pattern.length()];
        int i = 0;
        int j = 1;
        table[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                table[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    table[j] = 0;
                    j++;
                } else {
                    i = table[i - 1];
                }
            }
        }

        return table;
    }

    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0
                || text == null || comparator == null) {
            throw new IllegalArgumentException("Pattern is invalid");
        }
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                ret.add(i);
                i++;
            } else {
                if (lastTable.containsKey(text.charAt(i + j))) {
                    int shiftedIndex = lastTable.get(text.charAt(i + j));
                    if (shiftedIndex < j) {
                        i = i + j - shiftedIndex;
                    } else {
                        i++;
                    }
                } else {
                    i++;
                }
            }
        }
        return ret;
    }

    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern is invalid");
        }
        Map<Character, Integer> lastTable = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    private static final int BASE = 101;

    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0
                || text == null || comparator == null) {
            throw new IllegalArgumentException("Pattern is invalid");
        }
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (text.length() < pattern.length()) {
            return ret;
        }
        int patternHash = 0;
        int basePower = 1;
        int textHash = 0;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (i != pattern.length() - 1) {
                basePower = basePower * BASE;
            }
            patternHash += pattern.charAt(i) * basePower;
            textHash += text.charAt(i) * basePower;
        }

        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(
                        text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    ret.add(i);
                }
            }
            i++;
            if (i <= text.length() - pattern.length()) {
                textHash = (textHash - text.charAt(i - 1) * basePower)
                        * BASE + text.charAt(i + pattern.length() - 1);
            }
        }
        return ret;
    }
}
