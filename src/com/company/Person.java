package com.company;

import java.util.Random;

public class Person extends  Thread{
    private int personNumber;
    private Disco disco;

    public Person(int number, Disco disco) {
        this.personNumber = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
        System.out.println("Someone entered: " + personNumber);
        disco.enterDisco();
        Random random = new Random();
        int time = random.nextInt(2) + 10;
        try {
            sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disco.exitDisco();
        System.out.println("Someone left: " + personNumber);
    }

    @Override
    public String toString() {
        return "Person: "+ personNumber;
    }

}
