package fi.uutisjuttu.uutisjuttu;

import org.springframework.boot.SpringApplication;

public class SynchronizedApplicationRunner {
    private static boolean running = false;
    public static synchronized void run() {
        if (running) {
            return;
        }
        SpringApplication.run(Application.class);
        running = true;
    }
}
