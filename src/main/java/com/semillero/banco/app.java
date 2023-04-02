/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.semillero.banco;

import com.semillero.banco.controller.CuentaController;
import com.semillero.banco.controller.TransaccionController;
import com.semillero.banco.controller.UsuarioController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 *
 * @author aleon
 */
public class app {

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.setHandler(new DefaultHandler());

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");
        context.addServlet(UsuarioController.class, "/usuario/*");
        context.addServlet(CuentaController.class, "/cuenta/*");
        context.addServlet(TransaccionController.class, "/transacciones/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
