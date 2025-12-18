package com.cristian.servies;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.cristian.conector.conexionMysql;

public class pagoService {

    public void GestorPagos(Scanner input){
        String gestorPago = "INSERT INTO pagos(prestamo_id, fecha_pago, monto) VALUES(?, ?, ?)";
        String prestamoPago = "SELECT monto FROM prestamos WHERE id = ?";
        try (Connection conexion = conexionMysql.conectar()) {

            System.out.println("Ingrese el id del prestamos que quieres pagar");
            int prestamoId = input.nextInt();
            input.nextLine();

            double montoActual = 0;

            try (PreparedStatement stmt = conexion.prepareStatement(prestamoPago)) {
                stmt.setInt(1, prestamoId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    montoActual = rs.getDouble("monto");
                } else {
                    System.out.println("El prestamo no exite, digite un prestamo que este registrado");
                    return;
                }
            }

                System.out.printf("Monto restante del prestamo: %,.2f%n", montoActual);

                System.out.println("Ingrese el monto a pagar:");
                double montoPago = input.nextDouble();
                input.nextLine();

                if (montoPago <= 0) {
                    System.out.println("Que Que!!! Imposible mano el monto es de valor negativo");
                    System.out.println("Tratame como el elemento 58 de la tabla periodica");
                    return;
                } else if (montoPago > montoActual){
                    System.out.println("Quieres pagar mas de lo normal, nose tu pero no se puede");
                    return;
                }

            try (PreparedStatement stmt = conexion.prepareStatement(gestorPago)) {
                stmt.setInt(1, prestamoId);
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setDouble(3, montoPago);

                stmt.executeUpdate();
                System.out.println("El pago fue registrado correctamente");
            }    


            
        } catch (Exception e) {
            System.out.println("Error en Realizar pago");
            e.printStackTrace();
        }
    }

    public void registrarPago(Scanner input) {

        String consultaMonto = "SELECT monto FROM prestamos WHERE id = ?";
        String insertPago = "INSERT INTO pagos(prestamo_id, fecha_pago, monto) VALUES(?, ?, ?)";

        try (Connection conexion = conexionMysql.conectar()) {

            System.out.print("Ingrese el Id del prestamo: ");
            int prestamoId = input.nextInt();
            input.nextLine();

            double montoActual = 0;

            try (PreparedStatement stmt = conexion.prepareStatement(consultaMonto)) {
                stmt.setInt(1, prestamoId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    montoActual = rs.getDouble("monto");
                } else {
                    System.out.println("El préstamo no existe.");
                    return;
                }
            }

            System.out.printf("Monto restante del préstamo: %,.2f%n", montoActual);

            System.out.print("Ingrese el monto a pagar: ");
            double montoPago = input.nextDouble();
            input.nextLine();

            if (montoPago <= 0) {
                System.out.println("El monto del pago debe ser mayor a 0.");
                return;
            }

            if (montoPago > montoActual) {
                System.out.println("Error: el monto del pago es mayor al saldo del préstamo.");
                return;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(insertPago)) {
                stmt.setInt(1, prestamoId);
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setDouble(3, montoPago);

                stmt.executeUpdate();
                System.out.println("Pago registrado correctamente ✔");
            }

        } catch (SQLException e) {
            System.out.println("Error en la operación.");
            e.printStackTrace();
        }
    }

    public void pazYSalvo(Scanner input) {

        String query = "SELECT monto, estado FROM prestamos WHERE id = ?";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query)) {

            System.out.print("Ingrese el ID del préstamo: ");
            int prestamoId = input.nextInt();
            input.nextLine();

            stmt.setInt(1, prestamoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double monto = rs.getDouble("monto");
                String estado = rs.getString("estado");

                System.out.println("Estado del préstamo: " + estado);
                System.out.println("Saldo pendiente: " + monto);

                if (monto == 0) {
                    System.out.println("EL PRÉSTAMO ESTÁ PAZ Y SALVO");
                } else {
                    System.out.println("EL PRÉSTAMO AÚN TIENE SALDO PENDIENTE");
                }

            } else {
                System.out.println("El préstamo no existe.");
            }

        } catch (Exception e) {
            System.out.println("Error al consultar paz y salvo");
            e.printStackTrace();
        }
    } 
    
    public void listaHistoricoPagos(){
        String query = "SELECT c.nombre AS titular, p.monto AS monto_prestamo, pg.monto AS monto_pago, pg.fecha_pago AS fecha FROM pagos pg JOIN prestamos p ON pg.prestamo_id = p.id JOIN clientes c ON p.cliente_id = c.id";
        String archivo = "src/main/java/com/cristian/data/pagos.txt";

        try (Connection conexion = conexionMysql.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            
            boolean haypago = false;

            System.out.println("-------- Lista de pago --------");
            pw.println("-------- Lista de pago --------");

            while (rs.next()) {
                haypago = true;

                String titular = rs.getString("Titular");
                double monto_prestamo = rs.getDouble("monto_prestamo");
                double monto_pago = rs.getDouble("monto_pago");
                String fecha = rs.getString("fecha");

                System.out.println("titular: " + titular);
                pw.println("titular: " + titular);

                System.out.println("Monto del prestamo: " + monto_prestamo);
                pw.println("Monto del prestamo: " + monto_prestamo);
                
                System.out.println("Monto del pago: " + monto_pago);
                pw.println("Monto del pago: " + monto_pago);

                System.out.println("Fecha del pago: "  + fecha);
                pw.println("Fecha del pago: "  + fecha);

                System.out.println("------------------------------");
                pw.println("----------------------------");

                if (!haypago) {
                    System.out.println("No hay pagos registrados");
                    pw.println("No hay pagos registrados");
                }

            }
        } catch (Exception e) {
            System.out.println("Error en consultar la lista del prestamos");
            e.printStackTrace();
        }
    }
}

