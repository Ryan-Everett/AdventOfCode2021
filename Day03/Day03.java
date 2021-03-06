package Day03;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class Day03 {
    static int solve1(String fileName) throws IOException {
        Scanner sc = new Scanner(new File(fileName));

        String first = sc.next();   //Do first element separately. Can make a PeekableScanner if you don't want the bonus for loop, but it isn't worth it for one element. 
        final int LEN = first.length();
        int[] count = new int[LEN];
        for (int i = 0; i < LEN; i++) {
            count[i] += first.charAt(i) - '0';
        }
        int total = 1;
        
        while (sc.hasNextLine()){   //Fill count with the amount of '1's in each column
            String curr = sc.next();
            for (int i = 0; i < LEN; i++) {
                count[i] += curr.charAt(i) - '0';
            }
            total ++;
        }
        
        int gamma = 0;
        int epsilon = 0;
        for (int i = 0; i < LEN; i++){
            gamma *= 2;
            epsilon *= 2;
            if (count[i] >= (total/2)){  //If more than half of the numbers in the column are '1'
                gamma += 1;
            }
            else{
                epsilon += 1;   //espilon[i] = neg(gamma[i])
            }
        }
        return gamma*epsilon;
    }

    //Return 1 if more '1's than '0's, '0' otherwise. Setting mode to 1 switches this behaviour
    static char mcv (LinkedList<String> nums, int index, int mode){
        Iterator<String> iter = nums.iterator();
        int oneCount = 0;
        int len = 0;
        while(iter.hasNext()){
            oneCount += iter.next().charAt(index) - '0'; len++;
        }
        char a = '0';   //Define char to avoid cast
        if (oneCount*2 < len){
            a += mode;
        }
        else {
            a += (1 - mode);
        }
        return a;
    }
    static int solve2(String fileName) throws IOException{
        LinkedList<String> numList1 = new LinkedList<>(); //Initialise lists
        LinkedList<String> numList2 = new LinkedList<>();
        Scanner sc = new Scanner(new File(fileName));
        while (sc.hasNextLine()) {
            String next = sc.next();
            numList1.add(next);
            numList2.add(next);
        }

        int i = 0;
        while((numList1.size() > 1)){ //Find 02
            final int j = i;    //Make a final copy of i to use in the lambdas, to please the jvm
            final int lcv = mcv(numList1,j,1);
            numList1.removeIf(num -> num.charAt(j) == lcv);
            i++;
        }

        i = 0;
        while((numList2.size() > 1)){  //Find co2
            final int j = i;
            final int mcv = mcv(numList2,j,0);
                numList2.removeIf(num -> num.charAt(j) == mcv);
            i++;
        }
        String s1 = numList1.getFirst();
        String s2 = numList2.getFirst();

        int o2 = 0;
        int co2 = 0;
        for(int j = 0; j < s1.length(); j++){ //Compute int value of o2 and co2
            o2*=2; co2*=2;
            o2 += s1.charAt(j) - '0';
            co2 += s2.charAt(j) - '0';
            i++;
        }
        return o2*co2;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day03Input.txt";
        System.out.println(solve1(fileName));
        System.out.println(solve2(fileName));
    }
}