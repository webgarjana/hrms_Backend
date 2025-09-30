// package com.empsys.security.token;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.cors.CorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// public class SecurityConfig {

//     private final JwtAuthFilter jwtAuthFilter;

//     public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
//         this.jwtAuthFilter = jwtAuthFilter;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http
//                 .csrf(csrf -> csrf.disable())
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS enable
//                 .authorizeHttpRequests(auth -> auth
//                         .antMatchers("/auth/token").permitAll()
//                         .antMatchers("/api/chat/**").permitAll()   // 👈 allow chat
//                         .antMatchers("/api/leave/**").authenticated()
//                         .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                 .build();
//     }

//     // ✅ Allow CORS for React frontend and Netlify deployment
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration config = new CorsConfiguration();

//         // 👇 Existing localhost + Netlify origin
//         config.setAllowedOrigins(Arrays.asList(
//                 "http://localhost:3000",
//                 "http://localhost:5173",
//                 "https://cozy-chebakia-82171b.netlify.app" // ✅ Added Netlify frontend
//         ));

//         config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//         config.setExposedHeaders(Arrays.asList("Authorization"));
//         config.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);
//         return source;
//     }
// }
