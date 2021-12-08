package Day8;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class Day8 {
    static int solve1 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int totalDigs = 0;
        while (sc.hasNextLine()) {
            String[] currPattern = sc.nextLine().split("\\|");
            for (String digit : currPattern[1].split(" ")){
                int len = digit.length();
                if (len == 2 || len == 3 || len == 4 || len == 7){
                    totalDigs ++;
                }
            }
        }
        return totalDigs;
    }

    //Add all characters of s to hs
    static void addStr(HashSet<Character> hs, String s){
        for (int i = 0; i < s.length(); i++){
            hs.add(s.charAt(i));
        }
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int score = 0;

        while (sc.hasNextLine()) {
            String[] currPattern = sc.nextLine().split("\\|");

            ArrayList <HashSet<Character>> nums = new ArrayList<HashSet<Character>>();
            for (int i = 0; i < 10; i ++){
                nums.add(i, new HashSet<>());
            }
            Stack <HashSet<Character>> remainingDigits = new Stack<>();

            for (String digit : currPattern[0].split(" ")){     //Find the easy numbers
                HashSet<Character> activeEdges = new HashSet<>();
                addStr(activeEdges, digit);
                switch (digit.length()){
                    case 2:
                        nums.get(1).addAll(activeEdges); break;
                    case 3:
                        nums.get(7).addAll(activeEdges); break;
                    case 4:
                        nums.get(4).addAll(activeEdges); break;
                    case 7:
                        nums.get(8).addAll(activeEdges); break;
                    default:
                        remainingDigits.push(activeEdges); break;
                }
            }

            HashSet<Character> diff = new HashSet<>();
            diff.addAll(nums.get(4));
            diff.removeAll(nums.get(1));
            for (HashSet<Character> digit : remainingDigits){   //Find out what the other digits are
                if (digit.size() == 6){                     //One of (0,6,9)
                    if (digit.containsAll(nums.get(4))){        //Digit = 9
                        nums.get(9).addAll(digit);
                    }
                    else if (digit.containsAll(nums.get(1))){   //Digit = 0
                        nums.get(0).addAll(digit);
                    }
                    else {                                      //Digit = 6
                        nums.get(6).addAll(digit);
                    }
                }
                else{                                       //One of (2,3,5)
                    if (digit.containsAll(nums.get(1))){        //Digit = 3
                        nums.get(3).addAll(digit);
                    }
                    else if (digit.containsAll(diff)){          //Digit = 5
                        nums.get(5).addAll(digit);
                    }
                    else{                                       //Digit = 2
                        nums.get(2).addAll(digit);
                    }
                }
            }
            int outputValue = 0;
            for (String digit : currPattern[1].split(" ")){
                outputValue *= 10;
                HashSet<Character> activeEdges = new HashSet<>();
                addStr(activeEdges, digit);
                Iterator<HashSet<Character>> iter = nums.iterator();
                int i = 0;
                while(iter.hasNext()){
                    HashSet<Character> curr = iter.next();
                    if (curr.containsAll(activeEdges) && activeEdges.containsAll(curr)){    //If the sets are equal
                        outputValue += i;
                        break;
                    }
                    i ++;
                }
            }
            score += outputValue;
        }
        return score;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day8Input.txt";
        System.out.println("Answer 1: " + solve1(fileName));
        System.out.println("Answer 2: " + solve2(fileName));
    }
}
