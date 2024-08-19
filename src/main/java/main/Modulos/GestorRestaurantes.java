package main.Modulos;

import java.util.ArrayList;
import java.util.List;

public class GestorRestaurantes {
    private final List<Restaurante> restaurantes;

    public GestorRestaurantes() {
        this.restaurantes = new ArrayList<>();
        cargarRestaurantesEnHilo();
    }

    // Método para agregar restaurantes desde un hilo
    private void cargarRestaurantesEnHilo() {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simula un retraso en la carga
                restaurantes.add(new Restaurante(1, "La Trattoria", "Calle 123, San José", "contacto@trattoria.com", "Italiana"));
                restaurantes.add(new Restaurante(2, "El Mariachi Loco", "Avenida Central, Heredia", "info@mariachiloco.com", "Mexicana"));
                restaurantes.add(new Restaurante(3, "Sushi Express", "Boulevard Lindora, Santa Ana", "reservas@sushiexpress.com", "Japonesa"));
                restaurantes.add(new Restaurante(4, "Steak House", "Paseo Colón, San José", "reservas@steakhouse.com", "Americana"));
                restaurantes.add(new Restaurante(5, "La Casa del Curry", "Calle Principal, Escazú", "info@casadelcurry.com", "India"));
                restaurantes.add(new Restaurante(6, "Panadería Dulce Sabor", "Avenida Segunda, Alajuela", "pedidos@dulcesabor.com", "Panadería"));
                restaurantes.add(new Restaurante(7, "Café París", "Barrio Amón, San José", "contacto@cafeparis.com", "Francesa"));
                restaurantes.add(new Restaurante(8, "Pizzería Napoli", "Calle Real, Cartago", "ordenes@napoli.com", "Italiana"));
                restaurantes.add(new Restaurante(9, "El Fogón Tico", "Avenida 3, Puntarenas", "reservas@fogon.com", "Costarricense"));
                restaurantes.add(new Restaurante(10, "Bistro Mediterráneo", "Centro Comercial Multiplaza, Escazú", "info@bistromed.com", "Mediterránea"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Método para buscar restaurantes en la lista
    public List<Restaurante> buscarRestaurantes(String busqueda) {
        List<Restaurante> resultados = new ArrayList<>();
        for (Restaurante restaurante : restaurantes) {
            if (restaurante.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                    restaurante.getCategoria().toLowerCase().contains(busqueda.toLowerCase())) {
                resultados.add(restaurante);
            }
        }
        return resultados;
    }

    // Método para obtener todos los restaurantes
    public List<Restaurante> getTodosLosRestaurantes() {
        return new ArrayList<>(restaurantes); // Devuelve una copia de la lista
    }
}
