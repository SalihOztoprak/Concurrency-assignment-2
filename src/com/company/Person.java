package com.company;

public class Person extends  Thread{
    private int personNumber;
    private Disco disco;

    public Person(int number, Disco disco) {
        this.personNumber = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
        disco.enterDisco(this);
    }

    @Override
    public String toString() {
        return "Person: "+ personNumber;
    }

}
