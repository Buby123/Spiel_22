
/**
 * Write a description of class Tim here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bot_Tim extends Player
{
    private double average;
    private double divergence_top;
    private double divergence_bottom;
    private double delta_top;
    private double delta_bottom;
    private int unrated;
    
    private int forced_to_take=0;
    
    public Bot_Tim(String name) {
        this.average = 22.0/8;
        this.divergence_top = 2.247; //2.051492
        this.divergence_bottom = 2.643;//2.437843
        this.delta_top = 0.1;
        this.delta_bottom = 0.1;
        this.name = name;
    }
    
    public boolean rate_throw(int rolled_dice) {
        if(rolled_dice <= average + divergence_top + unrated * delta_top && rolled_dice >= average - divergence_bottom - unrated * delta_bottom) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void update_gamedata(int current_value, int num_rated, int num_throws) {
        super.update_gamedata(current_value, num_rated, num_throws);
        unrated = num_throws - num_rated;
        average = (22-current_value)/(double)(8-num_rated);
    }
}
