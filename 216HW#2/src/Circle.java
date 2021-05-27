import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Circle implements TwoDShape, Positionable, Comparable<TwoDShape>, Comparator<TwoDShape> {

    private TwoDPoint center;
    private double    radius;

    public Circle(double x, double y, double r) {
        this.center = new TwoDPoint(x,y);
        this.radius = r;
    }
    @Override
    public double getX() {
        return radius;
    }
    /**
     * Sets the position of this circle to be centered at the first element in the specified list of points.
     *
     * @param points the specified list of points.
     * @throws IllegalArgumentException if the input does not consist of {@link TwoDPoint} instances
     */
    @Override
    public void setPosition(List<? extends Point> points) throws IllegalArgumentException{
        if (points.size() == 0){
            throw new IllegalArgumentException();
        }
        else if (!(points.get(0) instanceof  TwoDPoint)){
            throw new IllegalArgumentException();
        }
        else{
            TwoDPoint centerPoint = (TwoDPoint) points.get(0);
            center = centerPoint;
            List<TwoDPoint> centercheck= new ArrayList<>();
            centercheck.add((TwoDPoint)points.get(0));
            if (isMember(centercheck) == false){
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @return the center of this circle as an immutable singleton list
     */
    @Override
    public List<? extends Point> getPosition() {
        List circleSingleton = Collections.singletonList(center);
        return circleSingleton;
    }

    /**
     * @return the number of sides of this circle, which is always set to positive infinity
     */
    @Override
    public int numSides() {
        return (int) Double.POSITIVE_INFINITY;
    }

    /**
     * Checks whether or not a list of vertices is a valid collection of vertices for the type of two-dimensional shape.
     *
     * @param centers the list of vertices to check against, where each vertex is a <code>Point</code> type. For
     *                the Circle object, this list is expected to contain only its center.
     * @return <code>true</code> if and only if <code>centers</code> is a single point, and the radius of this circle is
     * a positive value.
     */
    @Override
    public boolean isMember(List<? extends Point> centers) {
        return centers.size() == 1 && radius > 0;
    }

    /**
     * @return the area of this circle
     */
    public double area() {
        double radiusSquared = Math.pow(radius, 2);
        return Math.PI * radiusSquared;
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary-- aka circumference) of this quadrilateral
     */
    public double perimeter() {
        return 2.0 * Math.PI * radius;
    }
    @Override
    public int compare(TwoDShape o1, TwoDShape o2) {
        return (int) (o1.area() - o2.area());
    }
    @Override
    public int compareTo(TwoDShape o) {
        return (int)(area() - o.area());
    }
    public String toString(){
        String x = String.format("%.2f", center.coordinates()[0]);
        String y = String.format("%.2f", center.coordinates()[1]);
        String r = String.format("%.2f", radius);
        return "Circle centered at (" + x + ", " + y + ") of radius " + r + ": " +
                "\"Circle[center: " + x + ", " + y + "; radius: "+ r + "]\"";
    }
}
