import java.util.HashMap;
import java.util.Map;

/**
 * Created on 02.02.15.
 */
public class Storage {

    private volatile static Map<String, Byte[]> storage;
    private volatile static Storage storageInst;

    private Storage() {
        System.out.println("Storage was created >>>>");
        storage = new HashMap<String, Byte[]>();
    }

    /*conditional lock*/
    public static Storage getInst() {
        if (storageInst == null) {
            synchronized (storageInst) {
                storageInst = new Storage();
            }
        }
        return storageInst;
    }
}
