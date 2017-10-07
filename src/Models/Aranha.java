/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static Models.Base.getCurrentGraphic;
import java.awt.Color;
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
public class Aranha extends Obstacle{
    private int abaixar;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Aranha(int x, int y, int w, int h, int sx) 
    {
        super(x,y,w,h,-sx);
        loadImagem();
        abaixar = new Random().nextInt(getX());
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        if(getX() + getW()<= 0)
            return false;
        if(getX() < abaixar)
            abaixar();
        setY((int)(getY() + getSy()));
        getRectangle().y = getY();
        setX((int)(getX() + getSx()));
        getRectangle().x = getX();
        return true;
    }
    
    
        public void loadImagem() {
        try {
            for(int i = 0; i < 10; i++) {
                getImages().add(ImageIO.read(new File("src//imagens//Aranha//Descendo//"+i+".png")).getScaledInstance(getW() * 2, getH()*3, Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void draw() {
        getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), getX() - getW() / 2, getY() - 2 * getH(), null);
        getCurrentGraphic().setColor(RED);
        getCurrentGraphic().drawRect(x, y, w, h);
        changeFrame();
    }
    
    private void abaixar() {
        if(getY() <= 10) {
            setSy(1);
        }
        if(getY() >= getH() * 2.5) {
            setSy(-1);
        }
    }
    
    
}
