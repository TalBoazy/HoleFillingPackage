import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.LinkedList;

/**
 * ImageUtils Class handles the library image processing methods.
 */
public class ImageUtils implements Image{

    protected Mat image_;
    private Imgcodecs imagecodes_;
    private float HOLE = -1;
    private LinkedList<Coordinate> boundary_;
    private Weighter W_;
    private Neighbors neighbors_;

    // Downloads openCV Library
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    /**
     * Class Constructor
     * @param imgPath: Path to an RGB image. It will be processed as a grayscale image.
     * @param maskPath: Path to a binary mask representing the hole to fill.
     * @param W: Weighter Interface object. Contains weight function that receives two nodes.
     * @param N: Neighbors Interface object. Contains grids to allow access to all the defined neighbors.
     */
    public ImageUtils(String imgPath, String maskPath, Weighter W, Neighbors N) {
        imagecodes_ = new Imgcodecs();
        W_ = W;
        initializer(imgPath, maskPath,N);
    }

    /**
     * Class Constructor. Defines the default weight function.
     * @param imgPath: Path to an RGB image. It will be processed as a grayscale image.
     * @param maskPath: Path to a binary mask representing the hole to fill.
     * @param N: Neighbors Interface object. Contains grids to allow access to all the defined neighbors.
     */
    public ImageUtils(String imgPath, String maskPath, Neighbors N) {
        imagecodes_ = new Imgcodecs();
        W_ = new DefaultWeighter();
        initializer(imgPath, maskPath,N);
    }

    /**
     * Class Constructor.
     * @param grayScaleImg: 2D opencv.core.Mat image object.
     * @param mask: 2D opencv.core.Mat mask object.
     * @param n
     */
    public ImageUtils(Mat grayScaleImg, Mat mask, Neighbors n)
    {
        // todo: check the CV types, dimensions, and max values.
        neighbors_ = n;
        W_ = new DefaultWeighter();
        image_ = convertImageFormat(mask, grayScaleImg);
        boundary_ = new LinkedList<>();
        getBoundary();
    }

    /**The following function initialize the class arguments*/
    private void initializer(String path, String mask_path, Neighbors n)
    {
        neighbors_ = n;
        Mat image = imagecodes_.imread(path, imagecodes_.IMREAD_GRAYSCALE);
        image.convertTo(image,CvType.CV_32F, 1f/255);
        Mat mask = imagecodes_.imread(mask_path, imagecodes_.IMREAD_GRAYSCALE);
        mask.convertTo(mask, CvType.CV_8U);
        image_ = convertImageFormat(mask, image);
        boundary_ = new LinkedList<>();
        getBoundary();
    }

    /**
     * The method saves the fixed image in the given path
     */
    public void saveImage(String path)
    {
        Core.multiply(image_,new Scalar(255),image_);
        imagecodes_.imwrite(path, image_);
    }


    /**
     * calculates the pixel value for the coordinate u by the following formula:
     * I(u)=sum_(v in B)(w(u,v)*I(v))/sum_(v in B)(w(u,v))
     */
    private void I(Coordinate u) {
        int[] rows_index = neighbors_.getRowsGrid();
        int[] cols_index = neighbors_.getColsGrid();
        float numerator = 0;
        float denominator = 0;
        float v_val;
        int x,y;
        Coordinate v;
        for (int i = 0; i < neighbors_.getNeighborsNum(); i++) {
            x = u.getX() + rows_index[i];
            y = u.getY() + cols_index[i];
            v_val = (float) image_.get(x, y)[0];
            if (v_val != HOLE) { // v in B only if v is not a hole
                v = new Coordinate(x,y);
                numerator += W_.weighter(u, v) * v_val;
                denominator += W_.weighter(u, v);
            }
        }
        this.image_.put(u.getX(), u.getY(), numerator / denominator);
    }

    /**
     *
     * @param x: x coordinate
     * @param y: y coordinate
     * @return true if the given pixel is a boundary pixel, false otherwise.
     */
    private boolean isBoundary(int x, int y) {
        float v_val;
        float u_val = (float) this.image_.get(x, y)[0];
        if (u_val != HOLE) {
            return false;
        }
        int[] rows_index = neighbors_.getRowsGrid();
        int[] cols_index = neighbors_.getColsGrid();
        for (int i = 0; i < neighbors_.getNeighborsNum(); i++) {
            v_val = (float) image_.get(x + rows_index[i], y + cols_index[i])[0];
            if (v_val != HOLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function adds all the boundary pixels to a linked list object.
     */
    private void getBoundary() {
        for (int i = 0; i < image_.rows(); i++) {
            for (int j = 0; j < image_.cols(); j++) {
                if (isBoundary(i, j)) {
                    // boundary_ object contains all the boundary pixels
                    boundary_.add(new Coordinate(i, j));
                }
            }
        }
    }

    /**
     * The function fills the hole in the given image
     */
    public void fillHoles()
    {
        Coordinate pixel;
        while(boundary_.size() > 0){
            // apply I(pixel) for each pixel in boundary
            while(boundary_.size() > 0)
            {
                pixel=boundary_.pop();
                I(pixel);
            }
            // finds the new boundary pixels
            getBoundary();
        }
    }


    public Mat getImage()
    {
        return image_;
    }

    /**
     *
     * @param mask: mask image of CvType.CV_8U.
     * @param image: float 2D image to process
     * @return image in the requested format. i.e: holes are marked with the
     * value=-1
     */
    private Mat convertImageFormat(Mat mask, Mat image) {
        Mat notMask = new Mat();
        Core.bitwise_not(mask, notMask);
        image.setTo(new Scalar(-1), notMask);
        return image;
    }
}
