package ru.rti.holidays;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.InitializationError;
import ru.rti.holidays.listener.HolidaysNonSpringTestsListener;
import ru.rti.holidays.simple.HtmlUtilsTest;

public class HolidaysNonSpringTestMain {
   public static void main(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new HolidaysNonSpringTestsListener());
       try {
           core.run(new HolidaysNonSpringTestsRunner(HtmlUtilsTest.class));
       } catch (InitializationError initializationError) {
           initializationError.printStackTrace();
       }

/*        Result result = JUnitCore.runClasses(HolidaysNonSpringTests.class);

        System.out.println("Run count (tests number): " + result.getRunCount());
        System.out.println("Run time: " + result.getRunTime());

        RunListener listener = result.createListener();



        for (Failure fail : result.getFailures()) {
            System.out.println(fail.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests finished successfully...");
        }*/
    }
}
