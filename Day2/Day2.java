package Day2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day2 {
    static int solve1(String fileName) throws IOException {
        Scanner sc = new Scanner(new File(fileName));
        int x = 0;
        int y = 0;

        while (sc.hasNextLine()){
            String dir = sc.next();
            int mag = sc.nextInt();
            switch (dir){
                case "forward":
                    x += mag; break;
                case "up":
                    y -= mag; break;
                case "down":
                    y += mag; break;
            }
        }
        return x*y;
    }
    static int solve2(String fileName) throws IOException {
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
        return x*y;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Day2/Day2Input.txt";
        System.out.println(solve1(fileName));
        System.out.println(solve2(fileName));
    }
}
