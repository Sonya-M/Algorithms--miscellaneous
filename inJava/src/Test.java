import edu.princeton.cs.algs4.*;

public class Test {

    private static final boolean[][] BA = {
        {true, true, false},
        {false, false, true, true},
        {true, true, false}
    };
    
    private static void ex116() {
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }

    private static void ex117() {
        int sum = 0;
        for (int i = 1; i < 1000; i++)
            for (int j = 0; j < i; j++) sum++;
        StdOut.println(sum);
    }

    private static String decimalToBinary(int n) {
        if (n == 0) return "0";
        StringBuilder s = new StringBuilder();
        for (int i = Math.abs(n); i > 0; i /= 2) {
            s.insert(0, i % 2);
        }
        if (n < 0) s.insert(0, '-');
        return s.toString();
    }

    private static void bool2DArrayToStr(boolean[][] a) {
        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < a[row].length; col++)  {
                char next;
                if (a[row][col]) next = '*';
                else next = ' ';
                System.out.print("[" + row + "]" + "[" + col + "]"+ next);
            }
            System.out.println();
        }
    } 

    
    public static void main(String[] args) {
        // System.out.println(2.0e-6 * 100000000.1);
        // //.000002 * 100000000 = 200
        // System.out.println(true && false || true && true);
        // System.out.println((1 + 2.236)/2);
        // System.out.println(1 + 2 + 3 + 4.0);
        // System.out.println( 1 + 3 + "5"); //evaluated left to right, so "45"
        System.out.println(decimalToBinary(-703));
        bool2DArrayToStr(BA);

    }
}