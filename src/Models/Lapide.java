/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import JFrames.MainFormJFrame;
import static Models.Base.getCurrentGraphic;
import static java.awt.Color.RED;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private static ArrayList<Image> tchauImg;
    private static boolean img = false;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Lapide(int x, int y, int w, int h, double sx) 
    {
        super(x,y,w,h,-sx);
        if(!img)
        {
            tchauImg = new ArrayList<Image>();
            loadImagemTchau();
            img = true;
        }
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        if(getX() + getW()<= 0)
            return false;
        setY((int)(getY() + getSy()));
        getRectangle().y = getY();
        setX((int)(getX() + getSx()));
        getRectangle().x = getX();
        return true;
    }
    
    
    public void loadImagemTchau() {
        setCurrentFrame(0);
        try {
                for(int i = 0; i < 10; i++) {
                    tchauImg.add(ImageIO.read(getClass().getResource("/imagens//Lapide//Tchau//"+i+".png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
                }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    @Override
    public void draw() {
        getCurrentGraphic().drawImage(tchauImg.get(getCurrentFrame()), getX() - getW() - getW() / 5, getY() - getH() / 2, null);
        getCurrentGraphic().setColor(RED);
        //getCurrentGraphic().drawRect(x, y, w, h);
        changeFrame(tchauImg);
    }
    
    @Override
    public void changeFrame(ArrayList<Image> images)
    {
        long tempoAtual = System.currentTimeMillis();
        if (tempoAtual > getLastFrameTime() + 200) {
            setLastFrameTime(tempoAtual);
            setCurrentFrame(getCurrentFrame()+1);
            if (getCurrentFrame() == images.size()) {
                setCurrentFrame(0);
            }
        }
    }

    
}
