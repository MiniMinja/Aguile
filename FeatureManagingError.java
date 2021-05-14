public class FeatureManagingError extends RuntimeException{
    private Thread errorJob;

    public FeatureManagingError(String msg){
        super(msg);
        errorJob = new Thread(new Runnable(){
            public void run(){
                ErrorMindow.Error("FeatureManagingError: "+msg);
            }
        });
        errorJob.start();
    }
}
