package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Academia;


public class TelaPrincipalInstrutor extends JFrame implements ActionListener {

    private JButton btnCadastrarAluno, btnDefinirTreino, btnTrocarUsuario;
    private Academia academia;

    public TelaPrincipalInstrutor(Academia academia) {
        super("Menu Instrutor - " + academia.getNome());
        this.academia = academia;

        btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnDefinirTreino = new JButton("Definir Treino");
        btnTrocarUsuario = new JButton("Trocar Usuário");

 
        btnCadastrarAluno.addActionListener(this);
        btnDefinirTreino.addActionListener(this);
        btnTrocarUsuario.addActionListener(this);
      
     
        setLayout(new GridLayout(3, 1, 10, 10)); 
        add(btnCadastrarAluno);
        add(btnDefinirTreino);
        add(btnTrocarUsuario);

      
        setSize(300, 180); 
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public TelaPrincipalInstrutor() {};

	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrarAluno) {
            new TelaCadastroAluno(academia);
        } else if (e.getSource() == btnDefinirTreino) {
            new TelaTreino(academia);
        } else if (e.getSource() == btnTrocarUsuario) {
            this.dispose(); 
            new TelaLogin(academia);
        }
    }
}