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
    int[] scores = {0,0,0,0,0,0,0,0};
    int fuenf_num = 0;
    
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
    
        if(roled_dice == 6){
            return false;
        }
        
        if(roled_dice == 5){
            if (fuenf_num == 2){
                fuenf_num = 0;
                return false;
            }
            if (fuenf_num < 2){
                fuenf_num++;
            }
        }
            
        
        if((num_throws - num_rated) < 1){
            reduce(array_22, scores);
            boolean[] next = check_next(array_22, scores);
            
            for(int i=0; i<6; i++){
                    if(roled_dice==(i+1) && next[i]){
                        if(entscheideWurf(roled_dice)){
                            for(int x=0; x<scores.length; x++){
                                if(scores[x] == 0){
                                    scores[x] = roled_dice;
                                    return true;
                                }
                            } 
                        }
                        else{
                            return false;
                        }
                    }
            }
            return false;
        }
        
        else{
            if(entscheideWurf(roled_dice)) {
                    for(int i=0; i<scores.length; i++){
                        if(scores[i] == 0){
                            scores[i] = roled_dice;
                            return true;
                        }
                    }
                    return false;
            }
            else{
                return false;
            }
        }
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
    
    private void reduce(ArrayList<int[]> array, int[] scores){
        int len_scores_without_0 = 0;
        int array_size = array.size();
        
        for(int i=0; i<scores.length; i++){
            if (scores[i] == 0){
                break;
            }
            len_scores_without_0++;
        }
        
        for(int i=array_size-1; i>=0; i--){
            for(int x=0; x<len_scores_without_0; x++){
                if (scores[x] != array.get(i)[x]){
                    array.remove(i);
                    break;
                }
            }
        }
    }
    
    public void update_gamedata(int current_value, int num_rated, int num_throws){
        super.update_gamedata(current_value, num_rated, num_throws);
        average = (22-current_value)/(double)(8-num_rated);
    }
    
    private boolean entscheideWurf(int role_dice) {
        if(role_dice <= average + abweichungOben && role_dice >= average - abweichungUnten) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * check for all possible number in the next throw
     */
    private boolean[] check_next(ArrayList<int[]> array, int[] scores){
        int len_scores_without_0 = 0;
        boolean[] result = {false, false, false, false, false, false};
        
        for(int i=0; i<scores.length; i++){
            if (scores[i] == 0){
                break;
            }
            len_scores_without_0++;
        }
        
        //System.out.println("Laenge Scores check_next: " + len_scores_without_0);
        
        for(int i=0; i<array.size(); i++){
            if(array.get(i)[len_scores_without_0] == 1){
                result[0] = true;
            }
            if(array.get(i)[len_scores_without_0] == 2){
                result[1] = true;
            }
            if(array.get(i)[len_scores_without_0] == 3){
                result[2] = true;
            }
            if(array.get(i)[len_scores_without_0] == 4){
                result[3] = true;
            }
            if(array.get(i)[len_scores_without_0] == 5){
                result[4] = true;
            }
            if(array.get(i)[len_scores_without_0] == 6){
                result[5] = true;
            }
        }
        
        return result;
    }
}
