import java.util.ArrayList;


public class thingInfo {
	String name;
	String type;
	String descript;
    public thingInfo(){
    	name = " ";
    	type = " ";
    	descript = " ";
    }
    public thingInfo(String allinfo){
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
    	//copy vals into already existing thinginfo
    	name = orig.name;
    	type = orig.type;
    	descript = orig.descript;
    }
    public void setValsFromString(String allinfo){
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
    public void setFieldValsFromString(String allinfo){
    	ArrayList<String> stringBits = new ArrayList<String>();
    	stringBits = breakItUp(allinfo);
    	int len = stringBits.size();
    	//won't work if there is only one arg
    	System.out.println("len +"+len);
    	if (len > 0) type = stringBits.get(0);
    	else name = "";
    	if (len > 1) name = stringBits.get(1);
    	else type = "";
    	if (len > 2) descript = stringBits.get(2);
    	else descript = "";
    }
    public void setName(String thename){name = thename;}
    public void setType(String thetype){type = thetype;}
    public void setDescript(String thedescript){descript = thedescript;}
    public String toString(){
    	String backTogether="";
    	if (name.length() > 0) backTogether = backTogether.concat("Name: "+name);
        if (type.length() > 0) backTogether = backTogether.concat("Type: "+type);
    		//cute: from Pete System.out.println("it is not here wher is stuffes up");
    	if (descript.length() != 0) backTogether = backTogether+"Description: "+descript;
    	return(backTogether);
    }
	public ArrayList<String> breakItUp(String msg){
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
