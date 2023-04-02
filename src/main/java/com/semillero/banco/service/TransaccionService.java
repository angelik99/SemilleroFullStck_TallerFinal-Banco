/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.service;

import com.semillero.banco.entity.Cuenta;
import com.semillero.banco.entity.Transaccion;
import com.semillero.banco.interfaces.Servicio;
import com.semillero.banco.repository.TransaccionRepository;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arami
 */
public class TransaccionService implements Servicio {
    private final TransaccionRepository transaccionRepository;

    public TransaccionService() {
        transaccionRepository = new TransaccionRepository();
    }

    @Override
    public void crear(Map map) throws Exception {
        String fecha = (String) map.get("fecha");
        String hora = (String) map.get("hora");
        String tipoTransaccion = (String) map.get("tipoTransaccion");
        double monto = (double) map.get("monto");
        int idCuenta = (int) map.get("idCuenta");
        String tipoCuentaDestino = "";

        // Como el tipo de cuenta destino puede ser nulo debemos validar su valor
        if (map.get("tipoCuentaDestino") == null){
            tipoCuentaDestino = "";
        } else {
            tipoCuentaDestino = (String) map.get("tipoCuentaDestino");
        }

        Transaccion nuevaTransaccion = new Transaccion(fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuentaDestino);

        // Hacemos unas ultimas validaciones para saber que tipo de transacción se desea realizar
        switch (tipoTransaccion){
            case "Depositar":
                try{
                    transaccionRepository.depositar(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "Retirar":
                try {
                    transaccionRepository.retirar(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "Transferir":
                try{
                    transaccionRepository.transferir(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            default:
                throw new Exception("Tipo de transaccion no valida");
        }
    }

    @Override
    public void eliminar(String id) {
        transaccionRepository.eliminar(id);
    }

    @Override
    public List<Transaccion> listar() {
        return (List<Transaccion>) transaccionRepository.listar();
    }

    public List<Transaccion> listarPorId(String id){
        return (List<Transaccion>) transaccionRepository.listarPorId(id);
    }

    @Override
    public Object buscar(String id) throws Exception {
        Object transaccion = transaccionRepository.buscar(id);
        if (transaccion != null){
            return (Cuenta) transaccion;
        } else {
            throw new Exception("No se encontro la transacción");
        }
    }
    
}
