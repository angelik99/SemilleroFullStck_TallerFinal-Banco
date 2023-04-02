/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase se utiliza para la conexion a la bd y para crear las tablas
 * @author aleon
 */
public class Conexion {

    private String cadenaConexion;

    public Conexion() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            cadenaConexion = "jdbc:sqlite:banco.db";
            // Las funciones de crear tablas se llaman en el correcto orden debido a las relaciones que manejan entre ellas
            crearTablaUsuarios();
            crearTablaCuentas();
            crearTablaTransacciones();
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e);
        }
    }

    private void crearTablaUsuarios() {
        try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
            String sql = "CREATE TABLE IF NOT EXISTS USUARIOS(\n"
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "NOMBRE TEXT NOT NULL,\n"
                    + "APELLIDO TEXT NOT NULL,\n"
                    + "CEDULA TEXT NOT NULL UNIQUE\n"
                    + ");\n";

            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    private void crearTablaCuentas() {
        try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
            String sql = "CREATE TABLE IF NOT EXISTS CUENTAS(\n"
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "NUMERO_CUENTA TEXT NOT NULL UNIQUE,\n"
                    + "SALDO REAL NOT NULL,\n"
                    + "TIPO_CUENTA TEXT NOT NULL,\n"
                    + "ID_USUARIO INTEGER NOT NULL,\n"
                    + "FOREIGN KEY(ID_USUARIO) REFERENCES USUARIOS(ID)\n"
                    + ");\n";

            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    private void crearTablaTransacciones() {
        try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
            String sql = "CREATE TABLE IF NOT EXISTS TRANSACCIONES(\n"
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "FECHA TEXT NOT NULL,\n"
                    + "HORA TEXT NOT NULL,\n"
                    + "TIPO_TRANSACCION TEXT NOT NULL,\n"
                    + "MONTO REAL NOT NULL,\n"
                    + "ID_CUENTA INTEGER NOT NULL,\n"
                    + "TIPO_CUENTA_DESTINO TEXT,\n"
                    + "FOREIGN KEY(ID_CUENTA) REFERENCES CUENTAS(ID)\n"
                    + ");";

            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    public String getCadenaConexion() {
        return cadenaConexion;
    }

    public void setCadenaConexion(String cadenaConexion) {
        this.cadenaConexion = cadenaConexion;
    }
}
