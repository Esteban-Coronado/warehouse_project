package com.warehouse_project.warehouse_project.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.warehouse_project.warehouse_project.model.Role;
import com.warehouse_project.warehouse_project.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FirebaseAuthorizationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public FirebaseAuthorizationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String uid = decodedToken.getUid();

                // 1. Obtener roles de la base de datos
                List<Role> roles = userService.getRolesByUid(uid);

                //ToDo Borrar en producción
                // 2. Log para ver qué roles se obtienen
                System.out.println("UID desde el token: " + uid);
                System.out.println("Roles del usuario: " + roles);

                // 3. Convertir roles a GrantedAuthority
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                        .collect(Collectors.toList());

                //ToDo Borrar en producción
                // 4. Log para ver las autoridades finales
                System.out.println("Authorities asignadas: " + authorities);

                // 5. Crear objeto de autenticación
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(uid, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
