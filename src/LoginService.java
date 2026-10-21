// Logique de connexion pour SE-20
package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService; // Service utilitaire pour générer le JWT
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Optionnel : Méthode d'inscription
    public User register(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        // Hachage du mot de passe avant l'enregistrement
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    // Méthode de connexion (Login)
    public AuthResponse login(AuthRequest request) {
        // 1. Authentification via le gestionnaire Spring Security
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // 2. Recherche de l'utilisateur en base
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 3. Génération du token JWT
        String jwtToken = jwtService.generateToken(user.getUsername());

        return new AuthResponse(jwtToken);
    }
}
// Gestion des échecs de connexion
