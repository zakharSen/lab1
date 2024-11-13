import java.util.Scanner;

public class SubstrinMtx {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 3x3 Matrix: ");
        int rows = 3;
        int cols = 3;
        String[][] strArray = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.out.println("Enter line " + cols + " words, split spaces:");
            String line = scanner.nextLine();
            String[] words = line.split(" ");

            if (words.length != cols) {
                System.out.println("You must enter exactly " + cols + " words. Try again.");
                i--;
                continue;
            }

            for (int j = 0; j < cols; j++) {
                strArray[i][j] = words[j];
            }
        }

        System.out.println("Enter substring: ");
        String substring = scanner.nextLine();
        System.out.println("К-сть входжень " + substring + " - " + countSubstr(strArray, substring));

        String[][] teststr = {
                {"3", "1", "3"},
                {"1", "2", "1"},
                {"3", "1", "3"},
        };

        String substr = "3";
        System.out.println("К-сть входжень " + substr + " - " + countSubstr(teststr, "3"));
    }

    public static int countSubstr(final String[][] str, String substr) {
        int count = 0;
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                if (str[i][j].equals(substr)) {  // Use .equals for string comparison
                    count++;
                }
            }
        }
        return count;
    }
}
