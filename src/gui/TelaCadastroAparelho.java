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

import model.Aparelho;
import model.Academia;

public class TelaCadastroAparelho extends JFrame implements ActionListener {

    private JTextField tfNome;
    private JTextField tfDescricao;
    private JTextField tfFuncao;
    private JButton btnSalvar, btnLimpar;
    private JPanel campos, botoes, fundo;
    private Academia academia;
    private Aparelho aparelhoEdicao; // NOVO: Campo para o objeto em edição

    // Construtor 1: Novo Cadastro (Chama o construtor de edição com objeto nulo)
    public TelaCadastroAparelho(Academia academia) {
        this(academia, null); 
    }
    
    // Construtor 2: Edição (Recebe o objeto Aparelho existente)
    public TelaCadastroAparelho(Academia academia, Aparelho aparelho) {
    	
        super();
        this.academia = academia;
        this.aparelhoEdicao = aparelho; // Atribui o objeto a ser editado

        // Título dinâmico
        if (aparelhoEdicao != null) {
            this.setTitle("Editar Aparelho: " + aparelhoEdicao.getNome());
        } else {
            this.setTitle("Cadastro de Novo Aparelho");
        }
        
        this.setSize(400, 180);

        this.campos = new JPanel(new GridLayout(3, 2, 10, 10));
        this.botoes = new JPanel(new FlowLayout());
        this.fundo = new JPanel(new BorderLayout(10, 10));

        this.campos.add(new JLabel("Nome:"));
        this.tfNome = new JTextField();
        this.campos.add(this.tfNome);

        this.campos.add(new JLabel("Descrição:"));
        this.tfDescricao = new JTextField();
        this.campos.add(this.tfDescricao);

        this.campos.add(new JLabel("Função:"));
        this.tfFuncao = new JTextField();
        this.campos.add(this.tfFuncao);
        
        // --- Lógica de Carregamento de Dados (Se for Edição) ---
        if (aparelhoEdicao != null) {
            carregarDadosAparelho();
            tfNome.setEditable(false); // Não permite alterar o nome/ID do aparelho
            btnLimpar = new JButton("Cancelar");
        } else {
            btnLimpar = new JButton("Limpar");
        }
        // --------------------------------------------------------

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
    private void carregarDadosAparelho() {
        tfNome.setText(aparelhoEdicao.getNome());
        tfDescricao.setText(aparelhoEdicao.getDescricao());
        tfFuncao.setText(aparelhoEdicao.getFuncao());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
        Object src = e.getSource();

        if (src == btnSalvar) {
        	
            String nome = tfNome.getText();
            String descricao = tfDescricao.getText();
            String funcao = tfFuncao.getText();

            if (nome.isEmpty() || descricao.isEmpty() || funcao.isEmpty()) {
            	
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (aparelhoEdicao == null) {
                // MODO CADASTRO: Cria e adiciona novo Aparelho
                Aparelho novoAparelho = new Aparelho(nome, descricao, funcao);
                this.academia.addAparelho(novoAparelho);
                JOptionPane.showMessageDialog(this, "Aparelho " + novoAparelho.getNome() + " cadastrado com sucesso!");
                limparCampos();
            } else {
                // MODO EDIÇÃO: Atualiza o objeto existente
                // O nome (ID) não é alterado, pois o tfNome está desabilitado na edição.
                aparelhoEdicao.setDescricao(descricao);
                aparelhoEdicao.setFuncao(funcao);
                JOptionPane.showMessageDialog(this, "Aparelho " + aparelhoEdicao.getNome() + " atualizado com sucesso!");
                this.dispose(); 
            }

        } else if (src == btnLimpar) {
        	
            if (aparelhoEdicao != null) {
                 this.dispose(); // Fecha se for modo edição (Cancelar)
            } else {
                 limparCampos(); // Limpa se for modo cadastro
            }
        }
    }

    private void limparCampos() {
    	
        tfNome.setText("");
        tfDescricao.setText("");
        tfFuncao.setText("");
    }
}