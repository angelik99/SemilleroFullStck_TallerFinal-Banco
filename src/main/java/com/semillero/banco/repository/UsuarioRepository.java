/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.repository;

import com.semillero.banco.entity.Usuario;
import com.semillero.banco.interfaces.Repositorio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creacion de clase para el manejo de CRUD de los usuarios
 *
 * @author aleon
 */
public class UsuarioRepository implements Repositorio {

    private final Conexion conexion;

    public UsuarioRepository() {
        conexion = new Conexion();
    }

    @Override
    public void crear(Object objeto) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            // Hacemos un parseo del objeto entrante
            Usuario nuevoUsuario = (Usuario) objeto;
            String sql = "INSERT INTO USUARIOS (NOMBRE, APELLIDO, CEDULA) "
                    + "VALUES('" + nuevoUsuario.getNombre() + "', "
                    + "'" + nuevoUsuario.getApellido() + "', "
                    + "'" + nuevoUsuario.getCedula() + "');";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "DELETE FROM USUARIOS WHERE ID=" + id + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public List<?> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM USUARIOS;";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    int id = resultadoConsulta.getInt("ID");
                    String nombre = resultadoConsulta.getString("NOMBRE");
                    String apellido = resultadoConsulta.getString("APELLIDO");
                    String cedula = resultadoConsulta.getString("CEDULA");

                    Usuario usuario = new Usuario(id, nombre, apellido, cedula);
                    usuarios.add(usuario);
                }
                return usuarios;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object buscar(String idUsuario) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM USUARIOS WHERE ID=" + idUsuario + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()) {
                int id = resultadoConsulta.getInt("ID");
                String nombre = resultadoConsulta.getString("NOMBRE");
                String apellido = resultadoConsulta.getString("APELLIDO");
                String cedula = resultadoConsulta.getString("CEDULA");

                Usuario usuario = new Usuario(id, nombre, apellido, cedula);
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

}
