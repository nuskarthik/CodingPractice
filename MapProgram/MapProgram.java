import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MapProgram {

	final static String MAIN_FILE = "C:\\Users\\Test\\Desktop\\Karthik\\System Testing QN\\main.csv";
	final static String DATA_FILE = "C:\\Users\\Test\\Desktop\\Karthik\\System Testing QN\\data.csv";
	
	final static String FIELD_SPLIT = "\\^";
	final static String CSV_SPLIT = ",";
	final static String OPEN_LINE_1 = "Main_ID^Component_IDs^Main_Length^Sub_Lengths^Ideal_Travel_Time";
	final static String OPEN_LINE_2 = "Component_LinkID^Actual Speed(m/s)";
	
	static HashMap<String, Path> linkMap;
	static HashMap<String, SubPath> sublinkMap;
	static ArrayList<String> paths;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		String line = null;
		
		linkMap = new HashMap<String, Path>();
		sublinkMap = new HashMap<String, SubPath>();
		paths = new ArrayList<>();
		
		try {
			
			 br = new BufferedReader(new FileReader(MAIN_FILE));
			 
			 //read main file
			 while((line=br.readLine()) != null){
				 if(!line.equals(OPEN_LINE_1)){
					 String[] fields = line.split(FIELD_SPLIT);
					 
					 String retid = fields[0];
					 String retcompo = fields[1];
					 int length = Integer.parseInt(fields[2]);
					 String subLengths = fields[3];
					 float time = Float.parseFloat(fields[4]);
					 
					 Path newPath = new Path(retid, length, retcompo, time);
					 linkMap.put(retid, newPath);
					 paths.add(retid);
					 createSubPaths(retcompo, subLengths);
				 }
			 }
			 
			 br = new BufferedReader(new FileReader(DATA_FILE));
			 
			 //read data file
			 while((line=br.readLine()) != null){
				 if(!line.equals(OPEN_LINE_2)){
					 
					 String[] fields = line.split(FIELD_SPLIT);
					 
					 String retid = fields[0];
					 float speed = Float.parseFloat(fields[1]);
					 
					 SubPath oldSubPath = sublinkMap.get(retid);
					 oldSubPath.setSpeed(speed);
					 sublinkMap.put(retid, oldSubPath);
				 }
			 }
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		calculatePathSpeed();
	}
	
	private static void calculatePathSpeed() {
		
		for(String path: paths){
			
			float componentLength =0;
			float adjustedLength = 0;
			float adjustedSpeed ;
			
			Path tempPath = linkMap.get(path);
			float sum = 0, count =0;
			for(String id: tempPath.subpaths){
				SubPath tempSubPath = sublinkMap.get(id);
				if(tempSubPath!=null){
					count++;
					sum+=tempSubPath.speed;
					componentLength+=tempSubPath.length;
				}
			}
			
			float averageSpeed = sum/count;
			int l = tempPath.length;
			adjustedSpeed = ((l/componentLength)*averageSpeed);
			float idealSpeed = l/tempPath.time;
			
			checkCondition(path, adjustedSpeed, idealSpeed);
			
		}
		
	}

	private static void checkCondition(String path, float adjustedSpeed,
			float idealSpeed) {
		float error = (adjustedSpeed-idealSpeed)/idealSpeed;
		if(error<0){
			error*=-1;
		}
		System.out.println("Path "+path+" : "+ Float.toString(error)+" : ");
		if(error>0.8){
			System.out.println("GREEN");
		}
		else if(error>0.5){
			System.out.println("YELLOW");
		}
		else{
			System.out.println("RED");
		}
		
	}

	public static void createSubPaths(String subpaths, String subLengths){
		String[] lengths = subLengths.split(CSV_SPLIT);
		String[] pathIds = subpaths.split(CSV_SPLIT);
		for(int i=0;i<pathIds.length;i++){
			SubPath newSubPath = new SubPath(pathIds[i], Integer.parseInt(lengths[i]));
			sublinkMap.put(pathIds[i], newSubPath);
		}
		
	}

}
