import java.util.HashMap;


public class Sample {
	
	public static void main(String[] args){
		String[] pattern = {"2222", "888888888"};
		HashMap<String, Integer> map = new HashMap<>();
		for(int i=0;i<pattern.length;i++){
			String[] test = pattern[i].split("");
			boolean flag = false;
			String check = "";
			int count = 0;
	        for(int j=1;j<test.length;j++){
	        	if(check.isEmpty()){
	        		check = test[j];
	        	}
	        	if(!check.equals(test[j])){
	        		flag = true;
	        	}
	        	else{
	        		count++;
	        	}
	        	if(flag){
	        		flag = false;
	        		System.out.print(count+check);
	        		check=test[j];
	        		count=1;
	        	}
	        	
	        	/*if(map.get(test[j])!=null){
	        		int val = map.get(test[j]);
	        		map.remove(test[j]);
	        		map.put(test[j], val+1);
	        	}
	        	else{
	        		map.put(test[j], 1);
	        	}*/
	        }
	        System.out.print(count+check);
//	        for(int j=1;j<test.length;j++){
//	        	System.out.print(map.get(test[j])+test[j]);
//	        }
//	        System.out.print("\n");
//	        map.clear();
	    }


		}
}
