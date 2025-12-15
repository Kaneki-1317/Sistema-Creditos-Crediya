package com.cristian.view;

import java.util.Scanner;


public class Menu {
    public void menu(){
        Scanner input = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("""
                ========== Credyya S.A.S ===========
                |    1. Modulo de Empleado         |
                |    2. Modulo de Cliente          |
                |    3. Modulo de Préstamo         |
                |    4. Modulo de pago             |
                |    5. Modulo de Reportes         |
                |    0. Salir                      |
                ====================================
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();
            
                switch (opcion) {
                    case 1 -> new EmpleadoMenu().empleadoMenu();
                    case 2 -> new ClienteMenu().clienteMenu();
                    case 3 -> new PrestamosMenu().prestamosMenu();
                    case 4 -> new PagoMenu().pagoMenu();
                    case 5 -> new ReportesMenu().reportesMenu();
                    case 0 -> System.out.println("Saliendo del Crediya S.A.S");
                    default -> System.out.println("Opción inválida. Por favor, intente de nuevo");
                }

        } while (opcion != 0);

        
    }
}
