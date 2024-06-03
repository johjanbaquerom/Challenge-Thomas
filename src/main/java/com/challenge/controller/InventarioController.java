package com.challenge.controller;

import com.challenge.entity.Inventario;
import com.challenge.entity.Producto;
import com.challenge.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> obtenerInventarios() {
        return inventarioService.obtenerInventario();
    }

    @GetMapping("/{nombreProducto}")
    public ResponseEntity<Inventario> obtenerInventarioPorNombreProducto(@PathVariable String nombreProducto) {
        Inventario inventario = inventarioService.obtenerInventarioPorNombreProducto(nombreProducto);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario) {
        Producto producto = inventario.getProducto();
        if (producto == null) {
            return ResponseEntity.badRequest().body("El producto no puede ser nulo");
        }

        Producto productoExistente = inventarioService.obtenerProductoPorId(producto.getId());
        if (productoExistente != null) {
            productoExistente.setActivo(producto.getActivo());
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecio(producto.getPrecio());
            inventario.setProducto(productoExistente);
        } else {
            inventario.setProducto(producto);
        }

        Inventario inventarioGuardado = inventarioService.guardarInventario(inventario);
        return ResponseEntity.ok(inventarioGuardado);
    }
}

