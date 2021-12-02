package Day1;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day1 {

    /*
    For the second part: 
            Let A = x[i-1] + x[i] + x[i+1], and B = x[i] + x[i+1] + x[i+2]. 
                (A < B) iff (x[i-1] + x[i] + x[i+1]) < (x[i] + x[i+1] + x[i+2]) 
                        iff x[i-1] < x[i+1]
    
    */
    static int[] solve(String fileName) throws IOException{
        int[] ans = new int[2];
        Scanner sc = new Scanner(new File(fileName));
        int back = sc.nextInt();
        int middle = sc.nextInt();
        if (back < middle) {
            ans[0] ++;
        }
        int front = sc.nextInt();
        if (middle < front) {
            ans[0] ++;
        }
        while (sc.hasNextInt()) {
            int next = sc.nextInt();
            if (front < next) {
                ans[0] ++;
            }
            if (back < next) {
                ans[1] ++;
            }
            back = middle;
            middle = front;
            front = next;
        }
        return ans;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Day1/Day1Input.txt";
        int[] ans = solve(fileName);
        System.out.println("Answers: Part 1 = " + ans[0] + ", Part 2 = " + ans[1]);
    }
}
