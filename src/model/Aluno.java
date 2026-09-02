package model;

import java.time.LocalDate;

public class Aluno extends Pessoa{
	
	private LocalDate dataNascimento;
	private float altura;
	private Frequencia[] frequencias;
	private Evolucao[] evolucoes;
	private Treino[] treinos;
	private int ultimaPosF;
	private int ultimaPosE;
	private int ultimaPosT;
	public static final int MAX_FREQUENCIA = 10;
	public static final int MAX_EVOLUCOES = 10;
	public static final int MAX_TREINO = 10;

	
	public Aluno(int id, String nome, String email, String telefone, LocalDate dataNascimento, float altura) {
	    super(id, nome, email, telefone); 
	    this.frequencias = new Frequencia[MAX_FREQUENCIA];
	    this.evolucoes = new Evolucao[MAX_EVOLUCOES];
	    this.treinos = new Treino[MAX_TREINO];
	    this.ultimaPosF = 0;
	    this.ultimaPosE = 0;
	    this.ultimaPosT = 0;
	    this.dataNascimento = dataNascimento;
	    this.altura = altura;
	}
	
	/*public Aluno() {
		this.frequencias = new Frequencia[MAX_FREQUENCIA];
		this.evolucoes = new Evolucao[MAX_EVOLUCOES];
		this.treinos = new Treino[MAX_TREINO];
		this.ultimaPosF = 0;
		this.ultimaPosE = 0;
		this.ultimaPosT = 0;
	}*/
	
	public void addFrequencia(Frequencia f) {
		if(this.ultimaPosF < MAX_FREQUENCIA) {
			this.frequencias[this.ultimaPosF] = f;
			this.ultimaPosF++;
		}
	}
	public int countFrequencia() {
		return this.ultimaPosF;
	}
	public Frequencia getFrequencia(int indice) {
		if(indice >= 0 && indice < this.ultimaPosF) {
			return this.frequencias[indice];
		}else {
			return null;
		}
	}
	
	public void addEvolucao(Evolucao e) {
		if(this.ultimaPosE < MAX_EVOLUCOES) {
			this.evolucoes[this.ultimaPosE] = e;
			this.ultimaPosE++;
		}
	}
	public int countEvolucao() {
		return this.ultimaPosE;
	}
	public Evolucao getEvolucao(int indice) {
		if(indice >= 0 && indice < this.ultimaPosE) {
			return this.evolucoes[indice];
		}else {
			return null;
		}
	}
	
	public void addTreino(Treino t) {
		if(this.ultimaPosT < MAX_TREINO) {
			this.treinos[this.ultimaPosT] = t;
			this.ultimaPosT++;
		}
	}
	public int countTreino() {
		return this.ultimaPosT;
	}
	public Treino getTreino(int indice) {
		if(indice >= 0 && indice < this.ultimaPosT) {
			return this.treinos[indice];
		}else {
			return null;
		}
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public float getAltura() {
		return altura;
	}


	public void setAltura(float altura) {
		this.altura = altura;
	}
	
	
}
