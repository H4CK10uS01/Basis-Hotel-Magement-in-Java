// Archivo: HotelManagement.java
// Descripción: Proyecto de gestión hotelera que implementa reservas, room service, manejo de clientes así como chequeo de cuentas.

import java.util.ArrayList;
import java.util.Scanner;

// Clase Cliente para almacenar datos de clientes
class Cliente {
    private int id;
    private String nombre;
    private String telefono;

    public Cliente(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getTelefono() { 
        return telefono; 
    }

    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

// Excepción definida por el usuario para reservas no válidas
class ReservationException extends Exception {
    public ReservationException(String message) {
        super(message);
    }
}

// Clase abstracta Habitacion con atributos y métodos comunes
abstract class Habitacion {
    protected int id;
    protected String tipo;
    protected double precio;
    protected boolean disponible;
    protected Cliente cliente;
    protected double roomServiceCost;

    public Habitacion(int id, String tipo, double precio) {
        this.id = id;
        this.tipo = tipo;
        this.precio = precio;
        this.disponible = true;
        this.cliente = null;
        this.roomServiceCost = 0.0;
    }

    public int getId() { 
        return id; 
    }
    public String getTipo() { 
        return tipo; 
    }
    public double getPrecio() { 
        return precio; 
    }
    public boolean isDisponible() { 
        return disponible; 
    }
    public Cliente getCliente() { 
        return cliente; 
    }
    public double getRoomServiceCost() { 
        return roomServiceCost; 
    }

    // Método para reservar la habitación. Lanza excepción si ya está reservada.
    public void reservar(Cliente cliente) throws ReservationException {
        if (!disponible) {
            throw new ReservationException("La habitación ya está reservada.");
        }
        this.cliente = cliente;
        this.disponible = false;
    }

    // Cancela la reserva y resetea el costo de room service.
    public void cancelarReserva() {
        this.cliente = null;
        this.disponible = true;
        this.roomServiceCost = 0.0;
    }

    // Agrega un costo adicional por room service.
    public void agregarRoomService(double costo) {
        this.roomServiceCost += costo;
    }

    // Retorna la cuenta total (precio base + cargos adicionales).
    public double getTotalCuenta() {
        return precio + roomServiceCost;
    }

    // Método abstracto para mostrar las características de la habitación.
    public abstract void mostrarCaracteristicas();

    public String toString() {
        String estado = disponible ? "Disponible" : "Reservada";
        String infoCliente = (cliente != null) ? cliente.toString() : "Sin cliente";
        return "Habitación " + id + " - Tipo: " + tipo + ", Precio: " + precio 
                + ", Estado: " + estado + ", Cliente: " + infoCliente;
    }
}

// Clase HabitacionLujo
class HabitacionLujo extends Habitacion {
    public HabitacionLujo(int id, double precio) {
        super(id, "Lujo", precio);
    }

    public void mostrarCaracteristicas() {
        System.out.println("Habitación de Lujo. Características: servicio de mayordomo, vista panorámica, minibar, y decoración exclusiva.");
    }
}

// Clase HabitacionMatrimonial
class HabitacionMatrimonial extends Habitacion {
    public HabitacionMatrimonial(int id, double precio) {
        super(id, "Matrimonial", precio);
    }

    public void mostrarCaracteristicas() {
        System.out.println("Habitación Matrimonial. Características: cama matrimonial, decoración romántica y amenidades de pareja.");
    }
}

// Clase HabitacionEstandar
class HabitacionEstandar extends Habitacion {
    public HabitacionEstandar(int id, double precio) {
        super(id, "Estándar", precio);
    }

    public void mostrarCaracteristicas() {
        System.out.println("Habitación Estándar. Características: cama cómoda, baño privado y conexión WiFi.");
    }
}

// Clase principal para la gestión del hotel
public class HotelManagement {
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Habitacion> habitaciones = new ArrayList<>();
    private static int nextClienteId = 1;

    public static void main(String[] args) {
        // Pre-cargar habitaciones (2 de cada tipo)
        habitaciones.add(new HabitacionLujo(1, 150.0));
        habitaciones.add(new HabitacionLujo(2, 150.0));
        habitaciones.add(new HabitacionMatrimonial(3, 120.0));
        habitaciones.add(new HabitacionMatrimonial(4, 120.0));
        habitaciones.add(new HabitacionEstandar(5, 80.0));
        habitaciones.add(new HabitacionEstandar(6, 80.0));

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("\n--- Menú de Gestión Hotelera ---");
            System.out.println("1. Ingresar datos de cliente");
            System.out.println("2. Reservar habitación");
            System.out.println("3. Cancelar reserva de habitación");
            System.out.println("4. Room Service");
            System.out.println("5. Visualizar cuenta total");
            System.out.println("6. Mostrar habitaciones disponibles");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Intente nuevamente.");
                continue;
            }

            switch(opcion) {
                case 1:
                    ingresarCliente(sc);
                    break;
                case 2:
                    reservarHabitacion(sc);
                    break;
                case 3:
                    cancelarReserva(sc);
                    break;
                case 4:
                    agregarRoomService(sc);
                    break;
                case 5:
                    visualizarCuenta(sc);
                    break;
                case 6:
                    mostrarDisponibles();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while(opcion != 7);

        sc.close();
    }

    // Método para registrar un nuevo cliente
    private static void ingresarCliente(Scanner sc) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el teléfono del cliente: ");
        String telefono = sc.nextLine();
        Cliente nuevoCliente = new Cliente(nextClienteId++, nombre, telefono);
        clientes.add(nuevoCliente);
        System.out.println("Cliente registrado: " + nuevoCliente);
    }

    // Busca un cliente por ID
    private static Cliente buscarCliente(int id) {
        for(Cliente c : clientes) {
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Busca una habitación por ID
    private static Habitacion buscarHabitacion(int id) {
        for(Habitacion h : habitaciones) {
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    // Método para reservar una habitación
    private static void reservarHabitacion(Scanner sc) {
        System.out.print("Ingrese el ID del cliente: ");
        int clienteId = Integer.parseInt(sc.nextLine());
        Cliente cliente = buscarCliente(clienteId);
        if(cliente == null) {
            System.out.println("Cliente no encontrado. Por favor, registre al cliente primero.");
            return;
        }
        System.out.println("Habitaciones disponibles:");
        for(Habitacion h : habitaciones) {
            if(h.isDisponible()) {
                System.out.println(h);
                h.mostrarCaracteristicas();
            }
        }
        System.out.print("Ingrese el ID de la habitación que desea reservar: ");
        int habId = Integer.parseInt(sc.nextLine());
        Habitacion hab = buscarHabitacion(habId);
        if(hab == null) {
            System.out.println("Habitación no encontrada.");
            return;
        }
        try {
            hab.reservar(cliente);
            System.out.println("Habitación reservada exitosamente para " + cliente.getNombre());
        } catch (ReservationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para cancelar una reserva
    private static void cancelarReserva(Scanner sc) {
        System.out.print("Ingrese el ID de la habitación a cancelar la reserva: ");
        int habId = Integer.parseInt(sc.nextLine());
        Habitacion hab = buscarHabitacion(habId);
        if(hab == null) {
            System.out.println("Habitación no encontrada.");
            return;
        }
        if(hab.isDisponible()) {
            System.out.println("La habitación ya está disponible.");
        } else {
            hab.cancelarReserva();
            System.out.println("Reserva cancelada exitosamente.");
        }
    }

    // Método para agregar un cargo de Room Service a una habitación reservada
    private static void agregarRoomService(Scanner sc) {
        System.out.print("Ingrese el ID de la habitación para agregar Room Service: ");
        int habId = Integer.parseInt(sc.nextLine());
        Habitacion hab = buscarHabitacion(habId);
        if(hab == null) {
            System.out.println("Habitación no encontrada.");
            return;
        }
        if(hab.isDisponible()) {
            System.out.println("La habitación no está reservada. No se puede agregar Room Service.");
            return;
        }
        System.out.print("Ingrese el costo del Room Service: ");
        double costo = Double.parseDouble(sc.nextLine());
        hab.agregarRoomService(costo);
        System.out.println("Room Service agregado. Costo actual: " + hab.getRoomServiceCost());
    }

    // Método para visualizar la cuenta total de una habitación reservada
    private static void visualizarCuenta(Scanner sc) {
        System.out.print("Ingrese el ID de la habitación para ver la cuenta total: ");
        int habId = Integer.parseInt(sc.nextLine());
        Habitacion hab = buscarHabitacion(habId);
        if(hab == null) {
            System.out.println("Habitación no encontrada.");
            return;
        }
        if(hab.isDisponible()) {
            System.out.println("La habitación no está reservada.");
            return;
        }
        System.out.println("Cuenta total para la habitación " + habId + ": " + hab.getTotalCuenta());
    }

    // Método para mostrar todas las habitaciones disponibles
    private static void mostrarDisponibles() {
        System.out.println("Habitaciones disponibles:");
        for(Habitacion h : habitaciones) {
            if(h.isDisponible()) {
                System.out.println(h);
                h.mostrarCaracteristicas();
            }
        }
    }
}
