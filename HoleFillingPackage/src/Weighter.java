/**
 * The Weighter Interface.
 */
public interface Weighter {

    /**
     * Weighting function which assigns a non-negative float weight to a pair
     * of two pixel coordinates in the image.
     *
     * @param u: pixel coordinates
     * @param v: pixel coordinates
     * @return The calculated weight of the two given pixels: w(u,v)
     */
    float weighter(Coordinate u, Coordinate v);
}
