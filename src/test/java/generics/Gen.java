package generics;

public class Gen<T>{
    T ob;
    public Gen(T ob){
        this.ob=ob;
    }

    public void showClassType(){
        System.out.println("The type of ob is: "+ob.getClass().getName());
    }

    public T getOb(){
        return ob;
    }
}
