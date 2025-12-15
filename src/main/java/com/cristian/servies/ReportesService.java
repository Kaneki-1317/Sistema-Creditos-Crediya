package com.cristian.servies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cristian.conector.conexionMysql;

public class ReportesService {
    public void prestamosActivos(){
        String query = "SELECT * FROM prestamos WHERE estado = 'Activo'";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){

            boolean hayPrestamo = false;

            System.out.println("-------- Lista de Prestamos Activos --------");


            while (rs.next()) {
                hayPrestamo = true;

                int id = rs.getInt("id");
                int clienteId = rs.getInt("cliente_id");
                int empleadoId = rs.getInt("empleado_id");
                double monto = rs.getDouble("monto");
                double interes = rs.getDouble("interes");
                int cuotas = rs.getInt("cuotas");
                String fechaInicio = rs.getString("fecha_inicio");
                String estado = rs.getString("estado");

                System.out.println("ID: " + id);

                System.out.println("Cliente ID: " + clienteId);

                System.out.println("Empleado ID: " + empleadoId);

                System.out.printf("Monto total: %,.2f%n", monto);

                System.out.println("Interés: " + interes);

                System.out.println("Cuotas: " + cuotas);

                System.out.println("Fecha inicio: " + fechaInicio);

                System.out.println("Estado: " + estado);

                System.out.println("---------------------------------");
            }

            if (!hayPrestamo) {
                System.out.println("No hay préstamos Activos");
            }

        } catch (Exception e) {
            System.out.println("Error al consultar los préstamos prestamos activos");
            e.printStackTrace();
        }
    }

    public void prestamosVencidos(){
        String query = "SELECT * FROM prestamos WHERE estado = 'Vencido'";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){

            boolean hayPrestamo = false;

            System.out.println("-------- Lista de Prestamos Vencidos --------");


            while (rs.next()) {
                hayPrestamo = true;

                int id = rs.getInt("id");
                int clienteId = rs.getInt("cliente_id");
                int empleadoId = rs.getInt("empleado_id");
                double monto = rs.getDouble("monto");
                double interes = rs.getDouble("interes");
                int cuotas = rs.getInt("cuotas");
                String fechaInicio = rs.getString("fecha_inicio");
                String estado = rs.getString("estado");

                System.out.println("ID: " + id);

                System.out.println("Cliente ID: " + clienteId);

                System.out.println("Empleado ID: " + empleadoId);

                System.out.printf("Monto total: %,.2f%n", monto);

                System.out.println("Interés: " + interes);

                System.out.println("Cuotas: " + cuotas);

                System.out.println("Fecha inicio: " + fechaInicio);

                System.out.println("Estado: " + estado);

                System.out.println("---------------------------------");
            }

            if (!hayPrestamo) {
                System.out.println("No hay préstamos Vencidos");
            }

        } catch (Exception e) {
            System.out.println("Error al consultar los préstamos vencidos");
            e.printStackTrace();
        }
    }

    public void clientesMorosos() {

        String query = "SELECT DISTINCT c.* FROM clientes c JOIN prestamos p ON c.id = p.cliente_id WHERE p.estado = 'Vencido'";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            boolean hay = false;
            System.out.println("-------- Clientes Morosos --------");

            while (rs.next()) {
                hay = true;

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Documento: " + rs.getString("documento"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("---------------------------------");
            }

            if (!hay) {
                System.out.println("No hay clientes morosos");
            }

        } catch (Exception e) {
            System.out.println("Error al consultar clientes morosos");
            e.printStackTrace();
        }
    }

    
        
}
