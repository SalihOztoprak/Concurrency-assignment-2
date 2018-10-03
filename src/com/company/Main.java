package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    private ArrayList<Person> persons;
    private ArrayList<RecordCompany> recordCompanies;
    private Disco disco;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run(){
        persons = new ArrayList<>();
        recordCompanies = new ArrayList<>();

        disco = new Disco();
        for (int i = 0; i <20 ; i++) {
            persons.add(new Person(i,disco));
            persons.get(i).start();
        }
        for (int i = 0; i <3 ; i++) {
            recordCompanies.add(new RecordCompany(i,disco));
            recordCompanies.get(i).start();
        }
    }
}
