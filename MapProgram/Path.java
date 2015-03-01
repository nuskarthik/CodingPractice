
public class Path {

	String id;
	int length;
	String[] subpaths;
	float time;
	
	public Path(String idString, int lengthInt, String subpathString, float timeInt){
		this.id = idString;
		this.length = lengthInt;
		this.subpaths = subpathString.split(",");
		this.time = timeInt;
	}
}
