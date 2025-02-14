package com.auth.AuthorizationServer.Exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
/***
 * User Not Exists Exception
 */
public class UserNotExistsException extends RuntimeException{
    /**
     * @param message Message Associated with theException
     */
    public UserNotExistsException(String message){
        super(message);
    }
}
