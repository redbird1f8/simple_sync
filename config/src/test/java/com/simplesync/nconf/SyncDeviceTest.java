package com.simplesync.nconf;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nickitakalinkin on 18.12.17.
 */
public class SyncDeviceTest {
    @Test
    public void isEqual() throws Exception {
        SyncDevice expected = new SyncDevice("Name","192.168.1.1");
        SyncDevice actual = new SyncDevice("Name","192.168.1.1");
        assertTrue(expected.isEqual(actual));

    }

    @Test
    public void fromString() throws Exception {
        SyncDevice expected = new SyncDevice("Name","192.168.1.1");
        String testString = expected.toString();

        SyncDevice actual = SyncDevice.fromString(testString);

        assertEquals(actual.toString(),expected.toString());

    }

}