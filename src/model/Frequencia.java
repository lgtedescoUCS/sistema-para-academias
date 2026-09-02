package model;

import java.time.LocalDate;

public class Frequencia {

	private LocalDate data;
	private LocalDate entrada;
	private LocalDate saida;
	
	public Frequencia() {};
	
	public Frequencia(LocalDate data, LocalDate entrada, LocalDate saida) {
		super();
		this.data = data;
		this.entrada = entrada;
		this.saida = saida;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalDate getEntrada() {
		return entrada;
	}
	public void setEntrada(LocalDate entrada) {
		this.entrada = entrada;
	}
	public LocalDate getSaida() {
		return saida;
	}
	public void setSaida(LocalDate saida) {
		this.saida = saida;
	}
	
}
