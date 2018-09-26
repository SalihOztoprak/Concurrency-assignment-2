package com.company;

public class RecordCompany extends Thread {
    private String name;
    private int number;
    private Disco  disco;

    public RecordCompany(int number, Disco disco) {
        this.number = number;
        this.disco = disco;
    }


    @Override
    public String toString() {
        return "RecordCompany: "+ number;
    }
}
