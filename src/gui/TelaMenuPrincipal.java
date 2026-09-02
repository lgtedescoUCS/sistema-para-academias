package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 

import model.Academia;
import model.Aluno;
import model.Instrutor;
import model.Usuario;

public class TelaMenuPrincipal extends JFrame implements ActionListener {

    private JButton btnCadastrarAluno, btnCadastrarAparelho, btnListarAlunos, btnListarInstrutores, btnListarUsuarios, 
    btnCadastrarInstrutor, btnDefinirTreino, btnTrocarUsuario, btnCadastrarUsuario;
    
    private JButton btnGerenciarAluno, btnGerenciarInstrutor, btnGerenciarAparelho, btnGerenciarUsuario, btnGerenciarTreino;
    
    private Academia academia;
    private String tipoUsuario; 

    public TelaMenuPrincipal(Academia academia, String tipoUsuario) { 
        super("Menu " + (tipoUsuario.equalsIgnoreCase("admin") ? "Admin" : "Instrutor") + " - " + academia.getNome());
        this.academia = academia;
        this.tipoUsuario = tipoUsuario; 

        btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnCadastrarAparelho = new JButton("Cadastrar Aparelho");
        btnCadastrarInstrutor = new JButton("Cadastrar Instrutor");
        btnDefinirTreino = new JButton("Definir Treino");
        btnListarAlunos = new JButton("Listar Alunos");
        btnListarInstrutores = new JButton("Listar Instrutores");
        btnListarUsuarios = new JButton("Listar Usuarios");
        btnTrocarUsuario = new JButton("Trocar Usuário");
        btnCadastrarUsuario = new JButton("Cadastrar Usuario");
        
        btnGerenciarAluno = new JButton("Gerenciar/Editar Alunos");
        btnGerenciarInstrutor = new JButton("Gerenciar/Editar Instrutores");
        btnGerenciarAparelho = new JButton("Gerenciar/Editar Aparelhos");
        btnGerenciarUsuario = new JButton("Gerenciar/Editar Usuários");
        btnGerenciarTreino = new JButton("Gerenciar/Editar Treinos");

        btnCadastrarAluno.addActionListener(this);
        btnCadastrarAparelho.addActionListener(this);
        btnCadastrarInstrutor.addActionListener(this);
        btnDefinirTreino.addActionListener(this);      
        btnListarAlunos.addActionListener(this);
        btnListarInstrutores.addActionListener(this);
        btnListarUsuarios.addActionListener(this);
        btnTrocarUsuario.addActionListener(this);
        btnCadastrarUsuario.addActionListener(this);
        
        btnGerenciarAluno.addActionListener(this);
        btnGerenciarInstrutor.addActionListener(this);
        btnGerenciarAparelho.addActionListener(this);
        btnGerenciarUsuario.addActionListener(this);
        btnGerenciarTreino.addActionListener(this);
        
        boolean isAdmin = tipoUsuario.equalsIgnoreCase("admin");
        JPanel painelBotoes = new JPanel();
        
        
        int numBotoesVisiveis = 0;
        
        JButton[] ordemAdmin = {
            btnCadastrarAluno, btnGerenciarAluno, 
            btnCadastrarInstrutor, btnGerenciarInstrutor,
            btnCadastrarAparelho, btnGerenciarAparelho,
            btnCadastrarUsuario, btnGerenciarUsuario,
            btnDefinirTreino, btnGerenciarTreino,
            btnListarAlunos, btnListarInstrutores, btnListarUsuarios,
            btnTrocarUsuario
        };
        
        JButton[] ordemInstrutor = {
            btnCadastrarAluno, 
            btnDefinirTreino,
            btnTrocarUsuario
        };
        
        JButton[] botoesParaAdicionar = isAdmin ? ordemAdmin : ordemInstrutor;
        
        for (JButton btn : botoesParaAdicionar) {
             painelBotoes.add(btn);
             numBotoesVisiveis++;
        }
        
        painelBotoes.setLayout(new GridLayout(numBotoesVisiveis, 1, 10, 10));
        
        this.add(painelBotoes); 

        setSize(400, 45 * numBotoesVisiveis + 50); 
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public TelaMenuPrincipal() {
        this(new Academia(), "admin");
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btnCadastrarAluno) {
            new TesteCadastroeEdicaoAluno(academia);
        } else if (e.getSource() == btnCadastrarAparelho) {
             if (tipoUsuario.equalsIgnoreCase("admin")) new TelaCadastroAparelho(academia);
        } else if (e.getSource() == btnCadastrarInstrutor) {
             if (tipoUsuario.equalsIgnoreCase("admin")) new TelaInstrutor(academia);
        } else if (e.getSource() == btnCadastrarUsuario) { 
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaCadastroUsuario(academia);
        }
        
        else if (e.getSource() == btnGerenciarAluno) {
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaGerenciamento(academia, "aluno");
        } else if (e.getSource() == btnGerenciarInstrutor) {
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaGerenciamento(academia, "instrutor");
        } else if (e.getSource() == btnGerenciarAparelho) {
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaGerenciamento(academia, "aparelho");
        } else if (e.getSource() == btnGerenciarUsuario) {
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaGerenciamento(academia, "usuario");
        } else if (e.getSource() == btnGerenciarTreino) {
            if (tipoUsuario.equalsIgnoreCase("admin")) new TelaGerenciamento(academia, "treino");
        }
        
        else if (e.getSource() == btnDefinirTreino) {
            new TelaTreino(academia);
        } else if (e.getSource() == btnTrocarUsuario) {
            this.dispose(); 
            new TelaLogin(academia); 
        }
        
        else if (e.getSource() == btnListarAlunos) {
            if (tipoUsuario.equalsIgnoreCase("admin")) {
                StringBuilder sb = new StringBuilder("Alunos cadastrados:\n");
                if (academia.countAluno() == 0) sb.append("Nenhum aluno cadastrado.");
                else for (int i = 0; i < academia.countAluno(); i++) sb.append((i + 1) + " - " + academia.getAluno(i).getNome() + "\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }
        else if (e.getSource() == btnListarInstrutores) {
            if (tipoUsuario.equalsIgnoreCase("admin")) {
                StringBuilder sb = new StringBuilder("Instrutores cadastrados:\n");
                if (academia.countInstrutor() == 0) sb.append("Nenhum instrutor cadastrado.");
                else for (int i = 0; i < academia.countInstrutor(); i++) sb.append((i + 1) + " - " + academia.getInstrutor(i).getNome() + "\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }
        else if (e.getSource() == btnListarUsuarios) {
            if (tipoUsuario.equalsIgnoreCase("admin")) {
                StringBuilder sb = new StringBuilder("Usuarios cadastrados:\n");
                if (academia.countUsuario() == 0) sb.append("Nenhum Usuario cadastrado.");
                else for (int i = 0; i < academia.countUsuario(); i++) sb.append((i + 1) + " - " + academia.getUsuario(i).getLogin() + "\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }
	}
}