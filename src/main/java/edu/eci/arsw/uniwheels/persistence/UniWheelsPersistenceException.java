package edu.eci.arsw.uniwheels.persistence;
/**
 *
 * @author Carlos Murillo
 */
public class UniWheelsPersistenceException extends Exception{
    public UniWheelsPersistenceException(String message) {
        super(message);
    }

    public UniWheelsPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}