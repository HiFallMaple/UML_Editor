package mode.lineMode;

import component.line.AssociationLineType;
import component.line.Line;

public class AssociationLineMode extends NewLineMode {

    public AssociationLineMode(){
        super();
    }

    @Override
    public Line newLine(){
        return new Line(new AssociationLineType());
    }
}
