/**
 * The class implements the 4-connected neighbors method
 */
public class Image4Neighbors implements Neighbors {

    static int[] rowsGrid = {0, -1, 0, 1};
    static int[] colsGrid = {1, 0, -1, 0};

    /**
     * The method returns the rows grid for iterating a pixel's neighbors.
     */
    public int[] getRowsGrid()
    {
        return rowsGrid;
    }

    @Override
    /**
     * The method returns the columns grid for iterating a pixel's neighbors.
     */
    public int[] getColsGrid() {
        return colsGrid;
    }

    /**
     * The method returns the total number of neighbors.
     */
    public int getNeighborsNum() {
        return 4;
    }
}
