import java.util.ArrayList;
import java.io.*;
public class FeatureManager{
    private ArrayList<Feature> features;
    private int size;

    public FeatureManager(){
        init();
    }

    private void init(){
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
            ArrayList<Integer> taskIds = feature.getTaskIds();
            pw.print("-");
            pw.println(taskIds.size());
            for(int j = 0;j<taskIds.size();j++){
                pw.print("--");
                pw.println(taskIds.get(j));
            }
            pw.print("-");
            pw.println(feature.implemented());
        }
        pw.close();

        Task.writeToFile(filename);
    }

    public void loadFromFile(String filename) throws IOException{
        if(!filename.endsWith(".tk")) throw new IOException("must read from a tk file!");
        init();
        Task.loadFromFile(filename+"s");
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int size = Integer.parseInt(br.readLine());
        for(int i = 0;i<size;i++){
            br.readLine();
            String asthe = br.readLine().replace("-asthe: ", "");
            String iwant = br.readLine().replace("-iwant: ", "");
            String sothat = br.readLine().replace("-sothat: ", "");
            Size s = Size.valueOf(br.readLine().replace("-", ""));
            Feature newFeature = new Feature(asthe, iwant, sothat, s);
            int idSize = Integer.parseInt(br.readLine());
            for(int j =0;j<idSize;j++){
                int taskId = Integer.parseInt(br.readLine().replace("--", ""));
                newFeature.addTask(taskId);
            }
            boolean implemented = br.readLine().replace("-", "").equals("true") ? true: false;
            if(implemented) newFeature.complete();
            features.add(newFeature);
            this.size++;
        }
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