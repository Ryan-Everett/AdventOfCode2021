package Day2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day2 {
    static int[] solve(String fileName) throws IOException {
        Scanner sc = new Scanner(new File(fileName));
        int x = 0;
        int y = 0;
        int aim = 0;
        while (sc.hasNextLine()){
            String dir = sc.next();
            int mag = sc.nextInt();
            switch (dir){
                case "forward":
                    x += mag; y += (mag*aim); break;
                case "up":
                    aim -= mag; break;
                case "down":
                    aim += mag; break;
            }
        }
        return new int[] {x*aim,x*y};
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day2Input.txt";
        int[] ans = solve(fileName);
        System.out.println("First answer = " + ans[0] + "\nSecond answer = " + ans[1]);
    }
}
