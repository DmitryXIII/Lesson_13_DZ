package race;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static int CARS_FINISHED_COUNT;
    private Race race;
    private int speed;
    private String name;

    public static int getCarsFinishedCount() {
        return CARS_FINISHED_COUNT;
    }

    public static void setCarsFinishedCount() {
        CARS_FINISHED_COUNT++;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            Lesson_13_DZ.cdl_isAllCarsReady.countDown();

            Lesson_13_DZ.cdl_raceStarted.await(); // ждем, пока не будет сигнала о начале гонки

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
