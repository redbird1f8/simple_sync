package com.simplesync;

import org.junit.Assert;
import org.junit.Test;

public class ResolvingMethodFactoryTest {

    @Test
    public void testConstructingLocalMethod() throws Exception {
        ResolvingMethod method = ResolvingMethodFactory.construct("local:321ds45tg43ffsdsrt");
        Assert.assertEquals("local", method.methodName());
    }

    @Test
    public void testConstructingForeignMethod() throws Exception {
        ResolvingMethod method = ResolvingMethodFactory.construct("foreign:fsdfs34234fsdsrt");
        Assert.assertEquals("foreign", method.methodName());
    }
}