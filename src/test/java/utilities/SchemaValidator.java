package utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;

public class SchemaValidator {
    public static boolean validateSchema(Response response, String schemaFilePath){
        InputStream inputStream = SchemaValidator.class.getResourceAsStream(schemaFilePath);

        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(inputStream));
            return true;
        }catch (AssertionError e){
            return false;
        }
    }
}
