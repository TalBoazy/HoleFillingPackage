/**
 * Class Coordinate containing a pixel (x,y) location.
 */
public class Coordinate {
    private int x_;
    private int y_;

    Coordinate(int x, int y) {
        x_ = x;
        y_ = y;
    }

    /**
     *
     * @return the x value of the coordinate.
     */
    public int getX()
    {
        return x_;
    }

    /**
     *
     * @return the y value of the coordinate.
     */
    public int getY()
    {
        return y_;
    }


}
