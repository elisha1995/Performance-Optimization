package memoryManagement;

public class StringConcatenationOptimized {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            result.append("some text");
        }
        System.out.println(result.length());
    }
}
