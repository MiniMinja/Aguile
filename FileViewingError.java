public class FileViewingError extends RuntimeException{
    private Thread errorJob;

    public FileViewingError(String msg){
        super(msg);
        errorJob = new Thread(new Runnable(){
            public void run(){
                ErrorMindow.Error("FileViewingError: "+msg);
            }
        });
        errorJob.start();
    }
}
