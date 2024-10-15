import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageResizer {
    
    public static int bicubic_spline_interpolate(BufferedImage image, double x, double y){
        int xf = (int)Math.floor(x);
        int yf = (int)Math.floor(y);
        
        Matrix alpha_matrix = new Matrix(4, 4);
        Matrix red_matrix = new Matrix(4, 4);
        Matrix green_matrix = new Matrix(4, 4);
        Matrix blue_matrix = new Matrix(4, 4);
    
        for(int i = -1; i <= 2; ++i){
            for(int j = -1; j <= 2; ++j){
                int xn = Math.max(0, Math.min(xf + i, image.getWidth() - 1));
                int yn = Math.max(0, Math.min(yf + j, image.getHeight() - 1));
    
                int rgb = image.getRGB(xn, yn);
                
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
    
                alpha_matrix.set(i + 1, j + 1, alpha);
                red_matrix.set(i + 1, j + 1, red);
                green_matrix.set(i + 1, j + 1, green);
                blue_matrix.set(i + 1, j + 1, blue);
            }
        }
    
        int alpha = (int)BicubicSplineInterpolation.evaluate(alpha_matrix, x - xf, y - yf);
        int red   = (int)BicubicSplineInterpolation.evaluate(red_matrix, x - xf, y - yf);
        int green = (int)BicubicSplineInterpolation.evaluate(green_matrix, x - xf, y - yf);
        int blue  = (int)BicubicSplineInterpolation.evaluate(blue_matrix, x - xf, y - yf);
    
        alpha = Math.max(0, Math.min(alpha, 255));
        red   = Math.max(0, Math.min(red, 255));
        green = Math.max(0, Math.min(green, 255));
        blue  = Math.max(0, Math.min(blue, 255));
    
        //System.out.println((alpha << 24) | (red << 16) | (green << 8) | blue);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static BufferedImage resize(BufferedImage source, double scaleX, double scaleY) {
        int new_width = (int)(source.getWidth() * scaleX);
        int new_height = (int)(source.getHeight() * scaleY);
        
        BufferedImage resized_image = new BufferedImage(new_width, new_height, source.getType());
        
        for(int i = 0; i < new_width; ++i){
            for(int j = 0; j < new_height; ++j){
                double srcX = i / scaleX;
                double srcY = j / scaleY;
                
                int rgb = bicubic_spline_interpolate(source, srcX, srcY);
                
                resized_image.setRGB(i, j, rgb);
            }
        }
        
        return resized_image;
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("input.jpg");
            BufferedImage inputImage = ImageIO.read(inputFile);

            double scaleX = 1.0;
            double scaleY = 2.0;

            BufferedImage resizedImage = resize(inputImage, scaleX, scaleY);

            File outputFile = new File("output.jpg");
            ImageIO.write(resizedImage, "jpg", outputFile);

            System.out.println("Image resized and saved successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
