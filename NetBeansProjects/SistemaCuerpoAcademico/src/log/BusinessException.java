

package log;

public class BusinessException extends Exception {
    public BusinessException(String message, Exception ex){
        super(message);
        Log.logException(ex);
    }
<<<<<<< HEAD
    
     public BusinessException(String message){
=======
    public BusinessException(String message){
>>>>>>> MarianaChangesGUI
        super(message);
    }
}
