package com.company;

import java.util.ArrayList;

public class Main {
    private ArrayList<Person> persons;
    private ArrayList<RecordCompany> recordCompanies;
    private Disco disco;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run(){
        disco = new Disco();
        for (int i = 1; i <21 ; i++) {
            persons.add(new Person(i,disco));
        }
        for (int i = 1; i <4 ; i++) {
            recordCompanies.add(new RecordCompany(i,disco));
        }

    }
}
