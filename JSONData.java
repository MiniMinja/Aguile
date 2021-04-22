import java.util.*;
public class JSONData {
    public static enum Type{
        INTEGER, STRING, LIST, OBJECT;
    }

    private Type t;

    public JSONData(Type t){
        this.t = t;
        switch(t){
            case OBJECT:
                obj_data = new HashMap();
                break;
            case LIST:
                list_data = new ArrayList();
                break;
            case INTEGER:
                a = 0;
                break;
            case STRING:
                str_data = null;
                break;
        }
    }

    public Type type(){
        return t;
    }

    public void error(Type attemptedType){
        System.out.println("Tried to use " 
                                + attemptedType.toString() 
                                + " methods when the current data is "
                                + t.toString() );
    }

    public void error(String genericType){
        System.out.println("Tried to use " 
                                + genericType
                                + " methods when the current data is "
                                + t.toString() );
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ");
        sb.append(t.toString());
        switch(t){
            case OBJECT:
                sb.append(" contains: \n");
                for(String key: obj_data.keySet()){
                    sb.append(key);
                    sb.append(":");
                    String recursiveData = obj_data.get(key).toString();
                    recursiveData = recursiveData.replaceAll("\n", "\n\t");
                    sb.append('\t');
                    sb.append(recursiveData);
                    sb.append('\n');
                }
                sb.deleteCharAt(sb.length()-1);
                break;
            case LIST:
                sb.append(" contains: \n");
                sb.append('[');
                for(int i = 0;i<list_data.size();i++){
                    sb.append(list_data.get(i).toString());
                    if(i != list_data.size() - 1) sb.append(", ");
                }
                sb.append(']');
                break;
            case INTEGER:
                sb.append(" contains: ");
                sb.append(a);
                break;
            case STRING:
                sb.append(" contains: \"");
                sb.append(str_data);
                sb.append('\"');
                break;
        }
        return sb.toString();
    }
/*----------------------------------------------------------------------------------------
Whenever JSONData is a HashMap
----------------------------------------------------------------------------------------*/
    protected HashMap<String, JSONData> obj_data;

    public void put(String key, JSONData value) {
        if(t != Type.OBJECT) error(Type.OBJECT);
        obj_data.put(key, value);
    }

    public JSONData get(String key) {
        if(t != Type.OBJECT) error(Type.OBJECT);
        if(!obj_data.containsKey(key)) throw new JSONError("Current JSON Object does not contain the key: "+key);
        return obj_data.get(key);
    }

    public boolean containsKey(String key) {
        if(t != Type.OBJECT) error(Type.OBJECT);
        return obj_data.containsKey(key);
    }
//Size is listed in the arraylist section

/*----------------------------------------------------------------------------------------
Whenever JSONData is a List
----------------------------------------------------------------------------------------*/
    protected ArrayList<JSONData> list_data;

    public void add(JSONData data){
        if(t != Type.LIST) error(Type.LIST);
        list_data.add(data);
    }

    public JSONData get(int index){
        if(t != Type.LIST) error(Type.LIST);
        return list_data.get(index);
    }

    public int size()  {
        if(t != Type.OBJECT && t != Type.LIST) error("LIST or OBJECT");
        if(t == Type.OBJECT) return obj_data.size();
        return list_data.size();
    }

/*----------------------------------------------------------------------------------------
Whenever JSONData is a Integer
----------------------------------------------------------------------------------------*/
    protected int a;

    public void set(int a){
        if(t != Type.INTEGER) error(Type.INTEGER);
        this.a = a;
    }

    public int value(){
        if(t != Type.INTEGER ) error(Type.INTEGER);
        return a;
    }
    

/*----------------------------------------------------------------------------------------
Whenever JSONData is a STRING
----------------------------------------------------------------------------------------*/
    protected String str_data;

    public void set(String str){
        if(t != Type.STRING ) error(Type.STRING);
        str_data = str;
    }

    public String str(){
        if(t != Type.STRING ) error(Type.STRING);
        return str_data;
    }

    public int length(){
        if(t != Type.STRING ) error(Type.STRING);
        return str_data.length();
    }
}
