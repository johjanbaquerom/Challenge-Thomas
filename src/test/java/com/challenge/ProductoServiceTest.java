package com.challenge;

import com.challenge.entity.Producto;
import com.challenge.repository.ProductoRepository;
import com.challenge.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObtenerProductosActivos() {

        List<Producto> productosActivos = new ArrayList<>();
        when(productoRepository.findByActivo(true)).thenReturn(productosActivos);

        List<Producto> resultado = productoService.obtenerProductosActivos();
        assertEquals(productosActivos, resultado);
    }

    @Test
    public void testGuardarProducto() {

        Producto producto = new Producto(/* asignar valores necesarios */);
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.guardarProducto(producto);
        assertEquals(producto, resultado);
    }

    @Test
    public void testEliminarProducto() {

        Long idProducto = 1L;
        productoService.eliminarProducto(idProducto);
        verify(productoRepository, times(1)).deleteById(idProducto);
    }
}

