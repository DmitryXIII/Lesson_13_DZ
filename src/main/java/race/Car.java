package race;

public class Car implements Runnable {
    private static int CARS_STARTED_COUNT;
    protected static volatile int CARS_FINISHED_COUNT = 0;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_STARTED_COUNT++;
        this.name = "Участник #" + CARS_STARTED_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            Lesson_13_DZ.getCdlIsAllCarsReady().countDown();

            Lesson_13_DZ.getCdlRaceStarted().await(); // ждем, пока не будет сигнала о начале гонки

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
                if (i == race.getStages().size() - 1) {
                    CARS_FINISHED_COUNT++;
                    System.out.println(CARS_FINISHED_COUNT == 1 ? "ПОБЕДИТЕЛЬ - " + this.getName() : "ЗАНЯЛ " + CARS_FINISHED_COUNT + " место - " + this.getName());
                    Lesson_13_DZ.getCdlIsAllCarsFinished().countDown();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
