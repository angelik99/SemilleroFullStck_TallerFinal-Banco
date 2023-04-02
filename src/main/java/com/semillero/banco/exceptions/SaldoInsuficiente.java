/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.exceptions;

/**
 * Creacion de excepcion cuando no hay saldo suficiente para realizar una transaccion 
 * @author aleon
 */
public class SaldoInsuficiente extends Exception {

    public SaldoInsuficiente(String mensaje) {
        super(mensaje);
    }
    
}
