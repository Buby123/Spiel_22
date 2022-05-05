import java.util.ArrayList;
import java.util.Arrays;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import java.lang.Math;
/**
 * Beschreiben Sie hier die Klasse GameMaster.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GameMaster
{
    // Jeder Klasse ein Objekt 
    ArrayList<Player> list_player = new ArrayList<Player>();
    
    boolean with_comments = false;
    
    /**
     * Leerer Konstruktor
     */
    public void GameMaster(){
        
    }
    
    /**
     * Method to initialise object of all bot
     * Easier to start play_game methode
     */
    public void add_all_player() {
        //Player p1 = new Bot_Patrick("Patrick");
        Player p1 = new ArrayFourLight("Dagh");
        Player p2 = new Bot_Tim("Tim");
        Player p3 = new Bot_Konrad("Konrad");
        Player p4 = new Chances_Konrad("KonradInBesser");
        Player p5 = new Bot_Konrad_Trained_To_Win("Moin!");
        //Player p4 = new Bot_Konrad_Trained_To_Win("Konrad2");
        //this.add_player(p1);
        this.add_player(p1);
        //this.add_player(p2);
        //this.add_player(p3);
        //this.add_player(p4);
        this.add_player(p5);
        //this.add_player(p4);
    }
    
    /**
     * Add new Player Object to list of player objects
     */
    public void add_player(Player player){
        list_player.add(player);
    }
    
    /**
     * Remove the selected Player object from list of objects
     */
    public void remove_player(Player player){
        list_player.remove(player);
    }
    
    /**
     * Game method
     * Takes in all player object that are selected in the player list and gives them in the same dice value
     * For every of the 13 loop the player object must give back a boolean --> true for take the throw and false for discard the throw
     * 
     * Methode returns the sorted list of player object (winner is the first), in detail the result is printed in terminal
     */
    public ArrayList<Player> play_game(){
        int current_values[] = new int[list_player.size()];
        int num_rated[] = new int[list_player.size()];
        for(int i=0; i<13; i++){
            int current_throw = 1 + (int)(Math.random()*6);
            if(with_comments)System.out.println("Es wurde eine " + current_throw + " gewürfelt!");
            for(int j=0; j<list_player.size(); j++){
                list_player.get(j).update_gamedata(current_values[j], num_rated[j], i);
                
                if(i-num_rated[j] >= 5) {
                    current_values[j] += current_throw;
                    num_rated[j]++;
                    
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " muss den Wurf nehmen.");
                }
                else if(num_rated[j] >= 8) {
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " kann den Wurf nicht mehr nehmen.");
                }
                else if(list_player.get(j).rate_throw(current_throw)) {
                    current_values[j] += current_throw;
                    num_rated[j]++;
                    
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " hat den Wurf genommen.");
                }
                else {
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " hat den Wurf nicht genommen.");
                }
                
                list_player.get(j).update_gamedata(current_values[j], num_rated[j], i);
            }
        }
        
        
        int differences[] = new int[list_player.size()];

        for(int i=0; i<list_player.size(); i++) {
            differences[i] = Math.abs(current_values[i]-22);
        }
        
        int temp = 0;
        Player temp_player;

        for(int i=0; i<list_player.size(); i++){
            for(int j=0; j<list_player.size()-1; j++){
                if(differences[j] > differences[j+1]){
                    temp = differences[j];
                    differences[j] = differences[j+1];
                    differences[j+1] = temp;
                    
                    temp = current_values[j];
                    current_values[j] = current_values[j+1];
                    current_values[j+1] = temp;
                    
                    temp_player = list_player.get(j);
                    list_player.set(j, list_player.get(j+1));
                    list_player.set(j+1, temp_player);
                }
            }
        }
        
        

        if(with_comments) {
            System.out.println("Die Reihenfolge der Platzierung lautet:");
            for(int i=0; i<list_player.size(); i++) {
                System.out.println(list_player.get(i).get_name() + " mit " + current_values[i] + " Punkten");
                
            }
        }
        
        ArrayList<Player> winners = new ArrayList<Player>();
        winners.clear();
        int best_value = differences[0];
        
        for(int i=0; i<list_player.size(); i++){
            if(best_value == differences[i]){
                winners.add(list_player.get(i));
            }
        }
        
        for(int i=0; i<list_player.size(); i++) {
            boolean winned = false;
            for(Player e : winners){
                if(list_player.get(i) == e){
                    winned = true;
                }
            }
            list_player.get(i).game_ended(winned);
        }
        return winners;
    }
    
    /**
     * Methode for running a certain amount of times (parameter int) by using play_game methode serveral times
     * 
     * Result is printed in terminal
     */
    public ArrayList<Player> many_runs(int number_runs){
        ArrayList<Player> winners = new ArrayList<Player>();

        Dictionary<Player, Integer> dic = new Hashtable<Player, Integer>();
        for(int i=0; i<list_player.size(); i++){
            dic.put(list_player.get(i), 0);
        }
        
        for(int i=0; i<number_runs; i++){
            winners = play_game();
            
            for(int j=0; j<winners.size(); j++){
                dic.put(winners.get(j), dic.get(winners.get(j)) + 1);
            }
        }
        
        for(int i=0; i<dic.size(); i++){
             System.out.println("Spieler: " + list_player.get(i).get_name() + " hat " + dic.get(list_player.get(i)) + " Runden gewonnen.");
        }
        
        return winners;
    }
}
