package race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Lesson_13_DZ {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch cdl_isAllCarsReady = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch cdl_raceStarted = new CountDownLatch(1);
    public static CountDownLatch cdl_isAllCarsFinished = new CountDownLatch(CARS_COUNT);
    public static Semaphore tunnelSemaphore = new Semaphore((int) Math.floor(CARS_COUNT / 2)); // одновременно в тоннеле не больше половины участников

    public static void main(String[] args) {
        try {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            Race race = new Race(new Road(60), new Tunnel(), new Road(40), new Finish());
            Car[] cars = new Car[CARS_COUNT];

            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            }

            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }

            cdl_isAllCarsReady.await(); // ждем, пока все машины не будут готовы

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

            cdl_raceStarted.countDown(); // сигнал машинам, что гонка началась

            cdl_isAllCarsFinished.await(); // ждем, пока все машины не финишируют

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
