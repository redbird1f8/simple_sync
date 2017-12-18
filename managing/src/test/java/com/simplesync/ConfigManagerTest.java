package com.simplesync;

import com.simplesync.nconf.SymShare;
import com.simplesync.nconf.SyncDevice;
import org.junit.Test;

import javax.swing.*;

import static org.mockito.Mockito.*;

import java.io.OutputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Nickitakalinkin on 18.12.17.
 */
public class ConfigManagerTest {


    @Test
    public void containsDevice() throws Exception {

        List<SyncDevice> devices = ConfigManager.getSymShare().getDevices();

        SyncDevice td1 = new SyncDevice("n1","ip1");
        SyncDevice td2 = new SyncDevice("n2","ip2");
        SyncDevice td3 = new SyncDevice("n3","ip3");

        SyncDevice tempDevice  = new SyncDevice("n1","ip1");

        devices.add(td1);
        devices.add(td2);
        devices.add(td3);

       boolean actual =  ConfigManager.containsDevice(tempDevice);
       boolean expected = true;
       assertEquals(expected,actual);

       devices.remove(td1);
       devices.remove(td2);
       devices.remove(td3);

    }

}