package log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static Logger LOG_LOGIC ;
    private static Logger LOGGER ;
    public static void setLogic(){
            Logger LOG;
            LOG_LOGIC = Logger.getLogger("businessLogic");
            LOGGER = Logger.getLogger("businessLogic");
    }
    

    public static void logException(Exception exception) {
        setLogic();
        try {

            Handler fileHandler = new FileHandler("./bitacora.log", true);           

            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);           
            LOG_LOGIC.addHandler(fileHandler);
            fileHandler.setLevel(Level.ALL);
            LOGGER.log(Level.INFO, "Bitacora inicializada");
            LOGGER.log(Level.SEVERE, Log.getStackTrace(exception) );
        }catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error de IO");
        } catch (SecurityException ex) {
            LOGGER.log(Level.SEVERE, "Error de Seguridad");
        }

}
    
    public static String getStackTrace(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}

