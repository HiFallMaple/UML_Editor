public class Mode {
    private static int mode = 4;
    public final static int SELECT = 0;
    public final static int ASSOCIATION = 1;
    public final static int GENERALIZATION = 2;
    public final static int COMPOSITION = 3;
    public final static int CLASS = 4;
    public final static int USE_CASE = 5;
    public static int getStatus(){
        return mode;
    }
    public static String getStatusStr(){
        String[] modeStr = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "CLASS", "USE_CASE"};
        return modeStr[mode];
    }
    public static void setStatus(int newMode){
        mode = newMode;
    }
}
