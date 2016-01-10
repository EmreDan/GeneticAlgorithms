/*
 * A Genetic Algorithm approach to Asymmetric Travelling Salesman Problem.
 * 
 * Author: Emre Danisan
 * Soft Computing Assignment
 * Teacher: Mete Eminagaoglu
 */
package Emre_GA;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Emre Danisan
 */
public class TravelingSalesman {

    final int TournamentSize = 2;
    final int mutation_rate = 2;    //out of 10
    
    public void main(String[] args) {
        Integer map[][] = new Integer[10][10];    //Actually there is 10 cities 1 trough 10
        Integer chromosomes[][] = new Integer[8][10]; //8 Chromosomes each contains 10 genes.
        map=Initilize_Map();
        Initilize_Chromosomes(chromosomes);
        
        Global_Optimum(chromosomes, map, 321, 33000); 
    }
    
    private Integer[] Global_Optimum(Integer[][] chromosomes, Integer [][] map , int fitness_limit , int iteration_limit){
        
        Integer[] Best_Route = Get_Fittest(chromosomes, map);
        Integer i=0;
        int x;
        while((x = Get_Fitness_Value(Best_Route, map) ) > fitness_limit && i < iteration_limit ){
            Next_Generation(chromosomes, map);
            Integer [] Local_Optimum = Get_Fittest(chromosomes, map);
            if(Get_Fitness_Value(Local_Optimum, map) < x) {
                Best_Route = Local_Optimum;
            }
            i++;
        }
        System.out.print("Best route is:");
        Print_Single_Chromosome(Best_Route);
        System.out.println("With the best fitness of: " + x);
        System.out.println("Total Number of generations: " + i);
        return Best_Route;
    }
    
    private Integer[] Get_Fittest(Integer[][] Chromosomes, Integer[][] Map){
        int min = Get_Fitness_Value(Chromosomes[0], Map ) ;
        Integer[] Current = Chromosomes[0];
        for (int i = 1; i < Chromosomes.length; i++) {
            int fit = Get_Fitness_Value(Chromosomes[i], Map);
            if(fit< min) {
                Current = Chromosomes[i];
                min = fit;
            }
        }
        return Current;
    }
    
    private Integer[][] Next_Generation(Integer[][] Chromosomes, Integer[][] Map){
        
        for (int i = 0; i < 8; i+=2) {
            Integer[] Parent1=Tournament_Selection(Chromosomes, Map);
            Integer[] Parent2=Tournament_Selection(Chromosomes, Map);
            Crossover_Function(Parent1, Parent2);  
            Mutation(Parent1);
            Mutation(Parent2);
            Chromosomes[i]=Parent1;
            Chromosomes[i+1]=Parent2;
        }
        return Chromosomes;
    }
    
    private void Print_Single_Chromosome(Integer [] Chromosome){
        for (int i = 0; i < Chromosome.length; i++) {
            System.out.print(Chromosome[i]+1 + " ");
        }
        System.out.println("");
    }
    
    private void Mutation(Integer[] Chromosome){
        Random a = new Random();
        if(a.nextInt(10)>mutation_rate) return;
        int x = a.nextInt(10); int y=a.nextInt(10);
        if(x==y) return;
        int min = Math.min(x, y); int max = Math.max(x, y);
        int value=Chromosome[max];
        for (int i = max-1;i > min; i--) {
            Chromosome[i+1]=Chromosome[i]; 
        }
        Chromosome[min+1]=value;        
    }
    
    private Integer[] Tournament_Selection(Integer[][] Chromosomes, Integer[][] map){
        Random a = new Random();
        Integer [][] Selected =new Integer[TournamentSize][Chromosomes.length];
        for (int i = 0; i < TournamentSize; i++) {
            int x=a.nextInt(Chromosomes.length);
            Selected[i]=Chromosomes[x];
        }
        Integer[] current=Selected[0];
        for (int i = 0; i < TournamentSize; i++) {
            if(Get_Fitness_Value(current, map)>Get_Fitness_Value(Selected[i], map)){
                current=Selected[i];
            }
        }
        return current;
    }
    
