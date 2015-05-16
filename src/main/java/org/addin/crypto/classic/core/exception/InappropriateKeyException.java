package org.addin.crypto.classic.core.exception;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class InappropriateKeyException extends RuntimeException {
        
        public InappropriateKeyException() {
            super("Inapropriate key.");
        }

        public InappropriateKeyException(String message) {
            super(message);
        }  
}
