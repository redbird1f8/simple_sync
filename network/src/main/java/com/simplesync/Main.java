package com.simplesync;

        class Main {
            public static void main(String[] args) throws InterruptedException {
                Peer first =  new Peer();
                Peer second = new Peer();
                Peer third  = new Peer();

                first.listen(9100);
                second.connect("localhost",9100);
                third.connect("localhost",9100);

            }
        }
