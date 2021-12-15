package Day14;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

//This could be made much more space and time efficient by maintaining a map<Integer, Integer> of 24*(c1-'A') + (c2-'A') => count(c1c2), then iterating through the keyset
//For each step you would only need to build a new map and then overwrite the old map
public class Day14 {
    static long solve (String fileName, int steps) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        String template = sc.nextLine();
        HashMap<Integer, Integer> rules = new HashMap<>();
        sc.nextLine();

        long[] quantities = new long[24];
        long [] [][] polyI = new long[steps+1] [24][24];     //polyI(i, c1,c2) = num of c1c2 in string at step i
        while(sc.hasNextLine()){
            String[] rule = sc.nextLine().split(" -> ");
            int key = 24*(rule[0].charAt(0) - 'A') + (rule[0].charAt(1) - 'A'); //Base 24
            rules.put(key, rule[1].toCharArray()[0] - 'A');
        }
        quantities[template.charAt(0) - 'A'] ++;
        for (int i = 1; i < template.length(); i++){
            int c1 = template.charAt(i-1) - 'A';
            int c2 = template.charAt(i) - 'A';
            polyI[0] [c1][c2] ++;
            quantities[c2] ++;
        }
        for (int step = 1; step <= steps; step ++){
            for (int c1 = 0; c1 < 24; c1 ++){
                for (int c2 = 0; c2 < 24; c2 ++){
                    if (polyI[step -1] [c1][c2] > 0){
                        int newC = rules.get(24*c1 + c2);
                        polyI[step] [c1][newC] += polyI[step-1] [c1][c2];
                        polyI[step] [newC][c2] += polyI[step-1] [c1][c2];
                        quantities[newC] += polyI[step-1] [c1][c2];
                    }
                }
            }
        }
        long max = -1;
        long min = Long.MAX_VALUE;
        for (long x : quantities){
            if (x > 0){
                if (x > max){
                    max = x;
                }
                if (x < min){
                    min = x;
                }
            }
        }
        return max - min;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day14Input.txt";
        System.out.println("Answer 1: " + solve(fileName, 10) + "\nAnswer 2: " + solve(fileName, 40));
    }
}
