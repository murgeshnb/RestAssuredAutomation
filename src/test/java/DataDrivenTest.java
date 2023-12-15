import org.testng.annotations.Test;
import utilities.DataLoader;

public class DataDrivenTest {

    @Test
    public void fetchDataFromJsonUsingDataLoader(){
        String data = DataLoader.getData("dummyData", "user.admin.numbers[0]");
        System.out.println(data);

    }

}
