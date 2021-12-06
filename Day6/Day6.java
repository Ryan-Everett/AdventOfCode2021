package Day6;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day6 {
    static long solve (String fileName, int N) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        String[] fishString = sc.nextLine().split(",");
        long [] fishList = new long [9];
        for (String fish : fishString){
            fishList[Integer.parseInt(fish)] += 1;
        }
        int head = 0;
        for (int i = 1; i <= N; i++){   //fc(i+1) = fc(i) + fishList[head]
            long births = fishList[head];
            fishList[(head + 7)%9] += births;
            head = (head + 1) % 9;
        }
        long sum = 0;
        for (long fishCount : fishList){
            sum += fishCount;
        }
        return sum;
    }
    public static void main(String[] args) throws IOException{
        System.out.println("Answer 1: " + solve("Inputs/Day6Input.txt", 80));
        System.out.println("Answer 2: " + solve("Inputs/Day6Input.txt", 256));
    }
}
