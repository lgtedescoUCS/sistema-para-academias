package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Academia;
import model.Aluno;


public class TesteCadastroeEdicaoAluno extends JFrame implements ActionListener {

    private JTextField tfId;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JTextField tfTelefone;
    private JTextField tfDataNascimento;
    private JTextField tfAltura;
    private JButton btnSalvar, btnLimpar;
    private JPanel campos, botoes, fundo;
    private Academia academia;
    
    private Aluno alunoEdicao; 

    // Construtor 1: Para NOVO CADASTRO
    public TesteCadastroeEdicaoAluno(Academia academia) {
        this(academia, null); // Chama o construtor de edição com alunoEdicao = null
    }
    
    // Construtor 2: Para EDIÇÃO (Recebe o objeto Aluno existente)
    public TesteCadastroeEdicaoAluno(Academia academia, Aluno aluno) {
    	
        super();
        this.academia = academia;
        this.alunoEdicao = aluno; // Atribui o aluno a ser editado (ou null)
        
        // Define o título de forma dinâmica
        if (alunoEdicao != null) {
            this.setTitle("Editar Aluno: " + alunoEdicao.getNome());
        } else {
            this.setTitle("Cadastro de Novo Aluno");
        }
        
        this.setSize(500, 350);

        this.campos = new JPanel(new GridLayout(6, 2, 10, 10));
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

        this.campos.add(new JLabel("Data Nascimento (AAAA-MM-DD):"));
        this.tfDataNascimento = new JTextField();
        this.campos.add(this.tfDataNascimento);

        this.campos.add(new JLabel("Altura (ex: 1.75):"));
        this.tfAltura = new JTextField();
        this.campos.add(this.tfAltura);
        
        // --- Lógica de Carregamento de Dados (Se for Edição) ---
        if (alunoEdicao != null) {
            carregarDadosAluno();
            tfId.setEditable(false); // Não permite alterar o ID em modo de edição
        }
        // --------------------------------------------------------

        this.btnSalvar = new JButton("Salvar");
        this.btnSalvar.addActionListener(this);
        this.botoes.add(this.btnSalvar);
        
        this.btnLimpar = new JButton();
        
        if (alunoEdicao != null) {
            this.btnLimpar.setText("Cancelar");
        } else {
             this.btnLimpar.setText("Limpar");
        }
        
        this.btnLimpar.addActionListener(this);
        this.botoes.add(this.btnLimpar);

        this.fundo.add(this.campos, BorderLayout.CENTER);
        this.fundo.add(this.botoes, BorderLayout.SOUTH);
        this.getContentPane().add(this.fundo);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void carregarDadosAluno() {
        tfId.setText(String.valueOf(alunoEdicao.getId()));
        tfNome.setText(alunoEdicao.getNome());
        tfEmail.setText(alunoEdicao.getEmail());
        tfTelefone.setText(alunoEdicao.getTelefone());
        tfDataNascimento.setText(alunoEdicao.getDataNascimento().toString());
        tfAltura.setText(String.valueOf(alunoEdicao.getAltura()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
        Object src = e.getSource();

        if (src == btnSalvar) {
        	
            try {
                // Obtém o ID do objeto se for edição (ID imutável), ou do campo se for cadastro
                int id = (alunoEdicao != null) ? alunoEdicao.getId() : Integer.parseInt(tfId.getText());
                String nome = tfNome.getText();
                String email = tfEmail.getText();
                String telefone = tfTelefone.getText();
                LocalDate dataNascimento = LocalDate.parse(tfDataNascimento.getText());
                float altura = Float.parseFloat(tfAltura.getText());

                if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                	
                    JOptionPane.showMessageDialog(this, "Todos os campos de texto devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    
                } else {
                    
                    if (alunoEdicao == null) {
                        // MODO CADASTRO: Cria e adiciona novo Aluno
                        Aluno novoAluno = new Aluno(id, nome, email, telefone, dataNascimento, altura);
                        this.academia.addAluno(novoAluno); 
                        JOptionPane.showMessageDialog(this, "Aluno " + novoAluno.getNome() + " cadastrado com sucesso!");
                        limparCampos();
                    } else {
                        // MODO EDIÇÃO: Atualiza os dados do objeto existente (que é a referência na Academia)
                        alunoEdicao.setNome(nome);
                        alunoEdicao.setEmail(email);
                        alunoEdicao.setTelefone(telefone);
                        alunoEdicao.setDataNascimento(dataNascimento);
                        alunoEdicao.setAltura(altura);
                        JOptionPane.showMessageDialog(this, "Aluno " + alunoEdicao.getNome() + " atualizado com sucesso!");
                        this.dispose(); // Fecha a tela após a edição
                    }
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e Altura devem ser números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de Data de Nascimento inválido. Use AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == btnLimpar) {
            if (alunoEdicao != null) {
                this.dispose(); // Se for "Cancelar" (Modo Edição), fecha a janela
            } else {
                limparCampos(); // Se for "Limpar" (Modo Cadastro), limpa os campos
            }
        }
    }

    private void limparCampos() {
    	
        tfId.setText("");
        tfNome.setText("");
        tfEmail.setText("");
        tfTelefone.setText("");
        tfDataNascimento.setText("");
        tfAltura.setText("");
    }
}