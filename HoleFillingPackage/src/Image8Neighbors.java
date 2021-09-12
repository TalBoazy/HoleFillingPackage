/**
 * The class implements the 8-connected neighbors method
 */
public class Image8Neighbors implements Neighbors{


    static int[] rowsGrid = {1,1,1,0,0,-1,-1,-1};
    static int[] colsGrid = {-1,0,1,-1,1,-1,0,1};


    /**
     * The method returns the rows grid for iterating a pixel's neighbors.
     */
    public int[] getRowsGrid() {
        return rowsGrid;
    }

    @Override
    /**
     * The method returns the columns grid for iterating a pixel's neighbors.
     */
    public int[] getColsGrid() {
        return colsGrid;
    }

    @Override
    /**
     * The method returns the total number of neighbors.
     */
    public int getNeighborsNum() {
        return 8;
    }
}
