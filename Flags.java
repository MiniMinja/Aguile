public class Flags {
    public static final int IDLE = -1;
    public static final int EDITING_FEATURE = 1;
    public static final int FILE_VIEWING = 2;

    private static int state = -1;
    private static boolean error = false;

    static{
        state = IDLE;
        error = false;
    }

    public static int getState(){
        return state;
    }

    public static String getStateStr(int i){
        switch(i){
            case IDLE:
                return "IDLE";
            case EDITING_FEATURE:
                return "EDITING_FEATURE";
            case FILE_VIEWING:
                return "FILE_VIEWING";
        }
        throw new StateError("no such feature exists!!");
    }

    public static void setState(int nextState){
        if(state == IDLE){
            if(nextState == EDITING_FEATURE) state = nextState;
            else if(nextState == FILE_VIEWING) state = nextState;
            else{
                throw new StateError("You cannot go from "+getStateStr(state)+" to "+getStateStr(nextState));
            }
        }
        else if(state == EDITING_FEATURE){
            if(nextState == IDLE) state = nextState;
            else{
                throw new StateError("You cannot go from "+getStateStr(state)+" to "+getStateStr(nextState));
            }
        }else if(state == FILE_VIEWING){
            if(nextState == IDLE) state = nextState;
            else{
                throw new StateError("You cannot go from "+getStateStr(state)+" to "+getStateStr(nextState));
            }
        }
    }

    public static void setError(boolean err){
        error = err;
    }

    public static boolean isError(){
        return error;
    }
}
