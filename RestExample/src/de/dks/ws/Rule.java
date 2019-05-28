package de.dks.ws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rule{
	private String attrName1;
	String attr1;
	String attrName2;
	String attr2;
	int certainty;
	
	public Rule() {
		
	}
	
	public Rule(String attrName1, String attr1, String attrName2, String attr2, int certainty) {
		this.attrName1 = attrName1;
		this.attr1 = attr1;
		this.attrName2 = attrName2;
		this.attr2 = attr2;
		this.certainty = certainty;
	}
	
	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttrName2() {
		return attrName2;
	}

	public void setAttrName2(String attrName2) {
		this.attrName2 = attrName2;
	}

	public String getAttr2() {
		return attr2;
	}

	public int getCertainty() {
		return certainty;
	}

	public void setCertainty(int certainty) {
		this.certainty = certainty;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttrName1(){
		return attrName1;
	}
	
	public void setAttrName1(String attrName1) {
		this.attrName1 = attrName1;

	}
	
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Rule)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Rule c = (Rule) o; 
          
        // Compare the data members and return accordingly  
        return attr1.equals(c.attr1) 
                && attr2.equals(c.attr2) 
                && attrName1.equals(c.attrName1) 
                && attrName2.equals(c.attrName2) 
                && certainty == c.certainty;
    } 

	@Override
	public String toString() {
		return "Rule [attrName1=" + attrName1 + "]";
	}
	
}
