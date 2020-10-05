package practice;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

interface demo{
    int x=5;
    default void show(){
        System.out.println("myint");
    }
}
  class myDemo{

    myDemo(){
        System.out.println("Parent");
    }
    
    void show(){
        System.out.println("myDemo");
    }

}
class A implements demo{
    A() {
        System.out.println(x);
    }

    @Override
    public void show() {

    }
    public static void ex(){

        System.out.println("private class");
    }
}


public class Questions {
    public static boolean permutation(){
        String w1 = "abc ";
        String w2 = "bca";
        char[] temp1 = w1.toCharArray();
        char[] temp2 = w2.toCharArray();

        Arrays.sort(temp1);
        Arrays.sort(temp2);

        return new String(temp1).equals(new String(temp2));
    }

    public static void unique(){
        String word = "abs";
        Hashtable<Character, Integer> table = new Hashtable<>();
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(!table.containsKey(c)){
                table.put(c, 1);
            }else{
                System.out.println("Not unique");
                return ;

            }
        }
        System.out.println("Unique");
        return;


    }

    public static void mrSmith(){
        String word = "Mr John Smith   ";
        String temp = "";
        word = word.strip();
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i)!=' '){
                temp += word.charAt(i);
            }else{
                temp += "%20";
            }

        }
        System.out.println(temp);
    }
    public static boolean perm(){
        String word = "Tact Coa";
        word= word.toLowerCase();
        Hashtable<Character, Integer> table = new Hashtable<>();
        int check = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(!table.containsKey(c)){
                if (c==' '){
                    continue;
                }

                table.put(c,1);
            }else{

                table.put(c,table.get(c)+1);
            }
        }
        Set<Character> keys=table.keySet();
        for(Character key:keys){
            int val = table.get(key);
            System.out.println(" " +key+val);
            if(val>2){
                return false;
            }else if(val==1){
                check++;
            }
        }
        if(check!=1){
            return false;
        }else{
            return true;
        }
    }
    public static void method(int x){

    }
    static int fib(int n)
    {
        if (n <= 1)
            return n;

        return fib(n-1) + fib(n-2);
    }


    public static void method(){
        System.out.println("");
    }
    public static void main(String[] args) {
       int x=15;
        for (int i = 0; i < 3; i++) {
            if(x%3==0){
                System.out.println("divide by 3");

            }else if(x%5==0){
                System.out.println("divide by 5");
            }else{
                System.out.println("nothing");
            }
            System.out.println("loop");
        }


    }
}
