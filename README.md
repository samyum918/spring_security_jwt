# Spring Security + JWT Implementation
### Security Config is in:
- SpringSecurityConfig

### Filter logic for login and register API:
- JwtLoginFilter

### Filter logic for other APIs:
- JWTAuthenticationFilter

### Login logic:
- UsersAuthenticationProvider

### All APIs except "login" and "register" need to attach the JWT token in header for authorization.
- Header Key: Authorization
- Header Value: "Bearer " + token
