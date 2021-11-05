package com.glory.gloryUtils.example;

class ObjectWaitPkThreadSleep {

    /**
     * 锁对象
     */
    private static Object lock_object = new Object();

    public static void main(String[] args) {
        //测试wait方法
        main1();
        //测试sleep方法
        main2();

    }

    /**
     * sleep方法使用
     */
    public static void main2() {
        Thread thread02 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock_object) {
                    try {
                        System.err.println("线程02---计步器01");
                        Thread.currentThread().sleep(1000 * 10);
                        System.err.println("线程02---计步器02");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread02.start();
        System.err.println("主线程---计步器01");
        synchronized (lock_object) {
            System.err.println("因为thread02中sleep不会释放lock_object的锁，因此这里必须等thread02执行完，才会执行这里。");
        }
        System.err.println("主线程---计步器02");
    }

    /**
     * wait方法使用
     */
    public static void main1() {

        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock_object) {
                    try {
                        System.err.println("线程01---计步器01");
                        /*
                        当代码执行到这里的时候，已经获取了lock_object的锁。因此可以执行wait方法
                        执行wait方法后，当前线程会被挂起，并且释放lock_object的锁。
                        直到其他获取lock_object锁的线程，调用lock_object.notify()后，此线程才能从挂起状态，进入等待lock_object锁的状态。
                         */
                        lock_object.wait();
                        /*
                        lock_object.wait(1*1000);
                        代表1000毫秒后自动进入获取lock_object锁的状态，不需要其他获取lock_object锁的线程，调用lock_object.notify()
                         */
                        System.err.println("线程01---计步器02");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread01.start();
        try {
            int i = 1;
            while (i < 5) {
                Thread.currentThread().sleep(1 * 1000);
                System.err.println("主程序---休息" + i + "秒");
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock_object) {
            System.err.println("主程序---获取lock_object，并调用lock_object.notify()，解救被lock_object挂起的线程");
            lock_object.notify();
            System.err.println("主程序---获取lock_object，调用lock_object.notify()结束");
        }

    }
}

