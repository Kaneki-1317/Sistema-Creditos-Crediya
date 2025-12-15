package com.cristian.servies;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

import com.cristian.conector.conexionMysql;

public class prestamoService {
    
    public void crearPrestamoCliente(Scanner input){
        String query = "INSERT INTO prestamos(cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = conexionMysql.conectar()) {

            System.out.println("Ingrese los datos del prestamo");

            System.out.println("ID del cliente que va a recibir el prestamo");
            int cliente_id = input.nextInt();


            System.out.println("ID del empleado que hizo el prestamo");
            int empleado_id = input.nextInt();

            System.out.println("Monto del prestamo");
            double monto = input.nextDouble();

            System.out.println("Tasa de interes");
            double interes = input.nextDouble();

            System.out.println("Cantidad de cuotas del prestamo");
            int cuotas = input.nextInt();

            if (monto <= 0) {
                System.out.println("No se acepta que el monto sea negativo");
                return;
            } else if (interes <= 0) {
                System.out.println("No se acpeta que el interes sea negativo");
                return;
            } else if (cuotas <= 0 ) {
                System.out.println("No se acepta que el numero de cuotas sea negativos");
                return;
            }

            input.nextLine();

            LocalDate fecha = LocalDate.now();

            System.out.println("Ingrese el estado del prestamo");
            String estado = input.nextLine();

            double montoTotal = monto + (monto * interes / 100);
            double cuotaMensual = montoTotal / cuotas;


            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, cliente_id);
                stmt.setInt(2, empleado_id);
                stmt.setDouble(3, montoTotal);
                stmt.setDouble(4, interes);
                stmt.setInt(5, cuotas);
                try {
                    java.sql.Date fechaSql = java.sql.Date.valueOf(fecha);
                    stmt.setDate(6, fechaSql);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Fecha invalida, use el formato YYYY-MM-DD");
                    return;
                }
                stmt.setString(7, estado);

                int filas = stmt.executeUpdate();
                System.out.println("Prestamos creado correctamente");

                System.out.println();

                System.out.println("-------- Total a pagar --------");
                System.out.printf("  Monto total con interes: %,.2f%n",  montoTotal);
                System.out.printf("  Valor de cada cuota mensual: %,.2f%n", cuotaMensual);

