public class Boeing737 extends Plane implements Flyable{

    public Boeing737()
    {
        super();
        this.setModel("Boeing 737");
        this.setCapacity(215);
    }


    @Override
    public void takeoff() {
        System.out.println("Boeing 737 taking off");
        this.setFlying(true);
    }

    @Override
    public void boarding() {
        System.out.println("Boeing 737 on boarding");
    }

    @Override
    public void landing() {
        System.out.println("Boeing 737 is landing");
        this.setFlying(false);
    }
}
