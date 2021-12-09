package Day07;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Day07 {
    static int solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int[] vals = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int curr = 0;
        int moves = Integer.MAX_VALUE;
        int prevMoves = Integer.MAX_VALUE;
        while(prevMoves >= moves){ //Fuel consumption is convex
            prevMoves = moves;
            moves = 0;
            for (int a : vals){
                moves += Math.abs(curr - a);
            }
            curr ++;
        }
        return prevMoves;
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int[] vals = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int curr = 0;
        int moves = Integer.MAX_VALUE;
        int prevMoves = Integer.MAX_VALUE;
        while(prevMoves >= moves){ //Fuel consumption is still convex
            prevMoves = moves;
            moves = 0;
            for (int a : vals){
                int d = Math.abs(curr - a);
                moves += (d * (d+1))/2; //+= sum i over [0..d]
            }
            curr ++;
        }
        return prevMoves;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day07Input.txt";
        System.out.println("Ans 1: " + solve(fileName) + "\nAns 2: " + solve2(fileName));
    }
}
