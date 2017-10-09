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
import Models.Comparador;
import Models.Lapide;
import Models.Obstacle;
import Models.Player;
import Models.Rank;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JTextField;

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
    private static ArrayList<Image> images, logo;
    public static MainFormJFrame j;
    private boolean desl;
    private Image ceu, fundo;
    public static boolean rankBool;
    public ArrayList<Rank> rank;
     private JTextField input;
     private String valor;
    private long lastFrameTime;
    private int currentFrame;
    private boolean pegouNick;
    
    public Game()
    {
        rank = new ArrayList<Rank>();
        j.setSize(800, 500);
        setWidth(2000);
        setHeight(getWidth() / 4);
        setEntities(new ArrayList<Base>());
        setGarbage(new ArrayList<Base>());
        setR(new Random());
        setLastObstacleTime(0);
        prop = 10;
        rankBool = true;
        loadImages();
        setGameOver(true);
        images = new ArrayList<Image>();
        logo = new ArrayList<Image>();
        try {
            images.add(ImageIO.read(new File("src//imagens//Menu//Logo.png")).getScaledInstance(394, 326, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//MenuMaior.png")).getScaledInstance(175, 237, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//MenuMenor.png")).getScaledInstance(152, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//RankGrande.png")).getScaledInstance(145, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//RankPequeno.png")).getScaledInstance(123, 168, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//StartGrande.png")).getScaledInstance(177, 307, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(new File("src//imagens//Menu//StartPequeno.png")).getScaledInstance(145, 251, Image.SCALE_DEFAULT));
            ceu = ImageIO.read(new File("src//imagens//Cenario//Ceu.png")).getScaledInstance(4000, 500, Image.SCALE_DEFAULT);
            fundo = ImageIO.read(new File("src//imagens//Cenario//SegundoChao.png")).getScaledInstance(4000, 250, Image.SCALE_DEFAULT);
            for(int i = 0; i < 10; i++) 
                logo.add(ImageIO.read(new File("src//imagens//Logo//"+ i + ".png")).getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void init()
    {
        pegouNick = false;
        input = new JTextField(20);
        input.setFont(new Font("Dialog", Font.ITALIC, 28));
        input.setBounds(100, 100, 600, 38); //x, y, largura, altura
        input.setEnabled(false);
        j.add(input);
        j.setSize(getWidth(), getHeight());
        setScore(0);
        speed = 4;
        chao = new Cenario(0, getHeight() - getHeight() / 10, getWidth() * 2, getHeight() / 10, speed);
        getEntities().add(chao);
        setPlayer(new Player(getWidth() / 15, chao.getY() - getWidth()/prop, getWidth()/(prop * 2), getWidth()/prop, -getWidth() / (prop*35), prop, chao.getY(), speed));
        getEntities().add(getPlayer());
        j.setFocusable(true);
        j.setFocusTraversalKeysEnabled(true);
        j.setFocusCycleRoot(true);
        j.setFocusableWindowState(true);
        j.setFocusTraversalPolicyProvider(true);
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
            rankBool = false;
            return;
        }
        getEntities().removeAll(getGarbage());
        getGarbage().clear();
    }
    
   
    public void gameOver(Graphics g, boolean botoes[])
    {
        j.setSize(800, 500);
        g.drawImage(ceu, 0, 0, null);
        g.drawImage(fundo, 0, 300, null);
        g.drawImage(logo.get(currentFrame), 450, 320, null);
        changeFrame(logo);
        if(!rankBool)
        {
            input.setEnabled(true);
            Base.getCurrentGraphic().setColor(Color.WHITE);
            Base.getCurrentGraphic().drawString("Sua pontuação foi: " + getScore(), 80, 100);
            if(!botoes[3])
                g.drawImage(images.get(2), 650, 260, null); //Menu Menor
            else
                g.drawImage(images.get(1), 640, 250, null); //Menu Maior
            
            input.repaint();
            
            
            //Inicio
            if(botoes[4])
            {
                if(!pegouNick) {
                    valor = input.getText();
                    input.setText("");
                    pegouNick = true;
                }
                if(!valor.equals("null")) //o valor "null" é pra se na hora a pessoa não quiser colocar o rank, a gente digita null e ele nem adiciona
                {
                    Comparador comparador = new Comparador();
                    rank.add(new Rank(valor, score));
                    rank.sort(comparador);
                }
                Game.rankBool = true;
                j.botoes[4] = false;
                input.setEnabled(false);
                j.remove(input);
            }
            
            //Fim
            
        }
        else if(!botoes[2])
        {
            Base.getCurrentGraphic().setColor(Color.WHITE);
            Base.getCurrentGraphic().drawString("Ajude o esqueleto a salvar a Preguiça", 20, 80);
            Base.getCurrentGraphic().drawString("Use a seta para cima para pular ", 20, 140);
            Base.getCurrentGraphic().drawString("Use a seta para baixo para abaixar ", 20, 160);
            if(!botoes[0])
                g.drawImage(images.get(6), 420, 50, null); //Start Pequeno
            else
                g.drawImage(images.get(5), 410, 40, null); //Start Grande
            if(!botoes[1])
                g.drawImage(images.get(4), 615, 50, null); //Rank Pequeno
            else
                g.drawImage(images.get(3), 605, 40, null); //Rank Grande
            g.drawImage(images.get(0), 15, 165, null); //Logo
        }
        else
        {
            if(!botoes[3])
                g.drawImage(images.get(2), 650, 260, null); //Menu Menor
            else
                g.drawImage(images.get(1), 640, 250, null); //Menu Maior
            int i = 0;
            Base.getCurrentGraphic().setColor(Color.WHITE);
            for(Rank r: this.rank)
            {
                Base.getCurrentGraphic().drawString("" + (i+1) + "º - " + r.nome + " : " + r.score, 80, 100+i*20);
                i+= 1;
                if(i >= 10)
                    break;
            }
        }
    }
    
    public void changeFrame(ArrayList<Image> images)
    {
        long tempoAtual = System.currentTimeMillis();
        if (tempoAtual > lastFrameTime + 100) {
            lastFrameTime = tempoAtual;
            currentFrame++;
            if (currentFrame == images.size()) {
                currentFrame = 0;
            }
        }
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