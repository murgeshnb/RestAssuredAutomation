package generics;

import java.util.*;

public class GenericsSample {
    public static void main(String[] args) {
        String[] s = new String[10000];
        s[0]="dare";
        s[1]="india";
        s[2]="korea";

        ArrayList list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add(10);

        String a =(String) list.get(0);
        String b =(String) list.get(1);
        String c =(String) list.get(2); // ClassCastException


        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("dare");
        arrayList.add("ravi");
//        arrayList.add(10);

        String s1 = arrayList.get(0);
        System.out.println(s1);

        //baseType <parameter type>
        ArrayList<String> l = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        //        ArrayList<Objects> strings1 = new ArrayList<String>(); // CTE - incompatible types

        ArrayList<Integer> x = new ArrayList<>();

        ArrayList<String> strings1 = new ArrayList<>();

    }
}
