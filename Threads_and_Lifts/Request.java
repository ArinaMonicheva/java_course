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
            do {
                destination = 1 + (int) (Math.random() * lastFloor);
            } while(destination == fromFloor);
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
        return "dest " + destination +
                " direct " + direction +
                " from " + fromFloor;
    }
}
