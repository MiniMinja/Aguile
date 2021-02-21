import java.util.ArrayList;
public class Feature{
    private String asthe, iwant, sothat;
    private Size size;
    private ArrayList<Integer> taskIds;
    private boolean implemented;

    public Feature(String asthe, String iwant, String sothat, Size size){
        this.asthe = asthe;
        this.iwant = iwant;
        this.sothat = sothat;
        this.size = size;
        taskIds = new ArrayList<Integer>();
        implemented = false;
    }

    public String asthe() { return asthe; }

    public String iwant() { return iwant; }

    public String sothat() { return sothat; }

    public String size(){
        return size.toString();
    }

    public ArrayList<Integer> getTaskIds(){
        return taskIds;
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
        Task.removeTask(taskID);
        taskIds.remove(Integer.valueOf(taskID));
    }

    public void addTask(int a){
        taskIds.add(a);
    }

    public void addTask(String task){
        taskIds.add(Task.createTask(task));
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
        for(int tID: taskIds){
            ret.append("\t\t");
            ret.append(Task.getTask(tID));
            ret.append('\n');
            ret.append(Task.isTaskFinished(tID));
            ret.append('\n');
        }
        ret.append("Implemented: ");
        ret.append(implemented);
        return ret.toString();
    }
}