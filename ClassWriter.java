import javax.swing.JOptionPane;


public class ClassWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class2HTML dummy;
		boolean swap = true;//if types appear before names in the file (for fields and methods only)
		String fname =  JOptionPane.showInputDialog("Enter Name of Input File");
		dummy = new Class2HTML(fname,swap);
		ClassPrinter cP = new ClassPrinter(dummy);
		cP.printLaTeX(dummy);
		cP.printHTML2col(dummy);
		if (dummy.needsCSSwritten) cP.printCSS(dummy);

	}

}
