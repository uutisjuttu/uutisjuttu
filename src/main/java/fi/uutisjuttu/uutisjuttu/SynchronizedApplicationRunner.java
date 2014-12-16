package fi.uutisjuttu.uutisjuttu;

import org.springframework.boot.SpringApplication;

/**
 * Testejä varten tehty rinnakkaisuussuojattu luokka, jonka avulla sovelluksen
 * voi varmistaa olevan käynnissä kuitenkaan käynnistämättä sitä enempää kuin
 * kerran.
 *
 * @author kumikumi
 */
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
