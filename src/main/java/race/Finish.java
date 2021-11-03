package race;

public class Finish extends Stage {
    public Finish() {
        this.length = 0;
    }

    @Override
    public void go(Car c) {
        Car.setCarsFinishedCount();
        if (Car.getCarsFinishedCount() != 1) {
            System.out.println("<<< ВНИМАНИЕ! >>> " + c.getName() + " финишировал на " + Car.getCarsFinishedCount() + "-м месте");
        } else {
            System.out.println("======== ВНИМАНИЕ! ========\nВ гонке победил " + c.getName() + "!\n===========================");
        }
        Lesson_13_DZ.cdl_isAllCarsFinished.countDown();
    }
}
