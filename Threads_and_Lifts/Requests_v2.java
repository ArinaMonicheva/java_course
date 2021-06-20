import java.util.Vector;
import java.util.Iterator;

public class Requests {
    //private Vector<Request> poolOfRequests = new Vector<Request>();
    boolean flag = false;
    private int leftRequestsTotal;
    private int leftSentRequests;
    private Vector<Vector<Request>> poolOfRequests = new Vector<Vector<Request>>(7);

    public Requests(int requestsToCreate) {
        leftRequestsTotal = leftSentRequests = requestsToCreate;
        poolOfRequests = new Vector<Vector<Request>>(7);
        for (int i = 0; i < 7; i++) {
            poolOfRequests.add(new Vector<Request>());
        }
    }

    public int getLeftRequestsToPerform() {
        return leftRequestsTotal;
    }

    synchronized void sendRequest(Vector<Request> newRequests) {
        while (flag) {
            try {
                wait();
            }
           catch (InterruptedException e) {
                System.out.println("Failure in sendRequest waiting");
            }
        }
        while (!newRequests.isEmpty()) {
            int index = newRequests.get(0).getFromFloor() - 1;
            (poolOfRequests.get(index)).add(newRequests.get(0));
            newRequests.remove(0);
            leftSentRequests--;
        }
        flag = true;
        notify();
    }

    synchronized Vector<Request> getRequests(char direction, int placesLeft,
                                             int currentFloor, Lift stopped) {
        if (!flag) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Failure in getRequest waiting");
            }
        }
        int i = 0;
        Vector<Request> approvedRequests = new Vector<Request>();
        while (i < poolOfRequests.get(currentFloor - 1).size() && placesLeft > 0) {
            Request temp = poolOfRequests.get(currentFloor - 1).get(i);
            if (temp.getDirection() == direction && temp.getFromFloor() == currentFloor) {
                approvedRequests.add(temp);
                poolOfRequests.get(currentFloor - 1).remove(i);
                placesLeft--;
                leftRequestsTotal--;
            }
            else {
                i++;
            }
        }
        stopped.addPassengers(approvedRequests);
        stopped.findLastDestination();
        if (direction == 'u' && stopped.getLastDestination() <= currentFloor) {
            boolean contin = false;
            for (i = currentFloor; i < poolOfRequests.size(); i++) {
                if (poolOfRequests.get(i).size() > 0) {
                    stopped.setLastDestination(i + 1);
                    contin = true;
                }
            }
            if (!contin){
                stopped.changeDirection('d');
            }

        }
        else if (direction == 'd' && stopped.getLastDestination() >= currentFloor) {
            boolean contin = false;
            for (i = currentFloor - 2; i >= 0; i--) {
                if (poolOfRequests.get(i).size() > 0) {
                    stopped.setLastDestination(i + 1);
                    contin = true;
                }
            }
            if (!contin){
                stopped.changeDirection('u');
            }
        }
        if (leftSentRequests > 0) {
            flag = false;
        }
        notify();
        return approvedRequests;
    }

}
