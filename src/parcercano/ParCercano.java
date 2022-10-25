package parcercano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Algoritmos y Complejidad IST 4310
 * NRC: 3265
 * Name: David Daniel Henriquez Leal
 * Student code: 200157506
 * Date: 26/10/2022
 * 
 * WorkShop: Closest Pair
 * In this workshop I created a proyect that is able to find the closest pair of nodes in two diferent ways, using the brute force and
 * by divide and conquer, this also works as a check that the program is doing a good job.
 * 
 * References:
 * https://www.geeksforgeeks.org/arrays-sort-in-java-with-examples/
 */
public class ParCercano {
    static Random ran = new Random();
    static float minDist = 9999999;
    static Node FirstNode;
    static Node SecondNode;

    public static void main(String[] args) {
        int N = 6; //Set the size of the array of nodes.
        int x = 10; //Set the limit of the random function in x.
        int y = 5; //Set the limit of the random function in y.
        ArrayList<Node> Nodos = createNodes(N, x, y); //Creation of the nodes.
        parCercanoRecursivo(Nodos); //Start of the recursive process.
        print(Nodos); //Print in console the results of the program
    }
    /*
    Function that finds the closest pair of nodes in an array.
    Input:
    listaN - The array from which we want to find the closest pair.
    Outputs:
    minDist - The minimun distance between two nodes of the array.
    FirstNode - The first node of the closest pair in the array.
    SecondNode - The second node of the closest pair in the array.
    */
    public static void parCercanoRecursivo(ArrayList<Node> listaN){
        if (listaN.size() <= 3){
            brute(listaN); //Find the minimum distance of the subdivision of the list to analyze when it's size is less than 4.
        }else{
            parCercanoRecursivo(divide(listaN, 0)); //Call the recursive function with the first half of the array.
            parCercanoRecursivo(divide(listaN, 1)); //Call the recursive function with the second half of the array.
            middle(listaN); //Create the middle of the array and find its minium distance.
        }   
    }
    /*
    Function that create the array of nodes.
    Inputs: 
    N - The size of the array.
    ranX - The limit of the random function in x.
    ranY - The limit of the random function in y.
    Output:
    Nodes - Principal array of nodes.
    */
    public static ArrayList<Node> createNodes(int N, int ranX, int ranY){
        ArrayList<Node> Nodes = new ArrayList<Node>();
        for (int i = 0; i < N; i++) {
            int x = ran.nextInt(ranX);
            int y = ran.nextInt(ranY);
            Node n = new Node(x, y);
            Nodes.add(n);
        }
        
        Collections.sort(Nodes, new SortByXandY()); //Calls a function that sorts the array
        
        //Give names to each node
        for (int i = 0; i < N; i++) {
            Nodes.get(i).setName(i);
        }

        return Nodes; 
    }
    
    /*
    Function that finds the minimun distance between two nodes of an array.
    Inputs:
    listaN - The array from which we want to find the minimum distance.
    Outputs:
    dist - The minimun distance between two nodes of the array.
    nodeA - The first node of the closest pair in the array.
    nodeB - The second node of the closest pair in the array.
    */    
    public static void brute(ArrayList<Node> listaN){
        //Calculate the distance between each pair of nodes in the array to find the minimun distance and the two closest nodes.
        for (int j = 0; j < listaN.size()-1; j++) {
            for (int k = j+1; k < listaN.size(); k++) {
                Node nodeA = listaN.get(j);
                Node nodeB = listaN.get(k);
                int distX = nodeB.x - nodeA.x;
                int distY = nodeB.y - nodeA.y;
                float dist = (float)Math.sqrt(distX*distX + distY*distY);
                
                //Check if the distance between the two nodes is less than the minimun distance and refresh the data.
                if (dist < minDist){
                    FirstNode = nodeA;
                    SecondNode = nodeB;
                    minDist = dist;
                }
            }
        }
    }
    
    /*
    Function that divides an array of nodes in two halfs.
    Inputs:
    list - The array we want to divide.
    Half - An integer that determine if the output is going to be the first half or the second half of the array.
    Outputs:
    halfList - A half of the original array, the first half if Half is 0 and the second half if Half is 1.
    */
    public static ArrayList<Node> divide(ArrayList<Node> list, int Half){
        ArrayList<Node> halfList = new ArrayList<Node>();
        
        //Determine wich half of the array is going to return the function depending on the int Half.
        if (Half == 0){
            for (int i = 0; i < list.size()/2; i++) {
                halfList.add(list.get(i));
            }
        } else{
            for (int i = list.size()/2; i < list.size(); i++) {
                halfList.add(list.get(i));
            }
        }
        return halfList; 
    }
    
    /*
    Function that create the middle array of the given array.
    Inputs:
    listaN - The array from which we want to create the middle.
    Outputs:
    Middle - The middle of the given array depending on the minimun distance.
    */
    public static void middle(ArrayList<Node> listaN){
        ArrayList<Node> Middle = new ArrayList<Node>();
        double middle = (listaN.get(listaN.size()/2-1).x + listaN.get(listaN.size()/2).x)/2;
        double firstMiddle = middle - minDist;
        double secondMiddle = middle + minDist;
        
        //Creates the middle array depending on the range determinated by firstMiddle and secondMiddle.
        for (int j = 0; j < listaN.size(); j++) {
            if(listaN.get(j).x > firstMiddle && listaN.get(j).x < secondMiddle){
                Middle.add(listaN.get(j));
            }
        }

        brute(Middle); //Perform the brute function with the middle array.
    }    
    
    /*
    Function that prints the results of the recursive function and compares it with the brute function.
    Inputs:
    listaN - The array from which we want to print the collected data.
    Outputs:
    Prints in console the minimun distance and the closest pair of nodes of the array, showing first the results of the recursive function and them
    the results of putting thw whole array in the brute function.
    */
    public static void print(ArrayList<Node> listaN){
        //Print the list of nodes in the array.
        System.out.println("Nombre - X - Y");
        for (int j = 0; j < listaN.size(); j++) {
            Node nodo = listaN.get(j);
            System.out.println("  " + nodo.name + "      " + nodo.x + "  " + nodo.y);
        }
        
        //Prints the results of the recursive function.
        System.out.println("\n" + "Resultados divide and conquer; ");
        System.out.println("Distancia: " + minDist + " - Primer nodo: " + FirstNode.name + " - Segundo Nodo: " + SecondNode.name + "\n");
        
        brute(listaN); //Performs the brute function the whole array.

        //Prints the results of putting the whole array in the brute function.
        System.out.println("Resultados bruto; ");
        System.out.println("Distancia: " + minDist + " - Primer nodo: " + FirstNode.name + " - Segundo nodo: " + SecondNode.name);
    }   
}
