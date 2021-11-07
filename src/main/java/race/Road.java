package race;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            if (!c.isLastStageRunning()) {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            } else {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
