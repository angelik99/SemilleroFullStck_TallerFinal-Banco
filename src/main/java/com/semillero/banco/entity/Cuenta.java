/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.entidades;

/**
 *
 * @author aleon
 */
public class Cuentas {
    public int id;
    public String numCuenta;
    public double saldo;
    public String tipoCuenta;
    public int idUsuario;

    public Cuentas() {
    }

    public Cuentas(int id, String numCuenta, double saldo, String tipoCuenta, int idUsuario) {
        this.id = id;
        this.numCuenta = numCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    } 
  
}
