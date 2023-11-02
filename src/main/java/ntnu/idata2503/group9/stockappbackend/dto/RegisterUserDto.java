package ntnu.idata2503.group9.stockappbackend.dto;

/**
 * Data transfer object (DTO) for data from the create user form.
 */
public class RegisterUserDto {
    private final String email;
    private final String password;

    public RegisterUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
