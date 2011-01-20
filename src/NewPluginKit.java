/*
 * Written by MJMR on 1/19/2011
 * 
 * Replaces <Your Name> and <Plugin Name> in the template files, 
 * creates the file structure for a new plugin project
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.JFrame;

public class NewPluginKit{
	StringBuilder name = null,
						pname = null,
						ver = null;
	StringBuilder path;
	static StringBuilder absPath;
	File yml;
	JFrame frame;
	static boolean GUI = false;
	
	public static void main(String[] args) {
		//Determine if there should be a GUI or not.
			//If not, allows for a path to be specified
		if(args.length != 0)
			if(args[0].equals("-gui")){
				GUI = true;
				absPath = new StringBuilder(getAbsPath());
			}else if(args[0].equals("-path")){
				if(args.length == 2){
					absPath = new StringBuilder(args[1]);
				}else{
					absPath = new StringBuilder(getAbsPath());
				}
			}
			
		new NewPluginKit();
	}
	
	public NewPluginKit(){		
		if(GUI){
			frame = new NPKFrame("New Plugin Kit",this);
		} else {
			consoleInput();
		}
		
	}
	
	public void setValues(String name, String pname, String ver, String absPath){
		this.name = new StringBuilder(name);
		this.pname = new StringBuilder(pname);
		this.ver = new StringBuilder(ver);
		this.absPath = new StringBuilder(absPath);
	}
	
	public void consoleInput(){
		Scanner scn = new Scanner(System.in);
		//Prompt/receive data
		System.out.println("What is your name? ");
		name = new StringBuilder(scn.next());
		System.out.println("What is the Plugin Name? ");
		pname = new StringBuilder(scn.next());
		System.out.println("What is the version? ");
		ver = new StringBuilder(scn.next());
		Copy();
		System.out.println("Finished");
	}
	
	public void Copy() {
		//Create correct path
		path = new StringBuilder(pname + "/" + "com/bukkit/" + name + "/"+pname );
		(new File(absPath + "/" + path)).mkdirs();
	    
	    //Write plugin.yml file
	    yml = new File(absPath + "/" + pname + "/plugin.yml");
		try {
			Writer w = new FileWriter(yml);
			w.write("name: " + pname + " \n");
			w.write("main: " + "com.bukkit." + name + "." + pname + "." + pname + " \n");
			w.write("ver: " + ver);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Create new files while replacing
		createReplaceFile("PN", pname.toString());
		createReplaceFile("PNBlockListener", pname.toString()+"BlockListener");
		createReplaceFile("PNPlayerListener", pname.toString()+"PlayerListener");
		
	}
	
	public static String getAbsPath(){
		File f = new File("");
		return f.getAbsolutePath();
	}
	
	public void createReplaceFile(String oldname, String newname){
		StringBuilder origFileStr = new StringBuilder(), newFileStr = new StringBuilder();
		
		//Define new file
		File newFile = new File(absPath + "/" + path.toString() + "/" + newname + ".java");		
		try {
			//Load the original file text
			origFileStr = readFileAsString("template/" + oldname + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Replace 
		newFileStr = new StringBuilder(origFileStr.toString()
				.replace("<Your Name>", name.toString())
				.replace("<Plugin Name>", pname.toString()));
		
		//Write
		try {
			newFile.createNewFile();
			Writer w = new FileWriter(newFile);
			w.write("" + newFileStr);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//Reads file to string
	private StringBuilder readFileAsString(String filePath) throws java.io.IOException{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(filePath));
	        f.read(buffer);
	    } finally {
	        if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    return new StringBuilder(new String(buffer));
	}
}
