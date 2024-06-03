package com.challenge.repository;

import com.challenge.entity.Orden;
import com.challenge.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    List<Orden> findTop5ByOrderByTotalDesc();
    List<Orden> findTop5ByUsuarioOrderByTotalDesc(Usuario usuario);
}
