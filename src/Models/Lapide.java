/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static Models.Base.getCurrentGraphic;
import static java.awt.Color.RED;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author 1545 IRON V4
 */
public class Lapide extends Obstacle{
    private int sair;
    private boolean broto;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Lapide(int x, int y, int w, int h, int sx) 
    {
        super(x,y,w,h,-sx);
        loadImagem();
        sair = new Random().nextInt(getX());
        if(sair < getX() / 2)
            sair = getX() / 2;
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        if(getX() + getW()<= 0)
            return false;
        if(getX() < sair) {
            loadImagesBroto();
            sair = -100;
            broto = true;
        }
        else if(broto && getCurrentFrame() == getImages().size() - 1) {
            broto = false;
            loadImagemTchau();
        }
        setY((int)(getY() + getSy()));
        getRectangle().y = getY();
        setX((int)(getX() + getSx()));
        getRectangle().x = getX();
        return true;
    }
    
    public void loadImagem() {
        try {
            getImages().add(ImageIO.read(new File("src//imagens//Lapide//Brotando//0.png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void loadImagemTchau() {
            setCurrentFrame(0);
            getImages().clear();
        try {
            for(int i = 0; i < 10; i++) {
                getImages().add(ImageIO.read(new File("src//imagens//Lapide//Tchau//"+i+".png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void loadImagesBroto() {
            setCurrentFrame(0);
            getImages().clear();
            try {
            for(int i = 0; i < 10; i++) {
                getImages().add(ImageIO.read(new File("src//imagens//Lapide//Brotando//"+i+".png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    

    @Override
    public void draw() {
        getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), getX() - getW() - getW() / 5, getY() - getH() / 2, null);
        getCurrentGraphic().setColor(RED);
        getCurrentGraphic().drawRect(x, y, w, h);
        changeFrame();
    }
    
    
}
