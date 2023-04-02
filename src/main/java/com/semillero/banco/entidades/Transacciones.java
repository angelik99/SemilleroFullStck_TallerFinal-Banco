/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.entidades;

/**
 *
 * @author aleon
 */
public class Transacciones {
    public int id;
    public String fecha;
    public String hora;
    public double monto;
    public int idTipoCuenta;
    public String tipoCuenta;

    public Transacciones(int id, String fecha, String hora, double monto, int idTipoCuenta, String tipoCuenta) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.monto = monto;
        this.idTipoCuenta = idTipoCuenta;
        this.tipoCuenta = tipoCuenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(int idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
     
}
