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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lucas
 */
public class Player extends Base {
    private static ArrayList<Image> deslize;
    private static ArrayList<Image> pulo;
    private static ArrayList<Image> images;
    private int velPulo;
    private boolean pulando;
    private boolean deslizando;
    private int actionFrame;
    private long lastActionFrameTime;
    private static int posImgX;
    private int alturaChao;
    private int prop;
    private static boolean img = false;
    int xb, yb, wb, hb;
    private double speed;
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Player(int x, int y, int w, int h, int velPulo, int prop, int alturaChao, double speed) 
    {
        super(x,y,w,h);
        xb = x;
        yb = y;
        wb = w;
        hb = h;
        this.speed = speed;
        this.prop = prop;
        this.velPulo = velPulo;
        this.alturaChao = alturaChao;
        if(!img)
        {
            pulo = new ArrayList<Image>();
            images = new ArrayList<Image>();
            deslize = new ArrayList<Image>();
            img = true;
            loadImagem();
        }
    }
    //</editor-fold>
    
    public void loadImagem() {
        try {
            for(int i = 0; i < 10; i++) {
                getImages().add(ImageIO.read(new File("src//imagens//Player//Correndo//"+i+".png")).getScaledInstance(getW() * 2, getH(), Image.SCALE_DEFAULT));
                pulo.add(ImageIO.read(new File("src//imagens//Player//Pulando//"+i+".png")).getScaledInstance(getW() * 2, getH(), Image.SCALE_DEFAULT));
                deslize.add(ImageIO.read(new File("src//imagens//Player//Rolando//"+i+".png")).getScaledInstance(getW() * 2, getH(), Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        posImgX = getX() - getW() / 2;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    @Override
        public void changeFrame(ArrayList<Image> images)
    {
        long tempoAtual = System.currentTimeMillis();
        if (tempoAtual > getLastFrameTime() + (100 * 3 / speed)) {
            setLastFrameTime(tempoAtual);
            setCurrentFrame(getCurrentFrame()+1);
            if (getCurrentFrame() == images.size()) {
                setCurrentFrame(0);
            }
        }
    }
    
    @Override
    public void draw() {
        
        if(pulando) {
            getCurrentGraphic().drawImage(pulo.get(actionFrame), posImgX, getY(), null);
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual > lastActionFrameTime + (-2000 * velPulo / 4)/ prop) {
                lastActionFrameTime = tempoAtual;
                actionFrame++;
                if (actionFrame == pulo.size()) {
                    actionFrame = 0;
                }
            }
        }
        else if(deslizando) {
            getCurrentGraphic().drawImage(deslize.get(actionFrame), posImgX , getY() - getW(), null);
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual > lastActionFrameTime + (200 * 3 / speed)) {
                lastActionFrameTime = tempoAtual;
                actionFrame++;
                if (actionFrame == deslize.size()) {
                    deslizando = false;
                    setY(yb);
                    setX(xb);
                    getRectangle().setSize(wb, hb);
                    setW(wb);
                    setH(hb);
                }
            }
        }
        else
            getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), posImgX, getY(), null);
        getCurrentGraphic().setColor(RED);
        //getCurrentGraphic().drawRect(x, y, w, h);
    }

    @Override
    public boolean move(int width, int height) {
        setY((int)(getY() + getSy()));
        getRectangle().y = getY();
        
        if(pulando) {

            setSy(getSy() + 0.1);
        }
        if(getY() + getH() >= alturaChao && getSy() > 0) {
            pulando = false;
            setSy(0);
        }
        
        return true;
    }

    public void jump() {
        if(!deslizando && !pulando) {
        actionFrame = 0;
        pulando = true;
        setSy(velPulo);
        }
    }
    
    public void deslizar() {
        if(!deslizando && !pulando) {
        actionFrame = 0;
        deslizando = true;
        setY(alturaChao - getW());
        getRectangle().setSize(getW(), getW());
        setH(getW());
        }
    }
    
    public boolean isPulando() {
        return pulando;
    }

    public void setPulando(boolean pulando) {
        this.pulando = pulando;
    }
    public static ArrayList<Image> getImages()
    {
        return Player.images;
    }
    public static void setImages(ArrayList<Image> images)
    {
        Player.images = images;
    }
}
