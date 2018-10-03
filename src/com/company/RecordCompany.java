package com.company;

import java.util.Random;

public class RecordCompany extends Thread {
    private  int number;
    private Disco disco;

    public RecordCompany(int number, Disco disco) {
        this.number = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
//        while (true) {
            disco.enterDisco();
            System.out.println("Someone important entered: " + number);
            Random random = new Random();
            int time = random.nextInt(10) + 2;
            try {
                sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disco.exitDisco(this);
            System.out.println("Someone important left: " + number);
//            number++;
//        }
    }

    @Override
    public String toString() {
        return "RecordCompany: "+ number;
    }
}
