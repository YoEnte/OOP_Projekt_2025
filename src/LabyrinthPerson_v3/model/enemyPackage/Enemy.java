package model.enemyPackage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Enemy {

    private int x;
    private int y;
    private String url;
    private BufferedImage image;
    private BufferedImage imageFlipped;
    protected Enemy(int x, int y, String url){
        this.x = x;
        this.y = y;
        this.url = url;

        BufferedImage imageTemp;
        BufferedImage imageFlippedTemp;
        try {
            imageTemp = ImageIO.read(new File(url + ".png"));
            imageFlippedTemp = ImageIO.read(new File(url + "_flipped.png"));
        } catch (IOException e) {
            System.out.println("image error");
            imageTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            imageFlippedTemp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }
        this.image = imageTemp;
        this.imageFlipped = imageFlippedTemp;
    }

    public int getPositionX(){
        return x;
    }
    public int getPositionY(){
        return y;
    }
    public String getUrl() {return url;}
    public BufferedImage getImage(){
        return image;
    }
    public BufferedImage getImageFlipped(){
        return imageFlipped;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
