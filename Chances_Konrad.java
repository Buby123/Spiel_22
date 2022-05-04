import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Chances_Konrad.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Chances_Konrad extends Player
{
    ArrayList<Integer> Poss20 = new ArrayList<Integer>();
    ArrayList<Integer> Poss21 = new ArrayList<Integer>();
    ArrayList<Integer> Poss22 = new ArrayList<Integer>();
    ArrayList<Integer> Poss23 = new ArrayList<Integer>();
    ArrayList<Integer> Poss24 = new ArrayList<Integer>();
    
    int rolls_of_game = 0;
    boolean test_runs = true;
    int game_count = 0;
    int won_last = 0;
    int won = 0;
    double delta = 1;
    double multiplier = 10.141924662101625;
    
    int[] beg_search = new int[6];
    
    public Chances_Konrad(){
        read_all();
    }
    
    @Override
    public void game_ended(boolean winned_game) {
        rolls_of_game = 0;
        /*game_count++;
        if(winned_game)
            won++;
        if(game_count == 250){
            if(won_last < won){
                multiplier += delta;
                delta /= 1.1;
            } else {
                multiplier -= delta;
            }
            game_count = 0;
            System.out.println(won + " " + won_last + ": " + multiplier);
            won_last = won;
            won = 0;
        }*/
    }
    
    public boolean rate_throw(int rolled_dice){
        long take_zero = count_poss(rolls_of_game);
        int poss_rolls = 10 * rolls_of_game;
        poss_rolls += rolled_dice;
        //int[] Poss = count_modulo(poss_rolls);
        long take_dice = count_poss(poss_rolls);
        
        /*long poss_mult_dice;
        long poss_mult_zero = 1;//(long)OhneReihenfolgeMitWiederholung(9 - num_rated, 4 - num_throws + num_rated);
        if(num_rated == 8)
            poss_mult_dice = 1;//(long)OhneReihenfolgeMitWiederholung(8 - 7, 5 - num_throws + num_rated);
        else
            poss_mult_dice = 1;//(long)OhneReihenfolgeMitWiederholung(8 - num_rated, 5 - num_throws + num_rated);*/
        
        //System.out.println(poss_mult_zero * take_zero + "   \t\t" + take_dice * poss_mult_dice *multiplier);
        
        if(/*poss_mult_zero*/take_zero > take_dice/*poss_mult_dice*/*multiplier){
            //System.out.println("Take not " + rolled_dice);
            return false;
        } else {
            //System.out.println("Take     " + rolled_dice);
            rolls_of_game = poss_rolls;
            return true;
        }
    }
    
    public int OhneReihenfolgeMitWiederholung(int stellen, int nullen){
        return fakultät(stellen+nullen-1) / (fakultät(stellen - 1) * fakultät(nullen));
    }
    
    public int fakultät(int n){
        if(n == 0 || n == 1)
            return 1;
        else if (n == -1){
            System.out.println("Fakultät mit negativen Wert " + n + " rated " + num_rated + " throws " + num_throws);
            return 1;
            //Alles kleiner wird durchgelassen, da diese Fehler selten sind
        } 
            
        return fakultät(n-1) * n;
    }
    
    /*public long count_together(int rolls){
        //sum = (long)count_poss(rolls, Poss20)/4;
        //sum += (long)count_poss(rolls, Poss21)/2;
        long sum = (long)count_poss(rolls, Poss22);
        //sum += (long)count_poss(rolls, Poss23)/2;
        //sum += (long)count_poss(rolls, Poss24)/4;
        return sum;
    }*/
    
    public int[] count_modulo(int rolls){
        int[] Outputs = new int[2];
        
        int temp = rolls;
        int digits = 0;
        while(temp != 0){
            temp /= 10;
            digits++;
        }
        
        int rolls2 = rolls / 10;
        
        int teiler = (int)Math.pow(10, digits);
        int teiler2 = teiler / 10;
        boolean searchBoth = true;
        if(teiler2 == 1){
            Outputs[1] = Poss22.size(); 
            searchBoth = false;
        }
        
            
        for(int e : Poss22){
            if(searchBoth){
                if(e%teiler2 == rolls2){
                    Outputs[1]++;
                    if(e%teiler == rolls){
                        Outputs[0]++;
                    }
                }
            } else {
                if(e%teiler == rolls){
                    Outputs[0]++;
                }
            }
        }        
        
        return Outputs;
    }
    
    public int count_poss(int rolls/*, ArrayList<Integer> Poss*/){
        int temp = rolls;
        int digits = 0;
        while(temp != 0){
            temp /= 10;
            digits++;
        }
        
        int teiler = (int)Math.pow(10, 8 - digits);
        if(teiler == 0)
            teiler = 1;
            
        int teilerSpeedUp = (int)Math.pow(10, digits-1);
        if(teilerSpeedUp == 0)
            teilerSpeedUp = 1;
        int first = rolls / teilerSpeedUp;
        //System.out.println(teiler);
        int counter = 0;
        if(/*Poss == Poss22 && */first != 0){
            for(int i=beg_search[first-1]; i<Poss22.size(); i++){
                if(Poss22.get(i)/teiler == rolls){
                    counter++;
                } else {
                    if(counter != 0){
                        break;
                    }
                }
            }
        } else {
            for(int e : Poss22){
                if(e/teiler == rolls){
                    counter++;
                }
            }
        }
        
        return counter;
    }
    
    public void read_all(){
        read_data(20, Poss20);
        read_data(21, Poss21);
        read_data(22, Poss22);
        read_data(23, Poss23);
        read_data(24, Poss24);
    }
    
    public void read_data(int input, ArrayList<Integer> Poss){
        File file=new File(System.getProperty("user.dir") + "//" + Integer.toString(input) + ".txt");
        
        if(file.exists()){
            try {
                FileReader reader = new FileReader(System.getProperty("user.dir") + "//" + Integer.toString(input) + ".txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;

                int counter = 0;
                int number = 1;
                while ((line = bufferedReader.readLine()) != null) {
                    int num = Integer.valueOf(line);
                    Poss.add(num);
                    if(input == 22){
                        if(num / 10000000 == number){
                            beg_search[number-1] = counter;
                            number++;
                        }
                        counter++;
                    }
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Data not avaible!!!");
        }
    }
}
