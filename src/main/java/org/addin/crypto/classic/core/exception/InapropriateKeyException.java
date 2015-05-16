package org.addin.crypto.classic.core.exception;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class InapropriateKeyException extends RuntimeException {
        
        public InapropriateKeyException() {
            super("Inapropriate key.");
        }

        public InapropriateKeyException(String message) {
            super(message);
        }  
}
