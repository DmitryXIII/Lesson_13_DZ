package race;

public class Car implements Runnable {
    private static int CARS_STARTED_COUNT;
    protected static volatile int CARS_FINISHED_COUNT = 0;
    private Race race;
    private int speed;
    private String name;
    private boolean isLastStageRunning;

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_STARTED_COUNT++;
        this.name = "Участник #" + CARS_STARTED_COUNT;
        this.isLastStageRunning = false;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isLastStageRunning() {
        return isLastStageRunning;
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
                if (i < race.getStages().size() - 1) {
                    race.getStages().get(i).go(this);
                } else {
                    this.isLastStageRunning = true;
                    race.getStages().get(i).go(this);
                    Lesson_13_DZ.getFinishSemaphore().acquire();
                    CARS_FINISHED_COUNT++;
                    if (CARS_FINISHED_COUNT == 1) {
                        System.out.println(this.getName() + " закончил этап: " + race.getStages().get(i).description + "\nПОБЕДИТЕЛЬ - " + this.getName());
                    } else {
                        System.out.println(this.getName() + " закончил этап: " + race.getStages().get(i).description + "\nЗАНЯЛ " + CARS_FINISHED_COUNT + " МЕСТО - " + this.getName());
                    }
                    Lesson_13_DZ.getFinishSemaphore().release();
                    Lesson_13_DZ.getCdlIsAllCarsFinished().countDown();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
