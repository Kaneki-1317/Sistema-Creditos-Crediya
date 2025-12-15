package com.cristian.view;

import java.util.Scanner;

import com.cristian.servies.prestamoService;

public class PrestamosMenu {

    prestamoService prestamoService = new prestamoService();

    public void prestamosMenu() {
        Scanner input = new Scanner(System.in);
                int opcion;

        do {
            System.out.println("""
                ------------- Prestamos Menu -------------
                    1. Crear Prestamo
                    2. Simulación de Prestamo
                    3. Cambiar Estado Prestamo
                    4. Consultar Prestamo
                    5. Lista de Prestamos
                    0. Volver al Menu Principal
                ----------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1 -> prestamoService.crearPrestamoCliente(input);
                case 2 -> prestamoService.SimulacionPrestamos(input);
                case 3 -> prestamoService.cambiarEstado(input);
                case 4 -> prestamosSubMenu(input);
                case 5 -> prestamoService.listaPrestamo();
                case 0 -> System.out.println("Volver al Menu Principal");
                default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
            }
        } while (opcion != 0);
        
    }

    public void prestamosSubMenu(Scanner input) {
        int opcion;

        do {
            System.out.println("""
                ------------- Prestamos Menu -------------
                    1. Prestamos Clientes
                    2. Prestamos Realizado por Empleado
                    0. Salor al menu prestamo
                ----------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1 -> prestamoService.consultarPrestamosCliente(input);
                case 2 -> prestamoService.consultarPrestamosEmpleado(input);
                case 0 -> System.out.println("Volver al Menu Principal");
                default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
            }
        } while (opcion != 0);
        
    }
}
