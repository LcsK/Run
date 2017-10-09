/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controllers.Game;
import JFrames.MainFormJFrame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author 1545 IRON V4
 */
public class CarregaImagens {

    public ArrayList<Image> Carregar() {
        ArrayList<Image> images = new ArrayList<Image>();
        try {
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/Logo.png")).getScaledInstance(394, 326, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/MenuMaior.png")).getScaledInstance(175, 237, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/MenuMenor.png")).getScaledInstance(152, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/RankGrande.png")).getScaledInstance(145, 200, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/RankPequeno.png")).getScaledInstance(123, 168, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/StartGrande.png")).getScaledInstance(177, 307, Image.SCALE_DEFAULT));
            images.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Menu/StartPequeno.png")).getScaledInstance(145, 251, Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return images;
    }

    public ArrayList<Image> Logo() {
        ArrayList<Image> logo = new ArrayList<Image>();
        try {
            for (int i = 0; i < 10; i++) {
                logo.add(ImageIO.read(getClass().getResourceAsStream("/imagens/Logo/" + i + ".png")).getScaledInstance(150, 150, Image.SCALE_DEFAULT));
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logo;
    }

    public Image ceu() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/imagens/Cenario/Ceu.png")).getScaledInstance(4000, 500, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(CarregaImagens.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Image fundo() {

        try {
            return ImageIO.read(getClass().getResourceAsStream("/imagens/Cenario/SegundoChao.png")).getScaledInstance(4000, 250, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(CarregaImagens.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
