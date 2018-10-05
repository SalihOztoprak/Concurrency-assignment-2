package com.company;

import java.util.Random;

public class Visitor extends Thread {
    private int personNumber;
    private Disco disco;

    /**
     * @param number the number of the person
     * @param disco the disco
     */
    public Visitor(int number, Disco disco) {
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

            disco.exitDisco();
            waitSomeTime();

            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println(personNumber + ") A visitor left");
            System.out.println("-----------------------------------------");
            System.out.println();
        }
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
