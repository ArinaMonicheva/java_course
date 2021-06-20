import java.util.Vector;

public class Lift {
    private int currentFloor;
    private char currentDirection;
    private int maxFloors;
    private Vector<Request> passengers = new Vector<Request>();
    private int maxPeople;
    private int lastDestination;
    private int id;

    Lift(int initialFloor, char initialDirection, int totalFloors, int maxWeight, int newID) {
        currentFloor = initialFloor;
        currentDirection = initialDirection;
        maxFloors = totalFloors;
        maxPeople = maxWeight;
        id = newID;
    }

    public void move() {
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
                    System.out.println("(lift " + id + ") Passenger " + passengers.get(i) + " has left on " + currentFloor
                            + " floor." + " Total: " + (getCurrentNumOfPeople() - 1));
                    passengers.remove(i);
                }
                else {
                    i++;
                }
            }
        }
        if (currentFloor == maxFloors) {
            changeDirection('d');
        }
        else if (currentFloor == 1) {
            changeDirection('u');
        }
    }

    public void addPassengers(Vector<Request> newPassengers) {
        while (!newPassengers.isEmpty()) {
            passengers.add(newPassengers.get(0));
            System.out.println("(lift " + id + ") Passenger " + newPassengers.get(0) + " has arrived on " +
                    currentFloor + " floor." + " Total: " + getCurrentNumOfPeople());
            newPassengers.remove(0);
        }
    }

    public void changeDirection(char newDirection) {
        currentDirection = newDirection;
    }

    public int getLastDestination() {
        return lastDestination;
    }

    public void setLastDestination(int lastDestination) {
        this.lastDestination = lastDestination;
    }

    public void findLastDestination() {
        if (currentDirection == 'u') {
            int max = 1;
            for (int i = 0; i < passengers.size(); i++) {
                if (passengers.get(i).getDestination() > max) {
                    max = passengers.get(i).getDestination();
                };
            }
            lastDestination = max;
        }
        else {
            int min = maxFloors;
            for (int i = 0; i < passengers.size(); i++) {
                if (passengers.get(i).getDestination() < min) {
                    min = passengers.get(i).getDestination();
                };
            }
            lastDestination = min;
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
