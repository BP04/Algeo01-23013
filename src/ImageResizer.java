import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageResizer {

    private static int bicubic_spline_interpolate(BufferedImage image, double x, double y){
        int width = image.getWidth(), height = image.getHeight();
        int xf = (int) Math.max(0, Math.min(width - 1, Math.floor(x)));
        int yf = (int) Math.max(0, Math.min(height - 1, Math.floor(y)));
        
        Matrix alpha_matrix = new Matrix(4, 4);
        Matrix red_matrix = new Matrix(4, 4);
        Matrix green_matrix = new Matrix(4, 4);
        Matrix blue_matrix = new Matrix(4, 4);
    
        for(int i = -1; i <= 2; ++i){
            for(int j = -1; j <= 2; ++j){
                int xn = Math.max(0, Math.min(xf + i, width - 1));
                int yn = Math.max(0, Math.min(yf + j, height - 1));
    
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

        double xp = Math.max(0, Math.min(width - 1, x - (double)xf)), yp = Math.max(0, Math.min(height - 1, y - (double)yf));
    
        int alpha = (int)BicubicSplineInterpolation.evaluate_color(alpha_matrix, xp, yp);
        int red   = (int)BicubicSplineInterpolation.evaluate_color(red_matrix, xp, yp);
        int green = (int)BicubicSplineInterpolation.evaluate_color(green_matrix, xp, yp);
        int blue  = (int)BicubicSplineInterpolation.evaluate_color(blue_matrix, xp, yp);
    
        alpha = Math.max(0, Math.min(alpha, 255));
        red   = Math.max(0, Math.min(red, 255));
        green = Math.max(0, Math.min(green, 255));
        blue  = Math.max(0, Math.min(blue, 255));
    
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

    public static void scale_image(String input_file_name, String output_file_name, double scaleX, double scaleY) {
        try {
            File input_file = new File(input_file_name);
            BufferedImage input_image = ImageIO.read(input_file);

            BufferedImage resizedImage = resize(input_image, scaleX, scaleY);

            File output_file = new File(output_file_name + ".jpg");
            ImageIO.write(resizedImage, "jpg", output_file);

            System.out.println("Image resized and saved successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
