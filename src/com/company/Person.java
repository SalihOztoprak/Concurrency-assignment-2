package com.company;

import java.util.Random;

public class Person extends Thread {
    private int personNumber;
    private Disco disco;

    public Person(int number, Disco disco) {
        this.personNumber = number + 1;
        this.disco = disco;
    }

    @Override
    public void run() {
        while (true) {
            waitSomeTime();
            disco.enterDisco();

            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println(personNumber + ") A visitor entered");
            System.out.println("-----------------------------------------");
            System.out.println();

            waitSomeTime();
            disco.exitDisco();

            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println(personNumber + ") A visitor left");
            System.out.println("-----------------------------------------");
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Person: " + personNumber;
    }

    private void waitSomeTime() {
        Random random = new Random();
        int time = random.nextInt(15) + 1;
        try {
            sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
