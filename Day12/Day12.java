package Day12;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Day12 {
    static int[] solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        HashMap<String, HashSet<String>> edges = new HashMap<>();
        while(sc.hasNext()){    //Build graph
            String[] edge = sc.nextLine().split("-");
            for (int i = 0; i < 2; i ++){   //Add both directions of edge
                if (!edges.containsKey(edge[i])){
                    edges.put(edge[i], new HashSet<>());
                }
                edges.get(edge[i]).add(edge[(i+1)%2]);
            }
        }

        HashSet<String> visitedSmalls = new HashSet<>();
        return new int[] {numPaths("start", visitedSmalls, edges, "null"), numPaths("start", visitedSmalls, edges, "")};
    }

    static int numPaths(String curr, HashSet<String> visitedSmalls, HashMap<String, HashSet<String>> edges, String doubleCave) {
        if(curr.equals("end")){
            return 1;
        }
        else{
            HashSet<String> newVisitedSmalls = new HashSet<>(visitedSmalls);
            if (curr.charAt(0) >= 'a'){  //If this is a small cave

                newVisitedSmalls.add(curr);
            }
            int paths = 0;
            for (String next : edges.get(curr)){
                if (!visitedSmalls.contains(next)){ //If next is either a big cave or a small cave we havent visited
                    paths += numPaths(next, newVisitedSmalls, edges, doubleCave);
                }
                else if(!next.equals("start") && !next.equals("end")){
                    if (doubleCave.equals("")){ //If we haven't been to any small cave twice we can revisit this one
                        paths += numPaths(next, newVisitedSmalls, edges, next);
                    }
                }
            }
            return paths;
        }
    }

        public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day12Input.txt";
        int[] ans = solve(fileName);
        System.out.println("Answer 1: " + ans[0] + "\nAnswer 2: " + ans[1]);
    }
}
