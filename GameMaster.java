import java.util.ArrayList;
import java.util.Arrays;

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
    
    boolean with_comments = true;
    
    public void GameMaster(){
        
    }
    
    public void add_player(Player player){
        list_player.add(player);
    }
    
    public void remove_player(Player player){
        list_player.remove(player);
    }
    
    public Player[] play_game(){
        int current_values[] = new int[list_player.size()];
        int num_rated[] = new int[list_player.size()];
        
        for(int i=0; i<13; i++){
            int current_throw = 1 + (int)(Math.random()*6);
            System.out.println("Es wurde eine " + current_throw + " gewürfelt!");
            for(int j=0; j<list_player.size(); j++){
                list_player.get(j).update_gamedata(current_values[j], num_rated[j], j);
                if(i-num_rated[j] >= 5) {
                    current_values[j] += current_throw;
                    num_rated[j]++;
                    
                    if(with_comments)System.out.println(list_player.get(j).get_name() + " muss den Wurf genommen.");
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
        for(int i=0; i<list_player.size(); i++) {
            current_values[i] = Math.abs(current_values[i]-22);
        }
        Player ranked_players[] = new Player[list_player.size()];
        for(int i=0; i<list_player.size(); i++) {
            int best_value = Integer.MAX_VALUE;
            int best_index = -1;
            for(int j=0; j<list_player.size(); j++) {
                if(current_values[j] < best_value) {
                    best_value = current_values[j];
                    best_index = j;
                }
            }
            ranked_players[i] = list_player.get(best_index);
            current_values[i] = Integer.MAX_VALUE;
        }
        if(with_comments) {
            System.out.println("Die Reihenfolge der Platzierung lautet:");
            for(int i=0; i<ranked_players.length; i++) {
                System.out.println(ranked_players[i].get_name());
            }
        }
        
        return ranked_players;
    }
    // Anzahl Test festlegen 
    
    // 1. Wuefelt und übergibt zahl an Spieler
    // 2. Ergebnisse erhalten und auswerten
}
