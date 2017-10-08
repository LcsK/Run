/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import JFrames.MainFormJFrame;
import Models.Aranha;
import Models.Base;
import Models.Cenario;
import Models.Lapide;
import Models.Obstacle;
import Models.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.font.AttributeValues;

/**
 *
 * @author lucas
 */
public class Game {
    private Player player;
    private Base chao;
    private double speed;
    private int width, height, score, obstacleSide;
    private long lastObstacleTime;
    private ArrayList<Base> entities, garbage;
    private Random r;
    private boolean jump, gameOver;
    private int prop;
    private static ArrayList<Image> images;
    public static MainFormJFrame j;
    private boolean desl;
    
    public Game()
    {
        setWidth(2000);
        setHeight(getWidth() / 4);
        j.setSize(getWidth(), getHeight());
        setEntities(new ArrayList<Base>());
        setGarbage(new ArrayList<Base>());
        setR(new Random());
        setLastObstacleTime(0);
        prop = 10;
        loadImages();
        setGameOver(true);
        images = new ArrayList<Image>();
        try {
            images.add(ImageIO.read(new File("src//imagens//Menu//Logo.png")).getScaledInstance(252, 434, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//MenuMaior.png")).getScaledInstance(175, 237, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//MenuMenor.png")).getScaledInstance(152, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//RankGrande.png")).getScaledInstance(145, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//RankPequeno.png")).getScaledInstance(123, 168, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//StartGrande.png")).getScaledInstance(253, 439, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//StartPequeno.png")).getScaledInstance(207, 359, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init()
    {
        setScore(0);
        speed = 6;
        chao = new Cenario(0, getHeight() - getHeight() / 10, getWidth() * 2, getHeight() / 10, speed);
        getEntities().add(chao);
        setPlayer(new Player(getWidth() / 15, chao.getY() - getWidth()/prop, getWidth()/(prop * 2), getWidth()/prop, -getWidth() / (prop*35), prop, chao.getY(), speed));
        getEntities().add(getPlayer());
    }
    public void upDate(Graphics g, boolean botoes[]) {
        Base.screenUpdate(g, getWidth(), getHeight());
        if (isGameOver()) {
            gameOver(g, botoes);
        } else {
            entitiesGenerator();
            getPlayer().changeFrame(Player.getImages());
            draw();
            drawScore();

            move();
            jump();
            deslizar();
            playersCollision();
            
            verifyGameOver();
        }
    }
    
    public void verifySpeedUp() {
        if(getScore() % 3 == 0) {
            speed = speed + 1;
            for(Base b: getEntities()) {
                if(!(b instanceof Player)) {
                    b.setSx(-speed);
                }
            }
            Aranha.setDownSpeed(speed);
            getPlayer().setSpeed(speed);
        }
    }
    
    /*public void changeFrames()
    {
        for(Base b: getEntities())
            b.changeFrame();
    }*/
    private void loadImages()
    {
        Cenario c = new Cenario(0, getHeight() - getHeight() / 10, getWidth() * 2, getHeight() / 10, speed);
        Aranha a = new Aranha(getWidth() + 20, 10, getHeight() / 5, getHeight() / 8, speed);
        Lapide l = new Lapide(getWidth() + 20, c.getY() - getHeight() / 10, getHeight() / 10, getHeight() / 10, speed);
        l = null;
        a = null;
        c = null;
    }
    public void draw()
    {
        for(Base b: getEntities())
            b.draw();
    }
    public void move()
    {
        for(Base b: getEntities())
            if(!b.move(getWidth(), getHeight()) && b instanceof Obstacle)
            {
                getGarbage().add(b);
                setScore(getScore() + 1);
                verifySpeedUp();
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
                if(b instanceof Obstacle)
                {
                    getGarbage().add(player);
                    return;
                }
            }
    }
    private void entitiesGenerator()
    {
        long currentTime = System.currentTimeMillis();
        if(currentTime > getLastObstacleTime() + 4000)
        {
            setLastObstacleTime(currentTime);
            setObstacleSide(getR().nextInt(3));
            switch(getObstacleSide())
            {
                case 1:
                    getEntities().add(new Aranha(getWidth() + 20, 10, getHeight() / 5, getHeight() / 8, speed));
                    break;
                case 2:
                    getEntities().add(new Lapide(getWidth() + 20, chao.getY() - getHeight() / 10, getHeight() / 10, getHeight() / 10, speed));
                    break;
                default:
                    break;
            }
        }
    }
    public void setPlayersActions(boolean space, boolean action, boolean restart)
    {
        if(getEntities().size() > 0) {
            if (space && getPlayer().getSy() == 0) {
                setJump(true);
            }
            else if(action && getPlayer().getSy() == 0) {
                desl = true;
            }
        }
        if (isGameOver() && restart) {
            
        //setWidth(2000);
            setGameOver(false);
            restart = false;
            init();
        }
    }
    public void deslizar() {
        if(desl && getPlayer().getY() + getPlayer().getH() >= chao.getY()) {
            desl = false;
            getPlayer().deslizar();
        }
    }
    
    public void jump()
    {
        if (getPlayer().getY() + getPlayer().getH() >= chao.getY() && isJump()) {
            setJump(false);
            getPlayer().jump();
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
    public void gameOver(Graphics g, boolean botoes[])
    {
        //setWidth(800);
        if(!botoes[0])
            g.drawImage(images.get(6), 40, 40, null); //Start Pequeno
        else
            g.drawImage(images.get(5), 15, 15, null); //Start Grande
        //g.drawImage(images.get(0), 10, 10, null); //Logo
        //g.drawImage(images.get(1), 3, 2, null); //Menu Maior
        //g.drawImage(images.get(2), 3, 2, null); //Menu Menor
        //g.drawImage(images.get(3), 3, 2, null); //Rank Grande
        //g.drawImage(images.get(4), 3, 2, null); //Rank Pequeno
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