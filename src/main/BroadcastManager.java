package main;
import java.util.ArrayList;

public class BroadcastManager {
    private static ArrayList<BroadcastListener> listeners;

    static{
        listeners = new ArrayList<BroadcastListener>();
    }
    
    private BroadcastManager() {
        // 私有的構造函數，禁止外部創建實例
    }
    
    public static void subListener(BroadcastListener listener){
        listeners.add(listener);
    }

    public static void removeListener(BroadcastListener listener){
        listeners.remove(listener);
    }

    public static void fire(String EventName, String message){
        for (BroadcastListener listener : listeners) {
            listener.handle(EventName, message);
        }
    }
}
