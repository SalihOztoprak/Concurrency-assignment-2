package com.company;

import java.util.ArrayList;

public class Main {
    private ArrayList<Visitor> visitors;
    private ArrayList<RecordCompany> recordCompanies;
    private Disco disco;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run(){
        visitors = new ArrayList<>();
        recordCompanies = new ArrayList<>();

        disco = new Disco();
        for (int i = 0; i <20 ; i++) {
            visitors.add(new Visitor(i,disco));
            visitors.get(i).start();
        }
        for (int i = 0; i <3 ; i++) {
            recordCompanies.add(new RecordCompany(i,disco));
            recordCompanies.get(i).start();
        }
    }
}
