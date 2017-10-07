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
    private ArrayList<Image> pulo;
    private int velPulo;
    private boolean pulando;
    private int jumpFrame;
    private long lastJumpFrameTime;
    private int posImgX;
    private int alturaChao;
    private int prop;
    
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Player(int x, int y, int w, int h, int velPulo, int prop, int alturaChao) 
    {
        super(x,y,w,h);
        this.prop = prop;
        this.velPulo = velPulo;
        this.alturaChao = alturaChao;
        pulo = new ArrayList<Image>();
        loadImagem();
    }
    //</editor-fold>
    
    public void loadImagem() {
        try {
            for(int i = 0; i < 10; i++) {
                getImages().add(ImageIO.read(new File("src//imagens//Player//Correndo1//"+i+".png")).getScaledInstance(getW() * 2, getH(), Image.SCALE_DEFAULT));
                pulo.add(ImageIO.read(new File("src//imagens//Player//Pulando1//"+i+".png")).getScaledInstance(getW() * 2, getH(), Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        posImgX = getX() - getW() / 2;
    }
    
    @Override
    public void draw() {
        
        if(pulando) {
            getCurrentGraphic().drawImage(pulo.get(jumpFrame), posImgX, getY(), null);
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual > lastJumpFrameTime + (-2000 * velPulo / 4)/ prop) {
                lastJumpFrameTime = tempoAtual;
                jumpFrame++;
                if (jumpFrame == pulo.size()) {
                    jumpFrame = 0;
                }
            }
            
        }
        else
            getCurrentGraphic().drawImage(getImages().get(getCurrentFrame()), posImgX, getY(), null);
        getCurrentGraphic().setColor(RED);
        getCurrentGraphic().drawRect(x, y, w, h);
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
        jumpFrame = 0;
        pulando = true;
        setSy(velPulo);
    }
    
    public boolean isPulando() {
        return pulando;
    }

    public void setPulando(boolean pulando) {
        this.pulando = pulando;
    }
    
}
