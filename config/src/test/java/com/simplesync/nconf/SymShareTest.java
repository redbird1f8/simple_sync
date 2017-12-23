package com.simplesync.nconf;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Nickitakalinkin on 19.12.17.
 */
public class SymShareTest {
    @Test
    public void setDevices() throws Exception {
        List<SyncDevice> mockDevices = mock(List.class);
        SymShare mockShare = new SymShare();
        mockShare.setDevices(mockDevices);

        assertEquals(mockDevices,mockShare.getDevices());

    }

    @Test
    public void addDevice() throws Exception {
        SymShare mockShare = new SymShare();
        SyncDevice mockDevice = new SyncDevice("name","ip");

        mockShare.addDevice(mockDevice);
        int actual = mockShare.getDevices().size();
        int expected = 1;
        assertEquals(expected,actual);

        //assertEquals(mockShare.getDevices());

    }

    @Test
    public void setRootPath() throws Exception {
        String path = "path";
        SymShare mockShare = new SymShare();
        mockShare.setRootPath(path);
        assertEquals(path,mockShare.getRootPath());


    }

}