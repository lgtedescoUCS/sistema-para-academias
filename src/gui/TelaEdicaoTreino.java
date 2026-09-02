package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import model.Aparelho;
import model.Exercicio;
import model.Treino;

public class TelaEdicaoTreino extends JFrame implements ActionListener {

    private Treino treino;
    private Academia academia;
    
    // Componentes de Edição do Treino
    private JTextField tfNomeTreino;
    private JComboBox<String> cbDiaSemana;
    
    // Componentes de Edição de Exercício
    private JTextField tfCarga, tfRepeticoes, tfOrdem;
    private JComboBox<String> cbAparelho;
    private JButton btnAdicionarExercicio, btnRemoverExercicio, btnSalvar, btnVoltar;
    private JTable tabelaExercicios;
    private DefaultTableModel tableModel;

    public TelaEdicaoTreino(Academia academia, Treino treino) {
        super("Editar Treino: " + treino.getNome() + " para o Aluno: " + treino.getAluno().getNome());
        this.academia = academia;
        this.treino = treino;

        // Configuração da Janela
        setSize(750, 550);
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Detalhes do Treino ---
        JPanel painelDetalhes = new JPanel(new GridLayout(4, 2, 10, 10));
        
        painelDetalhes.add(new JLabel("Aluno:"));
        painelDetalhes.add(new JLabel(treino.getAluno().getNome() + " (ID: " + treino.getAluno().getId() + ")"));
        
        painelDetalhes.add(new JLabel("Instrutor:"));
        painelDetalhes.add(new JLabel(treino.getInstrutor().getNome() + " (ID: " + treino.getInstrutor().getId() + ")"));
        
        painelDetalhes.add(new JLabel("Nome do Treino:"));
        tfNomeTreino = new JTextField(treino.getNome());
        painelDetalhes.add(tfNomeTreino);

        painelDetalhes.add(new JLabel("Dia da Semana (1=Dom, 7=Sáb):"));
        cbDiaSemana = new JComboBox<>(new String[]{"1 - Domingo", "2 - Segunda", "3 - Terça", "4 - Quarta", "5 - Quinta", "6 - Sexta", "7 - Sábado"});
        
        // Seleciona o dia atual. O índice é (Dia da Semana - 1).
        cbDiaSemana.setSelectedIndex(treino.getDiaSemana() - 1); 
        painelDetalhes.add(cbDiaSemana);
        
        add(painelDetalhes, BorderLayout.NORTH);

        // --- Painel de Edição de Exercícios ---
        
        // Sub-painel para adicionar novos exercícios
        JPanel painelAddEx = new JPanel(new FlowLayout());
        
        painelAddEx.add(new JLabel("Ordem:"));
        tfOrdem = new JTextField(3);
        painelAddEx.add(tfOrdem);
        
        painelAddEx.add(new JLabel("Aparelho:"));
        cbAparelho = new JComboBox<>();
        carregarAparelhos();
        painelAddEx.add(cbAparelho);
        
        painelAddEx.add(new JLabel("Carga (kg):"));
        tfCarga = new JTextField(5);
        painelAddEx.add(tfCarga);
        
        painelAddEx.add(new JLabel("Repetições:"));
        tfRepeticoes = new JTextField(5);
        painelAddEx.add(tfRepeticoes);
        
        btnAdicionarExercicio = new JButton("Adicionar");
        btnAdicionarExercicio.addActionListener(this);
        painelAddEx.add(btnAdicionarExercicio);
        
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelAddEx, BorderLayout.NORTH);
        
        // Tabela de Exercícios
        tableModel = new DefaultTableModel(new Object[]{"Ordem", "Aparelho", "Carga", "Repetições"}, 0);
        tabelaExercicios = new JTable(tableModel);
        carregarTabelaExercicios();
        
        btnRemoverExercicio = new JButton("Remover Selecionado");
        btnRemoverExercicio.addActionListener(this);
        
        JPanel painelRemover = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRemover.add(btnRemoverExercicio);
        
