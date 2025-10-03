package SA3;

public class SoftwareResource extends NHResource{
    private String version;
    private String type;
    public SoftwareResource(String version, String type){
	this.version = version;
	this.type = type;
    }
    public String version(){
	return version;
    }
    public String type(){
	return type;
    }
}
