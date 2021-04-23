import java.awt.*;
import java.util.*;
public class PrettyDrawer {

    /*
        whether the maximum width is in pixels are characters, the logic is the same
        cases:
            Width is 5 chars long 
            The words are:
                Hello

                HelloHi

                Hello Hi

                Hello Hi Hi

                Hello Hi HelloHi

    */
    public static ArrayList<String> cutIntoLines(FontMetrics fm, String text, int width, int height){
        ArrayList<String> linesToDraw = new ArrayList<String>();
        String[] lines = text.trim().split("\n");
        for(String line: lines){
            StringTokenizer st = new StringTokenizer(line);
            int wordsUsed = 0;
            String currLine = "";
            boolean nextWord = true;
            String word = null;
            while(st.hasMoreTokens() || word.length() > 0){
                if(nextWord)
                    word = st.nextToken();
                if(fm.stringWidth(currLine + word) > width){
                    if(currLine.length() == 0){
                        int longestSubstringLength = fm.charWidth('-');
                        int numChars = 0;
                        for(int i = 0;i<word.length();i++){
                            if(longestSubstringLength + fm.charWidth(word.charAt(i)) > width){
                                break;
                            }
                            numChars++;
                            longestSubstringLength += fm.charWidth(word.charAt(i));
                        }
                        linesToDraw.add(word.substring(0,numChars)+'-');
                        word = word.substring(numChars);
                        currLine = "";
                    }
                    else{
                        linesToDraw.add(currLine.trim());
                        currLine = "";
                    }
                    nextWord = false;
                }
                else{
                    currLine += word;
                    if(fm.stringWidth(currLine + " ") <= width){
                        currLine += " ";
                    }
                    word = "";
                    nextWord = true;
                }
            }
            if(currLine.length() > 0){
                linesToDraw.add(currLine.trim());
            }
        }
        return linesToDraw;
    }

    public static void drawTextInArea(Graphics g, String text, int x, int y, int width, int height){
        FontMetrics fm = g.getFontMetrics();
        ArrayList<String> linesToDraw = cutIntoLines(fm, text, width, height);
        int lineno = 0;
        for(String line: linesToDraw){
            g.drawString(line, x, y + fm.getAscent() + lineno * (fm.getHeight() + 5));
            lineno++;
        }
    }

    public static void drawTextInMiddleOfArea(Graphics g, String str, int x, int y, int width ,int height){
        FontMetrics fm = g.getFontMetrics();
        ArrayList<String> linesToDraw = cutIntoLines(fm, str, width, height);
        int textHeight = linesToDraw.size() * (fm.getHeight() + 5);
        int lineno = 0;
        for(String line: linesToDraw){
            int lineWidth = fm.stringWidth(line);
            int xStart = x + width/2 - lineWidth/2;
            int yStart = y + height/2 - textHeight/2 + fm.getAscent() + lineno * (fm.getHeight() + 5);
            g.drawString(line, xStart, yStart);
            lineno++;
        }
    }
}
