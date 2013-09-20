import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;


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
	

	public static void main(String[] args) throws IOException {
		//read from text file
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("\\","/");
		currentDir += "/src/sample.txt";
        File file = new File(currentDir);
        Scanner sn = new Scanner(file);
        String[][] arr = new String[65536][7];
        int count=0;
        int con = 0;
        
        ArrayList<String> finish = new ArrayList<String>();
        ArrayList<String> label = new ArrayList<String>();
		while(sn.hasNextLine()) {
			
			
			String line = sn.nextLine();
			StringTokenizer strtok = new StringTokenizer(line); //create string tokenizer, slice by space
	
			ArrayList<String> code = new ArrayList<String>();
			while (strtok.hasMoreElements()) {
				code.add(strtok.nextElement().toString());
			}
			
			
			if(isInst(code.get(0))) {	
				code.add(0, null);
			}
			
			if(code.get(0)!=null) {
				if(label.contains(code.get(0))) {
					System.out.println("Found duplicate label");
					System.exit(1);
				} else 
					label.add(code.get(0));
				
				
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
				System.out.println("Undefine Opcode");
				System.exit(1);
			}
			//System.out.println(code.size());
			for (int k=0; k<code.size(); k++)
				arr[count][k]=(code.get(k));
			
			count++;
			
			//displayList(code);
					
			
			
		}
		
		
		
	    
		//short res = (short)Integer.parseInt("1000000000000001", 2);
		//System.out.println(res);
		
		
		
		/*for (int i=0; i<count; i++) {
			System.out.println(i);
			for (int j=0; j<6; j++)
				System.out.print(arr[i][j]+" ");
			System.out.println("\n");
		}*/
		
		ArrayList<String> output = new ArrayList<String>();
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
				if (arr[i][4].matches("\\d+")) {
				tmp = Integer.parseInt((String) arr[i][4]);
				c2 = Integer.toBinaryString(tmp | 0x10000);
				arr[i][4] = c2.substring(1, 17);
				}
				else {
					System.out.println("Found undefined label");
					System.exit(1);
				}
			} else {
				if (j>32767 && j<-32768) {
					System.out.println("Offset overflow");
					System.exit(1);
				}
					
				c2 = Integer.toBinaryString(j | 0x10000);
				arr[i][4] = c2.substring(1, 17);
			}
			
		} else if (isJtype(arr[i][1])) {
			int tmp = Integer.parseInt((String) arr[i][2]);
			String c2 = Integer.toBinaryString(tmp | 0x10);
			arr[i][2] = c2.substring(2, 5);
			
			c2 = Integer.toBinaryString(i+1 | 0x10);
			arr[i][3] = c2.substring(2, 5);
		} else if (isFill(arr[i][1])) {
			int j=0;
			for (j=0; j<count; j++) {
				if (arr[j][0]!=null)
					if(arr[i][2].equalsIgnoreCase(arr[j][0]))
						break;
			}
			
			int tmp;
			String c2;
			if (j==count) {
				tmp = Integer.parseInt((String) arr[i][2]);
				c2 = Integer.toBinaryString(tmp | 0x10000);
				arr[i][2] = c2.substring(1, 17);
			} else {
				c2 = Integer.toBinaryString(j | 0x10000);
				arr[i][2] = c2.substring(1, 17);
			}
		}
		
			
			// change opcode
		
		String txt = "null";
			if (arr[i][1].equalsIgnoreCase("add")){
				arr[i][1] = "000";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + "0000000000000" + arr[i][4];
				txt = String.valueOf(Integer.parseInt(txt, 2));
				
			}
			else if (arr[i][1].equalsIgnoreCase("nand")){
				arr[i][1] = "001";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + "0000000000000" + arr[i][4];
				txt = String.valueOf(Integer.parseInt(txt, 2));
				

			}
			else if (arr[i][1].equalsIgnoreCase("lw")){
				arr[i][1] = "010";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + arr[i][4];
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
			}
			else if (arr[i][1].equalsIgnoreCase("sw")){
				arr[i][1] = "011";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + arr[i][4];
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
			}
			else if (arr[i][1].equalsIgnoreCase("beq")){
				arr[i][1] = "100";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + arr[i][4];
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
			}
			else if (arr[i][1].equalsIgnoreCase("jalr")){
				arr[i][1] = "101";
				txt = arr[i][1] + arr[i][2] + arr[i][3] + "0000000000000000";
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
			}
			else if (arr[i][1].equalsIgnoreCase("halt")){
				arr[i][1] = "110";
				txt = arr[i][1] + "0000000000000000000000";
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
				
			}
			else if (arr[i][1].equalsIgnoreCase("noop")){
				arr[i][1] = "111";
				txt = arr[i][1] + "0000000000000000000000";
				txt = String.valueOf(Integer.parseInt(txt, 2));
				con++;
			}
			else if (arr[i][1].equalsIgnoreCase(".fill")){
				txt = arr[i][2];
				txt = String.valueOf((short)Integer.parseInt(txt, 2));
			}
			output.add(txt);
			
		}
		
		
		PrintWriter out = new PrintWriter(new FileWriter(System.getProperty("user.dir")+"\\src\\output.txt")); 
		for (int i=0; i<output.size(); i++)
		out.println(output.get(i));
		out.close();
		
		System.out.println(System.getProperty("user.dir")+"\\output.txt");
	}
	
}
