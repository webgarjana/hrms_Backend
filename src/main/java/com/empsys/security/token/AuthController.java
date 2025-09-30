// package com.empsys.security.token;

// import com.empsys.dao.OnboardingRepository;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.empsys.entity.OnboardingEntity;

// @RestController
// @RequestMapping("/auth")
// @CrossOrigin(origins = {
//     "http://localhost:3000",
//     "https://cozy-chebakia-82171b.netlify.app"
// })

// public class AuthController {

//     private final JwtService jwtService;
    
//     @Autowired
//     private OnboardingRepository onboardingRepository;

//     public AuthController(JwtService jwtService) {
//         this.jwtService = jwtService;
//     }

// //    @PostMapping("/token")
// ////    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
// ////        // Here, normally you'd validate username/password from DB
// ////        if ("hr@gmail.com".equals(authRequest.getUsername()) && "hr@123".equals(authRequest.getPassword())) {
// ////            String token = jwtService.generateToken(authRequest.getUsername());
// ////            return ResponseEntity.ok(new AuthResponse(token));
// ////        }
// ////        return ResponseEntity.status(401).body("Invalid username or password");
// ////    }
// //    
// //
// //    
// //    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
// //        // Fetch user from OnboardingEntity table using email/username
// //        Optional<OnboardingEntity> optionalUser = onboardingRepository.findByEmail(authRequest.getUsername());
// //
// //        if (optionalUser.isEmpty() || 
// //            !optionalUser.get().getPassword().equals(authRequest.getPassword())) {
// //            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
// //                    .body("Invalid username or password");
// //        }
// //
// //        OnboardingEntity user = optionalUser.get();
// //
// //        // Generate JWT token with email (and role if you need)
// //        String token = jwtService.generateToken(user.getEmail());
// //
// //        return ResponseEntity.ok(new AuthResponse(token));
// //    }
// //    
// //    
    
    
//     @PostMapping("/token")
//     public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//         // Fetch user from DB
//         Optional<OnboardingEntity> optionalUser = onboardingRepository.findByEmail(authRequest.getUsername());

//         if (optionalUser.isEmpty() || 
//             !optionalUser.get().getPassword().equals(authRequest.getPassword())) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body("Invalid username or password");
//         }

//         OnboardingEntity user = optionalUser.get();

//         // Generate JWT token with email (and role if you need)
//         String token = jwtService.generateToken(user.getEmail());

//         // ✅ Build custom response including firstLogin + employeeId
//         return ResponseEntity.ok(new java.util.HashMap<>() {{
//             put("token", token);
//             put("employeeId", user.getEmployeeId());
//             put("firstLogin", user.isFirstLogin()); // 👈 this is key
//         }});
//     }

    
// }

