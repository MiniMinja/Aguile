import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.regex.Pattern;

public class Task{
    private static HashMap<Integer, Task> tasks;
    private static Queue<Integer> availableIds;
    private static int size;
    private static int lastID;

    static {
        init();
    }

    private static void init(){
        tasks = new HashMap<Integer, Task>();
        availableIds = new LinkedList<Integer>();
        size = 0;
        lastID = 0;
    }

    public static int createTask(String data){
        Task newTask = new Task(data);
        if(availableIds.isEmpty()){
            size++;
            lastID++;
            tasks.put(lastID, newTask);
            return lastID;
        }
        else{
            int nextId = availableIds.poll();
            tasks.put(nextId, newTask);
            size++;
            return nextId;
        }
    }

    public static int createTask(String data, boolean isFinished){
        Task newTask = new Task(data, isFinished);
        if(availableIds.isEmpty()){
            size++;
            lastID++;
            tasks.put(lastID, newTask);
            return lastID;
        }
        else{
            int nextId = availableIds.poll();
            tasks.put(nextId, newTask);
            size++;
            return nextId;
        }
    }

    public static int setTask(int id, String data, boolean isFinished){
        Task newTask = new Task(data, isFinished);
        tasks.put(id, newTask);
        return id;
    }

    public static boolean removeTask(int id){
        if(tasks.get(id) == null) return false;
        tasks.remove(id);
        availableIds.add(id);
        size--;
        return true;
    }

    public static String getTask(int id){
        if(tasks.get(id) == null) return null;
        else return tasks.get(id).getData();
    }

    public static int isTaskFinished(int id){
        if(tasks.get(id) == null) return -1;
        else return tasks.get(id).isFinished() ? 1 : 0;
    }

    public static void writeToFile(String filename) throws IOException{
        if(Pattern.matches(".*\\.tk", filename)){
            filename+="s";
        }
        else if(!Pattern.matches(".*\\.tks", filename)){
            filename += ".tks";
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        pw.println(size);
        for(int i = 1;i<=lastID;i++){
            pw.print("ID: ");
            pw.println(i);
            pw.println("TASK: {");
            if(tasks.get(i) != null){
                pw.print("DATA: ");
                pw.println(tasks.get(i).getData());
                pw.print("DONE: ");
                pw.println(tasks.get(i).isFinished());
            }
            else{
                pw.println("null");
            }
            pw.println("\n}");
        }
        pw.close();
    }

    public static void loadFromFile(String filename) throws IOException{
        if(!filename.endsWith(".tks")) throw new IOException("Task must read from .tks file!!!");
        init();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int size = Integer.parseInt(br.readLine());
        for(int i = 0;i<size;i++){
            int id = Integer.parseInt(br.readLine().replace("ID: ", ""));
            br.readLine();
            String data = br.readLine();
            if(!data.equals("null")){
                data = data.replace("DATA: ", "");
                boolean isFinished = br.readLine().replace("DONE: ", "").equals("true") ? true : false;
                Task.createTask(data, isFinished);
            }
        }
    }

    private String taskData;
    private boolean done;

    private Task(String data){
        taskData = data;
    }

    private Task(String data, boolean isFinished){
        taskData = data;
        this.done = isFinished;
    }

    private void refresh(){
        done = false;
    }

    private void finish(){
        done = true;
    }

    private String getData(){
        return taskData;
    }

    private boolean isFinished(){ return done; }

    public String toString(){
        return taskData+"\nCompleted: "+done;
    }
}