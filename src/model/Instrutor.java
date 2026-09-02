package model;

public class Instrutor extends Pessoa{

	 private Formacao formacao;
	 
	 public Instrutor() {}

	 public Instrutor(int id, String nome, String email, String telefone, Formacao formacao) {
	        super(id, nome, email, telefone);
	        this.formacao = formacao;
	 }

	 public Formacao getFormacao() {
			return formacao;
	 }

	 public void setFormacao(Formacao formacao) {
			this.formacao = formacao;
	}
}
