import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.LinkedList;
import java.util.ArrayList;

public class ImageUtilsWrapper implements Image{
    private ImageUtils imageProcessor;
    private ArrayList<Mat> channels;
    private Neighbors neighbors_;
    private Imgcodecs imagecodes;
    private Mat mask;

    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public ImageUtilsWrapper(String path, String maskPath, Neighbors neighbors)
    {
        Mat image = imagecodes.imread(path, CvType.CV_32F);
        channels = new ArrayList<>(3);
        Core.split(image, channels);
        mask = imagecodes.imread(maskPath, imagecodes.IMREAD_GRAYSCALE);
        mask.convertTo(mask, CvType.CV_8U);
        neighbors_ = neighbors;
    }

    public void fillHoles()
    {
        Mat image;
        for(int i=0; i<channels.size(); i++)
        {
            image = channels.get(i);
            image.convertTo(image,CvType.CV_32F,1f/255);
            imageProcessor = new ImageUtils(image,mask, neighbors_);
            imageProcessor.fillHoles();
            image = imageProcessor.getImage();
            Core.multiply(image,new Scalar(255),image);
            channels.set(i,image);
        }
    }

    public void saveImage(String path)
    {
        Mat image = new Mat();
        Core.merge(channels, image);
        imagecodes.imwrite(path, image);
    }


}
