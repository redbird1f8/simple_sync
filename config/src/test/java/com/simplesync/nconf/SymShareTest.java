package com.simplesync.nconf;

import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Nickitakalinkin on 19.12.17.
 */
public class SymShareTest {
    @Test
    public void addDevice() throws Exception {
        SymShare mockShare = mock(SymShare.class);
        SyncDevice mockDevice = mock(SyncDevice.class);
        mockShare.addDevice(mockDevice);
        //assertEquals(mockShare.getDevices());

    }

    @Test
    public void setRootPath() throws Exception {
    }

}