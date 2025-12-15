package com.cristian.view;

import java.util.Scanner;

import com.cristian.servies.ReportesService;

public class ReportesMenu {
    public void reportesMenu() {
        Scanner input = new Scanner(System.in);
        ReportesService servicio = new ReportesService();
        int opcion;

        do {
            System.out.println("""
                ------------- Reportes Menu -------------
                    1. Prestamos Activos
                    2. Prestamos Vencidos
                    3. Clientes Morosos
                    0. Volver al Menu Principal
                ----------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt(); 

            switch(opcion) {
                case 1 -> servicio.prestamosActivos();
                case 2 -> servicio.prestamosVencidos();
                case 3 -> servicio.clientesMorosos();
                case 0 -> System.out.println("Volver al Menu Principal");
                default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
            }
        } while (opcion != 0);
        
    }

    
}
