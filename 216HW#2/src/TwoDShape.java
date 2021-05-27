import java.lang.management.CompilationMXBean;
import java.util.Comparator;
import java.util.List;

/**
 * An interface to represent a closed form in two-dimensional space. Every class implementing this interface must be
 * to able to specify the number of sides of such a form and implement a method to check whether the vertices of an
 * instance is a valid set of vertices for that class.
 */
public interface TwoDShape extends Comparator<TwoDShape>, Comparable<TwoDShape> {

    /**
     * @return the number of sides of the two-dimensional shape
     */
    int numSides();
    List<TwoDPoint> vertices = null;
    /**
     * Checks whether or not a list of vertices is a valid collection of vertices for the type of two-dimensional shape.
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for the two-dimensional shape,
     * and <code>false</code> otherwise. For example, a list of three vertices all in a straight line is invalid for a
     * type meant to implement triangles.
     */
    boolean isMember(List<? extends Point> vertices);
    double area();
    double getX();
    @Override
    int compareTo(TwoDShape o);
    String toString();
    @Override
    int compare(TwoDShape o1, TwoDShape o2);
}