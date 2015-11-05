package file;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Browser {
	public static final String[] RAW_EXTENSIONS = {".data", ".3fr", ".ari", ".arw", ".bay", ".crw", ".cr2", ".cap", ".dcs", ".dcr", ".dng", ".drf",
			".eip", ".erf", ".fff", ".iiq",	".k25", ".kdc", ".mdc", ".mef", ".mos", ".mrw",	".nef", ".nrw", ".obm", ".orf", ".pef", ".ptx", 
			".pxn", ".r3d", ".raf", ".raw", ".rwl", ".rw2", ".rwz", ".sr2", ".srf", ".srw", ".tif", ".x3f"};
	public static final String ROOT_FOLDER = "C:\\Users\\batal\\workspace\\Projet Algo - Compression raw\\resources\\pics";

		
	/**
	 * A directories browser like
	 * Display the content of the specified defaultDir directory
	 * Wait for the user enter one of its subdirectory's name or file's name with the keyboard
	 * When a correct file's name is entered, its absolute path is returned
	 * 
	 * Note : A complete absolute or relative path can be entered with the keyboard
	 * @param defaultDir : The directory where to start browser
	 * @return The absolute path of the file chosen by the user
	 */
	public static String askFile(String defaultDir){
		Scanner sc = new Scanner(System.in);
		String filePath;
		if(defaultDir==null)
			filePath = ROOT_FOLDER;
		else
			filePath = defaultDir;
		
		File file = new File(filePath);
		
		
		try{
			displayDir(file);
			System.out.print("File's path : ");
			
			do
			{
				String in = sc.next();

				
				if(in.startsWith("C:\\")){ //absolute path
					filePath = in;
				}else{ //relative path
					/* Add a backslash if required */
					if(!filePath.endsWith("\\"))
						if(!in.startsWith("\\"))
							filePath += "\\";
						
					filePath += in;
				}
				
				file = new File(filePath);
				if(file.isDirectory()){ //pathFile points to a directory
					displayDir(file);
					
					System.out.print("\nSelect : ");
					in = sc.next();
				}else{//pathFile points to a file
					if(!file.exists()){
						System.out.print("Error - The file " + filePath + " does not exist.");
						filePath = parentDir(filePath);
						file = new File(filePath);
					}
					else if(!contains(RAW_EXTENSIONS, getExtension(file.getName()))){
						System.out.print("Error - The file " + filePath + " has not a supported format.");
						filePath = parentDir(filePath);
						file = new File(filePath);
					}
				}
			}while(file.isDirectory() || !file.exists() || !contains(RAW_EXTENSIONS, getExtension(file.getName())));
			
			
			System.out.println("The following file will be compressed : "+file.getName());
			return file.getParentFile().getAbsolutePath();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			sc.close();
		}
		return null;
	}
	

	/**
	 * Display the content of the specified list
	 * @param bytes
	 */
	public static void display(List<Integer> bytes){
		for(Integer i : bytes){
			System.out.println(i);
			
		}
	}
	
	/**
	 * Indicates if the specified array contains the specified value
	 * @param arr
	 * @param value
	 * @return True if arr contains value ; false otherwise
	 */
	public static boolean contains(String[] arr, String value){
		for(String key : arr){
			if(key.compareTo(value) == 0){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Return the extension of the specified file's name
	 * @param filePath
	 * @return
	 */
	public static String getExtension(String filePath){
		int lastDot = filePath.lastIndexOf('.');
		if(lastDot == -1)
			return "";
		
		return filePath.substring(lastDot, filePath.length());
	}
	
	/**
	 * Removes the last folder from a file path, to get its parent directory path
	 * @param filePath
	 */
	public static String parentDir(String filePath){
		int lastBackSlash = filePath.lastIndexOf('\\');
		
		if(lastBackSlash == -1)
			return ROOT_FOLDER;
		
		return filePath.substring(0, lastBackSlash);
	}
	
	
	/**
	 * Display the specified directory
	 * @param dirPath
	 * @throws Exception 
	 */
	public static void displayDir(File dir) throws Exception{
		if(!dir.isDirectory())
			throw new Exception("The specified File is not a directory");
		
		System.out.println(dir.getName());
		
		int i=0;
		for(File nom : dir.listFiles()){
			System.out.print( ((nom.isDirectory()) ? nom.getName()+"/" : nom.getName())+"\t\t");	
			
			if(i%4==0)
				System.out.print("\n");
			
			i++;
		}
	}
}
