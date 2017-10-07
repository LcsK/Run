/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static Models.Base.getCurrentGraphic;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author 1545 IRON V4
 */
public class Cenario extends Base{
    private static Image img;
    private static Image noCeuTemPao;
    private static boolean imagem = false;
     //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Cenario(int x, int y, int w, int h, int sx) 
    {
        super(x,y,w,h,sx,0);
        if(!imagem)
            loadImage();
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        setX((int)(getX() + getSx()));
        if(getX() <= -getW() / 2)
            setX(0);
        getRectangle().x = getX();
        return true;
    }

    @Override
    public void draw() 
    {
        getCurrentGraphic().drawImage(noCeuTemPao, getX() - 200, 0, null);
        getCurrentGraphic().drawImage(img, getX(), getY() - getH(), null);
        
        getCurrentGraphic().setColor(Color.RED);
        getCurrentGraphic().drawRect(getX(), getY(), getW(), getH());
    }
    
    private void loadImage() {
        try {
            img = ImageIO.read(new File("src//imagens//Cenario//Chao.png")).getScaledInstance(getW(), getH() * 2, Image.SCALE_DEFAULT);
            noCeuTemPao = ImageIO.read(new File("src//imagens//Cenario//Ceu.png")).getScaledInstance(getW() + 200, getW()/8, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
