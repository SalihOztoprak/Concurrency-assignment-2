package com.company;

public class Person extends  Thread{
    private int personNumber;
    private Disco disco;

    public Person(int number, Disco disco) {
        this.personNumber = number;
        this.disco = disco;
    }


    @Override
    public String toString() {
        return "Person: "+ personNumber;
    }

}
