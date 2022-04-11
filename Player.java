
public abstract class Player
{
    int current_value;
    int num_rated;
    abstract boolean rate_throw(int rolled_dice);
    
    public void update_gamedata(int current_value, int num_rated){
        this.current_value = current_value;
        this.num_rated = num_rated;
    }
}
