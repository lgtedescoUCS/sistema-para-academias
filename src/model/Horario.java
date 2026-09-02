package model;

import java.time.LocalDate;

public class Horario {

	private String diaSemana;
	private LocalDate horaAbertura;
	private LocalDate horaFechamento;
	
	
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public LocalDate getHoraAbertura() {
		return horaAbertura;
	}
	public void setHoraAbertura(LocalDate horaAbertura) {
		this.horaAbertura = horaAbertura;
	}
	public LocalDate getHoraFechamento() {
		return horaFechamento;
	}
	public void setHoraFechamento(LocalDate horaFechamento) {
		this.horaFechamento = horaFechamento;
	}
	
	
	public Horario(String diaSemana, LocalDate horaAbertura, LocalDate horaFechamento) {
		this.diaSemana = diaSemana;
		this.horaAbertura = horaAbertura;
		this.horaFechamento = horaFechamento;
	}
}
