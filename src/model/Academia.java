package model;

public class Academia {

	private String nome;
	private String telefone;
	private String website;
	private Endereco endereco;
	private Aluno[] alunos;
	private Aparelho[] aparelhos;
	private Instrutor[] instrutor;
	private Horario[] horarios;
    private Usuario[] usuarios;
	private int ultimaPosA;
	private int ultimaPosAp;
	private int ultimaPosIns;
	private int ultimaPosHora;
    private int ultimaPosU;
	public static final int MAX_ALUNOS = 50;
	public static final int MAX_APARELHOS = 50;
	public static final int MAX_INSTRUTOR = 50;
	public static final int MAX_HORARIOS = 7;
    public static final int MAX_USUARIOS = 50;
	
	
	public Academia() {
		this.alunos = new Aluno[MAX_ALUNOS];
		this.aparelhos = new Aparelho[MAX_APARELHOS];
		this.instrutor = new Instrutor[MAX_INSTRUTOR];
		this.horarios = new Horario[MAX_HORARIOS];
        this.usuarios = new Usuario[MAX_USUARIOS];
		this.ultimaPosA = 0;
		this.ultimaPosAp = 0;
		this.ultimaPosIns = 0;
		this.ultimaPosHora = 0;
        this.ultimaPosU = 0;
	}
	
	public void addAluno(Aluno a) {
		if(this.ultimaPosA < MAX_ALUNOS) {
			this.alunos[this.ultimaPosA] = a;
			this.ultimaPosA++;
		}
	}
	public int countAluno() {
		return this.ultimaPosA;
	}
	public Aluno getAluno(int indice) {
		if(indice >= 0 && indice < this.ultimaPosA) {
			return this.alunos[indice];
		}else {
			return null;
		}
	}
	
	public void addAparelho(Aparelho p) {
		if(this.ultimaPosAp < MAX_APARELHOS) {
			this.aparelhos[this.ultimaPosAp] = p;
			this.ultimaPosAp++;
		}
	}
	public int countAparelho() {
		return this.ultimaPosAp;
	}
	public Aparelho getAparelho(int indice) {
		if(indice >= 0 && indice < this.ultimaPosAp) {
			return this.aparelhos[indice];
		}else {
			return null;
		}
	}
	
	public void addInstrutor(Instrutor i) {
		if(this.ultimaPosIns < MAX_INSTRUTOR) {
			this.instrutor[this.ultimaPosIns] = i;
			this.ultimaPosIns++;
		}
	}
	public int countInstrutor() {
		return this.ultimaPosIns;
	}
	public Instrutor getInstrutor(int indice) {
		if(indice >= 0 && indice < this.ultimaPosIns) {
			return this.instrutor[indice];
		}else {
			return null;
		}
	}
	
	public void addHorario(Horario h) {
		if(this.ultimaPosHora < MAX_HORARIOS) {
			this.horarios[this.ultimaPosHora] = h;
			this.ultimaPosHora++;
		}
	}
	public int countHorario() {
		return this.ultimaPosHora;
	}
	public Horario getHorario(int indice) {
		if(indice >= 0 && indice < this.ultimaPosHora) {
			return this.horarios[indice];
		}else {
			return null;
		}
	}
    
    public void addUsuario(Usuario u) {
        if(this.ultimaPosU < MAX_USUARIOS) {
            this.usuarios[this.ultimaPosU] = u;
            this.ultimaPosU++;
        }
    }
    
    public int countUsuario() {
		return this.ultimaPosU;
	}
    public Usuario getUsuario(int indice) {
		if(indice >= 0 && indice < this.ultimaPosU) {
			return this.usuarios[indice];
		}else {
			return null;
		}
	}

	public Usuario autenticar(String login, String senha) {
        for (int i = 0; i < this.ultimaPosU; i++) {
            Usuario u = this.usuarios[i];
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

	public Instrutor[] getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor[] instrutor) {
		this.instrutor = instrutor;
	}

	public Horario[] getHorarios() {
		return horarios;
	}

	public void setHorarios(Horario[] horarios) {
		this.horarios = horarios;
	}

	public Academia(String nome, String telefone, String website) {
		this();
		this.nome = nome;
		this.telefone = telefone;
		this.website = website;
	}
	
	public Academia(String nome, String telefone, String website, Endereco endereco) {
		this();
		this.nome = nome;
		this.telefone = telefone;
		this.website = website;
		this.endereco = endereco;
	}
	
	public Aparelho[] getAparelhos() {
		return aparelhos;
	}
	public void setAparelhos(Aparelho[] aparelhos) {
		this.aparelhos = aparelhos;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Aluno[] getAlunos() {
		return alunos;
	}
	public void setAlunos(Aluno[] alunos) {
		this.alunos = alunos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public Usuario[] getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario[] usuarios) {
		this.usuarios = usuarios;
	}
}