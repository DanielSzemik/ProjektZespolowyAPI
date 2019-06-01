package de.dks.ws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rule{
	String name;
	String word;
	String predicament;
	int certainty;
	
	public Rule() {
		
	}
	
	public Rule(String predicament, String name, String word, int certainty) {
		this.predicament = predicament;
		this.name = name;
		this.word = word;
		this.certainty = certainty;
	}
	
	public String getPredicament() {
		return predicament;
	}

	public void setPredicament(String predicament) {
		this.predicament = predicament;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCertainty() {
		return certainty;
	}
	
	public void setCertainty(int certainty) {
		this.certainty = certainty;
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
        return word.equals(c.word) 
                && name.equals(c.name) 
                && certainty == c.certainty
                && predicament.equals(c.predicament);
        		
    } 
	@Override
	public String toString() {
		return ("name = " + name + ", word = " + word + ", predicament = " + predicament + ", certainty = " + certainty);
	}
}
