package ru.rti.holidays.simple;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BitmapMaskTest {

    @Test
    public void testBitmapMaskOperation() {
        int maxMask = 7;

        maxMask = maxMask & 6;

        assertThat(maxMask).isEqualTo(6);
    }
}
