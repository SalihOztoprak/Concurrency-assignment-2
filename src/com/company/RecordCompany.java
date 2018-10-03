package com.company;

import java.util.Random;

public class RecordCompany extends Thread {
    private String name;
    private int number;
    private Disco disco;

    public RecordCompany(int number, Disco disco) {
        this.number = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
        disco.enterDisco();
        System.out.println("Someone important entered: " + number);
        Random random = new Random();
        int time = random.nextInt(2) + 10;
        try {
            sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Someone important left: " + number);
    }

    @Override
    public String toString() {
        return "RecordCompany: "+ number;
    }
}
