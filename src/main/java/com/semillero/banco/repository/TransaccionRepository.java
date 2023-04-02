/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semillero.banco.repository;

import com.semillero.banco.entity.Cuenta;
import com.semillero.banco.entity.Transaccion;
import com.semillero.banco.exceptions.RegistroNullException;
import com.semillero.banco.exceptions.SaldoInsuficiente;
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
 * Creacion de clase para el manejo de CRUD de las transacciones
 *
 * @author aleon
 */
public class TransaccionRepository implements Repositorio {

    private final Conexion conexion;
    private final CuentaRepository cuentaRepository;

    public TransaccionRepository() {
        conexion = new Conexion();
        cuentaRepository = new CuentaRepository();
    }

    @Override
    public void crear(Object objeto) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            Transaccion transaccion = (Transaccion) objeto;
            String sql = "INSERT INTO TRANSACCIONES (FECHA, HORA, TIPO_TRANSACCION, MONTO, ID_CUENTA, TIPO_CUENTA_DESTINO)"
                    + "VALUES('" + transaccion.getFecha() + "', "
                    + "'" + transaccion.getHora() + "', "
                    + "'" + transaccion.getTipoTransaccion() + "', "
                    + "" + transaccion.getMonto() + ", "
                    + "" + transaccion.getIdCuenta() + ", "
                    + "'" + transaccion.getTipoCuenta() + "');";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "DELETE FROM TRANSACCIONES WHERE ID = " + id + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    public void eliminarPorCuenta(String idCuenta) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = " + idCuenta + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public List<?> listar() {
        List<Transaccion> transacciones = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM TRANSACCIONES";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    int id = resultadoConsulta.getInt("ID");
                    String fecha = resultadoConsulta.getString("FECHA");
                    String hora = resultadoConsulta.getString("HORA");
                    String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                    double monto = resultadoConsulta.getDouble("MONTO");
                    int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                    Transaccion transaccion = new Transaccion(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuenta);
                    transacciones.add(transaccion);
                }
                return transacciones;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    public void actualizarSaldo(Object objeto) {
        Cuenta cuenta = (Cuenta) objeto;
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "UPDATE CUENTAS "
                    + "SET SALDO = " + cuenta.getSaldo() + " "
                    + "WHERE ID = " + cuenta.getId() + ";";
            Statement sentencia = conn.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    public int consultarCantidadTransacciones(Transaccion transaccion) throws RegistroNullException {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT count('" + transaccion.getTipoTransaccion() + "') AS CANTIDAD_TRANSACCIONES "
                    + "FROM TRANSACCIONES "
                    + "WHERE ID_CUENTA = " + transaccion.getIdCuenta() + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                int cantidadTransacciones = resultadoConsulta.getInt("CANTIDAD_TRANSACCIONES");

                return cantidadTransacciones;
            } else {
                throw new RegistroNullException("No se encontraron transacciones");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        } catch (RegistroNullException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void depositar(Object objeto) throws RegistroNullException {
        Transaccion transaccion = (Transaccion) objeto;
        String idCuenta = String.valueOf(transaccion.getIdCuenta());
        Cuenta cuentaTransacción = (Cuenta) cuentaRepository.buscar(idCuenta);
        if (cuentaTransacción != null) {
            int cantidadDepositos = consultarCantidadTransacciones(transaccion);
            if (cantidadDepositos < 2) {
                double bonoBienvenida = (transaccion.getMonto() * 0.5) / 100;
                transaccion.setMonto(transaccion.getMonto() + bonoBienvenida);
                cuentaTransacción.setSaldo(cuentaTransacción.getSaldo() + transaccion.getMonto());
                crear(transaccion);
                actualizarSaldo(cuentaTransacción);
            } else {
                cuentaTransacción.setSaldo(cuentaTransacción.getSaldo() + transaccion.getMonto());
                crear(transaccion);
                actualizarSaldo(cuentaTransacción);
            }
        } else {
            throw new RegistroNullException(idCuenta + ": Error, esta cuenta no existe");
        }
    }

    public void retirar(Object objeto) throws RegistroNullException, SaldoInsuficiente {
        // Hacemos lo mismo que con la funcion depositar()
        Transaccion transaccion = (Transaccion) objeto;
        String idCuenta = String.valueOf(transaccion.getIdCuenta());
        Cuenta cuentaTransaccion = (Cuenta) cuentaRepository.buscar(idCuenta);
        // Validamos que la cuenta exista
        if (cuentaTransaccion != null) {
            // Validamos el número de retiros
            int cantidadRetiros = consultarCantidadTransacciones(transaccion);
            if (cantidadRetiros < 3) {
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()) {
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new SaldoInsuficiente("No tiene fondos suficientes para hacer el retiro");
                }
            } else {
                // Como ha realizado más de 3 retiros restamos el 1% del valor que retira
                double cobroAdicional = (transaccion.getMonto() * 1) / 100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto() + cobroAdicional);

                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()) {
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new SaldoInsuficiente("No tiene fondos suficientes para hacer el retiro");
                }
            }
        } else {
            throw new RegistroNullException(idCuenta + ": Error, esta cuenta no existe");
        }
    }

    public void transferir(Object objeto) throws RegistroNullException, SaldoInsuficiente {
        Transaccion transaccion = (Transaccion) objeto;
        String idCuentaTransaccion = String.valueOf(transaccion.getIdCuenta());
        Cuenta cuentaTransaccion = (Cuenta) cuentaRepository.buscar(idCuentaTransaccion);
        if (cuentaTransaccion != null) {
            // cobros adicionales dependiendo de los tipos de cuentas
            if (cuentaTransaccion.getTipoCuenta().equals("Ahorro") && !cuentaTransaccion.getTipoCuenta().equals(transaccion.getTipoCuenta()) ) {
                // Se hace un cobro adicional del 1.5 %
                double cobroAdicional = (transaccion.getMonto() * 1.5) / 100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto() + cobroAdicional);
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()) {
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new SaldoInsuficiente("No tiene saldo suficientes para hacer el retiro");
                }
            } else if (cuentaTransaccion.getTipoCuenta().equals("Corriente") && !cuentaTransaccion.getTipoCuenta().equals(transaccion.getTipoCuenta())) {
                // Se hace un cobro adicional del 2 %
                double cobroAdicional = (transaccion.getMonto() * 2) / 100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto() + cobroAdicional);
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()) {
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new SaldoInsuficiente("No tiene fondos suficientes para hacer el retiro");
                }
            } else {
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()) {
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new SaldoInsuficiente("No tiene fondos suficientes para hacer el retiro");
                }
            }
        } else {
            throw new RegistroNullException(idCuentaTransaccion + ": Error, esta cuenta no existe");
        }
    }

    public List<?> listarPorId(String idConsulta) {
        List<Transaccion> transacciones = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM TRANSACCIONES WHERE ID_CUENTA = " + idConsulta + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    int id = resultadoConsulta.getInt("ID");
                    String fecha = resultadoConsulta.getString("FECHA");
                    String hora = resultadoConsulta.getString("HORA");
                    String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                    double monto = resultadoConsulta.getDouble("MONTO");
                    int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                    Transaccion transaccion = new Transaccion(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuenta);
                    transacciones.add(transaccion);
                }
                return transacciones;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object buscar(String idTransaccion) {
        try (Connection conn = DriverManager.getConnection(conexion.getCadenaConexion())) {
            String sql = "SELECT * FROM TRANSACCIONES WHERE ID = " + idTransaccion + ";";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()) {
                int id = resultadoConsulta.getInt("ID");
                String fecha = resultadoConsulta.getString("FECHA");
                String hora = resultadoConsulta.getString("HORA");
                String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                double monto = resultadoConsulta.getDouble("MONTO");
                int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                Transaccion transaccion = new Transaccion(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuenta);
                return transaccion;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion TransaccionRepo: " + e.getMessage());
        }
        return null;
    }

}
