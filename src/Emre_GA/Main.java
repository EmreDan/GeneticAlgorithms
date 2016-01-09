package Emre_GA;

import java.util.ArrayList;

/**
 *
 * @author Emre Danisan
 */
public class Main {
    
    public static void main(String[] args){         //I didn't want to use static variables
        //2 different GA aplications are created in this project. 
        //First one is Asymmetric Traveling Salesman. 
        TravelingSalesman GA = new TravelingSalesman();
        GA.main(args);
        
        //Second one is Minimization of a given function. 
        BinaryMinimization BM = new BinaryMinimization();
        BM.main(args);
                
    }
}
