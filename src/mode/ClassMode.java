package mode;

import component.Shape;
import component.basicObject.ClassObject;

public class ClassMode extends NewObjectMode {

    public ClassMode(){
        super();
    }

    @Override
    protected Shape newObject(){
        return new ClassObject();
    }

}
