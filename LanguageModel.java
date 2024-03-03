import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
        String window = "";
        char c;
        In in = new In(fileName);
        for (int i = 0; i < windowLength; i++) {
            window = window + in.readChar();
        }
        
        while (!in.isEmpty()) {
            c = in.readChar();
            List probs = CharDataMap.get(window);
            if (probs == null) {
                probs = new List();
                CharDataMap.put(window, probs);
            }
            probs.update(c);
            window = window.substring(1) + c;
        }
        
        for (List probs : CharDataMap.values()) {
            calculateProbabilities(probs);
        }
	}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) {				
        int sum = 0;
        ListIterator listIterator = probs.listIterator(0);
        while (listIterator.hasNext()) {
            CharData charData = listIterator.next();
            sum = sum + charData.count;
        }
        listIterator = probs.listIterator(0);
        double count = 0;
        while (listIterator.hasNext()) {
            CharData charData = listIterator.next();
            charData.p = ((double) charData.count) / sum;
            count = count + charData.p;
            charData.cp = count;
        }
    
	}

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
        double random = randomGenerator.nextDouble();
        for(int i = 0; i < probs.getSize(); i++){
            if(probs.get(i).cp >= random){
                return probs.get(i).chr;
            }
        }
        return 0;
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
		if (initialText.length() < windowLength) {
            return initialText;
        }

        String window = initialText.substring(initialText.length() - windowLength);
        String generated = initialText;
        List lst = new List();

        while (generated.length() - initialText.length() != textLength) {
            lst = CharDataMap.get(window);
            if (lst == null) {
                return generated;
            }
            else {
                generated = generated + getRandomChar(lst);
                window = generated.substring(generated.length() - windowLength);
            }
        }
        return generated;
	}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		
	}

    public static void main(String[] args) {
       
    }
}
