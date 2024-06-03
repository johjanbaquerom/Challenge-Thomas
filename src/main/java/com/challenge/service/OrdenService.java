package com.challenge.service;

import com.challenge.controller.dto.ClienteFrecuenteDTO;
import com.challenge.entity.Orden;
import com.challenge.entity.Usuario;
import com.challenge.repository.OrdenRepository;
import com.challenge.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Orden crearOrden(Orden orden) {
        Usuario usuario = usuarioRepository.findById(orden.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + orden.getUsuario().getId()));

        orden.setUsuario(usuario);

        return ordenRepository.save(orden);
    }

    public List<Orden> obtenerTop5MasVendidos() {
        return ordenRepository.findTop5ByOrderByTotalDesc();
    }

    public List<ClienteFrecuenteDTO> obtenerTop5ClientesFrecuentes() {
        if (usuarioRepository == null || ordenRepository == null) {
            throw new IllegalStateException("usuarioRepository o ordenRepository no están inicializados");
        }

        List<Usuario> frecuentes = usuarioRepository.findAll().stream()
                .filter(usuario -> usuario != null && usuario.getEsFrecuente() != null && usuario.getEsFrecuente())
                .collect(Collectors.toList());

        List<ClienteFrecuenteDTO> top5ClientesFrecuentes = new ArrayList<>();

        for (Usuario usuario : frecuentes) {
            if (usuario != null) {
                List<Orden> ordenes = ordenRepository.findTop5ByUsuarioOrderByTotalDesc(usuario);
                if (ordenes != null) {
                    for (Orden orden : ordenes) {
                        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO(usuario.getUsername(), orden.getTotal());
                        top5ClientesFrecuentes.add(dto);
                    }
                }
            }
        }

        return top5ClientesFrecuentes;
    }

    public void aplicarDescuentos(Orden orden) {

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioDescuento = LocalDate.of(2024, Month.JANUARY, 1);
        LocalDate fechaFinDescuento = LocalDate.of(2024, Month.DECEMBER, 31);

        Boolean esFrecuente = orden.getUsuario() != null && orden.getUsuario().getEsFrecuente() != null ?
                orden.getUsuario().getEsFrecuente() : false;

        if (esFrecuente == null) {
            esFrecuente = false;
        }
        if (orden.getFecha().isAfter(fechaInicioDescuento) && orden.getFecha().isBefore(fechaFinDescuento)) {
            orden.setTotal(orden.getTotal() * 0.9);
        }
        if (orden.getAleatorio()) {
            orden.setTotal(orden.getTotal() * 0.5);
        }
        if (esFrecuente) {
            orden.setTotal(orden.getTotal() * 0.95);
        }
    }

    public Orden procesarOrden(Orden orden) {
        aplicarDescuentos(orden);
        return ordenRepository.save(orden);
    }
}