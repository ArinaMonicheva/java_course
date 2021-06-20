import java.util.Vector;
import java.util.Random;

public class LiftsManager implements Runnable {
    private final int liftsNum = 2;
    private Lift[] lifts = new Lift[liftsNum];
    private final int totalFloors;
    private Requests toGet;

    LiftsManager(int maxFloors, Requests generalPool) {
        totalFloors = maxFloors;
        toGet = generalPool;
        Random r = new Random();
        for (int i = 0; i < liftsNum; i++) {
            int initialFloor = 1 + r.nextInt(totalFloors);
            char direction;
            if (r.nextBoolean()) {
                direction = 'u';
            }
            else {
                direction = 'd';
            }
            lifts[i] = new Lift(initialFloor, direction, totalFloors, 6, i);
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean isNotEmpty = true;
        while (toGet.getLeftRequestsToPerform() > 0 || isNotEmpty) {
            isNotEmpty = false;
            for (int i = 0; i < liftsNum; i++) {
                toGet.getRequests(lifts[i].getCurrentDirection(),
                        lifts[i].getMaxPeople() - lifts[i].getCurrentNumOfPeople(),
                        lifts[i].getCurrentFloor(), lifts[i]);
                lifts[i].move();
                if (lifts[i].getCurrentNumOfPeople() > 0) {
                    isNotEmpty = true;
                }
            }
        }
    }
}
