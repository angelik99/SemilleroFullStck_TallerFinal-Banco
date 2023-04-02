/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semillero.banco.entity.Transaccion;
import com.semillero.banco.service.TransaccionService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aleon
 */
public class TransaccionController  extends HttpServlet {
    private final TransaccionService transaccionService;
    private final ObjectMapper mapper;

    public TransaccionController() {
        transaccionService = new TransaccionService();
        mapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null){
            List<Transaccion> transacciones = transaccionService.listar();
            String json = mapper.writeValueAsString(transacciones);
            response.setContentType("application/json");
            response.getWriter().println(json);
        } else {
            switch (path){
                case "/listar":
                    try {
                        String id = request.getParameter("id");
                        List<Transaccion> transacciones = transaccionService.listarPorId(id);
                        String json = mapper.writeValueAsString(transacciones);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    } catch (Exception e){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        Map<String, String> error = new HashMap<>();
                        error.put("error", e.getMessage());
                        String json = mapper.writeValueAsString(error);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    }
                    break;
                case "/buscar":
                    try{
                        String id = request.getParameter("id");
                        Transaccion transaccion = (Transaccion) transaccionService.buscar(id);
                        String json = mapper.writeValueAsString(transaccion);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    } catch (Exception e){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        Map<String, String> error = new HashMap<>();
                        error.put("error", e.getMessage());
                        String json = mapper.writeValueAsString(error);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    }
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "No se encontro la transaccion");
                    String json = mapper.writeValueAsString(error);
                    response.setContentType("application/json");
                    response.getWriter().println(json);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getContentType();
        if (content != null && content.equals("application/json")){
            Map<String, Object> transaccionMap = mapper.readValue(request.getInputStream(), HashMap.class);
            try {
                transaccionService.crear(transaccionMap);
                response.setStatus(HttpServletResponse.SC_CREATED);
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Transacción exitosa");
                String json = mapper.writeValueAsString(respuesta);
                response.setContentType("application/json");
                response.getWriter().println(json);
            } catch (Exception e){
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                String json = mapper.writeValueAsString(error);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "El contenido debe ser en formato JSON");
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        try{
            transaccionService.eliminar(id);

            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Transacción eliminada con exito");
            String json = mapper.writeValueAsString(respuesta);
            response.setContentType("application/json");
            response.getWriter().println(json);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }
    
}
