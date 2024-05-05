public class Boeing777 extends Plane implements Flyable{

    public Boeing777()
    {
        super();
        this.setModel("Boeing 777");
        this.setCapacity(368);
    }

    @Override
    public void takeoff() {
        System.out.println("Boeing 777 taking off");
        this.setFlying(true);
    }

    @Override
    public void boarding() {
        System.out.println("Boeing 777 on boarding");
    }

    @Override
    public void landing() {
        System.out.println("Boeing 777 is landing");
        this.setFlying(false);
    }
}