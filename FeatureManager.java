import java.util.*;
import java.io.*;
public class FeatureManager{

    private static FeatureManager instance;

    public static FeatureManager getInstance(){
        if(instance == null) instance = new FeatureManager();
        return instance;
    }

/*------------------------------------------------------------------
FeatureManager instance part
--------------------------------------------------------------------*/

    private ArrayList<Feature> features;

    private FeatureManager(){
        features = new ArrayList<Feature>();
    }

    public void createFeature(){
        //finds next id spot to fill
        boolean[] featureIds = new boolean[features.size()];
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

    public void removeFeature(int id){
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