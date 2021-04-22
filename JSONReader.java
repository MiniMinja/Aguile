import java.io.*;
import java.util.*;

public class JSONReader {

    /*
        returns the first JSONData in the file provided by fileName
    */
    public static JSONData readFromFile(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        while(true){
            int c = br.read();
            if(c == -1) break;
            sb.append((char) c);
        }
        String data = sb.toString().trim();
        if(data.startsWith("{")){
            int[] firstObj = findFirstEnclosed(data, '{', '}');
            return parseObject(data.substring(firstObj[0]+1, firstObj[1]));
        }
        else if(data.startsWith("\"")){
            int[] firstStr = findFirstEnclosed(data, '\"', '\"');
            return parseString(data.substring(firstStr[0]+1, firstStr[1]));
        }
        else if(data.matches("\\d.*")){
            return parseString(data);
        }
        else if(data.startsWith("[")){
            int[] firstObj = findFirstEnclosed(data, '[', ']');
            return parseList(data.substring(firstObj[0]+1, firstObj[1]));
        }
        throw new JSONError("Incorrect JSONSyntax");
    }

    private static int[] findFirstEnclosed(String data, char op, char cl){
        int index1 = -1;
        int index2 = -1;
        int pCount = 0;
        for(int i = 0;i<data.length();i++){
            if(data.charAt(i) == op){
                if(pCount == 0) index1 = i;
                else if(op == cl && i != index1){
                    index2 = i;
                    return new int[] {index1, index2};
                }
                pCount++;
            }
            else if(data.charAt(i) == cl){
                if(pCount == 0) throw new JSONError("Incorrect JSONSyntax");
                if(pCount == 1) {
                    index2 = i;
                    return new int[] {index1, index2};
                }
                pCount--;
            }
        }
        return null;
    }

    /*
    The following 4 parse methods assume that the value inside of the String data 
    will contain the apppropriate type. Some minor error checking is done, but overall
    the values should be what they are. 
    For example, Strings like "hello" will basically have their quotes removed (hello)
    objects will lose their brackets {"hello":"bye"} -> "hello":"bye"
    */

    private static JSONData parseInt(String data){
        data = data.trim();
        JSONData ret = new JSONData(JSONData.Type.INTEGER);
        try{
            ret.set(Integer.parseInt(data));
        }catch(NumberFormatException e){
            throw new JSONError("Invalid JSON Syntax: "+data+" is not a valid integer");
        }
        return ret;
    }

    private static JSONData parseString(String data){
        data = data.trim();
        JSONData ret = new JSONData(JSONData.Type.STRING);
        ret.set(data);
        return ret;
    }

    private static JSONData parseList(String data){
        JSONData ret = new JSONData(JSONData.Type.LIST);
        int size = 0;
        while(true){
            data = data.trim();
            int nextComma = data.length();
            int pCount = 0;
            boolean inString = false;
            for(int i = 0;i<data.length();i++){
                if(data.charAt(i) == '\"') inString = !inString;
                if(data.charAt(i) == '{' || data.charAt(i) == '[') pCount++;
                else if(data.charAt(i) == '}' || data.charAt(i) == ']') {
                    if(pCount == 0) throw new JSONError("Invalid JSON Syntax");
                    else pCount--;
                } 
                if(pCount == 0 && data.charAt(i) == ',' && !inString){
                    nextComma = i;
                    break;
                }
            }
            String val = data.substring(0, nextComma);
            JSONData toAdd = null;
            if(val.length() == 0 && size != 0){
                throw new JSONError("Invalid use of commas in List");
            }
            else if(val.startsWith("{")){
                int[] firstValObj = findFirstEnclosed(val, '{', '}');
                toAdd = parseObject(val.substring(firstValObj[0]+1, firstValObj[1]));
            }
            else if(val.startsWith("\"")){
                int[] firstStr = findFirstEnclosed(data, '\"', '\"');
                toAdd = parseString(val.substring(firstStr[0]+1, firstStr[1]));
            }
            else if(val.matches("\\d.*")){
                toAdd = parseInt(val);
            }
            else if(val.startsWith("[")){
                int[] firstObj = findFirstEnclosed(data, '[', ']');
                toAdd = parseList(data.substring(firstObj[0]+1, firstObj[1]));
            }
            size++;
            ret.add(toAdd);
            if(nextComma == data.length()) break;
            data = data.substring(nextComma + 1);
        }
        return ret;
    }
    

    private static JSONData parseObject(String data){
        JSONData retVal = new JSONData(JSONData.Type.OBJECT);
        int size = 0;
        while(true){
            data = data.trim();
            int[] firstString = findFirstEnclosed(data, '\"', '\"');
            if(firstString == null) break;
            if(firstString[0] != 0) throw new JSONError("Expected a key for this object");
            String key = data.substring(firstString[0]+1, firstString[1]);
            int val_loc = data.indexOf(':');
            if(val_loc == -1) throw new JSONError("Expected a ':' for the key: "+key);
            int val_end = data.length();
            int pCount = 0;
            boolean inString = false;
            for(int i = val_loc + 1;i<data.length();i++){
                if(data.charAt(i) == '\"') inString = !inString;
                if(data.charAt(i) == '{' || data.charAt(i) == '[') pCount++;
                if(data.charAt(i) == '}' || data.charAt(i) == ']'){
                    if(pCount == 0){
                        throw new JSONError("Incorrect JSONSyntax");
                    }
                    pCount--;
                }
                if(data.charAt(i) == ',' && pCount == 0 && !inString){
                    val_end = i;
                    break;
                }
            }
            String val = data.substring(val_loc+1, val_end);
            JSONData toAdd = null;
            if(val.length() == 0 && size != 0){
                throw new JSONError("Invalid use of commas in Object");
            }
            else if(val.startsWith("{")){
                int[] firstValObj = findFirstEnclosed(val, '{', '}');
                toAdd = parseObject(val.substring(firstValObj[0]+1, firstValObj[1]));
            }
            else if(val.startsWith("\"")){
                int[] firstStr = findFirstEnclosed(val, '\"', '\"');
                toAdd = parseString(val.substring(firstStr[0]+1, firstStr[1]));
            }
            else if(val.matches("\\d.*")){
                toAdd = parseInt(val);
            }
            else if(val.startsWith("[")){
                int[] firstObj = findFirstEnclosed(data, '[', ']');
                toAdd = parseList(data.substring(firstObj[0]+1, firstObj[1]));
            }
            System.out.println("Putting: "+key+", "+toAdd);
            retVal.put(key, toAdd);
            if(val_end == data.length()) break;
            size++;
            data = data.substring(val_end+1);
        }
        return retVal;
    }

    public static void main(String[] args) throws IOException{
        JSONData f = readFromFile("sample.json");
        System.out.println(f);
    }
}
