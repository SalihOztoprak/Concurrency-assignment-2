package com.company;

public class RecordCompany extends Thread {
    private int number;
    private Disco  disco;

    public RecordCompany(int number, Disco disco) {
        this.number = number+1;
        this.disco = disco;
    }

    @Override
    public void run() {
        disco.enterDisco(this);
        disco.exitDisco();
    }

    @Override
    public String toString() {
        return "RecordCompany: "+ number;
    }
}
