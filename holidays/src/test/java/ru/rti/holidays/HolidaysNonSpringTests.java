package ru.rti.holidays;

import junit.runner.BaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import org.junit.*;
import org.junit.runner.*;
import ru.rti.holidays.simple.HtmlUtilsTest;
import ru.rti.holidays.utility.HtmlUtils;

@RunWith(Suite.class)
//@RunWith(HolidaysNonSpringTestsRunner.class)
@Suite.SuiteClasses({
    HtmlUtilsTest.class
})

public class HolidaysNonSpringTests {
}
