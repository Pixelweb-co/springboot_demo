package com.demousers.users.services;

import com.demousers.users.entidades.UsersModel;
import com.demousers.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createUser(UsersModel user) {
        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya está registrado");
        }

        // Validar el formato del correo electrónico
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del correo electrónico es incorrecto");
        }

        // Validar el formato de la contraseña
        if (!isValidPassword(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato de la contraseña es incorrecto");
        }

        // Generar token de acceso
        String token = UUID.randomUUID().toString();
        user.setToken(token);

        user.setPassword(user.getPassword());

        // Configurar fechas
        Date currentDate = new Date();
        user.setCreated(currentDate);
        user.setModified(currentDate);
        user.setLastLogin(currentDate);

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
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    }
}
