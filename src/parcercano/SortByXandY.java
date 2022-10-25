/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parcercano;

import java.util.Comparator;

/**
 *
 * @author ddhenriquez
 */
public class SortByXandY implements Comparator<Node> {
    public int compare(Node a, Node b)
    {
        if(a.x == b.x){
            return a.y - b.y;
        } else{
            return a.x - b.x;
        }
    }
}

