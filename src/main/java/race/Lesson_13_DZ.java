package race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Lesson_13_DZ {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch CDL_IS_ALL_CARS_READY = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch CDL_RACE_STARTED = new CountDownLatch(1);
    public static CountDownLatch CDL_IS_ALL_CARS_FINISHED = new CountDownLatch(CARS_COUNT);
    public static Semaphore TUNNEL_SEMAPHORE = new Semaphore((int) Math.floor(CARS_COUNT / 2)); // одновременно в тоннеле не больше половины участников

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

            CDL_IS_ALL_CARS_READY.await(); // ждем, пока все машины не будут готовы

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

            CDL_RACE_STARTED.countDown(); // сигнал машинам, что гонка началась

            CDL_IS_ALL_CARS_FINISHED.await(); // ждем, пока все машины не финишируют

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
