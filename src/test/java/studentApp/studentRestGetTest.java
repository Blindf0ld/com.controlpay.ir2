package studentApp;

import org.junit.Test;
import pages.BaseTest;

import static io.restassured.RestAssured.given;

/**
 * Created by pc on 05.06.2017.
 */
public class studentRestGetTest extends BaseTest {


    @Test
    public void getAllListOfStudents() {
        given().log().all().
                when().get("/list");
    }

    @Test
    public void checkResponseStatus() {
        given().log().all().
                when().get("/list").
                then().assertThat().statusCode(300);
    }
}
