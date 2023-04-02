/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.semillero.banco.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Creacion de interfaz para el manejo de metodos
 *
 * @author aleon
 */
public interface Servicio {

    public void crear(Map datos) throws Exception;

    public void eliminar(String id);

    public void actualizar(Map datos);

    public List<?> listar();

    public Object buscar(String id) throws Exception;
}
