package helpers;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Created by pc on 05.06.2017.
 */
@RunWith(MyTestRunner.class)
public class BaseTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
        RestAssured.basePath = "/student";
    }
}
