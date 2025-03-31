package com.warehouse_project.warehouse_project.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // Se busca el archivo en el directorio 'firebase' dentro de resources
        try (InputStream serviceAccount = getClass().getClassLoader()
                .getResourceAsStream("firebase/wherehouse-project-f91dd-firebase-adminsdk-fbsvc-3f9b556b2c.json")) {
            if (serviceAccount == null) {
                throw new IOException("Archivo de credenciales no encontrado en el classpath.");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            return FirebaseApp.initializeApp(options);
        }
    }
}
