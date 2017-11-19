package ru.rti.holidays;


import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import ru.rti.holidays.listener.HolidaysNonSpringTestsListener;

public class HolidaysNonSpringTestsRunner extends BlockJUnit4ClassRunner {
    HolidaysNonSpringTestsListener listener;

    public HolidaysNonSpringTestsRunner(Class cls) throws InitializationError {
        super(cls);
        listener = new HolidaysNonSpringTestsListener();
    }

    public void run(final RunNotifier notifier) {
        notifier.addListener(listener);
        super.run(notifier);
    }


}
