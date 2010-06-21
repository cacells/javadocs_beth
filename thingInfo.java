import java.util.ArrayList;
/* Classes are a collection of things that have name, type and description.
 * For instance a variable (field) has a name, type, and may be described.
 * A method has a name, a return type, and may also be described.
 * So, thingInfo is a Class to store a collection of three strings called name, type, and description.
*/

public class thingInfo {
	String name;
	String type;
	String descript;

    public thingInfo(){
    	//default constructor sets empty strings
    	name = " ";
    	type = " ";
    	descript = " ";
    }

    public thingInfo(String allinfo){
        //constructor to set the fields from a single input string
        //which holds name, type, and description separated by forward slashes
    	//called directly if reading the info using popup dialogs
    	ArrayList<String> stringBits = new ArrayList<String>();
    	stringBits = breakItUp(allinfo);
    	int len = stringBits.size();
    	if (len > 0) name = stringBits.get(0);
    	else name = "";
    	if (len > 1) type = stringBits.get(1);
    	else type = "";
    	if (len > 2) descript = stringBits.get(2);
    	else descript = "";
    }
    public thingInfo(String allinfo,boolean swapit){
        //constructor to set the fields from a single input string.
    	//The boolean swapit determines whether the fields are in standard format 
    	//( name before type) or in swapped format
    	//(type before name).
    	ArrayList<String> stringBits = new ArrayList<String>();
    	stringBits = breakItUp(allinfo);
    	int len = stringBits.size();
    	//if the name and type are swapped - this is an easier form to generate
    	if (swapit){
    		if (len > 0) type = stringBits.get(0);
    		else type = "";
    		if (len > 1) name = stringBits.get(1);
    		else name = "";
    	}
    	else{
        	if (len > 0) name = stringBits.get(0);
        	else name = "";
        	if (len > 1) type = stringBits.get(1);
        	else type = "";
    	}
    		
    	if (len > 2) descript = stringBits.get(2);
    	else descript = "";
    }
    public thingInfo(String thename,String thetype,String thedescript){
    	//unused constructor
    	name = thename;
    	type = thetype;
    	descript = thedescript;
    }
    public thingInfo(thingInfo orig){
    	//copy constructor
    	name = orig.name;
    	type = orig.type;
    	descript = orig.descript;
    }
    public void cloneVals(thingInfo orig){
    	//copy vals into already existing thingInfo
    	name = orig.name;
    	type = orig.type;
    	descript = orig.descript;
    }
    public void setValsFromString(String allinfo){
    	//sets the fields from an input string
    	ArrayList<String> stringBits = new ArrayList<String>();
    	stringBits = breakItUp(allinfo);
    	int len = stringBits.size();
    	System.out.println("len +"+len);
    	if (len > 0) name = stringBits.get(0);
    	else name = "";
    	if (len > 1) type = stringBits.get(1);
    	else type = "";
    	if (len > 2) descript = stringBits.get(2);
    	else descript = "";
    }

    public void setName(String thename){name = thename;}
    public void setType(String thetype){type = thetype;}
    public void setDescript(String thedescript){descript = thedescript;}
    public String toString(){
    	//for printing
    	String backTogether="";
    	if (name.length() > 0) backTogether = backTogether.concat("Name: "+name);
        if (type.length() > 0) backTogether = backTogether.concat("Type: "+type);
    		//cute: from Pete System.out.println("it is not here wher is stuffes up");
    	if (descript.length() != 0) backTogether = backTogether+"Description: "+descript;
    	return(backTogether);
    }
	public ArrayList<String> breakItUp(String msg){
		//break a string into fields at each slash separator.
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
		//for debug: System.out.println("substrings "+stringBits);
		return stringBits;
	}
}
