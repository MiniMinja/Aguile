import java.util.ArrayList;
public class Task {

    public static Task readFromJSONData(JSONData data){
        Task ret = new Task();
        return ret;
    }

    private ArrayList<VisualData> data;
    public Task(){
        data = new ArrayList<VisualData>();
    }

    public void setData(ArrayList<VisualData> d){
        data = d;
    }
}
