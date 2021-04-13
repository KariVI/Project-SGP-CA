package log;

import businessLogic.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
*
* @author David
*/
public class Log {

    // Preparamos el log para cada paquete del proyecto, esto con el fin de capturar cada log
// que se genere e irlo pasando al nivel superior hasta que encuentren un handler que los
    // maneje
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
            Handler fileHandler = new FileHandler("./bitacora.log", false);
            
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

/**
* Esta funcion nos permite convertir el stackTrace en un String, necesario para poder imprimirlos al log debido a
* cambios en como Java los maneja internamente
* @param e Excepcion de la que queremos el StackTrace
* @return StackTrace de la excepcion en forma de String
*/
public static String getStackTrace(Exception e) {
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter(sWriter);
    e.printStackTrace(pWriter);
    return sWriter.toString();
}
}

