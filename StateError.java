public class StateError extends RuntimeException{
    private Thread errorJob;
    public StateError (String error){
        super(error);
        errorJob = new Thread(new Runnable(){
            public void run(){
                ErrorMindow.Error("StateError: "+error);
            }
        });
        errorJob.start();
    }
}
