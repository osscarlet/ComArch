import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
	
	public static void displayList(ArrayList<String> list) {
		for (int j=0; j<list.size(); j++)
			System.out.print(list.get(j)+" ");
		System.out.println();
		for (int j=0; j<list.size(); j++)
			System.out.println("["+j+"] "+list.get(j));
		System.out.println("Size: "+list.size());
	}
	
	public static boolean isInst(String text) {
		if (text.equalsIgnoreCase("add") 
				|| text.equalsIgnoreCase("nand") 
				|| text.equalsIgnoreCase("lw") 
				|| text.equalsIgnoreCase("sw") 
				|| text.equalsIgnoreCase("beq") 
				|| text.equalsIgnoreCase("jalr") 
				|| text.equalsIgnoreCase("halt") 
				|| text.equalsIgnoreCase("noop"))
			return true;
		else
			return false;
	}
	
	public static boolean isRtype(String text) {
		if (text.equalsIgnoreCase("add"))
			return true;
		else
			return false;
	}
	
	public static boolean isItype(String text) {
		if (text.equalsIgnoreCase("lw")
				|| text.equalsIgnoreCase("sw")
				|| text.equalsIgnoreCase("beq"))
			return true;
		else
			return false;
	}
	
	public static boolean isJtype(String text) {
		if (text.equalsIgnoreCase("jalr"))
			return true;
		else return false;
	}
	
	public static boolean isOtype(String text) {
		if (text.equalsIgnoreCase("halt")
				|| text.equalsIgnoreCase("noop")
				)
			return true;
		else return false;
	}
	
	public static boolean isFill(String text) {
		if (text.equalsIgnoreCase(".fill"))
			return true;
		else return false;
		
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		//read from text file
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("\\","/");
		currentDir += "/src/sample.txt";
        File file = new File(currentDir);
        Scanner sn = new Scanner(file);

		
		while(sn.hasNextLine()) {
			String line = sn.nextLine();
			StringTokenizer strtok = new StringTokenizer(line); //create string tokenizer, slice by space
	
			ArrayList<String> code = new ArrayList<String>();
			while (strtok.hasMoreElements()) {
				code.add(strtok.nextElement().toString());
			}
			
			if(isInst(code.get(0))) {	
				code.add(0, null);;
				/*
				for (int j=code.size()-1; j>0 ;j--) {
					code.set(j, code.get(j-1));
				}
				code.set(0, null);
				*/
			}
			
			if (isRtype(code.get(1)) || isItype(code.get(1))) {
				String tmp = "";
				while (code.size()>5) {
					tmp += code.get(5) + " ";
					code.remove(5);
				}
				code.add(tmp);
			} else if (isJtype(code.get(1))) {
				String tmp = "";
				while (code.size()>4) {
					tmp += code.get(4) + " ";
					code.remove(4);
				}
				code.add(tmp);
				
			} else if (isOtype(code.get(1))) {
				String tmp = "";
				while (code.size()>2) {
					tmp += code.get(2) + " ";
					code.remove(2);
				}
				code.add(tmp);
			} else if (isFill(code.get(1))) {
				String tmp = "";
				while (code.size()>3) {
					tmp += code.get(3) + " ";
					code.remove(3);
				}
				code.add(tmp);
			}
			
			
			
			displayList(code);
					
			System.out.println();		
		}
	}
}
