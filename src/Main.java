import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
	
	public static boolean isInst(String text) {
		if (text.equals("and") || text.equals("nand") || text.equals("lw") || text.equals("sw") || text.equals("beq") || text.equals("jalr") || text.equals("halt") || text.equals("noop"))
			return true;
		else
			return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		//read from text file
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("\\","/");
		currentDir += "/src/sample.txt";
        File file = new File(currentDir);
        Scanner sn = new Scanner(file);
		int num = 0;
		
		while(sn.hasNextLine()) {
			String line = sn.nextLine();
			num++;
			StringTokenizer strtok = new StringTokenizer(line); //create string tokenizer, slice by space
			int i=0;	
			ArrayList<String> code = new ArrayList<String>();
			while (strtok.hasMoreElements()) {
				code.add((String) strtok.nextElement());
				System.out.print(code.get(i)+" ");
				i++;
			}

			// 0 label
			// 1 inst
			// 2 field0
			// 3 field1
			// 4 field2
			// 5 comment

			int index;
			if(isInst(code.get(0)))
				index=0;
			else
				index=1;
			
			System.out.println();
			System.out.print(code.get(index));
			

			System.out.println();
			
		}
	
		

	}

}
