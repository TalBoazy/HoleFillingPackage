import java.nio.file.*;

public class Main {


    /**
     * main method
     * the program receives the following args:
     * args[0] = image path (PNG)
     * args[1] = mask path (PNG)
     * args[2] = fixed image path (PNG)
     * args[3] = "4" for the 4 neighbors method or "8" for the 8 neighbors method.
     * args[4] = "B&W" for grayscale image processing, "color" for colored image processing.
     */
    public static void main(String[] args) {
        String path = args[0];
        String mask = args[1];
        // basic file not exist test
        if(!(Files.exists(Paths.get(path))&&Files.exists(Paths.get(mask))))
        {
            System.err.println("File does not exist");
            System.exit(1);
        }
        String fixedImage = args[2];
        Neighbors neighbors;
        Image processor;
        if(args[3].equals("8")){
            neighbors = new Image8Neighbors(); // 8 neighbors method
        }
        else{
            neighbors = new Image4Neighbors(); // default value - 4 neighbors method
        }
        if(args[4].equals("color"))
        {
            processor = new ImageUtilsWrapper(path,mask,neighbors); // colored image
        }
        else{
            processor = new ImageUtils(path,mask,neighbors); // grayscale image
        }
        processor.fillHoles();
        processor.saveImage(fixedImage);
    }
}
