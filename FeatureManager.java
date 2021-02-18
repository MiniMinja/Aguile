import java.util.ArrayList;
import java.io.*;
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

    public void saveToFile(String filename) throws IOException{
        if(!filename.endsWith(".tk")) filename += ".tk";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        pw.println(size);
        for(int i = 1;i<=size;i++){
            pw.print("feature ");
            pw.println(i);
            Feature feature = features.get(i);
            pw.print("-asthe: ");
            pw.println(feature.asthe());
            pw.print("-iwant: ");
            pw.println(feature.iwant());
            pw.print("-sothat: ");
            pw.println(feature.sothat());
            pw.print("-");
            pw.println(feature.size());
            ArrayList<Task> tasks = feature.getTasks();
            pw.print("-");
            pw.println(tasks.size());
            for(int j = 0;j<tasks.size();j++){
                pw.print("--Task ");
                pw.println(j+1);
                pw.print("--");
                pw.println(tasks.get(j).getData());
                pw.print("--");
                pw.println(tasks.get(j).isFinished());
            }
            pw.print("-");
            pw.println(feature.implemented());
        }
        pw.close();
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