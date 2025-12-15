package com.cristian.servies;

import java.util.Scanner;

import com.cristian.conector.conexionMysql;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class clienteService {
    public void registrarCliente(Scanner input){
        String query = "INSERT INTO clientes(nombre, documento, correo, telefono) VALUES(?, ?, ?, ?)";
        try (Connection conexion = conexionMysql.conectar()) {
            
            System.out.println("Ingrese los datos del cliente a registrar");

            input.nextLine();

            System.out.println("Nombre: ");
            String nombre = input.nextLine();

            System.out.println("Documento: ");
            String documento = input.nextLine();

            System.out.println("Correo: ");
            String correo = input.nextLine();

            System.out.println("Telefono: ");
            String telefono = input.nextLine();

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombre);
                stmt.setString(2, documento);
                stmt.setString(3, correo);
                stmt.setString(4, telefono);

                int filas = stmt.executeUpdate();
                System.out.println("Cliente fue registrado correctamente");
                System.out.println("Filas afectadas: " + filas);

                
            } catch (Exception e) {
                System.out.println("Error al registrar al cliente");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error en el INSERT");
            e.printStackTrace();
        }
    }

    public void listaDeCliente(){
        String query = "SELECT * FROM clientes";
        String archivo = "src/main/java/com/cristian/data/clientes.txt";

        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            
            boolean hayCliente = false;

            System.out.println("-------- Lista de CLientes --------");
            pw.println("-------- Lista de CLientes --------");

            while (rs.next()) {
                hayCliente = true;
                
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String documento = rs.getString("documento");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");

                System.out.println("ID: " + id);
                pw.println("ID: " + id);

                System.out.println("Nombre: " + nombre);
                pw.println("Nombre: " + nombre);

                System.out.println("Correo: " + correo);
                pw.println("Correo: " + correo);

                System.out.println("Documento: " + documento);
                pw.println("Documento: " + documento);

                System.out.println("Telefono " + telefono);
                pw.println("Telefono: " + telefono);

                System.out.println("---------------------------------");
                pw.println("---------------------------------");
            }

            if (!hayCliente) {
                System.out.println("No hay empleados registrados");
                pw.print("No hay empleados registrados");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la lista de empleados");
            e.printStackTrace();
        }

    }

    public void consultarCliente(Scanner input){
        String query = "SELECT * FROM clientes WHERE documento = ?";
        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            
            System.out.println("ingrese el documento a buscar: ");
            input.nextLine();
            String documento = input.nextLine();

            stmt.setString(1, documento);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println();
                System.out.println("--------------------------------------");
                System.out.println("   ID: " + rs.getInt("id"));
                System.out.println("   Nombre: " + rs.getString("nombre"));
                System.out.println("   Documento: " + rs.getString("documento"));
                System.out.println("   Correo: " + rs.getString("correo"));
                System.out.println("   Telefono: " + rs.getString("telefono"));
                System.out.println("--------------------------------------");
                System.out.println();
            } else {
                System.out.println("No se encontro ningun empleado con ese documento");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
