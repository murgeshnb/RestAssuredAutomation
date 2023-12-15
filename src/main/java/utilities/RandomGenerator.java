package utilities;

import java.util.UUID;

public class RandomGenerator {
    public static String generateRandomEmail(){
        return UUID.randomUUID() +"@gmail.com";
    }
}
