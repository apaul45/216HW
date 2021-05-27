import java.util.*;

public class Quadrilateral implements TwoDShape, Positionable, Comparator<TwoDShape>, Comparable<TwoDShape> {

    List<TwoDPoint> vertices;

    public Quadrilateral(List<TwoDPoint> vertices) {
        if (vertices.size() == 0){
            this.vertices = new ArrayList<>();
            this.vertices.add(new TwoDPoint(0.0,0.0));
            this.vertices.add(new TwoDPoint(0.0,0.0));
            this.vertices.add(new TwoDPoint(0.0,0.0));
            this.vertices.add(new TwoDPoint(0.0,0.0));
        }
        else{
            this.vertices = new ArrayList<>();
            this.vertices.addAll(vertices);
            setPosition(this.vertices);
        }
    }
    @Override
    public double getX() {
        return vertices.get(0).coordinates()[0];
    }
    /**
     * Sets the position of this quadrilateral according to the first four elements in the specified list of points. The
     * quadrilateral is formed on the basis of these four points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than four elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    public void setPosition(List<? extends Point> points) throws IllegalArgumentException{
        if (points.size() < 4){
            throw new IllegalArgumentException();
        }
        List<TwoDPoint> vertices1 = new ArrayList<>();
        double avgX = 0;
        double avgY = 0;
        for (int i = 0; i<4; i++) {
            if (!(points.get(i) instanceof TwoDPoint)){
                throw new IllegalArgumentException();
            }
            TwoDPoint newPoint = (TwoDPoint) points.get(i);
            vertices1.add(i, newPoint);
            avgX += newPoint.coordinates()[0];
            avgY += newPoint.coordinates()[1];
        }
        avgX /= 4.0;
        avgY /= 4.0;
        HashMap<Double, TwoDPoint> angleMapper = new HashMap<>();
        double[] anglePoints = new double[4];
        anglePoints[0] = Math.atan2((avgX - vertices1.get(0).coordinates()[0]), (avgY-vertices1.get(0).coordinates()[1]));
        angleMapper.put(anglePoints[0], vertices1.get(0));
        anglePoints[1] = Math.atan2((avgX - vertices1.get(1).coordinates()[0]), (avgY-vertices1.get(1).coordinates()[1]));
        angleMapper.put(anglePoints[1], vertices1.get(1));
        anglePoints[2] = Math.atan2((avgX - vertices1.get(2).coordinates()[0]), (avgY-vertices1.get(2).coordinates()[1]));
        angleMapper.put(anglePoints[2], vertices1.get(2));
        anglePoints[3] = Math.atan2((avgX - vertices1.get(3).coordinates()[0]), (avgY-vertices1.get(3).coordinates()[1]));
        angleMapper.put(anglePoints[3], vertices1.get(3));
        Arrays.sort(anglePoints); //anglePoints are sorted in the opposite order of least to to greatest
        List<TwoDPoint> newVertices = new ArrayList<>();
        for (int i = 0; i<4; i++){
            newVertices.add(i, angleMapper.get(anglePoints[3-i]));
        }
        int f = findLowest(newVertices, 0);
        if (f==4){
            int g = findLowest(newVertices, 1);
            if ((g>0) && (g!=4)){
                Collections.swap(newVertices, 0, g);
            }
            vertices = newVertices;
        }
        else if (f > 0){
            Collections.swap(newVertices, 0, f);
            vertices = newVertices;
        }
        else{
            vertices = newVertices;
        }
        if (isMember(vertices) == false){
            throw new IllegalArgumentException();
        }
    }
    public int findLowest(List<TwoDPoint> o, int i){
        //Find the index of the lowest x or y coordinate in the list. If two or more coords are the same lowest val, return 3
        double[] lowestX = {o.get(0).coordinates()[i], o.get(1).coordinates()[i], o.get(2).coordinates()[i], o.get(3).coordinates()[i]};
        Arrays.sort(lowestX);
        if (lowestX[0] == lowestX[1]){
            return 4;
        }
        else if (lowestX[0] == o.get(0).coordinates()[i]){
            return 0;
        }
        else if (lowestX[0] == o.get(1).coordinates()[i]){
            return 1;
        }
        else if (lowestX[0] == o.get(2).coordinates()[i]){
            return 2;
        }
        else{
            return 3;
        }
    }
    /**
     * Retrieve the position of an object as a list of points. The points are be retrieved and added to the returned
     * list in a clockwise manner on the two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends Point> getPosition() {
        return vertices;
    }

    /**
     * @return the number of sides of this quadrilateral, which is always set to four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The <i>trivial</i> quadrilateral, where all
     * four corner vertices are the same point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a quadrilateral, and
     * <code>false</code> otherwise. For example, if three of the four vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) throws IllegalArgumentException{
        if (vertices.size() < 4){
            throw new IllegalArgumentException();
        }
        /*Can check if this quadrilateral is valid with the use of the isMember of Triangle: that is, this quadrilateral
        can be checked by dividing it into two triangles and AND'ing their isMember return values
         */
        TwoDPoint first = (TwoDPoint) vertices.get(0);
        TwoDPoint second = (TwoDPoint) vertices.get(1);
        TwoDPoint third = (TwoDPoint) vertices.get(2);
        TwoDPoint fourth = (TwoDPoint) vertices.get(3);
        Triangle triangle1 = new Triangle(first, third, fourth);
        Triangle triangle2 = new Triangle(first, second, third);
        return ((triangle1.isMember(triangle1.getPosition())) && triangle2.isMember(triangle1.getPosition()));
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * quadrilateral becoming invalid (e.g., all four corners collapse to a single point), then it is left unchanged.
     * Snapping is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        List<TwoDPoint> snapVertices = new ArrayList<>();
        snapVertices.add(snapHelper(vertices.get(0)));
        snapVertices.add(snapHelper(vertices.get(1)));
        snapVertices.add(snapHelper(vertices.get(2)));
        snapVertices.add(snapHelper(vertices.get(3)));
        if (isMember(snapVertices) == true){
            setPosition(snapVertices);
        }
    }
    public TwoDPoint snapHelper(TwoDPoint x){
        int xCoord = (int) Math.round(x.coordinates()[0]);
        int yCoord = (int) Math.round(x.coordinates()[1]);
        TwoDPoint newPoint = new TwoDPoint(xCoord, yCoord);
        return newPoint;
    }
    /**
     * @return the area of this quadrilateral
     */
    public double area() {
        //Divide the square into two triangles (one is 1,3,4, and the other is 1,2,3) and add up the areas
        Triangle triangle1 = new Triangle(vertices.get(0), vertices.get(2), vertices.get(3));
        Triangle triangle2 = new Triangle(vertices.get(0), vertices.get(1), vertices.get(2));
        double totalArea = triangle1.area() + triangle2.area();
        return totalArea;
    }
    public double distanceHelper(TwoDPoint one, TwoDPoint two){
        double xDist = two.coordinates()[0] - one.coordinates()[0];
        double yDist = two.coordinates()[1] - one.coordinates()[1];
        double totalDist = Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0);
        return Math.sqrt(totalDist);
    }
    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
     */
    public double perimeter() {
        double x12Dist = distanceHelper(vertices.get(0), vertices.get(1));
        x12Dist += distanceHelper(vertices.get(0), vertices.get(1));
        x12Dist += distanceHelper(vertices.get(1), vertices.get(2));
        x12Dist += distanceHelper(vertices.get(2), vertices.get(3));
        return x12Dist;
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
        String first = String.format("%.2f",vertices.get(0).coordinates()[0]);
        String second = String.format("%.2f",vertices.get(0).coordinates()[1]);
        String third = String.format("%.2f",vertices.get(1).coordinates()[0]);
        String fourth = String.format("%.2f",vertices.get(1).coordinates()[1]);
        String fifth = String.format("%.2f",vertices.get(2).coordinates()[0]);
        String sixth = String.format("%.2f",vertices.get(2).coordinates()[1]);
        String seventh = String.format("%.2f", vertices.get(3).coordinates()[0]);
        String eighth = String.format("%.2f", vertices.get(3).coordinates()[1]);
        return "Quadrilateral with four vertices: \"Quadrilateral[("
                + first + ", " + second
                + "), ("+ third + ", " + fourth
                + "), (" +fifth + ", " + sixth
                + "), (" + seventh + ", " + eighth + ")]\"";
    }
}

