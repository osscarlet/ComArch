import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
	
	public String decToTwoBin(int input) {
		String c2 = Integer.toBinaryString(input);
		return c2.substring(16, 32);
	}
	
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
        String[][] arr = new String[65536][7];
        int count=0;
		while(sn.hasNextLine()) {
			
			String line = sn.nextLine();
			StringTokenizer strtok = new StringTokenizer(line); //create string tokenizer, slice by space
	
			ArrayList<String> code = new ArrayList<String>();
			while (strtok.hasMoreElements()) {
				code.add(strtok.nextElement().toString());
			}
			
			if(isInst(code.get(0))) {	
				code.add(0, null);;
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
			} else { // Undefine case
				code.removeAll(code);
				count--;
			}
			//System.out.println(code.size());
			for (int k=0; k<code.size(); k++)
				arr[count][k]=(code.get(k));
			
			count++;
			
			//displayList(code);
					
			
			
		}
		
	    
		//short res = (short)Integer.parseInt("1000000000000001", 2);
		//System.out.println(res);
		
		
		
		for (int i=0; i<count; i++) {
			System.out.println(i);
			for (int j=0; j<6; j++)
				System.out.print(arr[i][j]+" ");
			System.out.println("\n");
		}
		
		
		for (int i=0; i<count; i++) {
			
		if (isRtype(arr[i][1])) {
			int tmp = Integer.parseInt((String) arr[i][2]);
			String c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][2] = c2.substring(2, 5);
			
			tmp = Integer.parseInt((String) arr[i][3]);
			c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][3] = c2.substring(2, 5);
			
			tmp = Integer.parseInt((String) arr[i][4]);
			c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][4] = c2.substring(2, 5);
				
		} else if (isItype(arr[i][1])) {
			int tmp = Integer.parseInt((String) arr[i][2]);
			String c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][2] = c2.substring(2, 5);
			
			tmp = Integer.parseInt((String) arr[i][3]);
			c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][3] = c2.substring(2, 5);
			
			int j=0;
			for (j=0; j<count; j++) {
				if (arr[j][0]!=null)
					if(arr[i][4].equalsIgnoreCase(arr[j][0]))
						break;
			}
			
			
			if (j==count) {
				tmp = Integer.parseInt((String) arr[i][4]);
				c2 = Integer.toBinaryString(tmp | 0x10);
				arr[i][4] = c2.substring(2, 5);
			} else {
		
				c2 = Integer.toBinaryString(j | 0x10000);
				arr[i][4] = c2.substring(1, 17);
			}
			
		}
		
			
			// change opcode
			if (arr[i][1].equalsIgnoreCase("add"))
				arr[i][1] = "000";
			else if (arr[i][1].equalsIgnoreCase("nand"))
				arr[i][1] = "001";
			else if (arr[i][1].equalsIgnoreCase("lw"))
				arr[i][1] = "010";
			else if (arr[i][1].equalsIgnoreCase("sw"))
				arr[i][1] = "011";
			else if (arr[i][1].equalsIgnoreCase("beq"))
				arr[i][1] = "100";
			else if (arr[i][1].equalsIgnoreCase("jalr"))
				arr[i][1] = "101";
			else if (arr[i][1].equalsIgnoreCase("halt"))
				arr[i][1] = "110";
			else if (arr[i][1].equalsIgnoreCase("noop"))
				arr[i][1] = "111";
		}
		
		
		
		for (int i=0; i<count; i++) {
			System.out.println(i);
			for (int j=0; j<6; j++)
				System.out.print(arr[i][j]+" ");
			System.out.println("\n");
		}
		
	}
	
}
