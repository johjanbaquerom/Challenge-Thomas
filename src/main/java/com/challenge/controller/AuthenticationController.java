package com.challenge.controller;

import com.challenge.entity.Usuario;
import com.challenge.service.UsuarioService;
import com.challenge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        if (usuarioService.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuario ya existe");
        }

        String rawPassword = usuario.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuario.setPassword(encodedPassword);
        usuarioService.saveUser(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario usuario) {

        try {
            UserDetails userDetails = usuarioService.loadUserByUsername(usuario.getUsername());
            String storedPassword = userDetails.getPassword();

            if (passwordEncoder.matches(usuario.getPassword(), storedPassword)) {
                String jwt = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(jwt);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error durante el inicio de sesión");
        }
    }
}
