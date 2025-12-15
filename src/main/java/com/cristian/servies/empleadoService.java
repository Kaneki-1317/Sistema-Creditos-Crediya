package com.cristian.servies;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import com.cristian.conector.conexionMysql;


public class empleadoService {
    public void registrarEmpleado(Scanner input) {
        String query = "INSERT INTO empleado(nombre, documento, rol, correo, salario) VALUES(?, ?, ?, ?, ?)";

        try (Connection conexion = conexionMysql.conectar()){
            
            System.out.println("Ingrese los siguientes datos del Empleado");

            System.out.println("Nombre: ");
            String nombre = input.nextLine();

            System.out.println("Documento: ");
            String documento = input.nextLine();

            System.out.println("Rol (Administrador, Asesor, Contador, Supervisor): ");
            String rol = input.nextLine();

            System.out.println("Correo: ");
            String correo = input.nextLine();

            System.out.println("Salario: ");
            double salario = input.nextDouble();
            input.nextLine();

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombre);
                stmt.setString(2, documento);
                stmt.setString(3, rol);
                stmt.setString(4, correo);
                stmt.setDouble(5, salario);
                
                int filas = stmt.executeUpdate();
                System.out.println("Empleadp registrado existosamente");
                System.out.println("Filas afectadas: " + filas);

            } catch (Exception e) {
                System.out.println("Error al registrar el empleado");
                e.printStackTrace();
            }


        } catch (Exception e) {
            System.out.println("Error en el INSERT");
            e.printStackTrace();
        }
    }

    public void listaDeEmpleado() {

        String query = "SELECT id, nombre, documento, rol, correo, salario FROM empleado";
        String archivo = "src/main/java/com/cristian/data/empleados.txt";

        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {

            boolean hayEmpleado = false;

            System.out.println("-------- Lista de Empleados --------");
            pw.println("-------- Lista de Empleados --------");

            while (rs.next()) {
                hayEmpleado = true;

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String documento = rs.getString("documento");
                String rol = rs.getString("rol");
                String correo = rs.getString("correo");
                double salario = rs.getDouble("salario");

                System.out.println("ID: " + id);
                pw.println("ID: " + id);

                System.out.println("Nombre: " + nombre);
                pw.println("Nombre: " + nombre);

                System.out.println("Documento: " + documento);
                pw.println("Documento: " + documento);

                System.out.println("Rol: " + rol);
                pw.println("Rol: " + rol);

                System.out.println("Correo: " + correo);
                pw.println("Correo: " + correo);

                System.out.println("Salario: " + salario);
                pw.println("Salario: " + salario);

                System.out.println("---------------------------------");
                pw.println("---------------------------------");
            }

            if (!hayEmpleado) {
                System.out.println("No hay empleados registrados");
                pw.println("No hay empleados registrados");
            }
            
        } catch (Exception e) {
            System.out.println("Error al consultar la lista de empleados");
            e.printStackTrace();
        }
    }

    public void consultarEmpleado(Scanner input){
        String query = "SELECT * FROM empleado WHERE documento = ?";
        try (Connection conexion = conexionMysql.conectar();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
        
            System.out.println("ingrese el documento a buscar: ");
            String documento = input.nextLine();

            stmt.setString(1, documento);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println();
                System.out.println("--------------------------------------");
                System.out.println("   ID: " + rs.getInt("id"));
                System.out.println("   Nombre: " + rs.getString("nombre"));
                System.out.println("   Documento: " + rs.getString("documento"));
                System.out.println("   Rol: " + rs.getString("rol"));
                System.out.println("   Correo: " + rs.getString("correo"));
                System.out.println("   Salario: " + rs.getDouble("salario"));
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
