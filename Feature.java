import java.util.ArrayList;
public class Feature{
    private String asthe, iwant, sothat;
    private Size size;
    private ArrayList<Task> tasks;
    private boolean implemented;

    public Feature(String asthe, String iwant, String sothat, Size size){
        this.asthe = asthe;
        this.iwant = iwant;
        this.sothat = sothat;
        this.size = size;
        tasks = new ArrayList<Task>();
        implemented = false;
    }

    public String asthe() { return asthe; }

    public String iwant() { return iwant; }

    public String sothat() { return sothat; }

    public String size(){
        return size.toString();
    }

    public void refresh(){
        implemented = false;
    }

    public void complete(){
        implemented = true;
    }

    public boolean implemented(){
        return implemented;
    }

    public void removeTask(int taskID){
        tasks.remove(taskID);
    }

    public void addTask(Task t){
        tasks.add(t);
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("As the:\t\t");
        ret.append(asthe);
        ret.append('\n');
        ret.append("I want:\t\t");
        ret.append(iwant);
        ret.append('\n');
        ret.append("so that:\t");
        ret.append(sothat);
        ret.append('\n');
        ret.append("Size: ");
        ret.append(size());
        ret.append('\n');
        ret.append("Tasklist: \n");
        for(Task t: tasks){
            ret.append("\t\t");
            ret.append(t.toString());
            ret.append('\n');
        }
        ret.append("Implemented: ");
        ret.append(implemented);
        return ret.toString();
    }
}