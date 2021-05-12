import java.util.Vector;

public class Lift {
    private int currentFloor;
    private char currentDirection;
    private int maxFloors;
    private Vector<Request> passengers = new Vector<Request>();
    private int maxPeople;

    Lift(int initialFloor, char initialDirection, int totalFloors, int maxWeight) {
        currentFloor = initialFloor;
        currentDirection = initialDirection;
        maxFloors = totalFloors;
        maxPeople = maxWeight;
    }

    public void move() {
        if (currentFloor == maxFloors) {
            currentDirection = 'd';
        }
        else if (currentFloor == 1) {
            currentDirection = 'u';
        }
        if (currentDirection == 'u') {
            currentFloor++;
        }
        else {
            currentFloor--;
        }
        if (!passengers.isEmpty()) {
            int i = 0;
            while (i < passengers.size()) {
                if (passengers.get(i).getDestination() == currentFloor) {
                    passengers.remove(i);
                    System.out.println("Passenger " + " has left on " + currentFloor + " floor." +
                            " Total: " + getCurrentNumOfPeople());
                }
                else {
                    i++;
                }
            }
        }
    }

    public void addPassengers(Vector<Request> newPassengers) {
        while (!newPassengers.isEmpty()) {
            passengers.add(newPassengers.get(0));
            System.out.println("Passenger " + " has arrived on" + currentFloor + " floor." +
                    " Total: " + getCurrentNumOfPeople());
            newPassengers.remove(0);
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public char getCurrentDirection() {
        return currentDirection;
    }

    public int getCurrentNumOfPeople() {
        return passengers.size();
    }

    public int getMaxPeople() {
        return maxPeople;
    }
}
