import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Beschreiben Sie hier die Klasse Bot_Konrad_Trained_To_Win.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Bot_Konrad_Trained_To_Win extends Player
{
    //Durchlauf - Gewürfelte Augenzahl - Punktzahl
    private short[][][] diagramm = new short[13][6][48];
    short[][][] wuerfe = new short[13][6][48];
    private Bot_Tim Untrained_Helper = new Bot_Tim("UntrainedHelper");
    
    boolean training = true;

    public Bot_Konrad_Trained_To_Win(String name){
        this.name = name;
        InitBot();
    }
    
    public void game_ended(boolean winned_game) {
        int score = current_value - 22;
        score = score < 0 ? -score : score;
        
        //Rückschreiben der Daten
        for(int i=12; i>=0; i--){
            for(int i2=5; i2>=0; i2--){
                for(int i3=47; i3>=0; i3--){
                    if(winned_game){
                        if(32767 >= (long)diagramm[i][i2][i3] + (long)wuerfe[i][i2][i3] && -32767 <= (long)diagramm[i][i2][i3] + (long)wuerfe[i][i2][i3])
                            diagramm[i][i2][i3] += wuerfe[i][i2][i3];
                    } else {
                        if(32767 >= (long)diagramm[i][i2][i3] - (long)wuerfe[i][i2][i3] && -32767 <= (long)diagramm[i][i2][i3] - (long)wuerfe[i][i2][i3])
                            diagramm[i][i2][i3] -= wuerfe[i][i2][i3];
                    }
                    wuerfe[i][i2][i3] = 0;
                }
            }
        }
    }
    
    /*
     * Importiert die Datentabelle für den Bot
     */
    private void InitBot(){
        //Lesen der Datentabelle für die KI
        File file=new File(System.getProperty("user.dir") + "//dataKNear.txt");
        if(file.exists()){
            try {
                FileReader reader = new FileReader(System.getProperty("user.dir") + "//dataKNearWin.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] Numbers = line.split(",");

                    int counter = 0;
                    for(int i=12; i>=0; i--){
                        for(int i2=5; i2>=0; i2--){
                            for(int i3=47; i3>=0; i3--){
                                diagramm[i][i2][i3] = (short)Integer.parseInt(Numbers[counter]);
                                counter++;
                            }
                        }
                    }
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for(int i=12; i>=0; i--){
                for(int i2=5; i2>=0; i2--){
                    for(int i3=47; i3>=0; i3--){
                        diagramm[i][i2][i3] = 0;
                    }
                }
            }
        }
    }

    /*
     * Speichert die Tabelle diagramm
     */
    public void SaveData(){
        String new_data = "";

        for(int i=12; i>=0; i--){
            for(int i2=5; i2>=0; i2--){
                for(int i3=47; i3>=0; i3--){
                    new_data += (int)diagramm[i][i2][i3];
                    new_data += ',';
                }
            }
        }

        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "//dataKNearWin.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(new_data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Gibt das Ergebnis für einen Wurf aus
     */
    public boolean rate_throw(int rolled_dice){
        num_throws++;

        double gewicht = (int)diagramm[num_throws-1][rolled_dice-1][current_value]; 

        int difDurchlauf = 1;
        int difAugenzahl = 0;
        int difStand = 0;
        double distMax = CalcDistance(difDurchlauf, difAugenzahl, difStand);
        /*for(int durchl=-difDurchlauf; durchl <= difDurchlauf; durchl += 1){
            for(int augenz=-difAugenzahl; augenz <= difAugenzahl; augenz += 1){
                for(int stand=-difStand; stand <= difStand; stand += 1){
                    if(num_throws-1+durchl < 13 && rolled_dice-1+augenz < 6 && current_value+stand<48 && num_throws-1+durchl >= 0 && rolled_dice-1+augenz >= 0 && current_value+stand>=0){
                        if(SatzDesPythos(durchl, augenz, stand, distMax)){
                            int value = (int)diagramm[num_throws-1+durchl][rolled_dice-1+augenz][current_value+stand];
                            //System.out.println("Value: " + value);
                            if(value != 0){
                                gewicht += value * (distMax - CalcDistance(durchl, augenz, stand) / distMax);
                            }
                        }
                    }
                }
            }
        }*/
        
        if(Math.abs(gewicht) < 50){
            Untrained_Helper.update_gamedata(current_value, num_rated, num_throws-1);
            if(Untrained_Helper.rate_throw(rolled_dice)){
                //Werten
                if(training)
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)1; 
                return true;
            } else {
                //Nicht Werten
                if(training)
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)-1;   
                return false;
            }
        } else {
            if((int)(Math.random()*100) <= 50 + gewicht){
                //Werten
                if(training)
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)1; 
                return true;
            } else {
                //Nicht Werten
                if(training)
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)-1;   
                return false;
            }
        }
    }
    
    /*
     * Berechnet ob vier Punkte in einem Bereich liegen
     */
    public boolean SatzDesPythos(int x, int y, int z, double distMax){
        double distPoint = CalcDistance(x,y,z);

        if(distPoint <= distMax){
            return true;
        }
        return false;
    }

    /*
     * Berechnet eine Distanz von vier Punkten
     */
    public double CalcDistance(int x, int y, int z){
        return Math.sqrt((double)(x*x + y*y + z*z));
    }
}
