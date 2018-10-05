package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private ReentrantLock reentrantLockVisitor = new ReentrantLock();
    private ReentrantLock reentrantLockRC = new ReentrantLock();
    private Condition discoIsFull = reentrantLockVisitor.newCondition();
    private Condition rcInDisco = reentrantLockRC.newCondition();

    private int discoCounter = 0;
    private int maxPeople = 20;
    private boolean containsRecordCompany;
    private int rcCounter;

    /**
     *
     */
    public void enterDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            enterRecordCompany();
        } else {
            enterVisitor();
        }
        System.out.println();
        System.out.println("Discocounter " + discoCounter);
        System.out.println("Do we have a recordcompany: " + containsRecordCompany);
//        System.out.println();
        if (discoCounter == 20){
            rcCounter = 0;
        }
    }

    /**
     *
     */
    public void exitDisco(Object object) {
        Thread t = Thread.currentThread();

        if (t instanceof RecordCompany) {
            exitRecordCompany();
        } else {
            if (object instanceof Person) {
                exitVisitor();
            } else {
                exitRecordCompany();
            }
        }
        System.out.println();
        System.out.println("Discocounter " + discoCounter);
    }

    /**
     *
     */
    private void enterVisitor() {
        reentrantLockVisitor.lock();
        try {
            while (!mayEnterDisco()) {
                discoIsFull.await();
            }
            discoCounter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLockVisitor.unlock();
        }
    }

    /**
     *
     */
    private void exitVisitor() {
        reentrantLockVisitor.lock();
        discoCounter--;
        discoIsFull.signal();
        reentrantLockVisitor.unlock();
    }

    /**
     *
     */
    private void enterRecordCompany() {
        reentrantLockRC.lock();

        try {
            while (!canRecordCompanyEnter()) {
                rcInDisco.await();
            }
            rcCounter++;
            containsRecordCompany = true;
            discoCounter += 10;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLockRC.unlock();
        }
    }

    /**
     *
     */
    private void exitRecordCompany() {
        reentrantLockRC.lock();
        containsRecordCompany = false;
        discoCounter -= (maxPeople/2);
        rcInDisco.signal();
        reentrantLockRC.unlock();
    }

    /**
     * @return
     */
    private boolean mayEnterDisco() {
        return discoCounter < maxPeople && !containsRecordCompany;
    }

    /**
     * @return
     */
    private boolean canRecordCompanyEnter() {
        return discoCounter <= (maxPeople/2) && !containsRecordCompany && rcCounter < 3;
    }

    /**
     *
     */
    private void lockDisco() {
        try {
            discoIsFull.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLockVisitor.unlock();
        }
    }
}
