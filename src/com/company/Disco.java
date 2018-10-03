package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition discoIsFull = reentrantLock.newCondition();
    private int discoCounter;
    private boolean containsRecordCompany;

    public void enterDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            enterRecordCompany();
        } else {
            discoCounter++;
            enterVistor();
        }
    }

    public void exitDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            exitRecordCompany();
        } else {
            discoCounter--;
            exitVistor();
        }
    }


    private void enterVistor() {
        reentrantLock.lock();
        try {
            while (discoFull(20)) {
                discoIsFull.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void exitVistor() {
        reentrantLock.lock();
        discoIsFull.signal();
        reentrantLock.unlock();
    }

    private void enterRecordCompany() {
        while (canRecordCompanyEnter(10) && !containsRecordCompany) {
            reentrantLock.lock();
            containsRecordCompany = true;
            lockDisco();
        }
    }

    private boolean discoFull(int amount) {
        return discoCounter == amount;
    }

    private boolean canRecordCompanyEnter(int amount) {
        return discoCounter <= amount;
    }

    private void exitRecordCompany() {
        reentrantLock.unlock();
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
