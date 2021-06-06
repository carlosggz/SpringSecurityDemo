package es.security.example.springsecuritydemo.domain.exceptions;

public class InvalidArgumentsException extends Exception{
    public InvalidArgumentsException(String message){
        super(message);
    }
}
