package mode.lineMode;

import component.line.GeneralizationLineType;
import component.line.Line;

public class GeneralizationLineMode extends NewLineMode {

    public GeneralizationLineMode(){
        super();
    }

    @Override
    public Line newLine(){
        return new Line(new GeneralizationLineType());
    }
}
