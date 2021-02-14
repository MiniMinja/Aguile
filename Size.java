public enum Size{
    SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE"), EXTRALARGE("EXTRA LARGE");
    private String size;
    private Size(String size){
        this.size = size;
    }
    public String toString(){
        return size;
    }
}