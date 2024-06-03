package com.challenge.repository;

import com.challenge.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Inventario findByProductoNombre(String Producto);
}
