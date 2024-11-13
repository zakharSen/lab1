public class Replace {
    public static void main(String[] args) {
        replaceNum();
    }

    public static void replaceNum() {
        for (int i = 0; i < 100; i++) {
            if ((i % 3 == 0) && (i % 5 == 0)) {
                System.out.println("FizzBuzz");
                i++;
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
                i++;
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
                i++;
            }
            System.out.println(i);
        }
    }
}
