package Day11;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day11 {
    static int N = 10;
    static int solve1 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int[][] octos = new int[N][N];
        for (int i = 0; i < N; i++){
            String line = sc.nextLine();
            for (int j = 0; j < N; j++){
                octos[i][j] = line.charAt(j) - '0';
            }
        }

        //Both flash and increasing energy level are associative, so the first two parts of each step can be interleaved
        int flashes = 0;
        for (int step = 1; step <= 100; step++){
            for (int i = 0; i < N; i ++){
                for (int j = 0; j < N; j ++){
                    if (octos[i][j] == 9){
                        flash(octos, i, j);
                    }
                    else {
                        octos[i][j] ++;
                    }
                }
            }
            for (int i = 0; i < N; i ++){
                for (int j = 0; j < N; j ++){
                    if (octos[i][j] > 9){   //If i,j has flashed
                        flashes ++;
                        octos[i][j] = 0;
                    }
                }
            }
        }
        return flashes;
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int[][] octos = new int[N][N];
        for (int i = 0; i < N; i++){
            String line = sc.nextLine();
            for (int j = 0; j < N; j++){
                octos[i][j] = line.charAt(j) - '0';
            }
        }

        int stepNum = 0;
        int flashesInStep = 0;
        while(flashesInStep < 100){
            flashesInStep = 0;
            for (int i = 0; i < N; i ++){
                for (int j = 0; j < N; j ++){
                    if (octos[i][j] == 9){
                        flash(octos, i, j);
                    }
                    else {
                        octos[i][j] ++;
                    }
                }
            }
            for (int i = 0; i < N; i ++){
                for (int j = 0; j < N; j ++){
                    if (octos[i][j] > 9){
                        flashesInStep ++;
                        octos[i][j] = 0;
                    }
                }
            }
            stepNum ++;
        }
        return stepNum;
    }
    static void flash(int[][] octos, int i, int j){ //Ran when an octopus has been affected by a flash
        if((i >= 0) && (j >= 0) && (i < N) && (j < N)){
            octos[i][j] ++;
            if (octos[i][j] == 10) {    //Flash octopus
                for (int i2 = i-1; i2 <= i+1; i2++){
                    for (int j2 = j-1; j2 <= j+1; j2++){
                        if ((i2 != i) || (j2 != j)){
                            flash(octos, i2, j2);
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day11Input.txt";
        System.out.println("Answer 1: " + solve1(fileName));
        System.out.println("Answer 2: " + solve2(fileName));
    }
}