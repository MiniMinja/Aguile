import java.util.*;

public class Feature implements Comparable<Feature>{

    public static enum Size{
        SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE"), EXTRALARGE("EXTRA LARGE");
        private String size;
        private Size(String size){
            this.size = size;
        }
        public String toString(){
            return size;
        }
    }

    public static class Builder{
        private Feature creation;
        private boolean createdName, createdId, createdDesc, setSize, setImplemented;
        public Builder(){
            creation = new Feature();
            createdName = false;
            createdId = false;
            createdDesc = false;
            setSize = false;
            setImplemented = false;
        }

        /*
        This constructor will be used to edit a Feature
        */
        public Builder(Feature c){
            creation = c;
            createdName = true;
            createdId = true;
            createdDesc = true;
            setSize = true;
            setImplemented = true;
        }

        public Builder setFName(String fName){
            if(fName == null || fName.length() == 0) 
                throw new FeatureManagingError("cannot set a feature name that is empty");
            createdName = true;
            creation.fName = fName;
            return this;
        }

        public Builder setId(int id){
            createdId = true;
            creation.id = id;
            return this;
        }

        public Builder setDesc(String asthe, String iwant, String sothat){
            if(asthe == null || iwant == null || sothat == null 
                || asthe.length() == 0 || iwant.length() == 0 || sothat.length() == 0) 
                throw new FeatureManagingError("cannot set a feature description that is empty");
            createdDesc = true;
            creation.asthe = asthe;
            creation.iwant = iwant;
            creation.sothat = sothat;
            return this;
        }

        public Builder setSize(String size){
            if(size == null || size.length() == 0) 
                throw new FeatureManagingError("cannot set a feature name that is empty");
            setSize = true;
            creation.size = Size.valueOf(size);
            return this;
        }

        public Builder setImplemented(boolean implemented){
            setImplemented = true;
            creation.implemented = implemented;
            return this;
        }

        public Builder addTasks(ArrayList<Task> t){
            if(t == null) 
                throw new FeatureManagingError("cannot set a null task arraylist");
            creation.tasklist = t;
            return this;
        }

        public Feature build(){
            if(!createdName)
                throw new FeatureManagingError("a feature must have a name to be created");
            if(!createdId)
                throw new FeatureManagingError("a feature must have an id to be created");
            if(!createdDesc)
                throw new FeatureManagingError("a feature must have a description to be created");
            if(!setSize)
                throw new FeatureManagingError("a feature must have a size to be created");
            if(!setImplemented)
                throw new FeatureManagingError("a feature must say whether or not it is implemented to be created");
            return creation;
        }
    }

    private String fName;
    private int id;
    private String asthe, iwant, sothat;
    private Size size;
    private ArrayList<Task> tasklist;
    private boolean implemented;

    private Feature(){
        tasklist = new ArrayList<Task>();
    }

    public String fName() { return fName;}

    public int id() { return id; }

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

    public void addTask(Task t){
        tasklist.add(t);
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("id: ");
        ret.append(id);
        ret.append('\n');
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
        ret.append("Tasks: ");
        ret.append(tasklist.size());
        ret.append("\n");
        ret.append("Implemented: ");
        ret.append(implemented);
        return ret.toString();
    }

    public int compareTo(Feature f){
        return this.id - f.id;
    }
}