public class Mode {
    private static int mode = 0;
    public final static int SELECT = 0;
    public final static int ASSOCIATION = 1;
    public final static int GENERALIZATION = 2;
    public final static int COMPOSITION = 3;
    public final static int CLASS = 4;
    public final static int USE_CASE = 5;
    public final static String[] modeStr = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "CLASS", "USE_CASE"};
    public static int getStatus(){
        return mode;
    }
    public static String getStatusStr(){
        return modeStr[mode];
    }
    public static void setStatus(int newMode){
        mode = newMode;
    }
    public static boolean isConnectionLineMode(){
        return mode == ASSOCIATION || mode == GENERALIZATION || mode == COMPOSITION;
    }
}
