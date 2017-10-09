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
public class Rank implements Comparable {
    public String nome;
    public int score;
    
    public Rank(String nome, int score)
    {
        this.nome = nome;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        if(this.score >((Rank)o).score)
            return 1;
        else if(this.score < ((Rank)o).score)
            return -1;
        return 0;
    }
}