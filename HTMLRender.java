import java.util.Scanner;
/**
 *    HTMLRender
 *    This program Renders HTML code into a JFrame window.
 *
 *    The tags supported:
 *        <p>, </p> - Creates a new line before and a blank line after.
 *        Lines are restricted to 80 characters maximum.
 *        <b>, </b> - printed in bold
 *        <i>, </i> - printed in italics
 *        <br>    - creates newline (a.k.a. break)
 *        <hr>    - creates a horizontal rule on the following line.
 *        <pre>, </pre> - for preformatted text
 *
 *    @author     Mayank Singamreddy
 *    @version    13 Nov 2016
 */
public class HTMLRender
{
    // the array which holds the tokens of the HTML
    private String [] tokens;
    private final int SIZE_OF_TOKENS = 100000;
    private int characterCount;

    // SimpleHtmlRenderer fields
    private SimpleHtmlRenderer theRender;
    private HtmlPrinter theBrowser;

    public HTMLRender()
    {
        // Initialize token array
        tokens = new String[SIZE_OF_TOKENS];
        characterCount = 0;

        // Initialize Simple theBrowser
        theRender = new SimpleHtmlRenderer();
        theBrowser = theRender.getHtmlPrinter();
    }
    //main method
    public static void main(String[] args)
    {
        HTMLRender rn = new HTMLRender();//creates instance
        rn.run(args);
    }

  /**
	 *	Opens the HTML file specified on the command line
	 *	then inputs each line and prints out the line and the
	 *	tokens produced by HTMLUtilities.
	 *	@param args		the String array holding the command line arguments
	 */
    private void fillTokens(String[] args)
        {
            Scanner scanner = null;
            String fileName = "";
            if (args.length > 0)
                fileName = args[0];
            else {
                System.out.println("Usage: java HTMLTester <inputFileName>");
                System.exit(0);
            }

            scanner = OpenFile.openToRead(fileName); //reads the file
            int count = 0;
            //while characters are left
            while (scanner.hasNext())
            {
                String[] arr =
                    HTMLUtilities.tokenizeHTMLString(scanner.nextLine());
                for(int i = 0; i < arr.length; i++)
                    tokens[count++] = arr[i];
            }
        }
        /**
        * checks for if character count is over 80
        * then prints the line and resets to 0
        */

