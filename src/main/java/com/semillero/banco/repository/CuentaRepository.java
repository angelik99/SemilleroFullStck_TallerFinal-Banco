/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.repository;

import com.semillero.banco.entity.Cuenta;
import com.semillero.banco.interfaces.Repositorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creacion de clase para el manejo de CRUD de las cuentas
 *
 * @author aleon
 */
public class CuentaRepository implements Repositorio {

    private final Conexion conexion;

    public CuentaRepository() {
        conexion = new Conexion();
    }

    @Override
    public void crear(Object objeto) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            Cuenta nuevaCuenta = (Cuenta) objeto;
            String sql = "INSERT INTO CUENTAS (NUMERO_CUENTA, SALDO, TIPO_CUENTA, ID_USUARIO) "
                    + "VALUES('" + nuevaCuenta.getNumCuenta() + "', "
                    + "" + nuevaCuenta.getSaldo() + ", "
                    + "'" + nuevaCuenta.getTipoCuenta() + "', "
                    + "" + nuevaCuenta.getIdUsuario() + ");";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "DELETE FROM CUENTAS WHERE ID = " + id + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public List<?> listar() {
        List<Cuenta> cuentas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM CUENTAS;";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    int id = resultadoConsulta.getInt("ID");
                    String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                    double saldo = resultadoConsulta.getDouble("SALDO");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                    int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                    Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                    cuentas.add(cuenta);
                }
                return cuentas;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    public List<?> listarPorUsuario(String idConsulta) {
        List<Cuenta> cuentas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM CUENTAS WHERE ID_USUARIO = " + idConsulta + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    int id = resultadoConsulta.getInt("ID");
                    String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                    double saldo = resultadoConsulta.getDouble("SALDO");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                    int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                    Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                    cuentas.add(cuenta);
                }
                return cuentas;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object buscar(String idCuenta) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM CUENTAS WHERE ID = " + idCuenta + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()) {
                int id = resultadoConsulta.getInt("ID");
                String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                double saldo = resultadoConsulta.getDouble("SALDO");
                String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                return cuenta;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    public Object buscarPorCuenta(String idConsulta) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM CUENTAS WHERE ID_USUARIO = " + idConsulta + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()) {
                int id = resultadoConsulta.getInt("ID");
                String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                double saldo = resultadoConsulta.getDouble("SALDO");
                String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                return cuenta;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    public void eliminarPorUsuario(String idUsuario) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "DELETE FROM CUENTAS WHERE ID_USUARIO = " + idUsuario + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

}
