package model;

public class Exercicio {

	private int ordem;
	private float carga;
	private int nroRepeticoes;
	private Aparelho aparelho;

	public Exercicio() {};
	
	public Exercicio(int ordem, float carga, int nroRepeticoes) {
		this.ordem = ordem;
		this.carga = carga;
		this.nroRepeticoes = nroRepeticoes;
	}
	
	public Aparelho getAparelho() {
		return aparelho;
	}

	public void setAparelho(Aparelho aparelho) {
		this.aparelho = aparelho;
	}
	
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public float getCarga() {
		return carga;
	}
	public void setCarga(float carga) {
		this.carga = carga;
	}
	public int getNroRepeticoes() {
		return nroRepeticoes;
	}
	public void setNroRepeticoes(int nroRepeticoes) {
		this.nroRepeticoes = nroRepeticoes;
	}
	
	
}
