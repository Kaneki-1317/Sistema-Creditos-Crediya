package com.cristian.view;


import java.util.Scanner;


import com.cristian.servies.empleadoService;

public class EmpleadoMenu {
    public void empleadoMenu() {
        Scanner input = new Scanner(System.in);
        empleadoService servicios = new empleadoService();
        int opcion;

        do {
            System.out.println("""
                ------------- Empleado Menu -------------
                    1. Registrar Empleado
                    2. Consultar Empleado
                    3. lista de Empleados
                    0. Volver al Menu Principal
                ----------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();
            input.nextLine();
            
            switch (opcion) {
                case 1 -> servicios.registrarEmpleado(input);
                case 2 -> servicios.consultarEmpleado(input);
                case 3 -> servicios.listaDeEmpleado();
                case 0 -> System.out.println("Volver al Menu Principal");
                default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
            }
        } while (opcion != 0);

    }  
}
