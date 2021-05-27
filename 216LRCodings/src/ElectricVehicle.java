import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ElectricVehicle extends Vehicle {
    double charge = 0.0; // percentage of battery charged, between 0 and 100

    public ElectricVehicle(String name) {
        super(name);
        this.charge = 100; // new instances are fully charged
    }

    public void start() {
        System.out.println("Starting electric vehicle " + name + ".");
    }

    public void drive() {
        System.out.println("Driving electric vehicle " + name + ".");
    }

    public void stop() {
        System.out.println("Stopping electric vehicle " + name + ".");
    }

    public double getCharge() { return this.charge; }

    public static void main(String... args) {
        Vehicle v1 = new Vehicle("Truck");
        ElectricVehicle v2 = new ElectricVehicle("Tesla");
        Vehicle v3 = new ElectricVehicle("Leaf");
        v1.start();
        v2.start();
        v3.start();
        v2.getCharge();
        //v3.getCharge();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Vehicle("hi"));
        vehicles.add(new Vehicle("hi"));
        List<Bike> bikes = new ArrayList<Bike>();
        bikes.add(new Bike("motorsport"));
        bikes.add(new Bike("second"));
        List<Truck> trucks = new ArrayList<Truck>();
        trucks.add(new Truck("cybertruck"));
        trucks.add(new Truck("jeep"));

        // will the following code work? why or why not? try to understand what
        // works in the following lines and what doesn’t.
        Mechanic mechanic = new Mechanic();
        mechanic.serviceVehicles(vehicles);
        mechanic.serviceVehicles(bikes);
        mechanic.serviceVehicles(trucks);

    }
}
class BoundedWildcards {
    interface Output<S> { void print(S t); }
    static class OutputImpl<S> implements Output<S> {
        public void print(S t) { System.out.println(t.toString()); }
    }
    //Since both are T, that means they must be the same type
    //To fix, NEED TO ESTABLISH RELATIONSHIP BETWEEN PAYLOAD TYPES: either with ? extends or ? super
    /*static <S> S writeAll(Collection<? extends S> collection, Output<S> out) {
        S first = null;
        for (S t : collection) {
            if (first == null) {
                first = t;
                out.print(t);
            }
        }
        return first;
    }*/
    static <S> S writeAll(Collection<S> collection, Output<? super S> out){
        S first = null;
        for (S t: collection){
            if (first == null) {
                first = t;
                //The following line works because print is defined for type S in the Output<S> interface,
                //So we can either: establish rel. btwn String and Object with ? extends which would require a typecast
                // in the main method, or with ? super which would allow us to work on collection with no further typecasting
                //And print out the value due to Output.print being defined for type S-- ? super S is the S defined in the
                //class statement static class OutputImpl<S>, but is ? super S when related to a Collection<S> collection
            }
            out.print(t);
        }
        return first;
    }
    public static void main(String... args) {
        Output<Object> output = new OutputImpl<>();
        Collection<String> strings = new ArrayList<>();
        strings.add("hi");
        strings.add("hiI");
        strings.add("bye");
        strings.add("byebye");
// neither String nor Object is appropriate for the type T
// The collection and the type parameter of output must be the same type:
        //Throws error requiring both to be T
        //Need to typecast to receive correct output when wildcard is ? extends
        //String s = (String)writeAll(strings, output);

        //But by using ? super, no need to typecast because
        String s = writeAll(strings, output);
        System.out.println(s);
    }
}
class Demo{
    //Following code won't work due to compiler not knowing which method to call: the first 2 aMethods have the same
    //List signature after types are erased
    /*public void aMethod(List<String> l){

    }
    public void aMethod(List<Integer> i){

    }
    //The following method does overload since the return type is int even with the method signature being List after erasure:
    //In other words, the fact that this method has a return type but the previous one doesn't is what allows it to be
    //a overloaded method
    public int aMethod(List<Double> d){

    }*/
}
class Polymor {
    class A {
        public String show(A obj) {
            return ("A and A");
        }
        public String show(B obj) {
            return ("A and B");
        }
    }
    class B extends A {
        public String show(A obj) {
            return ("B and A");
        }
        public String show(B obj) {
            return ("B and B");
        }
    }
    class C extends B {
        public String show(A obj) {
            return ("C and A");
        }
        public String show(B obj) {
            return ("C and B");
        }
    }
    class D extends B {}

    public static void main(String[] args) throws FileNotFoundException {
        Polymor outerclass = new Polymor();
        A a1 = outerclass.new A();
        A a2 = outerclass.new B();
        B b = outerclass.new B();
        C c = outerclass.new C();
        System.out.print(a2.show(b));
        System.out.print(a2.show(c));

        D d = outerclass.new D();
        System.out.println("1:" + a1.show(b));
        System.out.println("2:" + a1.show(c));
        System.out.println("3:" + a1.show(d));
        System.out.println("4:" + a2.show(b));
        System.out.println("5:" + a2.show(c));
        System.out.println("6:" + a2.show(d));
        System.out.println("7:" + b.show(b));
        System.out.println("8:" + b.show(c));
        System.out.println("9:" + b.show(d));
        ArrayList<Integer> s = new ArrayList<>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(4);
        s.add(5);
        s.add(8);
        s.add(15);
        s.add(16);
        Path folder = Paths.get("/Users/apaul23/216HW5testfiles");
        //Q4 of Recitation 8
        System.out.println(Arrays.stream(new Scanner(folder.toFile().listFiles()[0]).nextLine().split(" ")).sequential().collect(Collectors.toList()));
        //Q5 of Recitation 8
        Arrays.stream(new Scanner(folder.toFile().listFiles()[0]).nextLine().split(" ")).sequential().flatMapToInt(j-> IntStream.of(j.length())).forEach(l->System.out.println(l));
        System.out.println(s.stream().filter(k->k%2==0).map(j->j/=2).filter(l->l%2==0).collect(Collectors.toSet()));
        List<String> k = Arrays.asList(new Scanner(folder.toFile().listFiles()[0]).nextLine().split(" "));
        //Q3 of Recitation 8
        System.out.println(k.stream().reduce(k.get(0), (m,n)-> n.length()<m.length()?m=n:m).toString());
    }
}



