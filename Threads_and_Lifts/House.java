import java.util.Vector;
import java.util.Random;

public class House implements Runnable {
    private int requestsLeftToCreate;
    private final int totalFloors;
    private Requests toSend;

    House(int requestsToCreate, int maxFloors, Requests generalPool) {
        requestsLeftToCreate = requestsToCreate;
        toSend = generalPool;
        totalFloors = maxFloors;
    }

    @Override
    public void run() {
        Vector<Request> generated = new Vector<Request>();
        Random r = new Random();
        for (int i = 0; i < requestsLeftToCreate; i++) {
            int currentFloor = 1 + r.nextInt(totalFloors);
            char direction;
            if (r.nextBoolean()) {
                direction = 'u';
            }
            else {
                direction = 'd';
            }
            generated.add(new Request(currentFloor, direction, totalFloors));
            System.out.println("Passenger created with " + currentFloor + direction);
            toSend.sendRequest(generated);
        }
    }
}
