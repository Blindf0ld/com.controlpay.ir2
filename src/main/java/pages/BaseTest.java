package pages;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

/**
 * Created by pc on 05.06.2017.
 */
public class BaseTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
        RestAssured.basePath = "/student";

    }
}
