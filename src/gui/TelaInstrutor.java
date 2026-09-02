package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Formacao;
import model.Instrutor;
import model.Academia; 
import model.Usuario; 

public class TelaInstrutor extends JFrame implements ActionListener {

    private JTextField tfId;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JTextField tfTelefone;
    private JTextField tfFormacaoDescricao;
    private JButton btnSalvar, btnLimpar;
    private JPanel campos, botoes, fundo;
    private Academia academia;
    private Instrutor instrutorEdicao; // NOVO: Campo para o objeto em edição

    // Construtor 1: Novo Cadastro (Chama o construtor de edição com objeto nulo)
    public TelaInstrutor(Academia academia) {
        this(academia, null); 
    }

    // Construtor 2: Edição (Recebe o objeto Instrutor existente)
    public TelaInstrutor(Academia academia, Instrutor instrutor) { // CONSTRUTOR MODIFICADO
        super();
        this.academia = academia;
        this.instrutorEdicao = instrutor; // Atribui o objeto a ser editado

        // Título dinâmico
        if (instrutorEdicao != null) {
            this.setTitle("Editar Instrutor: " + instrutorEdicao.getNome());
        } else {
            this.setTitle("Cadastro de Novo Instrutor");
        }
        
        this.setSize(500, 300);

        this.campos = new JPanel(new GridLayout(5, 2, 10, 10));
        this.botoes = new JPanel(new FlowLayout());
        this.fundo = new JPanel(new BorderLayout(10, 10));

        this.campos.add(new JLabel("ID:"));
        this.tfId = new JTextField();
        this.campos.add(this.tfId);

        this.campos.add(new JLabel("Nome:"));
        this.tfNome = new JTextField();
        this.campos.add(this.tfNome);

        this.campos.add(new JLabel("Email:"));
        this.tfEmail = new JTextField();
        this.campos.add(this.tfEmail);

        this.campos.add(new JLabel("Telefone:"));
        this.tfTelefone = new JTextField();
        this.campos.add(this.tfTelefone);

        this.campos.add(new JLabel("Formação (Descrição):"));
        this.tfFormacaoDescricao = new JTextField();
        this.campos.add(this.tfFormacaoDescricao);

        // --- Lógica de Carregamento de Dados (Se for Edição) ---
        if (instrutorEdicao != null) {
            carregarDadosInstrutor();
            tfId.setEditable(false); // Não permite alterar o ID
            btnLimpar = new JButton("Cancelar");
        } else {
            btnLimpar = new JButton("Limpar");
        }
        // --------------------------------------------------------
        
        // Inicializa btnSalvar e btnLimpar aqui
        this.btnSalvar = new JButton("Salvar");
        this.btnSalvar.addActionListener(this);
        this.botoes.add(this.btnSalvar);

        this.btnLimpar.addActionListener(this);
        this.botoes.add(this.btnLimpar);

        this.fundo.add(this.campos, BorderLayout.CENTER);
        this.fundo.add(this.botoes, BorderLayout.SOUTH);
        this.getContentPane().add(this.fundo);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    // NOVO MÉTODO: Carrega os dados para edição
    private void carregarDadosInstrutor() {
        tfId.setText(String.valueOf(instrutorEdicao.getId()));
        tfNome.setText(instrutorEdicao.getNome());
        tfEmail.setText(instrutorEdicao.getEmail());
        tfTelefone.setText(instrutorEdicao.getTelefone());
        if (instrutorEdicao.getFormacao() != null) {
             tfFormacaoDescricao.setText(instrutorEdicao.getFormacao().getDescricao());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
        Object src = e.getSource();

        if (src == btnSalvar) {
        	
            try {
                // Obtém o ID do objeto se for edição, ou do campo se for cadastro
                int id = (instrutorEdicao != null) ? instrutorEdicao.getId() : Integer.parseInt(tfId.getText());
                String nome = tfNome.getText();
                String email = tfEmail.getText();
                String telefone = tfTelefone.getText();
                String formacaoDescricao = tfFormacaoDescricao.getText();

                if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || formacaoDescricao.isEmpty()) {
                	
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Formacao formacao = new Formacao(nome, formacaoDescricao);
                
                if (instrutorEdicao == null) {
                    // MODO CADASTRO: Cria e adiciona novo Instrutor
                    Instrutor novoInstrutor = new Instrutor(id, nome, email, telefone, formacao);
                    this.academia.addInstrutor(novoInstrutor);
                    
                    // Cria e Adiciona Usuário (com senha padrão)
                    Usuario novoUsuario = new Usuario(email, "123", "instrutor");
                    novoUsuario.setPessoa(novoInstrutor);
                    this.academia.addUsuario(novoUsuario);

                    JOptionPane.showMessageDialog(this, "Instrutor " + novoInstrutor.getNome() + " cadastrado com sucesso! Login: " + novoUsuario.getLogin());
                    limparCampos();
                } else {
                    // MODO EDIÇÃO: Atualiza o objeto existente
                    instrutorEdicao.setNome(nome);
                    instrutorEdicao.setEmail(email);
                    instrutorEdicao.setTelefone(telefone);
                    instrutorEdicao.setFormacao(formacao); // Atualiza o objeto Formacao
                    
                    JOptionPane.showMessageDialog(this, "Instrutor " + instrutorEdicao.getNome() + " atualizado com sucesso!");
                    this.dispose(); 
                }
                
            } catch (NumberFormatException ex) {
            	
                JOptionPane.showMessageDialog(this, "ID deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                
            } catch (Exception ex) {
            	
                JOptionPane.showMessageDialog(this, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == btnLimpar) {
        	
            if (instrutorEdicao != null) {
                this.dispose(); // Fecha se for modo edição (Cancelar)
            } else {
                limparCampos(); // Limpa se for modo cadastro
            }
        }
    }

    private void limparCampos() {
    	
        tfId.setText("");
        tfNome.setText("");
        tfEmail.setText("");
        tfTelefone.setText("");
        tfFormacaoDescricao.setText("");
    }
}