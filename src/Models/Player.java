/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static java.awt.Color.RED;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lucas
 */
public class Player extends Base {
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Player(int x, int y, int w, int h) 
    {
        super(x,y,w,h);
        loadImagem();
    }
    //</editor-fold>
    
    public void loadImagem() {
        try {
            for(int i = 0; i < 10; i++)
            getImages().add(ImageIO.read(new File("src//imagensPersonagem//Walk ("+i+").png")).getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw() {
        getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), getX(), getY(), null);
        getCurrentGraphic().setColor(RED);
        getCurrentGraphic().drawRect(x, y, w, h);
    }

    @Override
    public boolean move(int width, int height) {
        setY(getY() + getSy());
        getRectangle().y = getY();
        if(getY() > height - getH())
        {
            setSy(0);
        }
        return true;
    }
}
