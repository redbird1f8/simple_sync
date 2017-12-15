package com.simplesync;

import java.io.FileNotFoundException;
import java.util.Scanner;

        class Main {

            public static void main(String[] args) throws InterruptedException, FileNotFoundException, java.io.IOException {

                Synchronizer a = new Synchronizer("C:/Users/Andrey/Desktop/KPO - Copy/untitled/A");
                Synchronizer b = new Synchronizer("C:/Users/Andrey/Desktop/KPO - Copy/untitled/B");

                a.listen();
                //a.LoadFileInfo("./AInfo");
                b.connect("localhost");
                Scanner in = new Scanner(System.in);
                for(;;) {
                    if(in.next().equals("exit")) break;
                    b.sync();
                }

                a.SaveFileInfo("./AInfo");
                return;
                /*
                Peer first =  new Peer();
                Peer second = new Peer();
                //Peer third  = new Peer();

                first.debugName  = "first";
                second.debugName = "second";

                second.listen(61020);
                first.listen(61040);
                first.connect("localhost",61020);
                sleep(5000);
                first.request("./test.rar", null);
                */

            }
        }
