package br.com.cronoaula.cronograma.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.cronoaula.cronograma.controller.CronogramaController;
import br.com.cronoaula.cronograma.model.Cronograma;

public class CronogramaView extends JFrame {

    private JTextField txtCargaTotal;
    private JTextField txtCargaAula;
    private JTextField txtArquivo;
    private JTextField txtDataInicio;

    private JCheckBox seg, ter, qua, qui, sex, sab;

    private JTextArea resultado;

    private CronogramaController controller;

    public CronogramaView() {

        controller = new CronogramaController();

        setTitle("CronoAula");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // =========================
        // TÍTULO
        // =========================
        JLabel titulo = new JLabel("CronoAula - Gerador de Cronograma");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(titulo, BorderLayout.NORTH);

        // =========================
        // PAINEL PRINCIPAL
        // =========================
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // =========================
        // PAINEL DE DADOS
        // =========================
        JPanel painelDados = new JPanel(new GridLayout(0, 2, 10, 10));
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Curso"));

        txtCargaTotal = new JTextField();
        txtCargaAula = new JTextField();
        txtArquivo = new JTextField();
        txtDataInicio = new JTextField("2026-01-01");

        painelDados.add(new JLabel("Carga Horária Total:"));
        painelDados.add(txtCargaTotal);

        painelDados.add(new JLabel("Carga por Aula:"));
        painelDados.add(txtCargaAula);

        painelDados.add(new JLabel("Data de Início:"));
        painelDados.add(txtDataInicio);

        painelDados.add(new JLabel("Arquivo Excel:"));

        JPanel painelArquivo = new JPanel(new BorderLayout(5, 0));
        JButton btnSelecionar = new JButton("Selecionar");
        painelArquivo.add(txtArquivo, BorderLayout.CENTER);
        painelArquivo.add(btnSelecionar, BorderLayout.EAST);

        painelDados.add(painelArquivo);

        btnSelecionar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                txtArquivo.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        // =========================
        // PAINEL DIAS DA SEMANA
        // =========================
        JPanel painelDias = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        painelDias.setBorder(BorderFactory.createTitledBorder("Dias da Semana"));

        seg = new JCheckBox("Seg");
        ter = new JCheckBox("Ter");
        qua = new JCheckBox("Qua");
        qui = new JCheckBox("Qui");
        sex = new JCheckBox("Sex");
        sab = new JCheckBox("Sab");

        painelDias.add(seg);
        painelDias.add(ter);
        painelDias.add(qua);
        painelDias.add(qui);
        painelDias.add(sex);
        painelDias.add(sab);

        // =========================
        // BOTÃO
        // =========================
        JButton btnCalcular = new JButton("Gerar Cronograma");
        btnCalcular.setPreferredSize(new Dimension(180, 40));
        btnCalcular.setFocusPainted(false);

        JPanel painelBotao = new JPanel();
        painelBotao.add(btnCalcular);

        // =========================
        // RESULTADO
        // =========================
        resultado = new JTextArea();
        resultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(resultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Cronograma Gerado"));

        // =========================
        // ORGANIZAÇÃO CENTRAL
        // =========================
        JPanel topo = new JPanel(new BorderLayout(10, 10));
        topo.add(painelDados, BorderLayout.NORTH);
        topo.add(painelDias, BorderLayout.CENTER);
        topo.add(painelBotao, BorderLayout.SOUTH);

        painelPrincipal.add(topo, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal, BorderLayout.CENTER);

        // =========================
        // AÇÃO BOTÃO
        // =========================
        btnCalcular.addActionListener(e -> calcular());
        
        
        
     // =========================
        // BARRA DE STATUS
        // =========================
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        // Texto central
        JLabel statusCentro = new JLabel("By Golbery Santos");
        statusCentro.setHorizontalAlignment(SwingConstants.CENTER);
        statusCentro.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Versão (lado direito)
        JLabel versao = new JLabel("v1.0");
        versao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versao.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Adicionando na barra
        statusBar.add(statusCentro, BorderLayout.CENTER);
        statusBar.add(versao, BorderLayout.EAST);

        // Adiciona ao frame
        add(statusBar, BorderLayout.SOUTH);	
    }

    private void calcular() {

        try {

            int cargaTotal = Integer.parseInt(txtCargaTotal.getText());
            int cargaAula = Integer.parseInt(txtCargaAula.getText());
            LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText());

            Set<DayOfWeek> dias = new HashSet<>();

            if (seg.isSelected()) dias.add(DayOfWeek.MONDAY);
            if (ter.isSelected()) dias.add(DayOfWeek.TUESDAY);
            if (qua.isSelected()) dias.add(DayOfWeek.WEDNESDAY);
            if (qui.isSelected()) dias.add(DayOfWeek.THURSDAY);
            if (sex.isSelected()) dias.add(DayOfWeek.FRIDAY);
            if (sab.isSelected()) dias.add(DayOfWeek.SATURDAY);

            if (dias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione ao menos um dia.");
                return;
            }

            List<Cronograma> aulas = controller.gerar(
                    txtArquivo.getText(),
                    dataInicio,
                    cargaTotal,
                    cargaAula,
                    dias
            );

            resultado.setText("");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Cronograma aula : aulas) {
                resultado.append(
                        aula.getData().getDayOfWeek() + " - " +
                        aula.getData().format(fmt) + "\n"
                );
            }

            if (!aulas.isEmpty()) {
                resultado.append("\nInício: " + aulas.get(0).getData().format(fmt));
                resultado.append("\nFim: " + aulas.get(aulas.size()-1).getData().format(fmt));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}