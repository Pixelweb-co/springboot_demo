package com.demousers.users.services;

import com.demousers.users.entidades.UsersModel;
import com.demousers.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${password.regex}")
    private String passwordRegex;


    public ResponseEntity<?> createUser(UsersModel user) {
        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya está registrado");
        }

        // Validar el formato del correo electrónico
        if (!isValidEmail(user.getEmail())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El formato del correo electrónico es incorrecto");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Validar el formato de la contraseña
        if (!isValidPassword(user.getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El formato de la contraseña es incorrecto la contraseña debe ser de minimo 8 caracteres, tener mayusculas, minusculas, y caracter especial");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        user.setPassword(user.getPassword());

        // Generar UUID único para el nuevo usuario
        UUID userId = UUID.randomUUID();
        user.setId(userId);

        // Configurar fechas
        Date currentDate = new Date();
        user.setCreated(currentDate);
        user.setModified(currentDate);
        user.setLastLogin(currentDate);

        // Generar una clave segura para HS256
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


        // Generar JWT único para el usuario
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();
        user.setToken(token);



        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Devolver el usuario creado con un ResponseEntity
        return ResponseEntity.ok(user);
    }

    private boolean isValidEmail(String email) {
        // Implementar validación de formato de correo electrónico
        // Utilizando expresiones regulares u otras técnicas
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        // Implementar validación de formato de contraseña
        // Utilizando expresiones regulares u otras técnicas
        return password.matches(passwordRegex);
    }
}
