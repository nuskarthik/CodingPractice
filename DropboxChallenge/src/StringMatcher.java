import java.util.HashMap;


public class StringMatcher {

	
	static HashMap<String, String> map;
	
	public static void main(String[] args){
	String pattern = "abab";
	String input = "xyzabcxzyabc";
	
	map = new HashMap<>();
	
	boolean val = patternMatch(pattern, input);
	System.out.println("POSSIBLE? = "+val);
	
	printOutput();
	}
	
	static void printOutput(){
		System.out.println(map.toString());
	}
	
	static String getCharFromPattern(String pattern){
		return String.valueOf(pattern.charAt(0));
	}
	
	static boolean patternMatch(String pattern, String input){
		if(input.isEmpty()&&pattern.isEmpty()){
				return true;
		}
		if(input.isEmpty()){
			return false;
		}
		if(pattern.isEmpty())
			return false;
		String curChar = getCharFromPattern(pattern);
		int patternIndex = 1;
		String matchingValue = input.substring(0, patternIndex);
		
		//if it is a new char add it
		if(!map.containsKey(curChar)){
			map.put(curChar, matchingValue);
			String nextinput = input.substring(patternIndex, input.length());
			String nextpattern = pattern.substring(1, pattern.length());
			while(!(patternMatch(nextpattern, nextinput))){

				if(map.get("a")=="xyzab"){
					int i=5;
					i++;
				}
				String nChar = getCharFromPattern(pattern);
				map.remove(nChar);
				patternIndex++;
				if(patternIndex==input.length()){
					return false;
				}
				if(patternIndex>input.length()){
					return false;
				}
				matchingValue = input.substring(0, patternIndex);

				map.remove(curChar);
				map.put(curChar, matchingValue);
				nextinput = input.substring(patternIndex, input.length());
			}
		}
		
		//if it is not a new char compare
		
		else{
			String value = map.get(curChar);
			int valueLength = value.length();
			
			if(valueLength>input.length()){
				return false;
			}
			
			String compareValue = input.substring(0, valueLength);
			
			//if it matches
			if(value.equals(compareValue)){
				String nextPattern = pattern.substring(1, pattern.length());
				String nextinput = input.substring(valueLength, input.length());
				return patternMatch(nextPattern, nextinput);
			}
			
			//if it doesnt match, backtrack
			
			else{
				return false;
			}
		}
		return true;
	}
	
}