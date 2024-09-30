package memoryManagement;

public class StringConcatenation {
    public static void main(String[] args) {
        String result = "";
        for (int i = 0; i < 10000; i++) {
            result += "some text";
        }
        System.out.println(result.length());
    }
}

