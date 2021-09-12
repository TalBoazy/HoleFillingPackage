# HoleFillingPackage
Small image processing JAVA library that fills holes in images, along with a small command line utility that uses that library.
Tal Boazy - tal.boazy@gmail.com

The package consist the following interfaces:
Image - Constructed for the Main utility.
Neighbors - Interface for neighbors methods
Weighter - Interface for a weight function.

The package consist the following classes:
Main - Main Class to parse the different required arguments.
Coordinate - Pixel (x,y) coordinate.
DefaultWeighter - Contains The default weight function.
Image4Neighbors - Implements the 4-connected neighbors rule.
Image8Neighbors - Implements the 4-connected neighbors rule.
ImageUtils - Handles the library image processing methods.
ImageUtilsWrapper - Wrapper class for ImageUtil that allows working with
colored images.

Command line utility:
java -jar HoleFillingPackage.jar img_path.png, mask_path.png, fixed_img.png,
neighbors_connectivity ("4"/"8"), color("grayscale"/"color")

Future work:
Due to time limits I didn't build an Exception Class in order to protect the
client's use. So for now I assumed all the inputs are valid, but future protection
is required.

OpenCV - version 4.5.3
