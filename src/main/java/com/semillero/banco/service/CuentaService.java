/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.service;

import com.semillero.banco.entity.Cuenta;
import com.semillero.banco.interfaces.Servicio;
import com.semillero.banco.repository.CuentaRepository;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arami
 */
public class CuentaService implements Servicio {
    private final CuentaRepository cuentaRepository;

    public CuentaService() {
        cuentaRepository = new CuentaRepository();
    }

    @Override
    public void crear(Map map) {
        String numCuenta = (String) map.get("numCuenta");
        double saldo = (double) map.get("saldo");
        String tipoCuenta = (String) map.get("tipoCuenta");
        int idUsuario = (int) map.get("idUsuario");

        Cuenta newCuenta = new Cuenta(numCuenta, saldo, tipoCuenta, idUsuario);
        cuentaRepository.crear(newCuenta);
    }

    @Override
    public void eliminar(String id) {
        cuentaRepository.eliminar(id);
    }

    @Override
    public List<?> listar() {
        return (List<Cuenta>) cuentaRepository.listar();
    }

    public List<?> listarPorUsuario(String id){
        return (List<Cuenta>) cuentaRepository.listarPorUsuario(id);
    }

    @Override
    public Object buscar(String id) throws Exception {
        Object cuenta = cuentaRepository.buscar(id);
        if (cuenta != null){
            return (Cuenta) cuenta;
        } else {
            throw new Exception("No se encontro la cuenta");
        }
    }
    
    public Object buscarPorCuenta(String idUsuario) throws Exception {
        Object cuenta = cuentaRepository.buscarPorCuenta(idUsuario);
        if (cuenta != null){
            return (Cuenta) cuenta;
        } else {
            throw new Exception("No se encontro la cuenta");
        }
    }
    
}
