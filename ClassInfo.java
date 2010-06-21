
/*
 * Creates a structure to hold info describing a class, and reads the info from 
 * interactive popups or from a file.
 */
	import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
	import java.io.IOException;
import java.io.InputStreamReader;
	import java.text.DecimalFormat;
import java.util.ArrayList;
	import java.util.Scanner;

import javax.swing.JOptionPane;

	public class ClassInfo{
		thingInfo classInfo = new thingInfo();
		ArrayList<thingInfo> fieldInfo = new ArrayList<thingInfo>();
		ArrayList<thingInfo> constructorInfo = new ArrayList<thingInfo>();
		ArrayList<thingInfo> methodInfo = new ArrayList<thingInfo>();
		String cssFilename = "class_style_1.css";
		String baseFilename = "";
		String baseDirname = ".";
		String htmlFilename = "untitled.html";
		String latexFilename = "untitled.tex";
		boolean needsCSSwritten = true;
		boolean swapit = false;
		
		//constructor that reads input from a file or from popups		
		public ClassInfo(String fname,String dirname,boolean swap){
			//no filename entered - use popup dialogs to get info
	  		baseDirname = dirname;
	  		if (baseDirname == null) baseDirname = ".";
			if (fname.length() == 0) {

				ArrayList<String> wasreadIn = new ArrayList<String>();
				classInfo.setName(JOptionPane.showInputDialog("Enter Class Name"));

				
				classInfo.setDescript(JOptionPane.showInputDialog("Enter Class Description"));
				System.out.println("Class Name: "+classInfo);
				wasreadIn = SetParamVals("Enter Field Name/Type/Description",20);			
				for (String f: wasreadIn){
					fieldInfo.add(new thingInfo(f));
				}
				
				wasreadIn = SetParamVals("Enter Constructor Name/Argument Type/Description",20);			
				for (String f: wasreadIn){
					constructorInfo.add(new thingInfo(f));
				}
				
				wasreadIn = SetParamVals("Enter Method Name/Return Type/Description",20);			
				for (String f: wasreadIn){
					methodInfo.add(new thingInfo(f));
				}
				
				
			}
			else{
				//read from file
			swapit = swap;
			try{
		  		FileInputStream fstream = new FileInputStream(baseDirname+"/"+fname);

		  		// Get the object of DataInputStream
		  		DataInputStream in = new DataInputStream(fstream);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  		String strLine;
	  			while ((strLine= br.readLine()) != null){
	  				if (strLine.contains("%class")){
	  		  			if (((strLine = br.readLine()) != null) && !(strLine.contains("%"))){
			  				classInfo.setValsFromString(strLine);
	  		  			}
	  		  		}	  				
	  				if (strLine.contains("%field")){
	  		  			while (((strLine = br.readLine()) != null) && !(strLine.contains("%"))){
	  		  			   if(strLine.length()>0) fieldInfo.add(new thingInfo(strLine,swapit));
	  		  			}
	  				}
	  				if (strLine.contains("%cons")){
	  		  			while (((strLine = br.readLine()) != null) && !(strLine.contains("%"))){
	  		  			if(strLine.length()>0) constructorInfo.add(new thingInfo(strLine));
		  		  			}
	  				}
	  				if (strLine.contains("%meth")){
	  		  			while (((strLine = br.readLine()) != null) && !(strLine.contains("%"))){
	  		  			if(strLine.length()>0) methodInfo.add(new thingInfo(strLine,swapit));
		  		  			}
	  				}
	  			}



			}
			catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			}//end of if for file input
			System.out.println("bas dir name: "+baseDirname);
			makeFilenames();
		}

		public ClassInfo(ClassInfo orig){
			//copy constructor - but only copies strings
			//used to make copies that can be changed while saving the orig
			//first copy classinfo thinginfo
			classInfo.cloneVals(orig.classInfo);
			//add a copy of each field of orig
			for (thingInfo t:orig.fieldInfo) {
				fieldInfo.add(new thingInfo(t));
			}
			for (thingInfo t:orig.constructorInfo) {
				constructorInfo.add(new thingInfo(t));
			}
			for (thingInfo t:orig.methodInfo) {
				methodInfo.add(new thingInfo(t));
			}
		}
		
		public void makeFilenames(){
			baseFilename=classInfo.name;
			/* this is for a variable cssFilename
			cssFilename = JOptionPane.showInputDialog("Enter Name of existing CSS file or press return");		
			if (cssFilename.length() == 0 ) {
				needsCSSwritten=true;
				cssFilename = classInfo.name+".css";
			}*/
			 //assume we have a baseDirname
			//make the htmldir
			File htmldir = new File(baseDirname+"/outdocshtml");
  	        System.out.println("Directory: " + htmldir + " created");
    	    boolean success = htmldir.mkdir();
    	    File cssFile = new File (htmldir+"/"+cssFilename);
    	    if (cssFile.exists()){
    	    	needsCSSwritten=false;
    	    }
    	    //if (success) {

    	    //} 
			//make the latexdir
			File latexdir = new File(baseDirname+"/outdocslatex");
    	    success = latexdir.mkdir();
    	    //if (success) {
    	      System.out.println("Directory: " + latexdir + " created");
    	    //} 
    	    //set the html file name
    	    htmlFilename = baseDirname+"/outdocshtml/"+baseFilename+".html";
    	    //set the latex file name
    	    latexFilename = baseDirname+"/outdocslatex/"+baseFilename+".tex";
    	    
			
		}
		public void resetVals(ClassInfo orig){
			//reset the strings to those from orig
			//used if the copy was changed for latex or html
			//first copy classinfo

			classInfo.cloneVals(orig.classInfo);

			//clear the arrays
			fieldInfo.clear();
			constructorInfo.clear();
			methodInfo.clear();
			//copy of each array of orig
			for (thingInfo t:orig.fieldInfo) {
				fieldInfo.add(new thingInfo(t));
			}
			for (thingInfo t:orig.constructorInfo) {
				constructorInfo.add(new thingInfo(t));
			}
			for (thingInfo t:orig.methodInfo) {
				methodInfo.add(new thingInfo(t));
			}
		}
		
		
		public ArrayList<String> SetParamVals(String msg,int maxit){
			//todo: remove maxit
			ArrayList<String> returnStrings = new ArrayList<String>();
			String firstNumber;
			int len=10,count = 0;
			while ((count < maxit) && (len>0)) {
				firstNumber = JOptionPane.showInputDialog(msg);
				len = firstNumber.length();
				returnStrings.add(count, firstNumber);
				count++;
			}
			//remove the last one if it was empty
			int numberofFields = returnStrings.size();
			if (returnStrings.get(numberofFields-1).length() < 1) {
				returnStrings.remove(numberofFields-1);			
			    numberofFields = returnStrings.size();
			}
				return returnStrings;

		}
		
		public ArrayList<String> breakItUp(String msg){
			//break up strings entered from popups 
			//which use forward slash as an info separator
			ArrayList<String> stringBits = new ArrayList<String>();
			int fi = 0,li;
			int len = msg.length();
			int count = 0;
			if (len > 0){
				li = msg.indexOf('/');
			while (li > -1){
				count++;
				stringBits.add(msg.substring(fi,li));
				fi = li+1;
				li = msg.indexOf('/',fi);
			}
			if (fi< len) stringBits.add(msg.substring(fi,len));
			}
			System.out.println("substrings "+stringBits);
			return stringBits;
		}
		

		


}
