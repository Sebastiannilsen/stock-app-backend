package ntnu.idata2503.group9.stockappbackend.dto;

/**
 * DTO for authentication response.
 * 
 */
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
