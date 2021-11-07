package race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Lesson_13_DZ {
    public static final int CARS_COUNT = 4;
    private static CountDownLatch CDL_IS_ALL_CARS_READY = new CountDownLatch(CARS_COUNT);
    private static CountDownLatch CDL_RACE_STARTED = new CountDownLatch(1);
    private static CountDownLatch CDL_IS_ALL_CARS_FINISHED = new CountDownLatch(CARS_COUNT);
    private static Semaphore TUNNEL_SEMAPHORE = new Semaphore(CARS_COUNT / 2);

    public static void main(String[] args) {
        try {
            System.out.println("<<< ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            Race race = new Race(new Road(60), new Tunnel(), new Road(40));
            Car[] cars = new Car[CARS_COUNT];

            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            }

            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }

            CDL_IS_ALL_CARS_READY.await(); // ждем, пока все машины не будут готовы

            startsCountDown(1000);

            CDL_RACE_STARTED.countDown(); // сигнал машинам, что гонка началась

            CDL_IS_ALL_CARS_FINISHED.await(); // ждем, пока все машины не финишируют

            System.out.println("<<< ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startsCountDown(int delay) {
        try {
            System.out.println("<<< 3 >>>");
            Thread.sleep(delay);
            System.out.println("<<< 2 >>>");
            Thread.sleep(delay);
            System.out.println("<<< 1 >>>");
            Thread.sleep(delay);
            System.out.println("<<< СТАРТ >>>");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CountDownLatch getCdlIsAllCarsReady() {
        return CDL_IS_ALL_CARS_READY;
    }

    public static CountDownLatch getCdlRaceStarted() {
        return CDL_RACE_STARTED;
    }

    public static CountDownLatch getCdlIsAllCarsFinished() {
        return CDL_IS_ALL_CARS_FINISHED;
    }

    public static Semaphore getTunnelSemaphore() {
        return TUNNEL_SEMAPHORE;
    }
}
