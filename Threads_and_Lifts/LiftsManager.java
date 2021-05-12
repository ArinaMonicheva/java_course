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
        int initialFloor = 1 + r.nextInt(totalFloors);
        char direction;
        if (r.nextBoolean()) {
            direction = 'u';
        }
        else {
            direction = 'd';
        }
        for (int i = 0; i < liftsNum; i++) {
            lifts[i] = new Lift(initialFloor, direction, totalFloors, 6);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < liftsNum; i++) {
            lifts[i].addPassengers(toGet.getRequests(lifts[i].getCurrentDirection(),
                    lifts[i].getMaxPeople() - lifts[i].getCurrentNumOfPeople(),
                    lifts[i].getCurrentFloor()));
            lifts[i].move();
        }
    }
}
