import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Beschreiben Sie hier die Klasse Bot_Konrad.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Bot_Konrad extends Player
{
    private short[][][][] diagramm = new short[13][6][48][27]; //Aktuellen Wert KI Netzwerk 
    short[][][] wuerfe = new short[13][6][48];
    int[] scores = new int[9];    
    
    /*
     * Konstuktor ruft die Initialisierung auf
     */
    public Bot_Konrad(){
        InitBot();
    }
    
    /*
     * Importiert die Datentabelle für den Bot
     */
    private void InitBot(){
        //Lesen der Datentabelle für die KI
        File file=new File(System.getProperty("user.dir") + "//dataKNear.txt");
        if(file.exists()){
            try {
                FileReader reader = new FileReader(System.getProperty("user.dir") + "//dataKNear.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] Numbers = line.split(",");

                    int counter = 0;
                    for(int i=12; i>=0; i--){
                        for(int i2=5; i2>=0; i2--){
                            for(int i3=47; i3>=0; i3--){
                                for(int i4=26; i4>=0; i4--){
                                    diagramm[i][i2][i3][i4] = (short)Integer.parseInt(Numbers[counter]);
                                    counter++;
                                }
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
                        for(int i4=26; i4>=0; i4--){
                            diagramm[i][i2][i3][i4] = 0;
                        }
                    }
                }
            }
        }
    }
    
    /*
     * Wird aufgerufen am Ende des Spieles,
     * speichert die Werte für die KI
     */
    public void game_ended() {
        int score = current_value - 22;
        score = score < 0 ? -score : score;
        
        //Rückschreiben der Daten
        for(int i=12; i>=0; i--){
            for(int i2=5; i2>=0; i2--){
                for(int i3=47; i3>=0; i3--){
                    if(32767 != diagramm[i][i2][i3][score] + wuerfe[i][i2][i3] && -32767 != diagramm[i][i2][i3][score] + wuerfe[i][i2][i3])
                            diagramm[i][i2][i3][score] += wuerfe[i][i2][i3];
                    wuerfe[i][i2][i3] = 0;
                }
            }
        }
        
        //SaveData();
    }
    
    /*
     * Speichert die Tabelle diagramm
     */
    private void SaveData(){
        String new_data = "";
        
        for(int i=12; i>=0; i--){
            for(int i2=5; i2>=0; i2--){
                for(int i3=47; i3>=0; i3--){
                    for(int i4=26; i4>=0; i4--){
                        new_data += (int)diagramm[i][i2][i3][i4];
                        new_data += ',';
                    }
                }
            }
        }
        
        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "//dataKNear.txt", false);
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
        
            double gewicht = 0; 

            int difDurchlauf = 1;
            int difAugenzahl = 0;
            int difStand = 0;
            int difEndwert = 2;
            double distMax = CalcDistance(difDurchlauf, difAugenzahl, difStand, difEndwert);
            for(int durchl=-difDurchlauf; durchl <= difDurchlauf; durchl += 1){
                for(int augenz=-difAugenzahl; augenz <= difAugenzahl; augenz += 1){
                    for(int stand=-difStand; stand <= difStand; stand += 1){
                        for(int endw=-difEndwert; endw <= 0; endw += 1){
                            if(num_throws-1+durchl < 13 && rolled_dice-1+augenz < 6 && current_value+stand<48 && 26+endw < 27 && num_throws-1+durchl >= 0 && rolled_dice-1+augenz >= 0 && 26+endw >= 0 && current_value+stand>=0){
                                if(SatzDesPythos(durchl, augenz, stand, endw, distMax)){
                                    int value = (int)diagramm[num_throws-1+durchl][rolled_dice-1+augenz][current_value+stand][26+endw];
                                    //System.out.println("Value: " + value);
                                    if(value != 0){
                                        gewicht += value * (distMax - CalcDistance(durchl, augenz, stand, endw) / distMax);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            int difDurchlaufNeg = 1;
            int difAugenzahlNeg = 0;
            int difStandNeg = 0;
            int difEndwertNeg = 20;
            double distMaxNeg = CalcDistance(difDurchlaufNeg, difAugenzahlNeg, difStandNeg, difEndwertNeg);
            for(int durchl=-difDurchlaufNeg; durchl <= difDurchlaufNeg; durchl += 1){
                for(int augenz=-difAugenzahlNeg; augenz <= difAugenzahlNeg; augenz += 1){
                    for(int stand=-difStandNeg; stand <= difStandNeg; stand += 1){
                        for(int endw=0; endw <= difEndwert; endw += 1){
                            if(num_throws-1+durchl < 13 && rolled_dice-1+augenz < 6 && current_value+stand<48 && endw < 27 && num_throws-1+durchl >= 0 && rolled_dice-1+augenz >= 0 && endw >= 0 && current_value+stand>=0){
                                if(SatzDesPythos(durchl, augenz, stand, endw, distMax)){
                                    int value = (int)diagramm[num_throws-1+durchl][rolled_dice-1+augenz][current_value+stand][endw];
                                    //System.out.println("Value: " + value);
                                    if(value != 0){
                                        gewicht -= value * (distMax - CalcDistance(durchl, augenz, stand, endw) / distMax);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //System.out.println(gewicht + " " + aktWurf);

            if(/*gewicht < 1000 && gewicht > -1000*/false){
                System.out.println("Automatikwertung");
                if(DurchschnittZiel(rolled_dice)/*BruteForceDecision(scores)*/){
                    //Werten
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)1; 
                    scores[num_rated] = rolled_dice;
                    //System.out.println("Werte");
                    return true;
                } else {
                    //Nicht Werten
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)-1;   
                    //System.out.println("Nicht Werten");
                    return false;
                }
            } else {
                if((int)(Math.random()*10000) <= 5000 + gewicht){
                    //Werten
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)1; 
                    scores[num_rated] = rolled_dice;
                    //System.out.println("Werte");
                    //werteDenWurf();
                    return true;
                } else {
                    //Nicht Werten
                    wuerfe[num_throws-1][rolled_dice-1][current_value] = (short)-1;   
                    //System.out.println("Nicht Werten");
                    return false;
                }
            }
    }
    
    /*
     * Berechnet ob vier Punkte in einem Bereich liegen
     */
    public boolean SatzDesPythos(int x, int y, int z, int k, double distMax){
        double distPoint = CalcDistance(x,y,z,k);

        if(distPoint <= distMax){
            return true;
        }
        return false;
    }

    /*
     * Berechnet eine Distanz von vier Punkten
     */
    public double CalcDistance(int x, int y, int z, int k){
        return Math.sqrt((double)(x*x + y*y + z*z + k*k));
    }
    
    //To less decisions
    /*
     * Durchschnitt
     */
    private boolean DurchschnittZiel(int rolled_dice){
        double durchs = current_value / (8 - num_rated);
        double range = (num_throws) / 6.5 - (num_rated+1) / 8 + 1;
        
        if(durchs + 2 < rolled_dice && rolled_dice < durchs + 2){
            return true;
        } else {
            return false;
        }
    }
}
