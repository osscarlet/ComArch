import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String filename = "C:/Users/Admin/workspace/ComArch/src/sample.txt";
        File file = new File(filename);
        Scanner sn = new Scanner(file);
		int num = 0;
		int i=0;
		while(sn.hasNextLine()) {
			String line = sn.nextLine();
			num++;
			StringTokenizer strtok = new StringTokenizer(line); //create string tokenizer, slice by space
			
			while (strtok.hasMoreElements()) {
				System.out.print(strtok.nextElement()+" ");
			}
			System.out.println();
		}
	
		System.out.println("Total line is: " + (num));

	}

}
