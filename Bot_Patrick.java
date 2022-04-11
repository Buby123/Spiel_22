import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.Math;

public class Bot_Patrick extends Player
{
    ArrayList<int[]> array_22 = new ArrayList<int[]>();
    int[] scores = new int[8];
    
    private double average;
    private double abweichungOben;
    private double abweichungUnten;
    
    String path;

    public Bot_Patrick(){
        path = ".\\dataset_22_o6.csv";
        read_csv(array_22);
        
        average = 22.0/8;
        abweichungOben = 2.247;
        abweichungUnten = 2.643;
    }
    
    public boolean rate_throw(int roled_dice){
        if((num_throws - num_rated) < 1){
            
        }
        
        if((num_throws - num_rated) < 1)
    }
    
    /**
     * Read in csv file from path set in constructor
     */
    private void read_csv(ArrayList<int[]> array){
        String line = "";
        int num_runs = 0;
        
        try{
            FileReader rd = new FileReader(path);
            BufferedReader br = new BufferedReader(rd);
            
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                num_runs++;
                int[] temp = new int[8];
                
                for(int i=0; i<values.length; i++){
                    temp[i] = Integer.valueOf(values[i]);
                }
                array.add(temp);
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void update_gamedata(int current_value, int num_rated, int num_throws){
        super.update_gamedata(current_value, num_rated, num_throws);
        average = (22-current_value)/(double)(8-num_rated);
    }
}
