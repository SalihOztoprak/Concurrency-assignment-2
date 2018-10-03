package com.company;

import java.util.Random;

public class Person extends  Thread{
    private  int personNumber;
    private Disco disco;

    public Person(int number, Disco disco) {
        this.personNumber = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
        while (true) {
            waitSomeTime();
            disco.enterDisco();
            System.out.println("Person : " + personNumber+" entered");

            waitSomeTime();
            disco.exitDisco(this);
            System.out.println("Person : " + personNumber+" left");
//            personNumber++;
        }
    }

    @Override
    public String toString() {
        return "Person: "+ personNumber;
    }

    private void waitSomeTime(){
        Random random = new Random();
        int time = random.nextInt(60) + 1;
        try {
            sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
