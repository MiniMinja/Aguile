import java.util.ArrayList;
public class FeatureManager{
    private ArrayList<Feature> features;
    private int size;

    public FeatureManager(){
        features = new ArrayList<Feature>();
        features.add(null);
        size = 0;
    }

    public void addFeature(Feature f){
        features.add(f);
        size++;
    }

    public Feature getFeature(int id){
        if(id <= size && id >= 1)
            return features.get(id);
        return null;
    }

    public void removeFeature(int id){
        if(id <= size && id >= 1){
            features.remove(id);
            size--;
        }
    }

    public int size(){
        return size;
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        for(int i = 1;i<=size;i++){
            ret.append("id: ");
            ret.append(i);
            ret.append('\n');
            ret.append(features.get(i).toString());
            ret.append('\n');
        }
        if(size >= 1)
            ret.deleteCharAt(ret.length()-1);
        return ret.toString();
    }
}