package SA3;

public abstract class Resource{
    protected String id;
    protected Boolean busy;

    public Boolean is_busy(){
	return busy;
    }
    public void set_status(Boolean status){
	busy = status;
    }
    public String id(){
	return id;
    }
}
