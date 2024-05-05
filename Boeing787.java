public class Boeing787 extends Plane implements Flyable{

    public Boeing787()
    {
        super();
        this.setModel("Boeing 787");
        this.setCapacity(290);
    }


    @Override
    public void takeoff() {
        System.out.println("Boeing 787 taking off");
        this.setFlying(true);
    }

    @Override
    public void boarding() {
        System.out.println("Boeing 787 on boarding");
    }

    @Override
    public void landing() {
        System.out.println("Boeing 787 is landing");
        this.setFlying(false);
    }
}