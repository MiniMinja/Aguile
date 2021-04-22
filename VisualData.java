import java.util.HashMap;

public class VisualData {
    protected HashMap<String, String> params;
    protected String content;

    public VisualData(String c){
        params = new HashMap();
        content = c;
    }

    public String getParams(String param){
        return params.get(param);
    }

    public String getContent(){
        return content;
    }
}
