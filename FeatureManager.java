import java.util.*;
import java.io.*;
import javax.swing.JPanel;
import java.awt.Graphics;
public class FeatureManager{

    private static FeatureManager instance;

    public static FeatureManager getInstance(){
        if(instance == null) instance = new FeatureManager();
        return instance;
    }

/*------------------------------------------------------------------
        FeatureManager instance part
--------------------------------------------------------------------*/

    private FeaturePane featureRenderer;
    private ArrayList<Feature> features;

    private FeatureManager(){
        features = new ArrayList<Feature>();
        featureRenderer = new FeaturePane(this);
    }

    public void createFeature(){
        //finds next id spot to fill
        int maxId = -1;
        for(Feature f: features){
            if(f.id() > maxId) maxId = f.id();
        }
        boolean[] featureIds = new boolean[maxId+1];
        for(Feature f: features){
            featureIds[f.id()] = true;
        }
        int nextId = featureIds.length;
        for(int i =0;i<featureIds.length;i++){
            if(!featureIds[i]){
                nextId = i;
                break;
            }
        }
        FeatureBuilderMindow.start(nextId);
    }

    public void addFeature(Feature f){
        features.add(f);
        Collections.sort(features);
    }

    public void editFeature(int id){
        Feature toEdit = null;
        for(Feature f: features){
            if(f.id() == id){
                toEdit = f;
                break;
            }
        }
        if(toEdit == null)
            throw new FeatureManagingError("feature with that id doesn't exist");
        else{
            features.remove(toEdit);
            FeatureBuilderMindow.edit(toEdit);
        }
    }

    public void removeFeature(int id) throws FeatureManagingError{
        Feature toRem = null;
        for(Feature f:features){
            if(f.id() == id){
                toRem = f;
            }
        }
        if(toRem == null){
            throw new FeatureManagingError("there is no feature with that id");
        }
        features.remove(toRem);
    }

    public Feature getFeature(int id){
        Feature toRet = null;
        for(Feature f: features){
            if(f.id() == id){
                toRet = f;
            }
        }
        if(toRet == null){
            throw new FeatureManagingError("there is no feature with that id");
        }
        return toRet;
    }

    public int getSize(){
        return features.size();
    }

    public void readFromFile(String filename) throws IOException{
        JSONData data = JSONio.readFromFile(filename);
        readFromJSONData(data);
    }

    /*
        First level is the List of features
        Second level are the params for each feature
        Third level are the list of Tasks
    */
    public void readFromJSONData(JSONData data){
        features = new ArrayList<Feature>();
        for(int i = 0;i<data.size();i++){
            JSONData JSONfeature = data.get(i);
            String asthe = null, iwant = null, sothat = null;
            Feature.Builder newBuilder = new Feature.Builder();
            for(String key: JSONfeature.keySet()){
                JSONData value = JSONfeature.get(key);
                //System.out.println("For key: "+key+" JSONData: "+value);
                switch(key){
                    case "name":
                        newBuilder.setFName(value.str());
                        break;
                    case "id":
                        newBuilder.setId(value.value());
                        break;
                    case "asthe":
                        asthe = value.str();
                        break;
                    case "iwant":
                        iwant = value.str();
                        break;
                    case "sothat":
                        sothat = value.str();
                        break;
                    case "size":
                        newBuilder.setSize(value.str());
                        break;
                    case "implemented":
                        newBuilder.setImplemented(value.str().equals("true"));
                        break;
                    case "tasks":
                        ArrayList<Task> tasks = new ArrayList<Task>();
                        for(int j = 0;j<value.size();j++){
                            Task toAdd = Task.readFromJSONData(value);
                            tasks.add(toAdd);
                        }
                        newBuilder.addTasks(tasks);
                        break;
                    default:
                        throw new FeatureManagingError("that param doesn't exist for feature");
                }

            }
            newBuilder.setDesc(asthe, iwant, sothat);
            addFeature(newBuilder.build());
        }
    }

    public void writeToFile(String filename) throws IOException{
        JSONio.outputToFile(outputToJSONData(), filename);
    }

    public JSONData outputToJSONData(){
        JSONData firstLevel = new JSONData(JSONData.Type.LIST);
        for(Feature f: features){
            JSONData feature = new JSONData(JSONData.Type.OBJECT);
            feature.put("name", JSONData.valueOf(f.fName()));
            feature.put("id", JSONData.valueOf(f.id()));
            feature.put("asthe", JSONData.valueOf(f.asthe()));
            feature.put("iwant", JSONData.valueOf(f.iwant()));
            feature.put("sothat", JSONData.valueOf(f.sothat()));
            feature.put("size", JSONData.valueOf(f.size().toString()));
            feature.put("implemented", JSONData.valueOf(f.implemented()?"true":"false"));
            feature.put("tasks", f.getTasklistAsJSONData());
            firstLevel.add(feature);
        }
        return firstLevel;
    }

    public FeaturePane getPane(){
        return featureRenderer;
    }

    public void render(Graphics g){
        for(Feature f: features){
            featureRenderer.renderNextFeature(g, f);
        }
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        for(int i = 0;i<features.size();i++){
            ret.append("-------------------------------------------------------------\n");
            ret.append(features.get(i).toString());
            ret.append('\n');
            ret.append("-------------------------------------------------------------\n");
            ret.append('\n');
        }
        if(features.size() >= 1)
            ret.deleteCharAt(ret.length()-1);
        return ret.toString();
    }
}