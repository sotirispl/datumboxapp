package com.sotiris.datumboxapp.controllers;

/**
 * Created by sotiris on 21/2/2015.
 */
public class SingletonController {

    private static SingletonController controller = null;

    private SingletonController() {
    }

    public static SingletonController getController() {
        if(controller == null) {
            controller = new SingletonController();
            return controller;
        }
        return controller;
    }

}
