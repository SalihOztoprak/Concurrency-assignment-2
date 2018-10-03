package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private Condition discoIsFull = reentrantLock.newCondition();
    private static int discoCounter;
    private static boolean containsRecordCompany;

    /**
     *
     */
    public void enterDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            enterRecordCompany();
        } else {
            discoCounter++;
            enterVistor();
        }
    }

    /**
     *
     */
    public void exitDisco() {
        Thread t = Thread.currentThread();
        if (t instanceof RecordCompany) {
            exitRecordCompany();
        } else {
            discoCounter--;
            exitVistor();
        }
    }

    /**
     *
     */
    private void enterVistor() {
        reentrantLock.lock();
        try {
            while (discoFull()) {
                discoIsFull.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     *
     */
    private void exitVistor() {
        reentrantLock.lock();
        discoIsFull.signal();
        reentrantLock.unlock();
    }

    /**
     *
     */
    private void enterRecordCompany() {
        synchronized (reentrantLock) {
            while (!containsRecordCompany) {
                reentrantLock.lock();
                if (canRecordCompanyEnter()) {
                    containsRecordCompany = true;
                }
            }
            reentrantLock.notifyAll();
            lockDisco();
            containsRecordCompany = false;
        }
    }

    /**
     *
     * @return
     */
    private boolean discoFull() {
        return discoCounter == 20;
    }

    /**
     *
     * @return
     */
    private boolean canRecordCompanyEnter() {
        return discoCounter <= 10;
    }

    /**
     *
     */
    private void exitRecordCompany() {
        reentrantLock.unlock();
        containsRecordCompany = false;
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
            reentrantLock.unlock();
        }
    }
}
