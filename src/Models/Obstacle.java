/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author lucas
 */
public class Obstacle extends Base {
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Obstacle(int x, int y, int w, int h, int sx) 
    {
        super(x,y,w,h,sx,0);
    }
    //</editor-fold>

    @Override
    public boolean move(int width, int height) {
        if(getX() + getW() + getSx() <= 0)
            return false;
        setY(getY() + getSy());
        getRectangle().y = getY();
        setX(getX() + getSx());
        getRectangle().x = getX();
        return true;
    }

    @Override
    public void draw() 
    {
        getCurrentGraphic().setColor(getColor());
        getCurrentGraphic().fillRect(getX(), getY(), getW(), getH());
    }
}
