package com.simplesync;

import org.junit.Assert;
import org.junit.Test;

public class TempDeviceTest {

    @Test
    public void testConstructing() throws Exception {
        TempDevice tempDevice = TempDevice.fromString("name | address");
        Assert.assertEquals(tempDevice.getName(), "name");
        Assert.assertEquals(tempDevice.getAdress(), "address");
    }
}
