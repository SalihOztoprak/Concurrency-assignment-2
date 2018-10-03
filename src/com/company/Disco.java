package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {

    private ReentrantLock reentrantLockVisitor = new ReentrantLock();
    private ReentrantLock reentrantLockRC = new ReentrantLock();
    private Condition discoIsFull = reentrantLockVisitor.newCondition();
    private Condition rcInDisco = reentrantLockRC.newCondition();

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
            enterVisitor();
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
            discoCounter--;
        }
    }

    /**
     *
     */
    private void enterVisitor() {
        reentrantLockVisitor.lock();
        try {
            while (discoFull()) {
                discoIsFull.await();
            }
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
        discoIsFull.signal();
        reentrantLockVisitor.unlock();
    }

    /**
     *
     */
    private void enterRecordCompany() {
        reentrantLockRC.lock();
        if (canRecordCompanyEnter()) {
            try {
                containsRecordCompany = true;
                rcInDisco.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLockRC.unlock();
            }
        }
    }

    /**
     *
     */
    private void exitRecordCompany() {
        reentrantLockRC.lock();
        containsRecordCompany = false;
        rcInDisco.signal();
        reentrantLockRC.unlock();
    }

    /**
     * @return
     */
    private boolean discoFull() {
        return discoCounter == 20 ;
    }

    /**
     * @return
     */
    private boolean canRecordCompanyEnter() {
        return discoCounter <= 10 && !containsRecordCompany;
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
