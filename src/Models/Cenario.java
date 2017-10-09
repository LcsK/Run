/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import JFrames.MainFormJFrame;
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
    private static Image mae;
    private static Image noCeuTemPao;
    private static Image eMorreu;
    private static boolean imagem = false;
     //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Cenario(int x, int y, int w, int h, double sx) 
    {
        super(x,y,w,h,-sx,0);
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
        getCurrentGraphic().drawImage(noCeuTemPao, getX(), 0, null);
        getCurrentGraphic().drawImage(eMorreu, getX(), getY() / 2, null);
        getCurrentGraphic().drawImage(mae, getX(), getY() - getH(), null);
        
        getCurrentGraphic().setColor(Color.RED);
        //getCurrentGraphic().drawRect(getX(), getY(), getW(), getH());
    }
    
    private void loadImage() {
        try {
            mae = ImageIO.read(getClass().getResource("/imagens//Cenario//Chao.png")).getScaledInstance(getW(), getH() * 2, Image.SCALE_DEFAULT);
            noCeuTemPao = ImageIO.read(getClass().getResource("/imagens//Cenario//Ceu.png")).getScaledInstance(getW(), getW()/8, Image.SCALE_DEFAULT);
            eMorreu = ImageIO.read(getClass().getResource("/imagens//Cenario//SegundoChao.png")).getScaledInstance(getW(), getW() / 13, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
