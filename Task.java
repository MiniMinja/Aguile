public class Task{
    private String taskData;
    private boolean done;

    public Task(String data){
        taskData = data;
    }

    public void refresh(){
        done = false;
    }

    public void finish(){
        done = true;
    }

    public String getData(){
        return taskData;
    }

    public String toString(){
        return taskData+"\nCompleted: "+done;
    }
}