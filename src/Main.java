import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		//read from text file
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("\\","/");
		currentDir += "/src/sample.txt";
        File file = new File(currentDir);
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
