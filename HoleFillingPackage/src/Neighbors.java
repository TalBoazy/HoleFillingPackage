/**
 * Neighbors interface for neighbors methods (i.e: 4 neighbors, 8 neighbors, and more)
 */
public interface Neighbors {

    /**
     * The method returns the rows grid for iterating a pixel's neighbors.
     */
    int[] getRowsGrid();

    /**
     * The method returns the columns grid for iterating a pixel's neighbors.
     */
    int[] getColsGrid();

    /**
     * The method returns the total number of neighbors.
     */
    int getNeighborsNum();

}
