import java.util.Random;

public class Request {
    private int destination;
    private final char direction;
    private final int fromFloor;
    private final int lastFloor;
    private boolean generated = false;

    Request(int imHere, char newDirection, int numOfFloors) {
        fromFloor = imHere;
        direction = newDirection;
        lastFloor = numOfFloors;
    }

    public int getDestination() {
        if (!generated) {
            Random r = new Random();
            if (direction == 'u') {
                destination = fromFloor + (1 + r.nextInt(2 * (lastFloor -
                        fromFloor) - 1) % (lastFloor - fromFloor));
            }
            else {
                destination = fromFloor - (1 + r.nextInt(2 * (fromFloor - 1)
                        - 1) % (fromFloor - 1));
            }
            generated = true;
        }
        return destination;
    }

    public char getDirection() {
        return direction;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    @Override
    public String toString() {
        return "dest " + getDestination() +
                " direct " + direction +
                " from " + fromFloor;
    }
}
