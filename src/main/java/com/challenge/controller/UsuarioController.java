package com.challenge.controller;

import com.challenge.entity.Usuario;
import com.challenge.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @GetMapping
        public List<Usuario> getAllUsers() {
            return usuarioService.findAll();
        }

        @GetMapping("/{id}")
        public Optional<Usuario> getUserById(@PathVariable Long id) {
            return usuarioService.findById(id);
        }

        @PutMapping("/{id}")
        public String updateUser(@PathVariable Long id, @RequestBody Usuario user) {
            Optional<Usuario> userData = usuarioService.findById(id);

            if (userData.isPresent()) {
                Usuario existingUser = userData.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                usuarioService.saveUser(existingUser);
                return "Usuario actualizado";
            } else {
                return "Usuario no encontrado";
            }
        }

        @DeleteMapping("/{id}")
        public String deleteUser(@PathVariable Long id) {
            usuarioService.deleteById(id);
            return "Usuario eliminado";
        }
}
