package com.libsimsync.managing;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class TempDevice {
    private String name;
    private String address;
    

    public TempDevice(String name, String adress) {
        this.name = name;
        this.address = adress;
    }

    public boolean isEqual(TempDevice device) {
      return (this.name.equals(device.name) && this.address.equals(device.address)) ;
    }



    // Скорее всего они уйдут (или это будет обертка для device )
    public String toString() {
        return name + " | " + address;
    }

    public static TempDevice fromString(String str) {

        String[] splitStr = str.split(" [|] ");
//        System.out.println("Разббиение строки");
//        for (String s : splitStr) {
//            System.out.println(s);
//        }
        return new TempDevice(splitStr[0],splitStr[1]);
    }


    public String getName() {
        return name;
    }

    public String getAdress() {
        return address;
    }
}
