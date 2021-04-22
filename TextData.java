import java.util.HashMap;
public class TextData {
    public class Builder{
        private TextData creation;
        public void init(){
            creation = new TextData();
        }
        public TextData setId(String id){
            creation.params.put("id", id);
            return creation;
        }
        public TextData setFont(String font){
            creation.params.put("font", font);
            return creation;
        }
        public TextData setSize(String size){
            creation.params.put("size", size);
            return creation;
        }
        public TextData setBolded(String bolded){
            creation.params.put("bolded", bolded);
            return creation;
        }
        public TextData setItalicized(String italicized){
            creation.params.put("italicized", italicized);
            return creation;
        }
        public TextData setAlignment(String alignment){
            creation.params.put("alignment", alignment);
            return creation;
        }
        public TextData setContent(String content){
            creation.content = content;
            return creation;
        }
        public TextData build(){
            if(!creation.params.containsKey("id")){
                throw new TaskCreationErro("text must have ID before building");
            }
            if(!creation.params.containsKey("font")){
                creation.params.put("font", "serif");
            }
            if(!creation.params.containsKey("size")){
                creation.params.put("size", "12");
            }
            if(!creation.params.containsKey("bolded")){
                creation.params.put("bolded", "false");
            }
            if(!creation.params.containsKey("italicized")){
                creation.params.put("italicized", "false");
            }
            if(!creation.params.containsKey("alignment")){
                creation.params.put("alignment", "left");
            }

            if(creation.content == null){
                creation.content = "Ipsum lorem";
            }
            return creation;
        }
    }

}
