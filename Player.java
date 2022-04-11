
public abstract class Player
{
    protected String name = "";
    protected int current_value;
    protected int num_rated;
    protected int num_throws;
    
    abstract boolean rate_throw(int rolled_dice);
    
    public void update_gamedata(int current_value, int num_rated, int num_throws){
        this.current_value = current_value;
        this.num_rated = num_rated;
        this.num_throws = num_throws;
    }
    
    public String get_name() {
        return name;
    }
}
