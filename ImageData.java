import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
public class ImageData extends VisualData{

    private BufferedImage image;

    public class Builder{
        private ImageData creation;
        public void init(){
            creation = new ImageData();
        }public ImageData setSize(String width, String height){
            creation.params.put("width", width);
            creation.params.put("height", height);
            return creation;
        }
        public ImageData setFilePath(String filepath){
            creation.setContent(filepath);
            return creation;
        }
        public ImageData build(){
            if(!creation.params.containsKey("id")){
                throw new TaskCreationError("image must have id before creation");
            }
            if(creation.content == null){
                creation.content = "broken-img.png";
            }
            
            getImage();

            if(creation.params.containsKey("width") && creation.params.containsKey("height")){
                int newWidth = Integer.parseInt(creation.params.get("width"));
                int newHeight = Integer.parseInt(creation.params.get("height"));
                setImageSize(newWidth, newHeight);
            }
            if(!creation.params.containsKey("width")){
                creation.params.put("width", ""+image.getWidth());
            }
            if(!creation.params.containsKey("height")){
                creation.params.put("height", "" + image.getHeight());
            }
            return creation;
        }
    }
    private void getImage(){
        try{
            image = ImageIO.read(new File(content));
        }catch(IOException e){
            System.out.println("\n=======================================\n");
            System.out.println("Error: Image not Found!!!");
            System.out.println(String.format("Filepath: %s", content));
            System.out.println("\n=======================================\n");
        }
    }

    private void setImageSize(int width, int height){
        BufferedImage newImg = new BufferedImage(width, height, image.getType());
        Graphics2D g = newImg.createGraphics();
        g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        image = newImg;
    }
}
