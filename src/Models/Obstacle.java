/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.awt.Color;

/**
 *
 * @author lucas
 */
public class Obstacle extends Base {
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Obstacle(int x, int y, int w, int h, double sx) 
    {
        super(x,y,w,h,sx,0);
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        if(getX() + getW() + getSx() <= 0)
            return false;
        setY((int)(getY() + getSy()));
        getRectangle().y = getY();
        setX((int)(getX() + getSx()));
        getRectangle().x = getX();
        return true;
    }

    @Override
    public void draw() 
    {
        getCurrentGraphic().setColor(Color.BLACK);
        getCurrentGraphic().fillRect(getX(), getY(), getW(), getH());
    }
}
