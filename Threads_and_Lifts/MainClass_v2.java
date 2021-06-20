public class MainClass {
    public static void main(String []args) {
        int requestsToCreate = 25;
        Requests generalPool = new Requests(requestsToCreate);
        House house = new House(requestsToCreate, 7, generalPool);
        LiftsManager lifts = new LiftsManager(7, generalPool);

        //house.run();
        //lifts.run();
    }
}
