package SA3;

public abstract class NHResource extends Resource{
    protected boolean operational;
    public boolean is_operational(){
	return operational;
    }
    public void set_operational_status(boolean status){
	operational = status;
    }
}
