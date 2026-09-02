package gui;

import java.time.LocalDate;

import model.Academia;
import model.Aluno;
import model.Endereco;
import model.Formacao;
import model.Instrutor;
import model.Usuario;

public class Principal {

public static void main(String[] args) {
    	
        Endereco end = new Endereco();
        end.setCidade("Caxias do Sul");
        Academia acad = new Academia("ExerciteAki", "99999-9999", "www.exerciteaki.com", end);
        
        acad.addUsuario(new Usuario("admin", "123", "admin"));

        Formacao formacaoInstrutor = new Formacao("Educação Física", "Bacharel em Educação Física");
        Instrutor instrutor = new Instrutor(100, "João da Silva", "joao@ucs.com", "5555-1000", formacaoInstrutor);
        acad.addInstrutor(instrutor);
        Usuario userInstrutor = new Usuario("joao", "456", "instrutor");
        userInstrutor.setPessoa(instrutor);
        acad.addUsuario(userInstrutor);

     
        Aluno aluno = new Aluno(200, "Maria 8", "maria@aluno.com", "5555-2000", LocalDate.of(1995, 1, 1), 1.70f);
        acad.addAluno(aluno);
        aluno.setNome("Maria Teste");
        Usuario userAluno = new Usuario("maria", "789", "aluno");
        userAluno.setPessoa(aluno);
        acad.addUsuario(userAluno);

        new TelaLogin(acad);
    }
}
