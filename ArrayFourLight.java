import java.io.Serializable;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; 

/**
 * Meine Abgabe für das Ziel_22 Wuerfelspiel Projekt.
 * 
 * Funktionsweise logischer weise streng geheim. Hehe. Hauptsache keine Kommentare schreiben muessen ^^
 * 
 * @author Dagh Zeppfenfeld
 * @version 03.05.2022
 */
public class ArrayFourLight extends Player implements Serializable
{
    private boolean [][][][] myOracle;

    public ArrayFourLight(String neuName) {
        super();

        try{
            FileInputStream fis = new FileInputStream("myOracle.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            myOracle = (boolean [][][][]) ois.readObject();
        } catch (ClassNotFoundException ignore) {}
        catch( IOException ignore) {}  
    }

    public boolean rate_throw(int rolled_dice) {
        if(num_throws == 22) {
            return false;
        }
        
        if(current_value < 22){
            return myOracle[num_throws][num_rated][current_value][rolled_dice-1];
        }else{
            return false;
        }
    }
}
