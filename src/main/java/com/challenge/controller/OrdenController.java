package com.challenge.controller;

import com.challenge.controller.dto.ClienteFrecuenteDTO;
import com.challenge.entity.Orden;
import com.challenge.entity.Producto;
import com.challenge.service.OrdenService;
import com.challenge.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orden")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden) {
        Orden nuevaOrden = ordenService.crearOrden(orden);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }

    @GetMapping("/top-5-mas-vendidos")
    public List<Orden> obtenerTop5MasVendidos() {
        return ordenService.obtenerTop5MasVendidos();
    }

    @GetMapping("/top-5-clientes-frecuentes")
    public ResponseEntity<List<ClienteFrecuenteDTO>> obtenerTop5ClientesFrecuentes() {
        try {
            List<ClienteFrecuenteDTO> top5ClientesFrecuentes = ordenService.obtenerTop5ClientesFrecuentes();
            if (top5ClientesFrecuentes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(top5ClientesFrecuentes, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}