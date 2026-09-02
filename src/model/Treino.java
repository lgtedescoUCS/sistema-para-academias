package model;

public class Treino {

	private int diaSemana;
	private String nome;
	private Exercicio[] exercicios;
	private Aluno aluno;
	private Instrutor instrutor;
	private int ultimaPos;
	
	
	public Treino(int diaSemana, String nome, Aluno aluno, Instrutor instrutor) {
		this();
		this.diaSemana = diaSemana;
		this.nome = nome;
		this.aluno = aluno;
		this.instrutor = instrutor;
	}

	public static final int MAX_EXERCICIOS = 10;
	
	
	public Treino() {
		this.exercicios = new Exercicio[MAX_EXERCICIOS];
		this.ultimaPos = 0;
	}
	
	public void addExercicio(Exercicio e) {
		if(this.ultimaPos < MAX_EXERCICIOS) {
			this.exercicios[this.ultimaPos] = e;
			this.ultimaPos++;
		}
	}
	public int countExercicio() {
		return this.ultimaPos;
	}
	public Exercicio getExercicio(int indice) {
		if(indice >= 0 && indice < this.ultimaPos) {
			return this.exercicios[indice];
		}else {
			return null;
		}
	}
	
	public Treino(int diaSemana, String nome) {
		this.diaSemana = diaSemana;
		this.nome = nome;
	}
	
	public int getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Exercicio[] getExercicios() {
		return exercicios;
	}

	public void setExercicios(Exercicio[] exercicios) {
		this.exercicios = exercicios;
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
