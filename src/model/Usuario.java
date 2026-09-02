package model;

public class Usuario {
	
	private String login;
    private String senha;
    private boolean isAdministrador;
    private Pessoa pessoa;
    private String tipo;

    public Usuario() {};
    
    public Usuario(String login, String senha, String tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.isAdministrador = tipo != null && tipo.equalsIgnoreCase("admin");
    }

    public Usuario(String login, String senha) {
    	this.login = login;
        this.senha = senha;
        this.tipo = "aluno";
        this.isAdministrador = false;
    }
    
    public Usuario(String login, String senha, Pessoa pessoa) {
    	this.login = login;
        this.senha = senha;
        this.pessoa = pessoa;
        this.tipo = "aluno";
        this.isAdministrador = false;
    };
    
    public Usuario(String login, String senha, boolean isAdministrador, Pessoa pessoa) {
        this.login = login;
        this.senha = senha;
        this.isAdministrador = isAdministrador;
        this.pessoa = pessoa;
        this.tipo = isAdministrador ? "admin" : "instrutor";
    }
    
    public Usuario(String login, String senha, boolean isAdministrador) {
        this.login = login;
        this.senha = senha;
        this.isAdministrador = isAdministrador;
        this.tipo = isAdministrador ? "admin" : "instrutor";
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdministrador() {
		return isAdministrador;
	}

	public void setAdministrador(boolean isAdministrador) {
		this.isAdministrador = isAdministrador;
        this.tipo = isAdministrador ? "admin" : this.tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.isAdministrador = tipo != null && tipo.equalsIgnoreCase("admin");
    }
}