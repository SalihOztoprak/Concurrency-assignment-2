package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition letVisitorEnter = reentrantLock.newCondition();
    private Condition letRcEnter = reentrantLock.newCondition();

    private int discoCounter = 0;
    private int maxPeople = 20;
    private boolean containsRecordCompany;
    private int rcCounter;

    /**
     * Let the a person enter, check if it is a visitor or a record company.
     */
    public void enterDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            enterRecordCompany();
        } else {
            enterVisitor();
        }
        System.out.println("Persons in disco: " + discoCounter);
    }

    /**
     * Let the person leave, check if it is a visitor or a record company.
     */
    public void exitDisco() {
        Thread t = Thread.currentThread();

        if (t instanceof RecordCompany) {
            exitRecordCompany();
        } else {
            exitVisitor();
        }
        System.out.println("Persons in disco: " + discoCounter);
    }

    /**
     * The visitor enters the disco.
     */
    private void enterVisitor() {
        reentrantLock.lock();
        try {
            while (!canVisitorEnter()) {
                letVisitorEnter.await();
            }
            assert discoCounter < maxPeople : " The visitors may not enter, the disco is full!";
            assert !containsRecordCompany : "The visitor may not enter, there is still a record guy in the club";
            discoCounter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * The visitor exits the disco
     */
    private void exitVisitor() {
        reentrantLock.lock();
        discoCounter--;
        if (rcCounter == 0) {
            letRcEnter.signalAll();
        } else {
            letVisitorEnter.signal();
        }
        reentrantLock.unlock();
    }

    /**
     * The record company guy is entering the disco.
     */
    private void enterRecordCompany() {
        reentrantLock.lock();

        try {
            while (!canRecordCompanyEnter()) {
                letRcEnter.await();
            }
            assert !containsRecordCompany : "The record guy cannot enter the disco, there is one already in!";
            assert discoCounter<= (maxPeople /2) : "The disco is to busy to enter ";
            rcCounter++;
            containsRecordCompany = true;
            discoCounter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * The record company guy is leaving.
     */
    private void exitRecordCompany() {
        reentrantLock.lock();
        containsRecordCompany = false;
        discoCounter--;
        if (rcCounter == 3) {
            letVisitorEnter.signalAll();
            rcCounter = 0;
        } else {
            letRcEnter.signal();
        }
        reentrantLock.unlock();
    }

    /**
     * Check if the visitor can enter.
     * @return
     */
    private boolean canVisitorEnter() {
        return discoCounter < maxPeople && !containsRecordCompany || discoCounter < maxPeople && rcCounter < 3 && !containsRecordCompany;
    }

    /**
     * check if the visitor can enter.
     * @return
     */
    private boolean canRecordCompanyEnter() {
        return discoCounter <= (maxPeople / 2) && !containsRecordCompany && rcCounter < 3;
    }
}
