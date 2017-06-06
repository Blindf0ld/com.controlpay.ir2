package helpers;

/**
 * Created by Plugatarenko Kate on 07-Apr-17.
 */

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class TestLogger extends RunListener {

    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestVar = new PrintStream(request, true);
    private PrintStream responseVar = new PrintStream(response, true);
    private Logger log = Logger.getLogger(TestLogger.class);
    private static String msg = "Test %s: %s %s";
    long startTime;
    long endTime;

    @Override
    public void testRunStarted(Description description) throws Exception {
        //Start time of the tests
        log.info(String.format(msg, "started", description.getTestClass(), description.getMethodName()));
        startTime = new Date().getTime();
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar));
        //Print the number of tests before the all tests execution.
        System.out.println("Tests started! Number of Test case: " + description.testCount() + "\n");
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        log.info(String.format(msg, "started", result.getClass(), result.getFailures()));
        //End time of the tests
        endTime = new Date().getTime();
        //Print the below lines when all tests are finished.
        System.out.println("Tests finished! Number of test case: " + result.getRunCount());
        long elapsedSeconds = (endTime-startTime)/1000;
        System.out.println("Elapsed time of tests execution: " + elapsedSeconds +" seconds");
    }

    @Override
    public void testStarted(Description description) throws Exception {
        log.info(String.format(msg, "started", description.getTestClass(), description.getMethodName()));
        //Write the test name when it is started.
        logRequest(request);
        logResponse(response);
        System.out.println(description.getMethodName() + " test is starting...");
    }

    @Override
    public void testFinished(Description description) throws Exception {
        //Write the test name when it is finished.
        System.out.println(description.getMethodName() + " test is finished...\n");
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        //Write the test name when it is failed.
        String testName = failure.getDescription().getTestClass().getClass().getSimpleName();
        log.info(String.format(msg, "failed", testName, failure.getDescription()));
        Optional.ofNullable(failure.getException()).ifPresent(Throwable::printStackTrace);
        ScreenShotHelper.allureScreenshot(testName + "_"+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        ScreenShotHelper.makeScreenShotIfTestFailed(testName);
        System.out.println(failure.getDescription().getMethodName() + " test FAILED!!!");
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
        Ignore ignore = description.getAnnotation(Ignore.class);
        String ignoreMessage = String.format(
                "@Ignore test method '%s()': '%s'",
                description.getMethodName(), ignore.value());
        System.out.println(ignoreMessage + "\n");
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}

