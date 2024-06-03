package com.challenge.controller.dto;

import com.challenge.entity.Orden;
import com.challenge.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteFrecuenteDTO {

    private String nombreCliente;
    private Double totalOrden;
}
