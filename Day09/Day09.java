package Day09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day09 {
    static int solve1(String fileName) throws IOException{
        ArrayList <char []> heightMap = new ArrayList<>();
        Scanner sc = new Scanner(new File(fileName));
        while(sc.hasNext()){    //Read in array
            heightMap.add(("A" + sc.nextLine() + "A").toCharArray());   //Pad with A
        }
        
        char[] above = new char[heightMap.get(0).length];
        char[] bottom = new char[heightMap.get(0).length];
        for (int i = 0; i < above.length; i++){
            above[i] = 'A';
            bottom[i] = 'A';
        }
        heightMap.add(bottom);
        int score = 0;
        for (int i = 0; i < heightMap.size() - 1; i++){
            char[] line = heightMap.get(i);
            char[] below = heightMap.get(i+1);
            for (int j = 1; j < line.length - 1; j++){
                int curr = line[j];
                if ((line[j-1] > curr) && (curr < line[j+1])) {
                    if((above[j] > curr) && (below[j] > curr)){ //Minimum
                        score += (curr - '0') + 1;
                    }
                }
            }
            above = line;
        }
        return score;
    }

    static int solve2(String fileName) throws IOException{
        ArrayList <char []> heightMap = new ArrayList<>();
        Scanner sc = new Scanner(new File(fileName));
        while(sc.hasNext()){
            heightMap.add((sc.nextLine()).toCharArray());
        }

        ArrayList<Integer> biggestThree = new ArrayList<>();
        biggestThree.add(0);
        biggestThree.add(0);
        biggestThree.add(0);
        for (int i = 0; i < heightMap.size() - 1; i++){
            char[] line = heightMap.get(i);
            for (int j = 0; j < line.length; j++){
                if(line[j] != '9'){
                    int regionSize = mapRegion(heightMap, i, j);
                    if (regionSize > biggestThree.get(0)){
                        biggestThree.set(0, regionSize);
                        Collections.sort(biggestThree);
                    }
                }
            }
        }
        return biggestThree.get(0) * biggestThree.get(1) * biggestThree.get(2);
    }

    //Return the size of the region containg (i,j), and set every height in this region to marked
    static int mapRegion(ArrayList <char []> heightMap, int i, int j){
        if (heightMap.get(i)[j] == '9'){
            return 0;
        }
        else {
            int regionSize = 1;
            heightMap.get(i)[j] = '9';  //Mark position as mapped
            if (i>0){   
                regionSize += mapRegion(heightMap, i-1, j); //Map above
            }   
            if (heightMap.size() > i+1) { 
                regionSize += mapRegion(heightMap, i+1, j); //Map below
            }
            if (j > 0){ 
                regionSize += mapRegion(heightMap, i, j-1); //Map left
            }
            if (heightMap.get(0).length > j+1){
                regionSize += mapRegion(heightMap, i, j+1); //Map right
            }
            return regionSize;
        }
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day09Input.txt";
        System.out.println("Answer 1: " + solve1(fileName));
        System.out.println("Answer 2: " + solve2(fileName));
    }
}
