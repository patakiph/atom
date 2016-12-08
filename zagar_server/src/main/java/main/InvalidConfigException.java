package main;

/**
 * Created by Ольга on 01.12.2016.
 */
public class InvalidConfigException extends Exception {
    InvalidConfigException(){
        System.out.println("Invalid configuration");
    }
}
