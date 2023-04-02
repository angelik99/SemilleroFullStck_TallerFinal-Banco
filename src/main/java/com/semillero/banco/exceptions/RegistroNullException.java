/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.exceptions;

/**
 * Creacion de excepcion cuando no se encuentra registro en la bd
 * @author aleon
 */
public class RegistroNullException extends Exception {

    public RegistroNullException(String mensaje) {
        super(mensaje);
    }
    
}
