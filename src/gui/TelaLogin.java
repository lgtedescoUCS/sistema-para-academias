package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import java.time.LocalDate;

public class TelaLogin extends JFrame implements ActionListener {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnSair;
    private Academia academia;

    public TelaLogin(Academia academia) {
        super("Login - " + academia.getNome());
        this.academia = academia;

        // Configuração da janela
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        
        painelCampos.add(new JLabel("Usuário (Login):"));
        txtUsuario = new JTextField(15);
        painelCampos.add(txtUsuario);

        painelCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField(15);
        painelCampos.add(txtSenha);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        
        btnSair = new JButton("Sair");
        btnSair.addActionListener(this);
        
        painelBotoes.add(btnLogin);
        painelBotoes.add(btnSair);

        // Adiciona painéis à janela
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            autenticarUsuario();
        } else if (e.getSource() == btnSair) {
            System.exit(0);
        }
    }

    private void autenticarUsuario() {
        String login = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        // Assume que a classe Academia tem um método 'autenticar' que retorna um Usuario
        Usuario usuarioAutenticado = academia.autenticar(login, senha);

        if (usuarioAutenticado != null) {
            
            String tipoUsuario = usuarioAutenticado.getTipo();
            
            // Tenta obter o nome da pessoa associada, senão usa o login
            String nomeExibicao = usuarioAutenticado.getPessoa() != null ? 
                                  usuarioAutenticado.getPessoa().getNome() : 
                                  usuarioAutenticado.getLogin();
                                 
            JOptionPane.showMessageDialog(this,
                    "Bem-vindo, " + nomeExibicao + " (" + tipoUsuario + ")!",
                    "Login realizado", JOptionPane.INFORMATION_MESSAGE);

            // CORREÇÃO: Passa o tipoUsuario para o construtor da TelaMenuPrincipal
            if (tipoUsuario.equalsIgnoreCase("admin") || 
                tipoUsuario.equalsIgnoreCase("instrutor")) {
                
                new TelaMenuPrincipal(academia, tipoUsuario); 
                
            } else if (tipoUsuario.equalsIgnoreCase("aluno")) {
                // Presume que se o tipo é "aluno", a Pessoa associada é um objeto Aluno
                if (usuarioAutenticado.getPessoa() instanceof Aluno) {
                    Aluno alunoLogado = (Aluno) usuarioAutenticado.getPessoa();
                    // Assumindo que existe uma TelaAluno(Aluno, Academia)
                    new TelaAluno(alunoLogado, academia); 
                } else {
                     JOptionPane.showMessageDialog(this, "Erro: Aluno não cadastrado ou associado.", "Erro", JOptionPane.ERROR_MESSAGE);
                     return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tipo de usuário não reconhecido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.dispose(); // fecha a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText(""); // limpa a senha após falha
            txtUsuario.requestFocus();
        }
    }
}