package com.simplesync.jni.sample;

import org.junit.Assert;
import org.junit.Test;

public class JNITest {

    @Test
    public void testJavaPow() {
        Assert.assertEquals(Math.pow(15.625, 1 / 3d), 2.5, 0);
    }

    @Test
    public void testNativePow() {

        double javaResult = Math.pow(15.625, 1 / 3d);
        double nativeResult = _pow(15.625, 1 / 3d);

        Assert.assertEquals(javaResult, nativeResult, Double.MIN_VALUE);
    }

    public native double _pow(double basis, double exponent);
}
