package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Academia;
import model.Aluno;
import model.Aparelho;
import model.Exercicio;
import model.Instrutor;
import model.Treino;

public class TelaTreino extends JFrame implements ActionListener {

    private JComboBox<String> cbAluno, cbInstrutor, cbDiaSemana;
    private JTextField tfNomeTreino, tfCarga, tfRepeticoes, tfOrdem;
    private JComboBox<String> cbAparelho;
    private JButton btnAdicionarExercicio, btnSalvarTreino, btnLimpar;
    private JTable tabelaExercicios;
    private DefaultTableModel tableModel;
    private Academia academia;
    private List<Exercicio> exerciciosTreino;

    public TelaTreino(Academia academia) {
        super("Definição de Treino");
        this.academia = academia;
        this.exerciciosTreino = new ArrayList<>();

        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        
        // 1. Aluno
        painelCampos.add(new JLabel("Aluno:"));
        cbAluno = new JComboBox<>();
        carregarAlunos();
        painelCampos.add(cbAluno);
        
        // 2. Instrutor
        painelCampos.add(new JLabel("Instrutor:"));
        cbInstrutor = new JComboBox<>();
        carregarInstrutores();
        painelCampos.add(cbInstrutor);

        // 3. Nome do Treino
        painelCampos.add(new JLabel("Nome do Treino:"));
        tfNomeTreino = new JTextField();
        painelCampos.add(tfNomeTreino);
        
        // 4. Dia da Semana
        painelCampos.add(new JLabel("Dia da Semana (1=Dom, 2=Seg, ...):"));
        cbDiaSemana = new JComboBox<>(new String[]{"2 - Segunda", "3 - Terça", "4 - Quarta", "5 - Quinta", "6 - Sexta", "7 - Sábado", "1 - Domingo"});
        painelCampos.add(cbDiaSemana);
        
        // 5. Espaço para Exercício - Ordem/Aparelho
        painelCampos.add(new JLabel("Ordem / Aparelho:"));
        JPanel ordemAparelhoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfOrdem = new JTextField(3);
        ordemAparelhoPanel.add(tfOrdem);
        cbAparelho = new JComboBox<>();
        carregarAparelhos();
        ordemAparelhoPanel.add(cbAparelho);
        painelCampos.add(ordemAparelhoPanel);

        // 6. Espaço para Exercício - Carga/Repetições
        painelCampos.add(new JLabel("Carga / Repetições:"));
        JPanel cargaRepeticoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfCarga = new JTextField(5);
        cargaRepeticoesPanel.add(new JLabel("Carga (kg):"));
        cargaRepeticoesPanel.add(tfCarga);
        tfRepeticoes = new JTextField(5);
        cargaRepeticoesPanel.add(new JLabel("Repetições:"));
        cargaRepeticoesPanel.add(tfRepeticoes);
        painelCampos.add(cargaRepeticoesPanel);
        
        add(painelCampos, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Ordem", "Aparelho", "Carga", "Repetições"}, 0);
        tabelaExercicios = new JTable(tableModel);
        add(new JScrollPane(tabelaExercicios), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnAdicionarExercicio = new JButton("Adicionar Exercício");
        btnSalvarTreino = new JButton("Salvar Treino");
        btnLimpar = new JButton("Limpar Campos");
        
        btnAdicionarExercicio.addActionListener(this);
        btnSalvarTreino.addActionListener(this);
        btnLimpar.addActionListener(this);
        
        painelBotoes.add(btnAdicionarExercicio);
        painelBotoes.add(btnSalvarTreino);
        painelBotoes.add(btnLimpar);
        
        add(painelBotoes, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void carregarAlunos() {
        cbAluno.removeAllItems();
        for (int i = 0; i < academia.countAluno(); i++) {
            cbAluno.addItem(academia.getAluno(i).getNome() + " (ID: " + academia.getAluno(i).getId() + ")");
        }
    }
    
    private void carregarInstrutores() {
        cbInstrutor.removeAllItems();
        for (int i = 0; i < academia.countInstrutor(); i++) {
            cbInstrutor.addItem(academia.getInstrutor(i).getNome() + " (ID: " + academia.getInstrutor(i).getId() + ")");
        }
    }

    private void carregarAparelhos() {
        cbAparelho.removeAllItems();
        for (int i = 0; i < academia.countAparelho(); i++) {
            cbAparelho.addItem(academia.getAparelho(i).getNome());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionarExercicio) {
            adicionarExercicio();
        } else if (e.getSource() == btnSalvarTreino) {
            salvarTreino();
        } else if (e.getSource() == btnLimpar) {
            limparTudo();
        }
    }

    private void adicionarExercicio() {
        try {
            int ordem = Integer.parseInt(tfOrdem.getText());
            float carga = Float.parseFloat(tfCarga.getText());
            int repeticoes = Integer.parseInt(tfRepeticoes.getText());
            String nomeAparelho = (String) cbAparelho.getSelectedItem();

            if (nomeAparelho == null || nomeAparelho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um aparelho.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Aparelho aparelho = null;
            for (int i = 0; i < academia.countAparelho(); i++) {
                if (academia.getAparelho(i).getNome().equals(nomeAparelho)) {
                    aparelho = academia.getAparelho(i);
                    break;
                }
            }
            
            if (aparelho == null) {
                 JOptionPane.showMessageDialog(this, "Erro: Aparelho não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Exercicio novoExercicio = new Exercicio(ordem, carga, repeticoes);
            novoExercicio.setAparelho(aparelho);
            
            exerciciosTreino.add(novoExercicio);
            tableModel.addRow(new Object[]{ordem, nomeAparelho, carga, repeticoes});
            
            tfOrdem.setText("");
            tfCarga.setText("");
            tfRepeticoes.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ordem, Carga e Repetições devem ser números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarTreino() {
        if (cbAluno.getSelectedItem() == null || cbInstrutor.getSelectedItem() == null || tfNomeTreino.getText().isEmpty() || exerciciosTreino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos e adicione pelo menos um exercício.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String alunoStr = (String) cbAluno.getSelectedItem();
            int alunoId = Integer.parseInt(alunoStr.substring(alunoStr.indexOf("ID: ") + 4, alunoStr.length() - 1));
            Aluno aluno = null;
            for (int i = 0; i < academia.countAluno(); i++) {
                if (academia.getAluno(i).getId() == alunoId) {
                    aluno = academia.getAluno(i);
                    break;
                }
            }
            String instrutorStr = (String) cbInstrutor.getSelectedItem();
            int instrutorId = Integer.parseInt(instrutorStr.substring(instrutorStr.indexOf("ID: ") + 4, instrutorStr.length() - 1));
            Instrutor instrutor = null;
            for (int i = 0; i < academia.countInstrutor(); i++) {
                if (academia.getInstrutor(i).getId() == instrutorId) {
                    instrutor = academia.getInstrutor(i);
                    break;
                }
            }
            String diaSemanaStr = (String) cbDiaSemana.getSelectedItem();
            int diaSemana = Integer.parseInt(diaSemanaStr.substring(0, diaSemanaStr.indexOf(" - ")));
            
            String nomeTreino = tfNomeTreino.getText();

            if (aluno == null || instrutor == null) {
                JOptionPane.showMessageDialog(this, "Erro: Aluno ou Instrutor não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Treino novoTreino = new Treino(diaSemana, nomeTreino, aluno, instrutor);
            for (Exercicio e : exerciciosTreino) {
                novoTreino.addExercicio(e);
            }
            aluno.addTreino(novoTreino);
            
            JOptionPane.showMessageDialog(this, "Treino '" + novoTreino.getNome() + "' para " + aluno.getNome() + " salvo com sucesso!");
            
            limparTudo();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar treino: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limparTudo() {
        tfNomeTreino.setText("");
        tfOrdem.setText("");
        tfCarga.setText("");
        tfRepeticoes.setText("");
        exerciciosTreino.clear();
        tableModel.setRowCount(0);
        
        if (cbAluno.getItemCount() > 0) cbAluno.setSelectedIndex(0);
        if (cbInstrutor.getItemCount() > 0) cbInstrutor.setSelectedIndex(0);
        if (cbDiaSemana.getItemCount() > 0) cbDiaSemana.setSelectedIndex(0);
        if (cbAparelho.getItemCount() > 0) cbAparelho.setSelectedIndex(0);
    }
}