    private void Crossover_Function(Integer[] ChromosomeA, Integer[] ChromosomeB){
        Random s=new Random();
        Random f=new Random();
        
        Integer[] ChildA = new Integer[ChromosomeA.length];
        Integer[] ChildB = new Integer[ChromosomeB.length];
        
        ArrayList<Integer> valuesA = new ArrayList<>();
        ArrayList<Integer> valuesB = new ArrayList<>();
        int x,y;
        x=s.nextInt(10);
        y=f.nextInt(10);
        int big = max(x,y);
        int small = min(x,y);
        for (int i = small; i < big ; i++) {
                valuesA.add(ChromosomeA[i]);
                valuesB.add(ChromosomeB[i]);
                ChildA[i]=ChromosomeA[i];
                ChildB[i]=ChromosomeB[i];
        }
        int k=big;
        int l=big;
        for (int i = 0; i < 10; i++) {
            if(! valuesA.contains(ChromosomeB[ (big+i)%10 ]) ){
                ChildA[k]=ChromosomeB[ (big+i)%10];
                k++;
                k%=10;
            }
            
            if(! valuesB.contains(ChromosomeA[ (big+i)%10 ]) ){
                ChildB[l]=ChromosomeA[ (big+i)%10];
                l++;
                l%=10;
            }
        }
        
        for(int i=0;i<ChildA.length ; i++){
            ChromosomeA[i]=ChildA[i];
            ChromosomeB[i]=ChildB[i];
        }
    }
    
    private int Get_Fitness_Value(Integer[] chromosomes, Integer[][] map){
        int sum = 0;
        for (int j = 0; j < chromosomes.length-1; j++) {
                sum+=map[ chromosomes[j] ] [ chromosomes[j+1] ];
        }
        
        return sum;
    }
    
    private void Print_Chromosomes(Integer[][] chromosomes, Integer[][] map){
        for (int i = 0; i < 8; i++) {
            System.out.print("Chromosome " + i + " : ");
            Print_Single_Chromosome(chromosomes[i]);
            System.out.println("Fitness = " + Get_Fitness_Value(chromosomes[i], map));
                    
        }
    }
    private Integer[][] Initilize_Map() {
        Integer[][] map={
        {-1000, 55, 34, 32, 54, 40, 36, 40, 37, 53},
        {64, -1000, 54, 55, 73, 45, 71, 50, 53, 52},
        {51, 48, -1000, 41, 40, 58, 55, 33, 35, 37},
        {47, 46, 55, -1000, 49, 45, 56, 52, 57, 55},
        {50, 39, 43, 52, -1000, 26, 40, 39, 38, 33},
        {60, 49, 48, 57, 58, -1000, 48, 47, 48, 48},
        {51, 37, 44, 43, 38, 40, -1000, 64, 48, 47},
        {58, 41, 53, 45, 47, 43, 74, -1000, 43, 42},
        {53, 38, 40, 33, 36, 58, 35, 30, -1000, 31},
        {60, 39, 40, 56, 41, 41, 45, 59, 19,-1000}
        };
        return map;
    }

    private Integer[][] Initilize_Chromosomes(Integer[][] chromosomes) {
        for (Integer[] chromosome : chromosomes) { 
            Set_Unique_Values(chromosome);
        }
        return chromosomes;
    }

    private void Set_Unique_Values(Integer[] chromosome) {
        Random rand = new Random();int data=-1;
        boolean flag;
        for (int i = 0; i < chromosome.length; i++) {   //for every gene
            flag = true;
            while (flag) {                              //until unique number found
                data = rand.nextInt(10);
                flag = false;
                for (int k = 0; k < i; k++) {           //until the initilized values.
                    if (chromosome[k] == data) {        //keep checking
                        flag = true;
                        break;}}}
            chromosome[i] = data;       //assign the unique value
        }}}
