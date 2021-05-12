public class MainClass {
    public static void main(String []args) {
        Requests generalPool = new Requests();
        LiftsManager lifts = new LiftsManager(7, generalPool);
        House house = new House(25, 7, generalPool);

        house.run();
        lifts.run();
    }
}
