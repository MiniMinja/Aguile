public class JSONError extends RuntimeException{
    private Thread errorJob;
    public JSONError(String msg){
        super(msg);
        errorJob = new Thread(new Runnable(){
            public void run(){
                ErrorMindow.Error("JSONError: "+msg);
            }
        });
        errorJob.start();
    }
}
