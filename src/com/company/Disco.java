package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition discoIsFull = reentrantLock.newCondition();
    private int discoCounter;
    private boolean containsRecordCompany;

    public void enterDisco(Object object) {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            enterRecordCompany((RecordCompany) object);
        } else {
            discoCounter++;
            enterVistor((Person) object);
        }
    }

    public void exitDisco() {

    }


    private void enterVistor(Person person) {
        reentrantLock.lock();
        try {
            while (discoContains(20)) {
                System.out.println("Someone entered: " + person);
                discoIsFull.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void exitVistor(Person person) {
        System.out.println("Someone left: " + person);
    }

    private void enterRecordCompany(RecordCompany recordCompany) {
        while (discoContains(10) && !containsRecordCompany) {
            reentrantLock.lock();
            System.out.println("Someone important entered: " + recordCompany);
            containsRecordCompany = true;
            lockDisco();
        }
    }

    private boolean discoContains(int amount) {
        return discoCounter <= amount;
    }

    private void exitRecordCompany(RecordCompany recordCompany) {
        System.out.println("Someone important left: " + recordCompany);
        containsRecordCompany = false;
    }

    private void lockDisco() {
        try {
            discoIsFull.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
