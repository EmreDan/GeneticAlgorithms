package Emre_GA;

import java.util.Random;

/**
 * Author: Emre Danisan
 * Soft Computing Assignment
 * Teacher: Mete Eminagaoglu
 */
public class BinaryMinimization {
    
    Boolean[][] chromosomes = new Boolean[6][8];
    Boolean[][] Elite_Chromosomes = new Boolean[2][8];
    private final int  mutation_rate = 2; //over 10
    
    public void main(String[] args){
        int i=0,sum=0;
        Initilize();
        System.out.println("Loop Count : Found Generation Number");
        for (int j = 0; j < 100; j++) {
        Initilize();
            i=0;
            do{
                Next_Generation();        
                i++;
            }while(Fitness_Value(Elite_Chromosomes[0])!=-64 && Fitness_Value(Elite_Chromosomes[1])!=-64);
        System.out.print(j+ ":" + i + "\t");
        sum+=i;
            if(j%10==9)System.out.println("");
        }
        System.out.println("Average:" + sum/100);
        
        //Print_All(chromosomes);
    }
    
    private void Next_Generation(){
        Get_Best_Two(chromosomes, Elite_Chromosomes);        
        Tournament_Selection(chromosomes);
        for (int i = 0; i < 6; i+=2) {
            Crossover(chromosomes[i], chromosomes[i+1]);
        }
        Mutate_All(chromosomes);
        Replace_Worst_Two(chromosomes);
    }
    
    private void Replace_Worst_Two(Boolean[][] chromosomes){
        Sort_Chromosomes(chromosomes);
       System.arraycopy(Elite_Chromosomes[0], 0, chromosomes[0], 0, 8);
       System.arraycopy(Elite_Chromosomes[1], 0, chromosomes[1], 0, 8);
        
    }
    
    private void Sort_Chromosomes(Boolean[][] chromosomes){
        Boolean [] temp = new Boolean[8];
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 6; j++) {
                if(Fitness_Value(chromosomes[i]) < Fitness_Value(chromosomes[j])){
                    System.arraycopy(chromosomes[i], 0, temp, 0, 8);
                    System.arraycopy(chromosomes[j], 0, chromosomes[i], 0, 8);
                    System.arraycopy(temp, 0, chromosomes[j], 0, 8);
                }
            }
        }
    }
    
    private void Tournament_Selection(Boolean[][] chromosomes){
        Random rastgele = new Random();
        Boolean[][] offspring = new Boolean[6][8];
        for (int i = 0; i < 6; i++) {
            int rand1=rastgele.nextInt(6);
            int rand2 =rastgele.nextInt(6);
            while(rand1==rand2) { rand1=rastgele.nextInt(6); }
            if(Fitness_Value(chromosomes[rand1]) < Fitness_Value(chromosomes[rand2]) )
                System.arraycopy(chromosomes[rand1], 0, offspring[i], 0, 8);
            else
                System.arraycopy(chromosomes[rand2], 0, offspring[i], 0, 8);
        }
        for (int i = 0; i < 6; i++) {
            System.arraycopy(offspring[i], 0, chromosomes[i], 0, 8);
        }
        
    }
    
    private void Get_Best_Two(Boolean[][] chromosomes, Boolean[][] Elite_Chromosomes){
        Sort_Chromosomes(chromosomes);
        System.arraycopy(chromosomes[4], 0, Elite_Chromosomes[0], 0, 8);
        System.arraycopy(chromosomes[5], 0, Elite_Chromosomes[1], 0, 8);
        
    }
    
    private void Mutate_All(Boolean[][] chromosomes){
        for(Boolean [] Chromosome : chromosomes){
            Mutation(Chromosome);
        }
    }
    
    private void Mutation(Boolean [] Chromosome){
        Random Rand = new Random();
        for(int i=0; i<Chromosome.length ;i++){
            if(Rand.nextInt(10)<mutation_rate){
                Chromosome[i]=!Chromosome[i];
            }
        }
    }
    
    private void Crossover(Boolean[] ParentA, Boolean[] ParentB){
        //One point crossover
        Random Rand = new Random();
        int x = Rand.nextInt(ParentA.length-2)+1;
        Boolean[] temp = new Boolean[ParentA.length];
        for (int i = x; i < ParentA.length; i++) {
            temp[i] = ParentA[i];
            ParentA[i] = ParentB[i];
            ParentB[i] = temp[i];
        }
    }
    
    private Integer Fitness_Value(Boolean[] real_Chromosome){
        Boolean[] fake_Chromosome = real_Chromosome.clone();
        Integer value = Convert_Decimal(fake_Chromosome);
        return value*value + 16 * value;
    }
    private Integer Convert_Decimal(Boolean[] Chromosome){
        Integer Sum=0;
        Boolean Is_Negative=false;
        if(Chromosome[0]){      //Negative!
            Is_Negative=true;
            Twos_Compliment(Chromosome);
        }
        for (int i = Chromosome.length-1; i > 0; i--){
            Double kk=Math.pow(2, (Chromosome.length-1 )-i);
            if(Chromosome[i]) Sum+= kk.intValue();
        }
        if(Is_Negative)Sum *=-1;
        return Sum;
    }
    private void Twos_Compliment(Boolean [] Chromosome){
        int i=0;
        for (i = Chromosome.length-1; i>0; i--) {
            if(Chromosome[i]) break; 
        }
        for (int j = 0; j < i; j++) {
            Chromosome[j]=!Chromosome[j];
        }
    }    
    private void Initilize(){
        Random Rand= new Random();
        for(Boolean[] c : chromosomes){
            for (int i = 0; i < c.length; i++) {
                c[i] = Rand.nextBoolean();
            }
        }
    }
    private void Print_One(Boolean[] Chromosome){
        for (Boolean d : Chromosome)
                System.out.print(d ? 1:0);
        System.out.println(" = " + Fitness_Value(Chromosome));
    }
    private void Print_All(Boolean[][] chromosomes){
        for (Boolean[] c : chromosomes){
            Print_One(c);
        }
        System.out.println();
    }
    
}
