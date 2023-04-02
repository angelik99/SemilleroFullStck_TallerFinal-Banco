package com.semillero.banco.service;


import com.semillero.banco.entity.Cuenta;
import com.semillero.banco.entity.Usuario;
import com.semillero.banco.interfaces.Servicio;
import com.semillero.banco.repository.CuentaRepository;
import com.semillero.banco.repository.TransaccionRepository;
import com.semillero.banco.repository.UsuarioRepository;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aleon
 */
public class UsuarioService implements Servicio {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    public void crear(Map map) {
        String nombre = (String) map.get("nombre");
        String apellido = (String) map.get("apellido");
        String cedula = (String) map.get("cedula");

        Usuario nuevoUsuario = new Usuario(nombre, apellido, cedula);
        usuarioRepository.crear(nuevoUsuario);
    }

    //Debemos eliminar sus llaves foranea para elimar el usuario
    @Override
    public void eliminar(String id) {
        CuentaRepository cuentaRepository = new CuentaRepository();
        TransaccionRepository transaccionRepository = new TransaccionRepository();
        Cuenta cuenta = (Cuenta) cuentaRepository.buscarPorCuenta(id);
        transaccionRepository.eliminarPorCuenta(String.valueOf(cuenta.getId()));
        cuentaRepository.eliminarPorUsuario(String.valueOf(cuenta.getIdUsuario()));
        usuarioRepository.eliminar(id);
    }


    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.listar();
    }

    @Override
    public Usuario buscar(String id) throws Exception {
        Object usuario = usuarioRepository.buscar(id);
        if (usuario == null) {
            throw new Exception("No se encontró el usuario");
        } else {
            return (Usuario) usuario;
        }
    }

}
