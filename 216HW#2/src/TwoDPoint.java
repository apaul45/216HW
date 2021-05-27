import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point, Comparator<TwoDPoint>, Comparable<TwoDPoint> {
    double[] coordinates = new double[2];
    public TwoDPoint(double x, double y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        return coordinates;
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {
        if (coordinates.length%2 != 0){
            throw new IllegalArgumentException();
        }
        else {
            List<TwoDPoint> pointsHolder = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i += 2) {
                TwoDPoint newPoint = new TwoDPoint(coordinates[i], coordinates[i + 1]);
                if (i == 0) {
                    pointsHolder.add(i, newPoint);
                } else {
                    pointsHolder.add(i - 1, newPoint);
                }
            }
            return pointsHolder;
        }
    }
    public double distanceHelper(TwoDPoint one, TwoDPoint two){
        double xDist = two.coordinates[0] - one.coordinates[0];
        double yDist = two.coordinates[1] - one.coordinates[1];
        double totalDist = Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0);
        return Math.sqrt(totalDist);
    }
    public double distanceHelper(TwoDPoint one){
        double xDist = one.coordinates[0] - coordinates[0];
        double yDist = one.coordinates[1] - coordinates[1];
        double totalDist = Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0);
        return Math.sqrt(totalDist);
    }
    @Override
    public int compare(TwoDPoint x, TwoDPoint y){
        TwoDPoint origin = new TwoDPoint(0.0,0.0);
        double first = distanceHelper(origin, x);
        double second = distanceHelper(origin, y);
        return (int)(first-second);
    }

    @Override
    public int compareTo(TwoDPoint o) {
        TwoDPoint origin = new TwoDPoint(0.0,0.0);
        return (int) (distanceHelper(origin) - distanceHelper(origin, o));
    }
}
