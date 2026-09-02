package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout; // Importação necessária para o JPanel de botões
import java.awt.event.ActionEvent; // Importação necessária
import java.awt.event.ActionListener; // Importação necessária
import javax.swing.JButton; // Importação necessária
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Academia;
import model.Aluno;
import model.Exercicio;
import model.Treino;

// Implementa ActionListener para lidar com o evento do botão
public class TelaAluno extends JFrame implements ActionListener {

    private Aluno aluno;
    private Academia academia;
    private JTextArea taDetalhesTreinos;
    private JButton btnTrocarUsuario; 

    public TelaAluno(Aluno aluno, Academia academia) {
        super("Área do Aluno - " + aluno.getNome());
        this.aluno = aluno;
        this.academia = academia;

        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));

        // Painel de Dados do Aluno
        JPanel painelDados = new JPanel(new GridLayout(3, 2, 5, 5));
        painelDados.add(new JLabel("ID:"));
        painelDados.add(new JLabel(String.valueOf(aluno.getId())));
        painelDados.add(new JLabel("Nome:"));
        painelDados.add(new JLabel(aluno.getNome()));
        painelDados.add(new JLabel("Altura:"));
        painelDados.add(new JLabel(String.valueOf(aluno.getAltura()) + "m"));
        
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelDados, BorderLayout.CENTER);

        JPanel painelBotaoLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTrocarUsuario = new JButton("Voltar");
        btnTrocarUsuario.addActionListener(this); 
        painelBotaoLogout.add(btnTrocarUsuario);
        
        painelSuperior.add(painelBotaoLogout, BorderLayout.SOUTH);
        
        add(painelSuperior, BorderLayout.NORTH); 

        taDetalhesTreinos = new JTextArea();
        taDetalhesTreinos.setEditable(false);
        carregarTreinos();
        
        add(new JScrollPane(taDetalhesTreinos), BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTrocarUsuario) {
            this.dispose();
            new TelaLogin(academia); 
        }
    }

    
    private void carregarTreinos() {
        StringBuilder sb = new StringBuilder("--- TREINOS CADASTRADOS ---\n\n");
        
        if (aluno.countTreino() == 0) {
            sb.append("Nenhum treino definido para você.");
        } else {
            for (int i = 0; i < aluno.countTreino(); i++) {
                Treino t = aluno.getTreino(i);
                
                // Mapeamento Dia da Semana
                String diaSemanaNome;
                switch (t.getDiaSemana()) {
                    case 1: diaSemanaNome = "Domingo"; break;
                    case 2: diaSemanaNome = "Segunda"; break;
                    case 3: diaSemanaNome = "Terça"; break;
                    case 4: diaSemanaNome = "Quarta"; break;
                    case 5: diaSemanaNome = "Quinta"; break;
                    case 6: diaSemanaNome = "Sexta"; break;
                    case 7: diaSemanaNome = "Sábado"; break;
                    default: diaSemanaNome = "Dia Inválido";
                }
                
                sb.append("Treino: ").append(t.getNome())
                  .append(" (Dia: ").append(diaSemanaNome)
                  .append(", Instrutor: ").append(t.getInstrutor().getNome()).append(")\n");
                
                sb.append("  Exercícios:\n");
                for (int j = 0; j < t.countExercicio(); j++) {
                    Exercicio ex = t.getExercicio(j);
                    sb.append("    - ").append(ex.getOrdem()).append(". ")
                      .append(ex.getAparelho().getNome())
                      .append(" - Carga: ").append(ex.getCarga())
                      .append("kg - Repetições: ").append(ex.getNroRepeticoes()).append("\n");
                }
                sb.append("\n");
            }
        }
        taDetalhesTreinos.setText(sb.toString());
    }
}