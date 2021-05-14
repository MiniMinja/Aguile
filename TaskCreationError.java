public class TaskCreationError extends RuntimeException{
    private Thread errorJob;
    public TaskCreationError(String msg){
        super(msg);
        errorJob = new Thread(new Runnable(){
            public void run(){
                ErrorMindow.Error("TaskCreationError: "+msg);
            }
        });
        errorJob.start();
    }
}
