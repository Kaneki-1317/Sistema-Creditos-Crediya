package com.cristian.view;

import java.util.Scanner;

import com.cristian.servies.pagoService;

public class PagoMenu {
    public void pagoMenu() {
        Scanner input = new Scanner(System.in);
        pagoService servicio = new pagoService();
        int opcion; 

        do {
            System.out.println("""
                --------------- Pago Menu -------------
                    1. Registrar Abono a Prestamo
                    2. Paz y salvo
                    3. lista de pagos
                    0. Volver al Menu Principal
                --------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();

            switch (opcion) {
                case 1 -> servicio.registrarPago(input);
                case 2 -> servicio.pazYSalvo(input);
                case 3 -> servicio.listaHistoricoPagos();
                case 0 -> System.out.println("Volver al Menu Principal");
                default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
            }
        } while (opcion != 0);

    }
}
