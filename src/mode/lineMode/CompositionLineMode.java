package mode.lineMode;

import component.line.CompositionLineType;
import component.line.Line;

public class CompositionLineMode extends NewLineMode {
    
    public CompositionLineMode(){
        super();
    }

    @Override
    public Line newLine(){
        return new Line(new CompositionLineType());
    }
}
