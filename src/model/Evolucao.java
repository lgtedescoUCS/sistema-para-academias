package model;

import java.time.LocalDate;

public class Evolucao {

	private LocalDate data;
	private float peso;
	private float percentMassaMuscular;
	
	
	public Evolucao() {};
	
	public Evolucao(LocalDate data, float peso, float percentMassaMuscular) {
		super();
		this.data = data;
		this.peso = peso;
		this.percentMassaMuscular = percentMassaMuscular;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public float getPercentMassaMuscular() {
		return percentMassaMuscular;
	}
	public void setPercentMassaMuscular(float percentMassaMuscular) {
		this.percentMassaMuscular = percentMassaMuscular;
	}
	
	
}
