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
    private ArrayList<Image> images;
    private static ArrayList<Image> tchauImg;
    private static ArrayList<Image> brotoImg;
    private static boolean img = false;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Lapide(int x, int y, int w, int h, int sx) 
    {
        super(x,y,w,h,-sx);
        images = new ArrayList<Image>();
        if(!img)
        {
            tchauImg = new ArrayList<Image>();
            brotoImg = new ArrayList<Image>();
            loadImagemTchau();
            loadImagesBroto();
            img = true;
        }
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
        getImages().add(brotoImg.get(0));
    }
    
    public void loadImagemTchau() {
        setCurrentFrame(0);
        getImages().clear();
        try {
            if(!img)
            {
                for(int i = 0; i < 10; i++) {
                    tchauImg.add(ImageIO.read(new File("src//imagens//Lapide//Tchau//"+i+".png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
                }
            }
            else
                getImages().addAll(tchauImg);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void loadImagesBroto() {
            setCurrentFrame(0);
            getImages().clear();
            try {
                if(!img)
                {
                    for(int i = 0; i < 10; i++) {
                        brotoImg.add(ImageIO.read(new File("src//imagens//Lapide//Brotando//"+i+".png")).getScaledInstance(getW() * 4, getH()*2, Image.SCALE_DEFAULT));
                    }
                }
                else
                    getImages().addAll(brotoImg);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    

    @Override
    public void draw() {
        getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), getX() - getW() - getW() / 5, getY() - getH() / 2, null);
        getCurrentGraphic().setColor(RED);
        getCurrentGraphic().drawRect(x, y, w, h);
        changeFrame(getImages());
    }
    public ArrayList<Image> getImages()
    {
        return this.images;
    }
    public void setImages(ArrayList<Image> images)
    {
        this.images = images;
    }
    
}
