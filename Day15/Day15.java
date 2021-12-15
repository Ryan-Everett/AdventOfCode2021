package Day15;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

    //A* graph search. 
    // Does not revisit co-ords if they have already been visited unless we have found a path to visit them with lower risk
    // Heuristic h(n) = (iMax - i) + (jMax - j). This heuristic is clearly consistent therefore our solution is always optimal
    // f(n) = risk(n) + h(n)
    // Run time is very speedy, 1300311 nodes visited in part 2, less than a second to solve both

class Node{
    int i, j, risk;
    Node(int i, int j, int risk){
        this.i = i; this.j = j; this.risk = risk;
    }
    @Override
    public boolean equals(Object p){    //Slightly cheeky as this equals is not commutative, this equals is only used when considering if a node has been visited, so we want it to return true if the risk of the new node is higher 
        if (p instanceof Node){
            return (i == ((Node)p).i && j == ((Node)p).j && risk >= ((Node)p).risk);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return i*53 + j * 271;
    }
}

public class Day15 {
    static int solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int iGoal = -1;
        int jGoal = -1;
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        while(sc.hasNextLine()){
            jGoal ++;
            iGoal = 0;
            String line = sc.nextLine();
            iGoal = line.length() - 1;
            ArrayList<Integer> row = new ArrayList<>();
            for(int i = 0; i <= iGoal; i++){
                row.add(line.charAt(i) - '0');
            }
            grid.add(row);
        }
        return aStarGraph(grid, iGoal, jGoal);
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        int width = 0;
        int height = 0;
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        while(sc.hasNextLine()){
            height ++;
            width = 0;
            String line = sc.nextLine();
            width = line.length();
            ArrayList<Integer> row = new ArrayList<>();
            for(int i = 0; i < width; i++){
                row.add(line.charAt(i) - '0');
            }
            
            for(int cell = 0; cell < 4; cell ++){   //Extend grid outwards
                for (int i = 0; i < width; i++){
                    int prev = row.get(cell*width + i);
                    if(prev == 9){
                        row.add(1);
                    }
                    else{
                        row.add(prev + 1);
                    }
                }
            }
            grid.add(row);
        }
        for (int cell = 0; cell < 4; cell++){   //Extend grid downwards
            for (int j = 0; j < height; j++){
                ArrayList<Integer> prevRow = grid.get(cell*(height) + j);
                ArrayList<Integer> newRow = new ArrayList<>();
                for (int i = 0; i < 5*width; i++){
                    int prev = prevRow.get(i);
                    if(prev == 9){
                        newRow.add(1);
                    }
                    else{
                        newRow.add(prev + 1);
                    }
                }
                grid.add(newRow);
            }
        }
        return aStarGraph(grid, 5*width-1, 5*height - 1);
    }

    static int aStarGraph (ArrayList<ArrayList<Integer>> grid, int iGoal, int jGoal){
        double startTime = System.currentTimeMillis();
        HashSet<Node> visited = new HashSet<>();
        PriorityQueue<Node> frontier = new PriorityQueue<Node>((n2,n1) -> (n2.risk + (iGoal - n2.i) + (jGoal - n2.j)) - (n1.risk + (iGoal - n1.i) + (jGoal - n1.j)));
        frontier.add(new Node(0,0,0));
        int visiteds = 0;
        while(!frontier.isEmpty()){
            visiteds ++;
            Node node = frontier.remove();
            visited.add(node);
            if (node.i == iGoal && node.j == jGoal){
                double elapsed = System.currentTimeMillis() - startTime;
                System.out.println("Nodes visited: " + visiteds + ", Time = " + elapsed + " ms");
                return node.risk;
            }
            Stack<Node> children = new Stack<>();
            if(node.i < iGoal){ children.push(new Node(node.i+1,node.j,node.risk + grid.get(node.j).get(node.i + 1)));}
            if(node.j < jGoal){ children.push(new Node(node.i,node.j+1,node.risk + grid.get(node.j + 1).get(node.i)));}
            if(node.i > 0){ children.push(new Node(node.i-1,node.j,node.risk + grid.get(node.j).get(node.i - 1)));}
            if(node.j > 0){ children.push(new Node(node.i,node.j-1,node.risk + grid.get(node.j - 1).get(node.i)));}
            for (Node child : children){
                if (!visited.contains(child)){
                    frontier.add(child);
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day15Input.txt";
        System.out.println("Answer 1: " + solve(fileName) + "\nAnswer 2: " + solve2(fileName));
    }
}
