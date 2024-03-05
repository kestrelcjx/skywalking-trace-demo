package cn.easyops.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new DemoApplication().deadlock();
        SpringApplication.run(DemoApplication.class, args);
    }

    public void deadlock() {
        Object a = new Object();
        Object b = new Object();

        Thread threadA = new Thread(() -> {
            synchronized (a) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                }
            }
        });
        threadA.setName("Thread_Monitor_A");
        threadA.start();
        Thread threadB = new Thread(() -> {
            synchronized (b) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                }
            }
        });
        threadB.setName("Thread_Monitor_B");
        threadB.start();
    }
}
