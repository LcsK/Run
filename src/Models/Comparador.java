/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class Comparador implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if( ((Rank)o1).score > ((Rank)o2).score )
            return -1;
        else if( ((Rank)o1).score < ((Rank)o2).score )
            return 1;
        return 0;
    }
}
