import java.util.regex.Matcher;


public class ClassPrinter {
	ClassInfo c;//define a copy because we need to filter
	boolean changedOrig = false;//to see if it has been changed
	public static String dnt = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//indent
	//beware: no default constructor
	public ClassPrinter(ClassInfo orig){
		c = new ClassInfo(orig);
	}
	public void printHTML(ClassInfo classy){
		//reset the copied vals if necessary
		if (changedOrig) {
			c.resetVals(classy);
			changedOrig = false;
		}
		int len;
		String fileName=classy.htmlFilename;
		filterFor("HTML");
		thingInfo con;
		//thingInfo f;
		thingInfo m;//constructor field,method

		try {
			java.io.FileWriter file = new java.io.FileWriter(fileName);
			java.io.BufferedWriter buffer = new java.io.BufferedWriter(file);
			System.out.println(fileName);
			buffer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
			buffer.write("\"http://www.w3.org/TR/html4/loose.dtd\">\n");
			buffer.write("<html>\n");
			buffer.write("<head>\n");
			buffer.write("<title>Class "+classy.classInfo.name+"</title>\n");
			buffer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
			buffer.write("<link href=\""+classy.cssFilename+"\" rel=\"stylesheet\" type=\"text/css\">\n");
			buffer.write("\n");
			buffer.write("</head>\n");
			buffer.write("\n");
			buffer.write("<body>\n");
			buffer.write("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" class=\"tabletype1\" valign=\"top\" >\n");
			buffer.write("  <tr class=\"CHdr\">\n");
			buffer.write("    <td colspan=\"3\">Class</td>\n");
			buffer.write("  </tr>\n");
			buffer.write("  <tr>\n");
			buffer.write("    <td class=\"indent hdr ftr\">"+c.classInfo.name+"</td>\n");
			buffer.write("	<td colspan=\"2\" class=\"hdr ftr\">\n");
			String tmpstr = c.classInfo.descript.replaceAll("<","&lt;");
			buffer.write("	  "+tmpstr+"</td>\n");
			buffer.write("  </tr>\n");
			buffer.write("  <tr class=FHdr>\n");
			buffer.write("    <td>Fields</td>\n");
			buffer.write("    <td>type</td>\n");
			buffer.write("    <td>&nbsp;</td>\n");
			buffer.write("  </tr>\n");
			//write out field info
			//first, the field names
			buffer.write("  <tr>\n");			
			buffer.write("    <td class=\"FList\">\n");
			buffer.write("      <p class=\"indent\">");
			for (thingInfo f : c.fieldInfo){
				buffer.write("    "+f.name+"  <br>\n");
			}
			buffer.write("	</p>   </td>\n");
			//field types
			buffer.write("    <td class=\"midcol\"><p>");
			for (thingInfo f : c.fieldInfo){
			     buffer.write("    "+f.type+"<br>\n");
			}
			buffer.write("</p>  </td>\n");
			//field descriptions
			buffer.write("    <td class=\"col3\"><p>");
			for (thingInfo  f:c.fieldInfo){
			     buffer.write("    "+f.descript+"<br>\n");
			}
			buffer.write("</p></td>\n");
			buffer.write("  </tr>\n");
			//now for the constructors
			buffer.write("  <tr class=\"ConHdr\">\n");
			buffer.write("    <td colspan=\"3\">Constructors</td>\n");
			buffer.write("  </tr>\n");

			//for each constructor
			len = c.constructorInfo.size();
			if (len > 0){
		    	con = c.constructorInfo.get(0);
			    if (len == 1){
			    	//special case: this row is both hdr and ftr
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr ftr\">"+con.name+"</td>\n");
					buffer.write("    <td colspan=\"2\" class=\"hdr\">"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    }
			    else {
			    	//print the first row
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr\">"+con.name+"</td>\n");
					buffer.write("    <td colspan=\"2\" class=\"hdr\">"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    	//print the middle rows
				for (int i=1;i<len-1;i++){
			    	con = c.constructorInfo.get(i);
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent\">"+con.name+"</td>\n");
					buffer.write("    <td colspan=\"2\">"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
				}
		    	//print the last row
				con = c.constructorInfo.get(len-1);
				buffer.write("  <tr>\n");
				buffer.write("    <td class=\"indent ftr\">"+con.name+"</td>\n");
				buffer.write("    <td colspan=\"2\" class=\"ftr\">"+con.descript+" </td>\n");
				buffer.write("  </tr>\n");
				}
			}
			else buffer.write("<tr>&nbsp;</tr>\n");
			//now for the methods
			buffer.write("  <tr class=\"MHdr\">\n");
			buffer.write("    <td>Methods</td>\n");
			buffer.write("    <td>Return Type </td>\n");
			buffer.write("    <td>&nbsp;</td>\n");
			buffer.write("  </tr>\n");
			//for each method
			len = c.methodInfo.size();
			if (len > 0){
		    	m = c.methodInfo.get(0);
			    if (len == 1){
			    	//special case: this row is both hdr and ftr
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr ftr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"hdr\">"+m.type+" </td>\n");
					buffer.write("    <td class=\"hdr\">"+m.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    }
			    else {
			    	//print the first row
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent hdr\">"+m.name+"</td>\n");
			    	buffer.write("    <td class=\"hdr\">"+m.type+" </td>\n");
			    	buffer.write("    <td class=\"hdr\">"+m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    	//print the middle rows
			    	for (int i=1;i<len-2;i++){
			    		m = c.methodInfo.get(i);
			    		buffer.write("  <tr>\n");
			    		buffer.write("    <td class=\"indent hdr\">"+m.name+"</td>\n");
			    		buffer.write("    <td >"+m.type+" </td>\n");
			    		buffer.write("    <td >"+m.descript+" </td>\n");
			    		buffer.write("  </tr>\n");
			    	}
			    	//print the last row if there are at least two rows
			    	m = c.methodInfo.get(len-1);
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent ftr\">"+m.name+"</td>\n");
			    	buffer.write("    <td class=\"ftr\">"+m.type+" </td>\n");
			    	buffer.write("    <td class=\"ftr\">"+m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    }
			}

			
			buffer.write("</table>\n");
			buffer.write("</body>\n");
			buffer.write("</html>\n");
			buffer.write("\n");
			buffer.close();
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
	}

	public void printHTML2col(ClassInfo classy){
		if (changedOrig) {
			changedOrig = false;
			c.resetVals(classy);
		}
		int len;
		String fileName=classy.htmlFilename;
		filterFor("HTML");
		thingInfo con;

		//thingInfo f;
		thingInfo m;//constructor field,method

		try {
			java.io.FileWriter file = new java.io.FileWriter(fileName);
			java.io.BufferedWriter buffer = new java.io.BufferedWriter(file);
			System.out.println(fileName);
			buffer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
			buffer.write("\"http://www.w3.org/TR/html4/loose.dtd\">\n");
			buffer.write("<html>\n");
			buffer.write("<head>\n");
			buffer.write("<title>Class "+classy.classInfo.name+"</title>\n");
			buffer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
			buffer.write("<link href=\""+classy.cssFilename+"\" rel=\"stylesheet\" type=\"text/css\">\n");
			buffer.write("\n");
			buffer.write("</head>\n");
			buffer.write("\n");
			buffer.write("<body>\n");
			buffer.write("<table width=\"100%\"  border=\"0\" cellspacing=\"1\" class=\"tabletype1\" valign=\"top\" >\n");
			buffer.write("  <tr class=\"CHdr\">\n");
			buffer.write("    <td colspan=\"2\">Class</td>\n");
			buffer.write("  </tr>\n");
			buffer.write("  <tr>\n");
			buffer.write("    <td class=\"indent hdr ftr\">"+c.classInfo.name+"</td>\n");
			buffer.write("	<td class=\"hdr ftr\">\n");
			buffer.write("	  "+c.classInfo.descript+"</td>\n");
			buffer.write("  </tr>\n");
			//write out field info
			buffer.write("  </tr>\n");
			buffer.write("  <tr class=FHdr>\n");
			buffer.write("    <td colspan=\"2\">Fields</td>\n");
			buffer.write("  </tr>\n");
			//for each field
			len = c.fieldInfo.size();
			System.out.println(len+" fields");
			if (len > 0){
		    	m = c.fieldInfo.get(0);
			    if (len == 1){
			    	//special case: this row is both hdr and ftr
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr ftr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"hdr\"> <i>Type: "+m.type+" </i><br>"+dnt+
					               m.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    }
			    else {
			    	//print the first row
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent hdr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"hdr\"> <i>Type: "+m.type+" </i><br>"+dnt+
				               m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    	//print the middle rows
			    	for (int i=1;i<len-1;i++){
			    		m = c.fieldInfo.get(i);
			    		buffer.write("  <tr>\n");
			    		buffer.write("    <td class=\"indent\">"+m.name+"</td>\n");
						buffer.write("    <td> <i>Type: "+m.type+" </i><br>"+dnt+
					               m.descript+" </td>\n");
			    		buffer.write("  </tr>\n");
			    	}
			    	//print the last row if there are at least two rows
			    	m = c.fieldInfo.get(len-1);
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent ftr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"ftr\"> <i>Type: "+m.type+" </i><br>"+dnt+
				               m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    }
			}
			buffer.write("  <tr class=\"ConHdr\">\n");
			buffer.write("    <td colspan=\"2\">Constructors</td>\n");
			buffer.write("  </tr>\n");
			//for each constructor
			len = c.constructorInfo.size();
			if (len > 0){
		    	con = c.constructorInfo.get(0);
			    if (len == 1){
			    	//special case: this row is both hdr and ftr
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr ftr\">"+con.name+"</td>\n");
					buffer.write("    <td class=\"hdr\">"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    }
			    else {
			    	//print the first row
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr\">"+con.name+"</td>\n");
					buffer.write("    <td class=\"hdr\">"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    	//print the middle rows
				for (int i=1;i<len-1;i++){
			    	con = c.constructorInfo.get(i);
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent\">"+con.name+"</td>\n");
					buffer.write("    <td >"+con.descript+" </td>\n");
					buffer.write("  </tr>\n");
				}
		    	//print the last row
				con = c.constructorInfo.get(len-1);
				buffer.write("  <tr>\n");
				buffer.write("    <td class=\"indent ftr\">"+con.name+"</td>\n");
				buffer.write("    <td class=\"ftr\">"+con.descript+" </td>\n");
				buffer.write("  </tr>\n");
				}
			}
			else buffer.write("<tr>&nbsp;</tr>\n");
			//now for the methods
			buffer.write("  <tr class=\"MHdr\">\n");
			buffer.write("    <td colspan=\"2\">Methods</td>\n");
			buffer.write("  </tr>\n");
			//for each method
			len = c.methodInfo.size();
			if (len > 0){
		    	m = c.methodInfo.get(0);
		    	if (m.name.length() > 25) m.name = breaklongname(m.name);
			    if (len == 1){
			    	//special case: this row is both hdr and ftr
					buffer.write("  <tr>\n");
					buffer.write("    <td class=\"indent hdr ftr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"hdr\"> <i>Return Type: "+m.type+" </i><br>"+dnt+
					               m.descript+" </td>\n");
					buffer.write("  </tr>\n");
			    }
			    else {
			    	//print the first row
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent hdr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"hdr\"> <i>Return Type: "+m.type+" </i><br>"+dnt+
				               m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    	//print the middle rows
			    	for (int i=1;i<len-1;i++){
			    		m = c.methodInfo.get(i);
				    	if (m.name.length() > 25) m.name = breaklongname(m.name);
			    		buffer.write("  <tr>\n");
			    		buffer.write("    <td class=\"indent\">"+m.name+"</td>\n");
						buffer.write("    <td> <i>Return Type: "+m.type+" </i><br>"+dnt+
					               m.descript+" </td>\n");
			    		buffer.write("  </tr>\n");
			    	}
			    	//print the last row if there are at least two rows
			    	m = c.methodInfo.get(len-1);
			    	if (m.name.length() > 25) m.name = breaklongname(m.name);
			    	buffer.write("  <tr>\n");
			    	buffer.write("    <td class=\"indent ftr\">"+m.name+"</td>\n");
					buffer.write("    <td class=\"ftr\"> <i>Return Type: "+m.type+" </i><br>"+dnt+
				               m.descript+" </td>\n");
			    	buffer.write("  </tr>\n");
			    }
			}

			
			buffer.write("</table>\n");
			buffer.write("</body>\n");
			buffer.write("</html>\n");
			buffer.write("\n");
			buffer.close();
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public void printCSS(ClassInfo classy){
        String fileName = classy.baseDirname+"/outdocshtml/"+classy.cssFilename;
		try {
			java.io.FileWriter file = new java.io.FileWriter(fileName);
			java.io.BufferedWriter buffer = new java.io.BufferedWriter(file);
			System.out.println(fileName);
			buffer.write(".CHdr {\n");
			buffer.write("	background-color: #B4A595;\n");
			buffer.write("}\n");
			buffer.write(".FHdr {\n");
			buffer.write("	background-color: #5CA3BE;\n");
			buffer.write("}\n");
			buffer.write(".ConHdr {\n");
			buffer.write("	background-color: #B5CAD5;\n");
			buffer.write("}\n");
			buffer.write(".MHdr {\n");
			buffer.write("	background-color: #D8EAF3;\n");
			buffer.write("}\n");
			buffer.write("td {\n");
			buffer.write("vertical-align:top\n");
			buffer.write("}\n");
			buffer.write("td.FList {\n");
			buffer.write("width:40%\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.ConList {\n");
			buffer.write("width:40%;\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.MList {\n");
			buffer.write("width:40%;\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.midcol{\n");
			buffer.write("width:20%;\n");
			buffer.write("}\n");
			buffer.write("td.col3 {\n");
			buffer.write("width:40%\n");
			buffer.write("}\n");
			buffer.write(".indent {\n");
			buffer.write("padding-left: 20px;\n");
			buffer.write("font-weight:bold;\n");
			buffer.write("}\n");
			buffer.write("\n");
			buffer.write("td.hdr {\n");
			buffer.write("	padding-top: 10px;\n");
			buffer.write("}\n");
			buffer.write("td.ftr {\n");
			buffer.write("	padding-bottom: 10px;\n");
			buffer.write("}\n");
			buffer.write("\n");


			buffer.close();
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
	}

	public void printLaTeX(ClassInfo classy){
		if (changedOrig) {
			changedOrig = false;
			c.resetVals(classy);
		}
		String fileName=classy.latexFilename;
		filterFor("Latex");
		try {
			java.io.FileWriter file = new java.io.FileWriter(fileName);
			java.io.BufferedWriter buffer = new java.io.BufferedWriter(file);
			System.out.println(fileName);

			buffer.write("\\documentclass[11pt,a4paper]{article}\n");
			buffer.write("\n");
			buffer.write("\\usepackage{longtable}\n");
			buffer.write("\\newcommand \\bt{\\begin{longtable}{p{0.25\\textwidth}p{0.74\\textwidth}}}\n");
			buffer.write("\\newcommand \\et{\\end{longtable}}\n");
			buffer.write(" \n");
			buffer.write("\\usepackage[pdftex,usenames,dvipsnames]{color}\n");
			buffer.write("\n");
			buffer.write("\\definecolor{classbg}{rgb}{0.707,0.648,0.586}\n");
			buffer.write("\\definecolor{fieldbg}{rgb}{0.363,0.641,0.746}\n");
			buffer.write("\\definecolor{conbg}{rgb}{0.711,0.793,0.836}\n");
			buffer.write("\\definecolor{descriptbg}{rgb}{0.848,0.918,0.953}\n");
			buffer.write("\n");
			buffer.write("\\usepackage[T1]{fontenc}\n");
			buffer.write("\\renewcommand*\\familydefault{\\sfdefault}\n");
			buffer.write("\n");
			buffer.write("\\newcommand{\\hs}{\\hspace{0.5cm}}\n");
			buffer.write("\n");
			buffer.write("%environment for indented description\n");
			buffer.write("\\newenvironment{di}\n");
			buffer.write("{\\begin{flushright}\n");
			buffer.write("\\begin{minipage}{0.95\\textwidth}\n");
			buffer.write("\\begin{description}\n");
			buffer.write("}\n");
			buffer.write("{\\end{description}\n");
			buffer.write("\\end{minipage}\n");
			buffer.write("\\end{flushright}\n");
			buffer.write("}\n");
			buffer.write("\n");
			buffer.write("\\usepackage[hmargin=2.5cm,vmargin=2.5cm]{geometry}\n");
			buffer.write("\\setlength{\\parindent}{0.05\\textwidth}\n");
			buffer.write("\n");
			buffer.write("\\begin{document}\n");
			buffer.write("\n");
			buffer.write("\\noindent\n");
			buffer.write("\\colorbox{classbg}{\\parbox{1.0\\textwidth}{\\Large{Class}}}\n");
			//now we get specific
			//class name and description
			buffer.write("\\begin{di}\n");
			buffer.write("\\item[\\large{"+c.classInfo.name+"}]\\qquad\\\\\n");
			buffer.write(c.classInfo.descript+"\n");	
			buffer.write("\\end{di}\n");
			//field header then fields
			buffer.write("\\colorbox{fieldbg}{\\parbox{1.0\\textwidth}{\\Large{Fields}}}");
	        if (!c.fieldInfo.isEmpty()){	        	
			buffer.write("\\vspace{0.5cm}\n\\bt\n");
			for (thingInfo f:c.fieldInfo){
			     buffer.write("\\hs \\textbf{"+f.name+"} & \\emph{type: "+f.type+"}\\\\\n");
			 			buffer.write("& \\hs "+f.descript+"\\\\\n");
			}
			buffer.write("\\et\n");
	        }
			//constructors
			buffer.write("\\noindent\\colorbox{conbg}{\\parbox{1.0\\textwidth}{\\Large{Constructors}}}\n");		
			if (!c.constructorInfo.isEmpty()){
			buffer.write("\\begin{di}\n");
			for (thingInfo f:c.constructorInfo){
				buffer.write("\\item[{"+f.name+"}]\\qquad\\\\\n");
				buffer.write(f.descript+"\n");		
			}
			buffer.write("\\end{di}\n");
			}
			//methods
			buffer.write("\\colorbox{descriptbg}{\\parbox{1.0\\textwidth}{\\Large{Methods}}}\n");
			if (!c.methodInfo.isEmpty()){
			buffer.write("\\begin{di}\n");
			for (thingInfo f:c.methodInfo){
				buffer.write("\\item[{"+f.name+"}]\\emph{Returns "+f.type+"}\\\\\n");
		 			buffer.write(f.descript+"\\\\\n");		
			}
			buffer.write("\\end{di}\n");
			}

			buffer.write("\n");
			buffer.write("\\end{document}\n");

			
			buffer.close();
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
	}
	public void printCSS2col(ClassInfo classy){
        String fileName = classy.baseDirname+"/outdocshtml/"+classy.cssFilename;
		try {
			java.io.FileWriter file = new java.io.FileWriter(fileName);
			java.io.BufferedWriter buffer = new java.io.BufferedWriter(file);
			System.out.println(fileName);
			buffer.write(".CHdr {\n");
			buffer.write("	background-color: #B4A595;\n");
			buffer.write("}\n");
			buffer.write(".FHdr {\n");
			buffer.write("	background-color: #5CA3BE;\n");
			buffer.write("}\n");
			buffer.write(".ConHdr {\n");
			buffer.write("	background-color: #B5CAD5;\n");
			buffer.write("}\n");
			buffer.write(".MHdr {\n");
			buffer.write("	background-color: #D8EAF3;\n");
			buffer.write("}\n");
			buffer.write("td {\n");
			buffer.write("vertical-align:top\n");
			buffer.write("}\n");
			buffer.write("td.FList {\n");
			buffer.write("width:40%\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.ConList {\n");
			buffer.write("width:40%;\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.MList {\n");
			buffer.write("width:40%;\n");
			buffer.write("vertical-align:top;\n");
			buffer.write("}\n");
			buffer.write("td.midcol{\n");
			buffer.write("width:20%;\n");
			buffer.write("}\n");
			buffer.write("td.col3 {\n");
			buffer.write("width:40%\n");
			buffer.write("}\n");
			buffer.write(".indent {\n");
			buffer.write("padding-left: 20px;\n");
			buffer.write("}\n");
			buffer.write("\n");
			buffer.write("td.hdr {\n");
			buffer.write("	padding-top: 10px;\n");
			buffer.write("}\n");
			buffer.write("td.ftr {\n");
			buffer.write("	padding-bottom: 10px;\n");
			buffer.write("}\n");
			buffer.write("\n");
			buffer.close();
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
	}

    public String breaklongname(String oldstr){
    	String newstr = oldstr;
    	//find the ( character, and if there, break before
    	int i = oldstr.indexOf('(');
    	if (i > 0) newstr = oldstr.substring(0, i)+"<br>"+dnt+oldstr.substring(i);
    	return (newstr);
    }
    public void filterFor(String App){
		int Appval=50;
		if (App == "Latex") {

			Appval = 0;
		}
		if (App == "HTML"){

			Appval = 1;
		}
		switch (Appval){
		case (0)://Latex
			filterForLaTex(c.classInfo);
			for (thingInfo t:c.fieldInfo) {filterForLaTex(t);}
			for (thingInfo t:c.constructorInfo) {filterForLaTex(t);}
			for (thingInfo t:c.methodInfo) {filterForLaTex(t);}
			System.out.println("LaTex filtering");
		break;
		case (1)://HTML
			filterForHTML(c.classInfo);
			for (thingInfo t:c.fieldInfo) {filterForHTML(t);}
			for (thingInfo t:c.constructorInfo) {filterForHTML(t);}
			for (thingInfo t:c.methodInfo) {filterForHTML(t);}
			System.out.println("HTML filtering");
		break;
		default:
			break;
		}
	}
	public void filterForLaTex(thingInfo t){
		String[] toFind = {"<",">","_"};
		String[] replacement = {"\\$<\\$","\\$>\\$","\\\\_"};
		//crazy backslashing is necessary
		String r;
		changedOrig = true;
		for (int i=0;i<toFind.length;i++){
			r = replacement[i];
			t.name = t.name.replaceAll(toFind[i],r).trim();
			t.type = t.type.replaceAll(toFind[i],r).trim();
			t.descript = t.descript.replaceAll(toFind[i],r).trim();
		}
	}
	public void filterForHTML(thingInfo t){
		String[] toFind = {"<",">"};
		String[] replacement = {"&lt;","&gt;"};
		changedOrig = true;
		for (int i=0;i<toFind.length;i++){
			t.name = t.name.replaceAll(toFind[i],replacement[i]);
			t.type = t.type.replaceAll(toFind[i],replacement[i]);
			t.descript = t.descript.replaceAll(toFind[i],replacement[i]);
		}
	}

}
