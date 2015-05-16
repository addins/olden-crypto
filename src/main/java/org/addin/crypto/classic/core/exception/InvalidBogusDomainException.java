package org.addin.crypto.classic.core.exception;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class InvalidBogusDomainException extends RuntimeException {

    public InvalidBogusDomainException() {
        super("Invalid bogus domain value");
    }

    public InvalidBogusDomainException(String message) {
        super(message);
    }
}
