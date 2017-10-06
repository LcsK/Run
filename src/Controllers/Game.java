/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Base;
import Models.Obstacle;
import Models.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import sun.font.AttributeValues;

/**
 *
 * @author lucas
 */
public class Game {
    private Player player;

    
    private int width, height, score, obstacleSide;
    private long lastObstacleTime;
    private ArrayList<Base> entities, garbage;
    private Random r;
    private boolean jump, gameOver;
    
    public Game(int width, int height)
    {
        setWidth(width);
        setHeight(height);
        setEntities(new ArrayList<Base>());
        setGarbage(new ArrayList<Base>());
        setR(new Random());
        setLastObstacleTime(0);
    }
    
    public void init()
    {
        setScore(0);
        setPlayer(new Player(30, getWidth()/2, 60, 80));
        getEntities().add(getPlayer());
    }
    public void upDate(Graphics g) {
        Base.screenUpdate(g, getWidth(), getHeight());
        if (isGameOver()) {
            gameOver();
        } else {
            entitiesGenerator();
            getPlayer().changeFrame();
            draw();
            drawScore();

            move();
            jump();
            playersCollision();

            verifyGameOver();
        }
    }
    public void changeFrames()
    {
        for(Base b: getEntities())
            b.changeFrame();
    }
    public void draw()
    {
        for(Base b: getEntities())
            b.draw();
    }
    public void move()
    {
        for(Base b: getEntities())
            if(!b.move(getWidth(), getHeight()) && b.getClass() == Obstacle.class)
            {
                getGarbage().add(b);
                setScore(getScore() + 1);
            }
    }
    public void drawScore()
    {
        Base.getCurrentGraphic().setColor(Color.WHITE);
        Base.getCurrentGraphic().drawString("" + getScore(), 80, 50);
    }
    
    public void playersCollision()
    {
        for(Base b: getEntities())
            if(getPlayer().hasCollision(b))
            {
                if(b.getClass() == Obstacle.class)
                {
                    getGarbage().add(player);
                    return;
                }
            }
    }
    private void entitiesGenerator()
    {
        long currentTime = System.currentTimeMillis();
        if(currentTime > getLastObstacleTime() + 2500)
        {
            setLastObstacleTime(currentTime);
            setObstacleSide(getR().nextInt(3));
            Obstacle o = null;
            switch(getObstacleSide())
            {
                case 1:
                    o = new Obstacle(getWidth() + 20, 30, 10, 40, 2);
                    break;
                case 2:
                    o = new Obstacle(getWidth() + 20, getHeight() - 40, 10, 40, 2);
                    break;
            }
            getEntities().add(o);
        }
    }
    public void setPlayersActions(boolean space, boolean restart)
    {
        if (space && getPlayer().getY() > getHeight() - getPlayer().getH() - 9 ) {
            player.setSy(-2);
        }
        if (isGameOver() && restart) {
            setGameOver(false);
            restart = false;
            init();
        }
    }
    public void jump()
    {
        if (getPlayer().getY() < getHeight() / 3 && isJump())
            setJump(false);
        if (!isJump()) 
        {
            getPlayer().setSy(2);
            setJump(true);
        }
    }
    private void verifyGameOver()
    {
        if(getGarbage().contains(getPlayer()))
        {
            getGarbage().clear();
            getEntities().clear();
            setGameOver(true);
            return;
        }
        getEntities().removeAll(getGarbage());
        getGarbage().clear();
    }
    public void gameOver()
    {
        Base.getCurrentGraphic().setColor(Color.WHITE);
        Base.getCurrentGraphic().drawString("FIM DE JOGO", 50, 100);
        Base.getCurrentGraphic().drawString(getScore() + " Pontos", 50, 130);
        Base.getCurrentGraphic().drawString("R - Reiniciar", 50, 160);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getObstacleSide() {
        return obstacleSide;
    }

    public void setObstacleSide(int obstacleSide) {
        this.obstacleSide = obstacleSide;
    }

    public long getLastObstacleTime() {
        return lastObstacleTime;
    }

    public void setLastObstacleTime(long lastObstacleTime) {
        this.lastObstacleTime = lastObstacleTime;
    }

    public ArrayList<Base> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Base> entities) {
        this.entities = entities;
    }

    public ArrayList<Base> getGarbage() {
        return garbage;
    }

    public void setGarbage(ArrayList<Base> garbage) {
        this.garbage = garbage;
    }

    public Random getR() {
        return r;
    }
    public void setR(Random r) {
        this.r = r;
    }
    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    //</editor-fold>
}