        private void checkCharacterCount()
            {
    	       if(characterCount > 80)
    	       {
    		      theBrowser.println();
    		      characterCount = 0;
    	       }
            }
    //run method
    //check for tags
    public void run(String[] args)
    {
        fillTokens(args);
        for(int i = 0; i < tokens.length && tokens[i] != null; i++)
        {
            switch(tokens[i])
            {
                //ignore html body
                case "<html>": case "<body>": case "</body>": case "</html>":
                    break;
                    
                //start a new line and character count = 0
                case "<p>": case "<P>":
                	theBrowser.println();
                	characterCount = 0;
                	break;
                // end of a character is a break
                case "</p>": case "</P>":
                	theBrowser.printBreak();
                	characterCount = 0;
                	break;
                	
                  // print a quotation mark
                case "<q>": case "<Q>":
                	theBrowser.print(" \"");
                	characterCount++; // increment by one
                	checkCharacterCount(); // checks for if 80
                	break;
                	
                case "</q>": case "</Q>": // same as quoation
                	theBrowser.print("\"");
                	characterCount++; //increment the character count
                	checkCharacterCount();
                	break;
                	
                case "<b>": case "<B>"://searches for bold
                    i++;
                    //searches until closing bold tag
                    //or else searches for single punctuation
                    while(!(tokens[i].equalsIgnoreCase("</b>")))
                    {
                    	String str = "";
                      // if character count = if start of a new line
                      // also checks for single punctuation
                      // in this case just a token or else you must add
                      // a space in front of the string
                      if(characterCount == 0 || (tokens[i].length() == 1 &&
                         HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                    		str = tokens[i];
                    	else
                    		str = " " + tokens[i];
                    	theBrowser.printBold(str);//print in italics
                    	characterCount += str.length();//adds this length
                    	checkCharacterCount();//to check for 80
                        i++;//increment i
                    }
                    break;
                    
                case "<i>": case "<I>": //searches for italics
                    i++;
                    //searches until closing italics tag
                    //or else searches for single punctuation
                    while(!(tokens[i].equalsIgnoreCase("</i>")))
                    {
                    	String str = "";
                      // if character count = if start of a new line
                      // also checks for single punctuation
                      // in this case just a token or else you must add
                      // a space in front of the string
                      if(characterCount == 0 || (tokens[i].length() == 1 &&
                         HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                    		str = tokens[i];
                    	else
                    		str = " " + tokens[i];
                    	theBrowser.printItalic(str);//print in italics
                    	characterCount += str.length();//adds this length
                    	checkCharacterCount();//to check for 80
                        i++; //increment i
                    }
                    break;
                    
                    //case h1
                    //case insensitive
                    //increments by i, skips the next tags
                    case "<h1>": case "<H1>":
                        i++;
                        //searches until closing h1 tag
                        while(!(tokens[i].equalsIgnoreCase("</h1>")))
                        {
                            String theStr = "";
                          // if character count = if start of a new line
                          // also checks for single punctuation
                          // in this case just a token or else you must add
                          // a space in front of the string
                          if(characterCount == 0 || (tokens[i].length() == 1 &&
                            HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                        		theStr = tokens[i];
                        	else
                        		theStr = " " + tokens[i];
                        	theBrowser.printHeading1(theStr);//print the heading
                        	characterCount += theStr.length();//adds this length
                                                            //to check for 80
                        	checkCharacterCount();
                            i++; // increment i
                        }
                        theBrowser.printBreak();
                        characterCount = 0;
                        break;
                        
                    //case h2
                    //case insensitive
                    //increments by i, skips the next tags
                    case "<h2>": case "<H2>":
                        i++;
                        //searches until closing h2 tag
                        while(!(tokens[i].equalsIgnoreCase("</h2>")))
                        {
                            String theStr = "";
                          // if character count = if start of a new line
                          // also checks for single punctuation
                          // in this case just a token or else you must add
                          // a space in front of the string
                          if(characterCount == 0 || (tokens[i].length() == 1 &&
                            HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                        		theStr = tokens[i];
                        	else
                        		theStr = " " + tokens[i];
                        	theBrowser.printHeading2(theStr);//print the heading
                        	characterCount += theStr.length();//adds this length
                                                            //to check for 80
                        	checkCharacterCount();
                            i++; // increment i
                        }
                        theBrowser.printBreak();
                        characterCount = 0;
                        break;
                        
                    //case h3
                    //case insensitive
                    //increments by i, skips the next tags
                    case "<h3>": case "<H3>":
                        i++;
                        //searches until closing h3 tag
                        while(!(tokens[i].equalsIgnoreCase("</h3>")))
                        {
                            String theStr = "";
                          // if character count = if start of a new line
                          // also checks for single punctuation
                          // in this case just a token or else you must add
                          // a space in front of the string
                          if(characterCount == 0 || (tokens[i].length() == 1 &&
                            HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                        		theStr = tokens[i];
                        	else
                        		theStr = " " + tokens[i];
                        	theBrowser.printHeading3(theStr);//print the heading
                        	characterCount += theStr.length();//adds this length
                                                            //to check for 80
                        	checkCharacterCount();
                            i++; // increment i
                        }
                        theBrowser.printBreak();
                        characterCount = 0;
                        break;
                        
                    //case h4
                    //case insensitive
                    //increments by i, skips the next tags
                    case "<h4>": case "<H4>":
                        i++;
                        //searches until closing h4 tag
                        while(!(tokens[i].equalsIgnoreCase("</h4>")))
                        {
                            String theStr = "";
                          // if character count = if start of a new line
                          // also checks for single punctuation
                          // in this case just a token or else you must add
                          // a space in front of the string
                          if(characterCount == 0 || (tokens[i].length() == 1 &&
                            HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                        		theStr = tokens[i];
                        	else
                        		theStr = " " + tokens[i];
                        	theBrowser.printHeading4(theStr);//print the heading
                        	characterCount += theStr.length();//adds this length
                                                            //to check for 80
                        	checkCharacterCount();
                            i++; // increment i
                        }
                        theBrowser.printBreak();
                        characterCount = 0;
                        break;
                        
                    //case h5
                    //case insensitive
                    //increments by i, skips the next tags
                    case "<h5>": case "<H5>":
                        i++;
                        //searches until closing h5 tag
                        while(!(tokens[i].equalsIgnoreCase("</h5>")))
                        {
                            String theStr = "";
                          // if character count = if start of a new line
                          // also checks for single punctuation
                          // in this case just a token or else you must add
                          // a space in front of the string
                          if(characterCount == 0 || (tokens[i].length() == 1 &&
                            HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                        		theStr = tokens[i];
                        	else
                        		theStr = " " + tokens[i];
                        	theBrowser.printHeading5(theStr);//print the heading
                        	characterCount += theStr.length();//adds this length
                                                            //to check for 80
                        	checkCharacterCount();
                            i++; // increment i
                        }
                        theBrowser.printBreak();
                        characterCount = 0;
                        break;
                
                //case h6
                //case insensitive
                //increments by i, skips the next tags
                case "<h6>": case "<H6>":
                    i++;
                   
                    //searches until closing h6 tag
                    while(!(tokens[i].equalsIgnoreCase("</h6>")))
                    {
                        String theStr = "";
                      // if character count = if start of a new line
                      // also checks for single punctuation
                      // in this case just a token or else you must add
					  // a space in front of the string
                      if(characterCount == 0 || (tokens[i].length() == 1 &&
							HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                    		theStr = tokens[i];
                    	else
                    		theStr = " " + tokens[i];
                    	theBrowser.printHeading6(theStr);//print the heading
                    	characterCount += theStr.length();//adds this length
                                                        //to check for 80
                    	checkCharacterCount();
                        i++; // increment i
                    }
                    theBrowser.printBreak();
                    characterCount = 0;
                    break;
                  //prints a horizontal rule = line
                case "<hr>": case "<HR>":
                	theBrowser.printHorizontalRule();
                	characterCount = 0;
                    break;
                //creates a new line
                case "<br>": case "<BR>":
                	theBrowser.printBreak();
                	characterCount = 0;
                    break;
                case "<pre>": case "<PRE>":
                    i++;
                    //while preformatted textt, print preformatted text
                    while(!(tokens[i].equalsIgnoreCase("</pre>")))
                    {
                        theBrowser.printPreformattedText(tokens[i]);
                        theBrowser.println();
                        characterCount = 0;
                        i++;
                    }
                    break;
                //default case: if no tags come before the word read
                //then printed as normal
                default:
                 	String str = ""; //creates empty string
                    if(characterCount == 0 || (tokens[i].length() == 1 &&
						HTMLUtilities.isPunctuation(tokens[i].charAt(0))))
                    	str = tokens[i];
                    else
                    	str = " " + tokens[i];
                    theBrowser.print(str);
                    characterCount += str.length();
                    checkCharacterCount();
                    break;
            }
        }
    }
}