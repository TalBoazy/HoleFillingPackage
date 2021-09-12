public class DefaultWeighter implements Weighter {
    private int z;
    private float epsilon;

    /**
     * public constructor 3
     * @param z: z value for the weighting function
     * @param epsilon: epsilon value for the weighting function
     */
    public DefaultWeighter(int z, float epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    /**
     * public constructor
     * @param z: z value for the weighting function
     */
    public DefaultWeighter(int z) {
        this.z = z;
        this.epsilon = 0.001f;
    }

    /**
     * public constructor
     * @param epsilon: epsilon value for the weighting function
     */
    public DefaultWeighter(float epsilon) {
        this.z = 3;
        this.epsilon = epsilon;
    }

    /**
     * Default public constructor with the default z and epsilon values.
     */
    public DefaultWeighter() {
        this.z = 3;
        this.epsilon = 0.001f;
    }

    /**
     *
     * @param u: hole pixel
     * @param v: pixel in B and a neighbor of u.
     * @return the euclidean distance between u and v.
     */
    private double euclideanDistance(Coordinate u, Coordinate v)
    {
        return Math.sqrt(Math.pow(u.getX()-v.getX(),2)+Math.pow(u.getY()-v.getY(),2));
    }
    
    /**
     * Weighting function which assigns a non-negative float weight to a pair
     * of two pixel coordinates in the image.
     *
     * @param u: pixel coordinates
     * @param v: pixel coordinates
     * @return The calculated weight of the two given pixels: w(u,v)=1/(|u-v|^z+epsilon)
     */
    public float weighter(Coordinate u, Coordinate v) {
        return 1 / (((float) (Math.pow(euclideanDistance(u,v),this.z)) + this.epsilon));
    }
}
