

package log;

public class BusinessException extends Exception {
    public BusinessException(String message, Exception ex){
        super(message);
        Log.logException(ex);
    }
}
