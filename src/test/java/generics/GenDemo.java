package generics;

import java.util.Comparator;

public class GenDemo {
    public static void main(String[] args) {
        Gen<String> gen = new Gen<>("Hello generics");
        gen.showClassType();
        System.out.println(gen.getOb());

        Gen<Integer> gen1 = new Gen<>(90);
        gen1.showClassType();
        System.out.println(gen1.getOb());

        Gen<Double> gen2 = new Gen<>(55.55);
        gen2.showClassType();
        System.out.println(gen2.getOb());
    }

    class Test<T extends Number>{

    }

    class Test2<T extends Runnable>{

    }

    class Test3<T extends String>{

    }

    public void practice(){
        Test<Integer> integerTest = new Test<>();
        Test2<Runnable> runnableTest = new Test2();
        Test2<Thread> threadTest2 = new Test2<>();
        new Test3<String>();
    }

    class Test4<T extends Number & Runnable>{

    }

    class Test5<T extends Runnable & Comparator>{

    }

    class Test6<T extends Number & Runnable & Comparator>{

    }

//    class Test7<T extends Runnable & Number>{
//
//    }

    class A extends Test4 implements Runnable{

        @Override
        public void run() {

        }
    }

    class B<X>{

    }
    class E<A>{

    }
    class F<C>{

    }
    class G<T>{

    }

    class Fg<X, Y, Z>{

    }
}
