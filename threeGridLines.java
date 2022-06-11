
import java.io.File;
import java.util.Scanner;

import java.util.*;

class YellowRule {
int index;
char charValue;
YellowRule(int index, char charValue) {
this.index = index;
this.charValue = charValue;
}
}

class threeGridLines {


enum GridColors {Gray, Yellow, Green};

static List<String> wordList = new ArrayList<>(

List.of("spook",
"jokes",
"backs",
"spine",
"hoses"));

// Word secretWord = {'jokes'};

// Word firstGuess = 'spine';

// Word secondGuess = 'hoses';

static String guesses[];
static String matched[];
static int nGridLines;
static GridColors gridLines[][];

public static void main (String[] args)throws Exception {
	int gridLen = args.length;
	gridLines = new GridColors[gridLen][5];
	int index = 0;
	if (gridLen < 1)
		annAndStop ("Please specify grid lines as words containing G for green, Y for yellow, and A for gray");
for (String word : args)
	if (5 == word.length())
	gridLines[index++] = stringToGrid(word);
else annAndStop("Each grid needs to be 5 characters.  (We saw %d for word '%s'",
	word.length(), word);
if (!"GGGGG".equals(args[gridLen-1]))
	annAndStop ("Please specify the last grid as five greens (We saw '%s' instead)",
		args[gridLen-1]);
	selfTests();
    File file = new File("C:\\Users\\erico\\folders\\development\\words\\CharlesReidKnuth.txt");
 wordList.clear();
 Scanner sc = new Scanner(file);
 
    while (sc.hasNextLine()) {
		String aLine = sc.nextLine();
		String originalLine = aLine;
		while (aLine.length() > 0) {
			int remainingLength = aLine.length();
			if (5 > remainingLength)
			{
				System.out.println (String.format(
				
		"Uh oh, partial word detected on line in file.  The line says \"%s\" ",
		originalLine));
				aLine = "";
				continue;
		}
			String aWord = aLine.substring(0,5);
			remainingLength -= 5;
			boolean expectSpace = true;
			if (remainingLength < 1)
				expectSpace = false;
			if (expectSpace)
			    aLine = aLine.substring(6);
			else
				aLine = aLine.substring(5);
			wordList.add(aWord);
		}
	}
	System.out.println (String.format("Wordlist has %d words in it." , wordList.size() ));
nGridLines = gridLines.length;
guesses = new String [nGridLines];
matched = new String [nGridLines];
findPossibleSecretWords();
}

static void selfTests() {

String guess = "hoses";
String secret = "jokes";
GridColors [] expectedColorRow = stringToGrid("AGAGG");
GridColors [] gotColorRow = generateGridRow (secret, guess);
String gotColors = gridToString (gotColorRow);
     if (!Arrays.equals(gotColorRow, expectedColorRow))
	 {
		 System.out.printf ("When evaluating guess %s with secret word %s, we should have gotten %s but got %s instead.",
			guess, secret, gridToString(expectedColorRow), gridToString(gotColorRow));
			System.exit(0);
	 }
}

static GridColors[]  stringToGrid (String colors) {
	GridColors[] result = new GridColors[colors.length()];
for (int i=0; i<colors.length(); i++)
	switch (colors.charAt(i)) {
		case 'G': result[i] = GridColors.Green; break;
		case 'Y': result[i] = GridColors.Yellow; break;
		case 'A': result[i] = GridColors.Gray; break;
	}
	return result;
	
}

static String gridToString (GridColors []grid) {
	String result = "";
for (GridColors gridColor : grid)
	switch (gridColor) {
		case Green: result += "G"; break;
		case Yellow: result += "Y"; break;
		case Gray: result +=  "A"; break;
	}
	return result;
}

static void findPossibleSecretWords() {
	for (String secretWord : wordList) {
		guesses[nGridLines-1] = secretWord;
	findValidGuesses (secretWord, nGridLines - 2, new ArrayList<>(), new ArrayList<>());
	}
//System.out.println ("Matched is " + Arrays.toString(matched));
}
		
static String prevSecretWord = "x";
	
static boolean findValidGuesses (String secretWord, int indexToFill,
    List<Character> knownGrays, List<YellowRule> knownYellows) {
    	
		if (0 > indexToFill)
				{
			System.out.println( Arrays.toString( guesses ));
		if (!secretWord.equals(prevSecretWord)) {
//			System.out.println(secretWord);
			prevSecretWord = secretWord;
		}
		return true;}
		for (String nextGuess : wordList)
			if (matchGridRow (secretWord, gridLines[indexToFill], nextGuess, knownGrays
		       , knownYellows)) {
		guesses[indexToFill] = nextGuess;
		int originalGraySize = knownGrays.size();
		int originalYellowSize = knownYellows.size();
		boolean foundStory = findValidGuesses (secretWord, indexToFill-1, knownGrays,
		     knownYellows);
		guesses[indexToFill] = null;
		while (knownGrays.size() > originalGraySize)
			knownGrays.remove(knownGrays.size()-1);
		while (knownYellows.size() > originalYellowSize)
			knownYellows.remove(knownYellows.size()-1);
		if (foundStory)
			return true;
		
		}
		return false;
		}
		

static List<Character> harvestGrays (String guess, GridColors[] gridRow) {
List<Character> result = new ArrayList<>();
String gridString = gridToString (gridRow);
for (int i = 0; i<5; i++)
	if (gridString.charAt(i)=='A')
		result.add(guess.charAt(i));
	return result;
}

static List<YellowRule> harvestYellows (String guess, GridColors[] gridRow) {
List<YellowRule> result = new ArrayList<>();
String gridString = gridToString (gridRow);
for (int i=0; i<5; i++)
	if (gridString.charAt(i)=='Y')
	result.add (new YellowRule (i, guess.charAt(i)));
return result;
}


static boolean charIsInList (char aChar, List<Character> aList) {
for (char charFromList : aList)
	if (aChar == charFromList)
		return true;
return false;
}

static boolean matchGridRow (String secretWord, GridColors [] gridRow, String guess,
	List<Character> knownGrays, List<YellowRule> knownYellows) {
GridColors[]  genGridRow = generateGridRow (secretWord, guess);
//System.out.printf("Comparing %s with %s and result is %b%n", gridToString(genGridRow), gridToString(gridRow), Arrays.equals(genGridRow, gridRow));
if (!Arrays.equals(genGridRow, gridRow)) {

	return false;
}
List<Character> grays = harvestGrays(guess, gridRow);
List<Character>newGrays = new ArrayList<>();
for (char gray : grays)
	if (charIsInList(gray, knownGrays)) {
		return false;
	}
	else newGrays.add(gray);
	
List<YellowRule> yellows = harvestYellows (guess, gridRow);
List<YellowRule> newYellows = new ArrayList<>();
for (YellowRule newYellow : yellows) {
for (YellowRule existingYellow : knownYellows)
	    if (existingYellow.index == newYellow.index && existingYellow.charValue
			== newYellow.charValue)
			return false;
	newYellows.add(newYellow);
}

for (char newGray : newGrays)
	knownGrays.add(newGray);
for (YellowRule newYellow : newYellows)
	knownYellows.add(newYellow);

return true;
}


static String replaceChar (String str, char ch, int index) {
StringBuilder newString = new StringBuilder (str);
newString.setCharAt(index, ch);
return newString.toString();
}

static void ann (String format, Object ... params) {
System.out.println(String.format (format, params));
}

static void annAndStop (String format, Object ... params) {
	ann (format, params);
	System.exit(0);
}

static GridColors[] generateGridRow (String originalSecretWord, String originalGuessWord) {
boolean verbose = false;
	String secretWord = originalSecretWord;
	String guessWord = originalGuessWord;
//if (secretWord.equals("jokes") && guessWord.equals("hoses"))
//	verbose = true;
if (verbose)
	ann( "generateGridRow starting with secret = '%s' and guess = '%s'",
secretWord, guessWord);
//	System.out.printf ("Starting to compare secret word %s and guess word %s%n", secretWord, guessWord);
	GridColors resultGridRow[] = new GridColors[5];
	Arrays.fill (resultGridRow, GridColors.Gray);
	for (int secretRowPosition=0; secretRowPosition<5;secretRowPosition++)
		if (secretWord.charAt(secretRowPosition) == guessWord.charAt(secretRowPosition)) {
			char guessChar = guessWord.charAt(secretRowPosition);
			char secretChar = secretWord.charAt(secretRowPosition);
			resultGridRow[secretRowPosition] = GridColors.Green;
			secretWord = replaceChar (secretWord, '5', secretRowPosition);
			guessWord = replaceChar (guessWord, '9', secretRowPosition);
if (verbose)
ann("We just shaded green because guess[%d]=='%c' and secret[%d]=='%c'.  Updated guess is '%s and updated secret word is '%s'.",
 secretRowPosition, guessChar, secretRowPosition, secretChar, guessWord, secretWord);
		}
	for (int guessRowPosition = 0; guessRowPosition<5; guessRowPosition++)		
	for (int secretRowPosition = 0; secretRowPosition<5; secretRowPosition++)		
		if ((guessRowPosition != secretRowPosition) && (secretWord.charAt(secretRowPosition) == guessWord.charAt(guessRowPosition)))
		{
			char secretChar = secretWord.charAt(secretRowPosition);
			char guessChar = guessWord.charAt(guessRowPosition);
//			System.out.printf("Setting position %d to yellow because secret position %d matches guess position %d",
//			    guessRowPosition, secretRowPosition, guessRowPosition);
			resultGridRow[guessRowPosition] = GridColors.Yellow;
			secretWord = replaceChar (secretWord, '5', secretRowPosition);
			guessWord = replaceChar (guessWord, '9', guessRowPosition);
if (verbose)
ann("We just shaded yellow because guess[%d]=='%c' and secret[%d]=='%c'.  Updated guess is '%s and updated secret word is '%s'.",
 guessRowPosition, guessChar, secretRowPosition, secretChar, guessWord, secretWord);
			break;
		}
//		System.out.printf ("Returning %s%n", gridToString(resultGridRow));
	return resultGridRow;		
}

} // end of main class


