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
        new Thread(this).start();
    }

    @Override
    public void run() {
        Vector<Request> generated = new Vector<Request>();
        Random r = new Random();
        while(requestsLeftToCreate > 0) {
            int currentFloor = 1 + r.nextInt(totalFloors);
            char direction;
            if (r.nextBoolean()) {
                if (currentFloor <  totalFloors)
                direction = 'u';
                else {
                    direction = 'd';
                }
            }
            else {
                if (currentFloor > 1)
                    direction = 'd';
                else {
                    direction = 'u';
                }
            }
            generated.add(new Request(currentFloor, direction, totalFloors));
            System.out.println("Passenger created with " + currentFloor + direction);
            toSend.sendRequest(generated);
            requestsLeftToCreate--;
        }
    }

    public int getRequestsLeftToCreate() {
        return requestsLeftToCreate;
    }
}
