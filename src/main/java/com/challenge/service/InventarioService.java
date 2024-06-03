package com.challenge.service;

import com.challenge.entity.Inventario;
import com.challenge.entity.Producto;
import com.challenge.repository.InventarioRepository;
import com.challenge.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> obtenerInventario() {
        return inventarioRepository.findAll();
    }

    public Producto obtenerProductoPorNombre(String nombreProducto) {
        return productoRepository.findByNombre(nombreProducto);
    }

    public Inventario obtenerInventarioPorNombreProducto(String nombreProducto) {

        Producto producto = productoRepository.findByNombre(nombreProducto);
        if (producto == null) {
            return null;
        }
        return inventarioRepository.findByProductoNombre(String.valueOf(producto));
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
}
