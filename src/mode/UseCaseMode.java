package mode;

import component.Shape;
import component.basicObject.UseCaseObject;

public class UseCaseMode extends NewObjectMode {

    public UseCaseMode(){
        super();
    }

    @Override
    protected Shape newObject(){
        return new UseCaseObject();
    }

}
