import java.io.File;

import javax.swing.JOptionPane;
/*
 * main program creating the class information structures and calling the required printers
 */

public class Class2Doc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassInfo dummy;
		ClassPrinter cP;
		String fname,dirname;
		File inFile;
		boolean swap = true;//if types appear before names in the file (for fields and methods only)
		if (args.length > 0) {
			//the argument should be a filename	
			fname = args[0];
		}
		else{
			//ask for a filename
			fname =  JOptionPane.showInputDialog("Enter Name of Input File");	
		}		
		//fname may or may not include a directory
		inFile = new File(fname);
		if ((fname.length() == 0) || inFile.exists()){
			dirname = inFile.getParent();
			fname = inFile.getName();
			inFile = new File(fname);
			if (dirname == null) dirname = System.getProperty("user.dir");

			//todo: cope with null filename or cancel
			//create a structure and read in the info
			dummy = new ClassInfo(fname,dirname,swap);
			//instance a printer and call the required print functions
			cP = new ClassPrinter(dummy);
			cP.printLaTeX();
			cP.printHTML2col();
			if (dummy.needsCSSwritten) cP.printCSS();
		}
		else {
			System.out.println(fname+" not found");
		}

	}

}
