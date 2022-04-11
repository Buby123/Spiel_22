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
    public void GameMaster(){
        
    }
    
    public void add_player(Player player){
        list_player.add(player);
    }
    
    public void remove_player(Player player){
        list_player.remove(player);
    }
    
    public ArrayList<Player> game(){
        for(int i=0; i<13; i++){
            int aktWurf = 1 + (int)Math.random()*6;
            
            for(int x=0; x<list_player.length; x++){
                
            }
        }
        
        
        return list_player;
    }
    // Anzahl Test festlegen 
    
    // 1. Wuefelt und übergibt zahl an Spieler
    // 2. Ergebnisse erhalten und auswerten
}
