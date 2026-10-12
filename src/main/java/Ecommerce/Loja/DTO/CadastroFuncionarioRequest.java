package Ecommerce.Loja.DTO;

import Ecommerce.Loja.Model.Enum.TipoFuncionario;

public class CadastroFuncionarioRequest {
    private String nome;
    private String email;
    private String senha;
    private TipoFuncionario tipoFuncionario;
    public CadastroFuncionarioRequest() {
}
public CadastroFuncionarioRequest(String nome, String email, String senha, TipoFuncionario tipoFuncionario) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.tipoFuncionario = tipoFuncionario;
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
   if (senha.length() < 8 || senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
        this.senha = senha;
}
public TipoFuncionario getTipoFuncionario() {
    return tipoFuncionario;
}
public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
    this.tipoFuncionario = tipoFuncionario;
}
}
