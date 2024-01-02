package e2e;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utilities.PropertyUtility;

public class BaseTest {

    @BeforeClass(groups = {"e2e", "parallel"})
    public void setBaseURI() {
        String propertyValue = PropertyUtility.getPropertyValue("base.url");
        RestAssured.baseURI = propertyValue;
    }

}
