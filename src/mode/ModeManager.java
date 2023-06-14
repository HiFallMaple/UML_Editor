package mode;

import editor.canvasArea.CanvasMouseListener;
import mode.lineMode.AssociationLineMode;
import mode.lineMode.CompositionLineMode;
import mode.lineMode.GeneralizationLineMode;
import mode.objectMode.ClassMode;
import mode.objectMode.UseCaseMode;

public class ModeManager {
    public final static int SELECT = 0;
    public final static int ASSOCIATION = 1;
    public final static int GENERALIZATION = 2;
    public final static int COMPOSITION = 3;
    public final static int CLASS = 4;
    public final static int USE_CASE = 5;
    public final static String[] modeStr = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "CLASS", "USE_CASE"};
    
    private static CanvasMouseListener canvasML;
    static{
        canvasML = CanvasMouseListener.getInstance();
    }

    private static int modeIndex = 0;
    private final static Mode[] modeInstances = {
        new SelectMode(),
        new AssociationLineMode(),
        new GeneralizationLineMode(),
        new CompositionLineMode(),
        new ClassMode(),
        new UseCaseMode()
    };

    public static String getModeName(int index){
        return modeStr[index];
    }

    public static int getMode(){
        return modeIndex;
    }

    public static int getModeLength(){
        return modeInstances.length;
    }

    public static void setMode(int index){
        modeIndex = index;
        canvasML.setMode(modeInstances[index]);
    }
}
