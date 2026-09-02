package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Academia;
import model.Usuario;
import model.Aluno;   
import model.Instrutor; 
import model.Pessoa;  

public class TelaCadastroUsuario extends JFrame implements ActionListener {

    private JTextField tfLogin;
    private JPasswordField tfSenha;
    private JComboBox<String> cbTipo;
    private JComboBox<String> cbPessoa; 
    private JLabel lblPessoa;          
    private JButton btnSalvar, btnLimpar;
    private JPanel campos, botoes, fundo;
    private Academia academia;
    private Usuario usuarioEdicao; // CAMPO ADICIONADO para o modo de edição
    
    // Construtor 1: Novo Cadastro (Chama o construtor de edição com objeto nulo)
    public TelaCadastroUsuario(Academia academia) {
        this(academia, null);
    }

    // Construtor 2: Edição (NOVO CONSTRUTOR para ser chamado pela TelaGerenciamento)
    public TelaCadastroUsuario(Academia academia, Usuario usuario) {
        super();
        this.academia = academia;
        this.usuarioEdicao = usuario; // Atribui o objeto a ser editado

        // Define o título de forma dinâmica
        if (usuarioEdicao != null) {
            this.setTitle("Editar Usuário: " + usuarioEdicao.getLogin());
        } else {
            this.setTitle("Cadastro de Novo Usuário");
        }
        
        this.setSize(450, 300); 
        this.campos = new JPanel(new GridLayout(4, 2, 10, 10)); 
        this.botoes = new JPanel(new FlowLayout());
        this.fundo = new JPanel(new BorderLayout(10, 10));

        this.campos.add(new JLabel("Login:"));
        this.tfLogin = new JTextField();
        this.campos.add(this.tfLogin);

        this.campos.add(new JLabel("Senha (Deixar Vazio para Manter):")); 
        this.tfSenha = new JPasswordField();
        this.campos.add(this.tfSenha);

        this.campos.add(new JLabel("Tipo:"));
        this.cbTipo = new JComboBox<>(new String[]{"admin", "instrutor", "aluno"});
        this.campos.add(this.cbTipo);
        
        lblPessoa = new JLabel("Pessoa Associada (ID/Nome):");
        this.campos.add(lblPessoa);
        cbPessoa = new JComboBox<>();
        this.campos.add(cbPessoa);
        
        this.cbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarListaPessoas();
            }
        });
        
        // --- Lógica de Carregamento de Dados (Se for Edição) ---
        if (usuarioEdicao != null) {
            carregarDadosUsuario();
            tfLogin.setEditable(false); // Não permite alterar o login em modo de edição
            btnLimpar = new JButton("Cancelar");
        } else {
             btnLimpar = new JButton("Limpar");
        }
        // --------------------------------------------------------

        this.btnSalvar = new JButton("Salvar");
        this.btnSalvar.addActionListener(this);
        this.botoes.add(this.btnSalvar);

        // Inicializa btnLimpar (agora que ele foi definido condicionalmente)
        this.btnLimpar.addActionListener(this);
        this.botoes.add(this.btnLimpar);

        this.fundo.add(this.campos, BorderLayout.CENTER);
        this.fundo.add(this.botoes, BorderLayout.SOUTH);
        this.getContentPane().add(this.fundo);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        atualizarListaPessoas();
    }
    
    // NOVO MÉTODO: Carrega os dados para edição
    private void carregarDadosUsuario() {
        tfLogin.setText(usuarioEdicao.getLogin());
        cbTipo.setSelectedItem(usuarioEdicao.getTipo());
        
        // Tenta pré-selecionar a pessoa
        if (usuarioEdicao.getPessoa() != null) {
            // Recarrega a lista para ter certeza de que o item existe antes de tentar selecionar
            atualizarListaPessoas(); 
            String itemText = usuarioEdicao.getPessoa().getNome() + " (ID: " + usuarioEdicao.getPessoa().getId() + ")";
            cbPessoa.setSelectedItem(itemText);
        }
    }
    
    private void atualizarListaPessoas() {
        String tipoSelecionado = (String) cbTipo.getSelectedItem();
        cbPessoa.removeAllItems();

        lblPessoa.setVisible(!tipoSelecionado.equalsIgnoreCase("admin"));
        cbPessoa.setVisible(!tipoSelecionado.equalsIgnoreCase("admin"));

        if (tipoSelecionado.equalsIgnoreCase("aluno")) {
            if (academia.countAluno() == 0) {
                cbPessoa.addItem("Nenhum Aluno cadastrado.");
            }
            for (int i = 0; i < academia.countAluno(); i++) {
                Aluno a = academia.getAluno(i);
                cbPessoa.addItem(a.getNome() + " (ID: " + a.getId() + ")");
            }
        } else if (tipoSelecionado.equalsIgnoreCase("instrutor")) {
            if (academia.countInstrutor() == 0) {
                 cbPessoa.addItem("Nenhum Instrutor cadastrado.");
            }
            for (int i = 0; i < academia.countInstrutor(); i++) {
                Instrutor ins = academia.getInstrutor(i);
                cbPessoa.addItem(ins.getNome() + " (ID: " + ins.getId() + ")");
            }
        } else {
            cbPessoa.addItem("N/A (Admin)");
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnSalvar) {
            String login = tfLogin.getText();
            String senha = new String(tfSenha.getPassword());
            String tipo = (String) cbTipo.getSelectedItem();
            
            Pessoa pessoaAssociada = null; 

            if (login.isEmpty() || tipo == null) {
                JOptionPane.showMessageDialog(this, "O campo Login e Tipo devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } 
            
            // --- Lógica de Associação/Atualização ---
            if (!tipo.equalsIgnoreCase("admin")) {
                String pessoaStr = (String) cbPessoa.getSelectedItem();
                
                if (pessoaStr == null || pessoaStr.contains("Nenhum")) {
                    JOptionPane.showMessageDialog(this, "Para o tipo '" + tipo + "', uma Pessoa deve ser selecionada (e cadastrada primeiro).", "Erro de Associação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    // Extrai o ID da string "Nome (ID: X)"
                    int idInicio = pessoaStr.indexOf("ID: ") + 4;
                    int idFim = pessoaStr.indexOf(")", idInicio);
                    int pessoaId = Integer.parseInt(pessoaStr.substring(idInicio, idFim));
                    
                    if (tipo.equalsIgnoreCase("aluno")) {
                        for (int i = 0; i < academia.countAluno(); i++) {
                            if (academia.getAluno(i).getId() == pessoaId) {
                                pessoaAssociada = academia.getAluno(i);
                                break;
                            }
                        }
                    } else if (tipo.equalsIgnoreCase("instrutor")) {
                        for (int i = 0; i < academia.countInstrutor(); i++) {
                            if (academia.getInstrutor(i).getId() == pessoaId) {
                                pessoaAssociada = academia.getInstrutor(i);
                                break;
                            }
                        }
                    }
                    
                    if (pessoaAssociada == null) {
                        JOptionPane.showMessageDialog(this, "Erro: Pessoa com ID " + pessoaId + " não encontrada no cadastro da Academia.", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                     JOptionPane.showMessageDialog(this, "Erro ao extrair ID da pessoa selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                     return;
                }
            }
            // --- Fim Lógica de Associação ---

            if (usuarioEdicao == null) {
                // MODO CADASTRO
                Usuario novoUsuario = new Usuario(login, senha, tipo);
                novoUsuario.setPessoa(pessoaAssociada); 
                academia.addUsuario(novoUsuario);
                
                JOptionPane.showMessageDialog(this, "Usuário '" + login + "' cadastrado com sucesso!");
                limparCampos();
            } else {
                // MODO EDIÇÃO
                usuarioEdicao.setTipo(tipo);
                usuarioEdicao.setPessoa(pessoaAssociada); 
                
                if (!senha.isEmpty()) {
                    usuarioEdicao.setSenha(senha);
                }
                
                JOptionPane.showMessageDialog(this, "Usuário '" + login + "' atualizado com sucesso!");
                this.dispose();
            }

        } else if (src == btnLimpar) {
            if (usuarioEdicao != null) {
                 this.dispose(); 
            } else {
                 limparCampos();
            }
        }
    }

    private void limparCampos() {
        tfLogin.setText("");
        tfSenha.setText("");
        cbTipo.setSelectedIndex(0);
        atualizarListaPessoas();
    }
}