                System.out.println();
                System.out.println("filas afectadas " + filas );

            } catch (Exception e) {
                System.out.println("Error en crear prestamo");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Error en el INSERT");
            e.printStackTrace();
        }
    }

    public void listaPrestamo() {

        String query = "SELECT * FROM prestamos";
        String archivo = "src/main/java/com/cristian/data/prestamos.txt";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {

            boolean hayPrestamo = false;

            System.out.println("-------- Lista de Prestamos --------");
            pw.println("-------- Lista de Prestamos --------");

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
                pw.println("ID: " + id);

                System.out.println("Cliente ID: " + clienteId);
                pw.println("Cliente ID: " + clienteId);

                System.out.println("Empleado ID: " + empleadoId);
                pw.println("Empleado ID: " + empleadoId);

                System.out.printf("Monto total: %,.2f%n", monto);
                pw.printf("Monto total: %,.2f%n", monto);

                System.out.println("Interés: " + interes);
                pw.println("Interés: " + interes);

                System.out.println("Cuotas: " + cuotas);
                pw.println("Cuotas: " + cuotas);

                System.out.println("Fecha inicio: " + fechaInicio);
                pw.println("Fecha inicio: " + fechaInicio);

                System.out.println("Estado: " + estado);
                pw.println("Estado: " + estado);

                System.out.println("---------------------------------");
                pw.println("---------------------------------");
            }

            if (!hayPrestamo) {
                System.out.println("No hay préstamos registrados");
                pw.println("No hay préstamos registrados");
            }

        } catch (Exception e) {
            System.out.println("Error al consultar la lista de préstamos");
            e.printStackTrace();
        }
    }

    public void consultarPrestamosEmpleado(Scanner input){
        String query = "SELECT e.nombre AS 'Empleado', p.id AS 'Prestamo_id', c.nombre AS 'Cliente',  p.monto, p.interes, p.cuotas, p.fecha_inicio AS 'Fecha', p.estado  FROM prestamos p JOIN clientes c ON p.cliente_id = c.id JOIN empleado e ON p.empleado_id = e.id WHERE e.documento = ?";
        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            
            System.out.println("Ingrese el documento del Empleado: ");
            String documento = input.nextLine();

            stmt.setString(1, documento);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println();
                System.out.println("--------------------------------------");
                System.out.println("   Empleado: " + rs.getString("Empleado"));
                System.out.println("   Prestamos_id: " + rs.getString("Prestamo_id"));
                System.out.println("   Cliente: " + rs.getString("Cliente"));
                System.out.println("   Monto: " + String.format("%.2f", rs.getDouble("monto")));
                System.out.println("   Interes: " + rs.getDouble("interes"));
                System.out.println("   Cuotas: " + rs.getInt("cuotas"));
                System.out.println("   Fecha: " + rs.getDate("fecha"));
                System.out.println("   Estado: " + rs.getString("estado"));
                System.out.println("--------------------------------------");
                System.out.println();
            } else {
                System.out.println("No hay ningun prestamo con ese documento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultarPrestamosCliente(Scanner input){
        String query = "SELECT c.nombre AS 'Cliente', p.id AS 'Prestamo_id', e.nombre AS 'Empleado',  p.monto, p.interes, p.cuotas, p.fecha_inicio AS 'Fecha', p.estado  FROM prestamos p JOIN clientes c ON p.cliente_id = c.id JOIN empleado e ON p.empleado_id = e.id WHERE c.documento = ?";
        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            
            System.out.println("Ingrese el documento del Cliente: ");
            String documento = input.nextLine();

            stmt.setString(1, documento);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println();
                System.out.println("--------------------------------------");
                System.out.println("   Cliente: " + rs.getString("Cliente"));
                System.out.println("   Prestamos_id: " + rs.getString("Prestamo_id"));
                System.out.println("   Empleado: " + rs.getString("Empleado"));
                System.out.println("   Monto: " + String.format("%.2f", rs.getDouble("monto")));
                System.out.println("   Interes: " + rs.getDouble("interes"));
                System.out.println("   Cuotas: " + rs.getInt("cuotas"));
                System.out.println("   Fecha: " + rs.getDate("fecha"));
                System.out.println("   Estado: " + rs.getString("estado"));
                System.out.println("--------------------------------------");
                System.out.println();
            } else {
                System.out.println("No hay ningun prestamo con ese documento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SimulacionPrestamos(Scanner input){
        System.out.println("Ingrese los datos que quieres simular del prestamo");

        System.out.println("Monto del prestamo");
        double monto = input.nextDouble();

        System.out.println("Tasa de interes");
        double interes = input.nextDouble();

        System.out.println("Cantidad de cuotas del prestamo");
        int cuotas = input.nextInt();

        if (monto <= 0 || interes <= 0 || cuotas <= 0) {
            System.out.println("Datos invalido, revise monto, interes y cuotas");
            return;
        }

        input.nextLine();

        double montoTotal = monto + (monto * interes / 100);
        double cuotaMensual = montoTotal / cuotas;

        System.out.println();

        System.out.println("-------- Total a pagar --------");
        System.out.printf("  Monto total con interes: %,.2f%n",  montoTotal);
        System.out.printf("  Valor de cada cuota mensual: %,.2f%n", cuotaMensual);
        System.out.println("-------------------------------");

        System.out.println();

        System.out.println("Precione Enter para continuar");
        input.nextLine();
    }

    public void cambiarEstado(Scanner input){
        try (Connection conexion = conexionMysql.conectar()) {
            System.out.println("Ingrese el ID del prestamos a actualizar: ");
            int prestamoId = input.nextInt();
            input.nextLine();

            System.out.println("Ingrese el nuevo estado: ");
            String nuevoEstado = input.nextLine();

            String query = "UPDATE prestamos SET estado = ? WHERE id = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nuevoEstado);
                stmt.setInt(2, prestamoId);

                int filas = stmt.executeUpdate();
                if (filas > 0 ) {
                    System.out.println("El estado ha actualizado corrextamente");
                } else {
                    System.out.println("No se encontro el prestamos con ese id");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

