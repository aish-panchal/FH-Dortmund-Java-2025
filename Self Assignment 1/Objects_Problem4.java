public class Objects_Problem4{
    static String filterWord(String word){
	// return a string with only letters
	String out = "";
	for (char character : word.toCharArray()){
	    if(Character.isLetter(character)){
		out += Character.toString(character);
	    }
	}
	return out;
    }
    public static void main(String[] args){
	// words are any series of characters seperated by a space
	// and only contain alphabetical characters

	String text = "To be or not to be, that is the question; "
	    +"Whether `tis nobler in the mind to suffer"
	    +" the slings and arrows of outrageous fortune,"
	    +" or to take arms against a sea of troubles,"
	    +" and by opposing end them?";

	// split string on spaces
	String[] words = text.split(" ");
	// filter the string for only letters
	for(int i = 0; i < words.length; i++){
	    words[i] = filterWord(words[i]);
	}
	// sort the cleaned words using bubble sort
	boolean sorted = false;
	while(!sorted){
	    sorted = true;
	    for(int i = 0; i < words.length - 1; i++){
		int j = i+1;
		// we ignore upper case and just sort the characters as lower case
		if(Character.toLowerCase(words[i].charAt(0)) > Character.toLowerCase(words[j].charAt(0))){
		    String temp = words[i];
		    words[i] = words[j];
		    words[j] = temp;
		    sorted = false;
		}
	    }
	}
	for (String word : words){
	    System.out.println(word);
	}
    }
}

