package Day10;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Day10 {
    static long[] solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        Map <Character, Character> delimMap = Map.of(')','(',  '>','<', '}','{',  ']','[');
        Map <Character, Integer> score1Map = Map.of(')', 3,  ']', 57,  '}', 1197,  '>', 25137);
        Map <Character, Integer> score2Map = Map.of('(', 1,  '[', 2,  '{', 3,  '<', 4);
        long[] score = new long[2];
        ArrayList<Long> incompleteScores = new ArrayList<>();
        while(sc.hasNextLine()){
            char [] line = sc.nextLine().toCharArray();
            Stack<Character> openers = new Stack<>();
            boolean corrupt = false;
            for (char c : line){
                if (c == '[' || c == '(' || c == '<' || c == '{'){
                    openers.push(c);
                }
                else{   //c is a closing delimiter
                    if (openers.size() > 0){
                        char open = openers.pop();
                        if (open != delimMap.get(c)){
                            score[0] += score1Map.get(c);
                            corrupt = true;
                            break;
                        }
                    }
                }
            }
            if (!corrupt){
                long lineScore = 0;
                Stack<Character> closeOrder = new Stack<>();
                while(!openers.isEmpty()){  //Close order equals reverse of open order
                    closeOrder.push(openers.pop());
                }
                for (Character c : closeOrder){
                    lineScore *= 5;
                    lineScore += score2Map.get(c);
                }
                incompleteScores.add(lineScore);
            }
        }
        Collections.sort(incompleteScores);
        score[1] = incompleteScores.get(incompleteScores.size()/2);
        return score;
    }

    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day10Input.txt";
        long[] ans = solve(fileName);
        System.out.println("Answer 1: " + ans[0] + "\nAnswer 2: " + ans[1]);
    }
}
