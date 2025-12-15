package com.cristian.view;

import java.util.Scanner;

import com.cristian.servies.clienteService;

public class ClienteMenu {
    public void clienteMenu() {

        clienteService servicios = new clienteService();

        Scanner input = new Scanner(System.in);
        int opcion; 

        do {
            System.out.println("""
                --------------- Cliente Menu -------------
                    1. Registrar Cliente
                    2. Consultar Cliente
                    3. Lista de Clientes
                    0. Volver al Menu Principal
                ------------------------------------------
                """);
            System.out.print("Seleccione una opcion: ");
            opcion = input.nextInt();

                switch (opcion) {
                    case 1 -> servicios.registrarCliente(input);
                    case 2 -> servicios.consultarCliente(input);
                    case 3 -> servicios.listaDeCliente();
                    case 0 -> System.out.println("Volver al Menu Principal");
                    default -> System.out.println("Opcion no valida, por favor intente de nuevo.");
                }
        } while (opcion != 0);
    }
}