        painelCentral.add(new JScrollPane(tabelaExercicios), BorderLayout.CENTER);
        painelCentral.add(painelRemover, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        // --- Painel de Salvar/Voltar ---
        JPanel painelSalvar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar Alterações");
        btnVoltar = new JButton("Voltar sem Salvar");
        
        btnSalvar.addActionListener(this);
        btnVoltar.addActionListener(this);
        
        painelSalvar.add(btnSalvar);
        painelSalvar.add(btnVoltar);
        
        add(painelSalvar, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void carregarAparelhos() {
        cbAparelho.removeAllItems();
        if (academia.countAparelho() == 0) {
            cbAparelho.addItem("Nenhum Aparelho");
            return;
        }
        for (int i = 0; i < academia.countAparelho(); i++) {
            cbAparelho.addItem(academia.getAparelho(i).getNome());
        }
    }
    
    private void carregarTabelaExercicios() {
        tableModel.setRowCount(0); // Limpa a tabela
        for (int i = 0; i < treino.countExercicio(); i++) {
            Exercicio e = treino.getExercicio(i);
            // É necessário que Treino.java tenha getExercicio(i) implementado para obter o objeto Exercicio
            // E que o Exercicio tenha um Aparelho associado e métodos de acesso (getNome, getCarga, etc.)
            
            // Note: Carga e Repetições são tipos primitivos (float e int), são seguros para usar no JTable
            tableModel.addRow(new Object[]{
                e.getOrdem(), 
                e.getAparelho() != null ? e.getAparelho().getNome() : "N/A", 
                e.getCarga(), 
                e.getNroRepeticoes()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVoltar) {
            this.dispose();
        } else if (e.getSource() == btnSalvar) {
            salvarTreino();
        } else if (e.getSource() == btnAdicionarExercicio) {
            adicionarExercicio();
        } else if (e.getSource() == btnRemoverExercicio) {
            removerExercicio();
        }
    }

    private void adicionarExercicio() {
        if (cbAparelho.getSelectedItem() == null || cbAparelho.getSelectedItem().toString().contains("Nenhum")) {
             JOptionPane.showMessageDialog(this, "Selecione um aparelho válido.", "Erro", JOptionPane.ERROR_MESSAGE);
             return;
        }
        try {
            int ordem = Integer.parseInt(tfOrdem.getText());
            float carga = Float.parseFloat(tfCarga.getText());
            int repeticoes = Integer.parseInt(tfRepeticoes.getText());
            String nomeAparelho = (String) cbAparelho.getSelectedItem();
            
            // Adiciona diretamente à tabela (a persistência final é no salvarTreino)
            tableModel.addRow(new Object[]{ordem, nomeAparelho, carga, repeticoes});
            
            tfOrdem.setText("");
            tfCarga.setText("");
            tfRepeticoes.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ordem, Carga e Repetições devem ser números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerExercicio() {
        int selectedRow = tabelaExercicios.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um exercício para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarTreino() {
        // 1. Atualiza detalhes do treino (nome e dia)
        String nomeTreino = tfNomeTreino.getText();
        String diaSemanaStr = (String) cbDiaSemana.getSelectedItem();
        
        // Extrai o número do dia (ex: "2 - Segunda" -> 2)
        int diaSemana = Integer.parseInt(diaSemanaStr.substring(0, diaSemanaStr.indexOf(" - ")));
        
        if (nomeTreino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do treino não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        treino.setNome(nomeTreino);
        treino.setDiaSemana(diaSemana); // Assume que Treino.java tem setDiaSemana(int)
        
        // 2. Reconstroi e salva a lista de exercícios
        ArrayList<Exercicio> novosExercicios = new ArrayList<>();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                int ordem = (int) tableModel.getValueAt(i, 0);
                String nomeAparelho = (String) tableModel.getValueAt(i, 1);
                float carga = (float) tableModel.getValueAt(i, 2);
                int repeticoes = (int) tableModel.getValueAt(i, 3);
                
                Aparelho aparelho = null;
                // Busca o objeto Aparelho na Academia
                for (int j = 0; j < academia.countAparelho(); j++) {
                    if (academia.getAparelho(j).getNome().equals(nomeAparelho)) {
                        aparelho = academia.getAparelho(j);
                        break;
                    }
                }
                
                Exercicio novoExercicio = new Exercicio(ordem, carga, repeticoes);
                novoExercicio.setAparelho(aparelho);
                novosExercicios.add(novoExercicio);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao processar exercício na linha " + (i + 1) + ".", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // 3. Salva a nova lista no objeto Treino
        // O método setExercicios foi adicionado na classe Treino.java (implementado em passos anteriores)
        Exercicio[] novoArray = novosExercicios.toArray(new Exercicio[novosExercicios.size()]);
        treino.setExercicios(novoArray);
        
        JOptionPane.showMessageDialog(this, "Treino '" + nomeTreino + "' atualizado com sucesso!");
        this.dispose();
    }
}