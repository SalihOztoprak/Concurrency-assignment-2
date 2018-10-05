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
    }

    /**
     *
     */
    public void exitDisco() {
        Thread t = Thread.currentThread();

        if (t instanceof RecordCompany) {
            exitRecordCompany();
        } else {
            if (t instanceof Person) {
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
        reentrantLock.lock();
        try {
            while (!canVisitorEnter()) {
                letVisitorEnter.await();
            }

            discoCounter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     *
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
     *
     */
    private void enterRecordCompany() {
        reentrantLock.lock();

        try {
            while (!canRecordCompanyEnter()) {
                letRcEnter.await();
            }
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
     *
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
     * @return
     */
    private boolean canVisitorEnter() {
        return discoCounter < maxPeople && !containsRecordCompany || discoCounter < maxPeople && rcCounter < 3 && !containsRecordCompany;
    }

    /**
     * @return
     */
    private boolean canRecordCompanyEnter() {
        return discoCounter <= (maxPeople / 2) && !containsRecordCompany && rcCounter < 3;
    }

    /**
     *
     */
    private void lockDisco() {
        try {
            letVisitorEnter.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
