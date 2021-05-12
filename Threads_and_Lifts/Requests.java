import java.util.Vector;
import java.util.Iterator;

public class Requests {
    private Vector<Request> poolOfRequests = new Vector<Request>();
    boolean flag = false;

    synchronized void sendRequest(Vector<Request> newRequests) {
        while (flag) {
            try {
                wait();
            }
           catch (InterruptedException e) {
                System.out.println("Failure in sendRequest waiting");
            }
        }
        //if (flag) {
        //    return;
        //}
        System.out.println("Here");
        while (!newRequests.isEmpty()) {
            poolOfRequests.add(newRequests.get(0));
            newRequests.remove(0);
        }
        flag = true;
        notify();
    }

    synchronized Vector<Request> getRequests(char direction, int placesLeft, int currentFloor) {
        if (!flag) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Failure in getRequest waiting");
            }
        }
        System.out.println("Here 2");
        int i = 0;
        Vector<Request> approvedRequests = new Vector<Request>();
        while (i < poolOfRequests.size() && placesLeft > 0) {
            if (poolOfRequests.get(i).getDirection() == direction &&
                    poolOfRequests.get(i).getFromFloor() == currentFloor) {
                approvedRequests.add(poolOfRequests.get(i));
                poolOfRequests.remove(i);
                placesLeft--;
            }
            else {
                i++;
            }
        }
        flag = false;
        notify();
        return approvedRequests;
    }

}
