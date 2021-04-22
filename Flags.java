public class Flags {
    public static final int IDLE = -1;
    public static final int EDITING_FEATURE = 1;

    private static int state = -1;

    public static int getState(){
        return state;
    }

    public static String getStateStr(int i){
        switch(i){
            case IDLE:
                return "IDLE";
            case EDITING_FEATURE:
                return "EDITING_FEATURE";
        }
        throw new StateError("no such feature exists!!");
    }

    public static void setState(int nextState){
        if(state == IDLE){
            if(nextState == EDITING_FEATURE) state = nextState;
            else{
                throw new StateError("You cannot go from "+getStateStr(state)+" to "+getStateStr(nextState));
            }
        }
        else if(state == EDITING_FEATURE){
            if(nextState == IDLE) state = nextState;
            else{
                throw new StateError("You cannot go from "+getStateStr(state)+" to "+getStateStr(nextState));
            }
        }
    }
}