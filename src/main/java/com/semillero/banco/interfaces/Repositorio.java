/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.semillero.banco.interfaces;

import java.util.List;

/**
 *
 * @author aleon
 */
public interface Repositorio {

    public void crear(Object objeto);

    public void eliminar(String id);

    public List<?> listar();

    public Object buscar(String id);
}
