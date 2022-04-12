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
    
    public void GameMaster(){
        
    }
    
    public void add_all_player() {
        Player p1 = new Bot_Patrick("Patrick");
        Player p2 = new Bot_Tim("Tim");
        this.add_player(p1);
        this.add_player(p2);
    }
    
    public void add_player(Player player){
        list_player.add(player);
    }
    
    public void remove_player(Player player){
        list_player.remove(player);
    }
    
    //public Player[] play_game(){
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
                else if(list_player.get(j).rate_throw(current_throw) && num_rated[j] < 8) {
                    current_values[j] += current_throw;
                    num_rated[j]++;
                    
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " hat den Wurf genommen.");
                }
                else {
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " hat den Wurf nicht genommen.");
                }
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
                    list_player.set(i, list_player.get(i+1));
                    list_player.set(i+1, temp_player);
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
        return winners;
    }
    
    public void many_runs(int number_runs){
        ArrayList<Player> winners;
        
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
    }
}
