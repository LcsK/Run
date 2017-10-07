/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public abstract class Base {
    protected int x, y, w, h, currentFrame;
    double sx, sy;
    protected long lastFrameTime;
    protected Color color;
    protected static Graphics currentGraphic;
    protected Rectangle rectangle;
    protected ArrayList<Image> images;
    //<editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Base(int x, int y, int w, int h)
    {
        setX(x);
        setY(y);
        setW(w);
        setH(h);
        setSx(1);
        setSy(1);
        setRectangle(new Rectangle(x, y, w, h));
        setColor(Color.BLACK);
        setImages(new ArrayList<Image>());
    }
    public Base(int x, int y, int w, int h, int sx, int sy)
    {
        this(x,y,w,h);
        setSx(sx);
        setSy(sy);
        setImages(new ArrayList<Image>());
    }
    public Base(int x, int y, int w, int h, Color color)
    {
        this(x,y,w,h);
        setColor(color);
        setImages(new ArrayList<Image>());
    }
    public Base(int x, int y, int w, int h, int sx, int sy, Color color)
    {
        this(x,y,w,h,color);
        setSx(sx);
        setSy(sy);
        setImages(new ArrayList<Image>());
    }
    //</editor-fold>
    
    public abstract void draw();
    public abstract boolean move(int width, int height);
    public boolean hasCollision(Base b)
    {
        if(this.getClass().equals(b.getClass()))
            return false;
        else
            return getRectangle().intersects(b.getRectangle());
    }
    
    public static void screenUpdate(Graphics g, int width, int height)
    {
        setCurrentGraphic(g);
        getCurrentGraphic().setColor(Color.GREEN);
        getCurrentGraphic().fillRect(0, 0, width, height);
    }
    public void changeFrame()
    {
        long tempoAtual = System.currentTimeMillis();
        if (tempoAtual > getLastFrameTime() + 100) {
            setLastFrameTime(tempoAtual);
            setCurrentFrame(getCurrentFrame()+1);
            if (getCurrentFrame() == getImages().size()) {
                setCurrentFrame(0);
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public static Graphics getCurrentGraphic()
    {
        return Base.currentGraphic;
    }
    private static void setCurrentGraphic(Graphics currentGraphic)
    {
        Base.currentGraphic = currentGraphic;
    }
    public int getX()
    {
        return this.x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getW()
    {
        return this.w;
    }
    public void setW(int w)
    {
        this.w = w;
    }
    public int getH()
    {
        return this.h;
    }
    public void setH(int h)
    {
        this.h = h;
    }
    public double getSx()
    {
        return this.sx;
    }
    public void setSx(double sx)
    {
        this.sx = sx;
    }
    public double getSy()
    {
        return this.sy;
    }
    public void setSy(double sy)
    {
        this.sy = sy;
    }
    public int getCurrentFrame()
    {
        return this.currentFrame;
    }
    public long getLastFrameTime()
    {
        return this.lastFrameTime;
    }
    public void setLastFrameTime(long lastFrameTime)
    {
        this.lastFrameTime = lastFrameTime;
    }
    public void setCurrentFrame(int currentFrame)
    {
        this.currentFrame = currentFrame;
    }
    public Color getColor()
    {
        return this.color;
    }
    public void setColor(Color color)
    {
        this.color = color;
    }
    public Rectangle getRectangle()
    {
        return this.rectangle;
    }
    private void setRectangle(Rectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    public ArrayList<Image> getImages()
    {
        return this.images;
    }
    public void setImages(ArrayList<Image> images)
    {
        this.images = images;
    }
    //</editor-fold>
}
