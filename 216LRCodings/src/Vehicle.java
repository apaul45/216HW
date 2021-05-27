import java.util.List;

class Vehicle {
    String name;
    public Vehicle(String name) { this.name = name; }


    public void start() {
        System.out.println("Starting vehicle " + this.name + ".");
    }

    public void drive() {
        System.out.println("Driving vehicle " + this.name + ".");
    }

    public void stop() {
        System.out.println("Stopping vehicle " + this.name + ".");
    }
    public void service() { System.out.println("Generic vehicle servicing"); }
}
class Bike extends Vehicle {
    public Bike(String name) {
        super(name);
    }

    @Override
    public void service() { System.out.println("Bike servicing"); }
}
class Truck extends Vehicle {
    public Truck(String name) {
        super(name);
    }

    @Override
    public void service() { System.out.println("Truck servicing"); }
}
class Mechanic {
    //Have to replace type parameter/payload type <Vehicle> with <? extends Vehicle> to account for the children of the
    //Vehicle class
    public void serviceVehicles(List<? extends Vehicle> vehicles) {
        for (Vehicle v : vehicles) v.service();
    }
}


