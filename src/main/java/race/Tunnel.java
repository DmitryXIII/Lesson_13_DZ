package race;

public class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            if (!c.isLastStageRunning()) {
            try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    Lesson_13_DZ.getTunnelSemaphore().acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    System.out.println(c.getName() + " закончил этап: " + description);
                    Lesson_13_DZ.getTunnelSemaphore().release();
                }
            } else {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    Lesson_13_DZ.getTunnelSemaphore().acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Lesson_13_DZ.getTunnelSemaphore().release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
