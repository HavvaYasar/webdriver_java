package utilities.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import core.TestBase;
import utilities.ExtentReports.ExtentManager;
import java.util.Date;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class ExecutionListener extends  TestBase {

    public static class InnerExecutionListener extends  RunListener{

        private ExtentReports extent = ExtentManager.createInstance();
        private ThreadLocal<ExtentTest> test = new ThreadLocal<>();
        long startTime;
        long endTime;

        @Override
        public void testRunStarted(Description description) {
            startTime = new Date().getTime();
            System.out.println("Tests started! Number of Test case: " + description.testCount() + "\n");
        }

        @Override
        public void testRunFinished(Result result) {
            endTime = new Date().getTime();
            System.out.println("Tests finished! Number of test case: " + result.getRunCount());
            long elapsedSeconds = (endTime - startTime) / 1000;
            System.out.println("Elapsed time of tests execution: " + elapsedSeconds + " seconds");
            extent.flush();
        }

        @Override
        public void testStarted(Description description) {
            System.out.println(description.getMethodName() + " test is starting...");
            ExtentTest extentTest = extent.createTest(description.getMethodName(), description.getDisplayName());
            test.set(extentTest);
        }

        @Override
        public void testFinished(Description description) {
            System.out.println(description.getMethodName() + " test is finished...\n");
            test.get().pass("Test passed");
        }

        @Override
        public synchronized void testFailure(Failure failure) {
            System.out.println(failure.getDescription().getMethodName() + " test FAILED!!!");
            Throwable t = new RuntimeException("A runtime exception");
            test.get().fail(t);
            test.get().log(Status.FAIL, t, MediaEntityBuilder.createScreenCaptureFromBase64String("base64").build());
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
    }
}