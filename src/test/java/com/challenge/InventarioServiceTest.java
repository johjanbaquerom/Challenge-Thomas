package com.challenge;

import com.challenge.entity.Inventario;
import com.challenge.entity.Producto;
import com.challenge.repository.InventarioRepository;
import com.challenge.repository.ProductoRepository;
import com.challenge.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGuardarInventario() {
        Inventario inventario = new Inventario(/* asignar valores necesarios */);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        Inventario resultado = inventarioService.guardarInventario(inventario);

        assertEquals(inventario, resultado);
    }

    @Test
    public void testObtenerInventario() {

        List<Inventario> inventarioList = new ArrayList<>();

        when(inventarioRepository.findAll()).thenReturn(inventarioList);

        List<Inventario> resultado = inventarioService.obtenerInventario();

        assertEquals(inventarioList, resultado);
    }

    @Test
    public void testObtenerProductoPorNombre() {
        String nombreProducto = "nombreProducto";
        Producto producto = new Producto(/* asignar valores necesarios */);

        when(productoRepository.findByNombre(nombreProducto)).thenReturn(producto);

        Producto resultado = inventarioService.obtenerProductoPorNombre(nombreProducto);

        assertEquals(producto, resultado);
    }

    @Test
    public void testObtenerProductoPorId() {
        Long idProducto = 1L;
        Producto producto = new Producto(/* asignar valores necesarios */);

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        Producto resultado = inventarioService.obtenerProductoPorId(idProducto);

        assertEquals(producto, resultado);
    }
}
