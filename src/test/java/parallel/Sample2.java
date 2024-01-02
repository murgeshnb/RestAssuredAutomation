package parallel;

import org.testng.annotations.Test;

public class Sample2 {
    @Test(groups = {"parallelClasses"})
    public void test1(){
        System.out.println("class 2");
    }
}
