package Ecommerce.Loja.DTO;

public class CadastroClienteRequest {
    private String nome;
    private String email;
    private String senha;
    public CadastroClienteRequest() {
    }
    public CadastroClienteRequest(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser vazio.");
        }
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        if (senha.length() < 8|| senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
        this.senha = senha;
    }
    
}
