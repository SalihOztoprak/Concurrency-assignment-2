package com.company;

import java.util.Random;

public class RecordCompany extends Thread {
    private int number;
    private Disco disco;

    public RecordCompany(int number, Disco disco) {
        this.number = number + 1;
        this.disco = disco;
    }

    @Override
    public void run() {
        while (true) {
            waitSomeTime();
            disco.enterDisco();

            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println(number + ") A Record Company entered");
            System.out.println("-----------------------------------------");
            System.out.println();

            waitSomeTime();
            disco.exitDisco();

            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println(number + ") A Record Company left");
            System.out.println("-----------------------------------------");
            System.out.println();
        }
    }


    private void waitSomeTime() {
        Random random = new Random();
        int time = random.nextInt(10) + 1;
        try {
            sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
