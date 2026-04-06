package br.com.cronoaula.cronograma.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel painel = new JPanel(new GridLayout(0, 2));

		txtCargaTotal = new JTextField();
		txtCargaAula = new JTextField();
		txtArquivo = new JTextField();
		txtDataInicio = new JTextField("2026-01-01");

		painel.add(new JLabel("Carga Total:"));
		painel.add(txtCargaTotal);

		painel.add(new JLabel("Carga por Aula:"));
		painel.add(txtCargaAula);

		painel.add(new JLabel("Data Início:"));
		painel.add(txtDataInicio);

		painel.add(new JLabel("Arquivo Excel:"));
		painel.add(txtArquivo);

		JButton btnSelecionar = new JButton("Selecionar Excel");
		painel.add(btnSelecionar);

		btnSelecionar.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				txtArquivo.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});

		seg = new JCheckBox("Seg");
		ter = new JCheckBox("Ter");
		qua = new JCheckBox("Qua");
		qui = new JCheckBox("Qui");
		sex = new JCheckBox("Sex");
		sab = new JCheckBox("Sab");

		painel.add(seg);
		painel.add(ter);
		painel.add(qua);
		painel.add(qui);
		painel.add(sex);
		painel.add(sab);

		JButton btnCalcular = new JButton("Calcular");

		resultado = new JTextArea();

		/*
		 * add(painel, BorderLayout.NORTH); add(btnCalcular, BorderLayout.CENTER);
		 * add(new JScrollPane(resultado), BorderLayout.SOUTH);
		 */

		JPanel painelCentro = new JPanel(new BorderLayout());

		// painelCentro.add(btnCalcular, BorderLayout.NORTH);
		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));

		btnCalcular.setPreferredSize(new Dimension(120, 35));
		painelBotao.add(btnCalcular);
		painelCentro.add(painelBotao, BorderLayout.NORTH);
		painelCentro.add(new JScrollPane(resultado), BorderLayout.CENTER);

		add(painel, BorderLayout.NORTH);
		add(painelCentro, BorderLayout.CENTER);

		btnCalcular.addActionListener(e -> calcular());
	}

	private void calcular() {

		try {

			int cargaTotal = Integer.parseInt(txtCargaTotal.getText());
			int cargaAula = Integer.parseInt(txtCargaAula.getText());

			LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText());

			Set<DayOfWeek> dias = new HashSet<>();

			if (seg.isSelected())
				dias.add(DayOfWeek.MONDAY);
			if (ter.isSelected())
				dias.add(DayOfWeek.TUESDAY);
			if (qua.isSelected())
				dias.add(DayOfWeek.WEDNESDAY);
			if (qui.isSelected())
				dias.add(DayOfWeek.THURSDAY);
			if (sex.isSelected())
				dias.add(DayOfWeek.FRIDAY);
			if (sab.isSelected())
				dias.add(DayOfWeek.SATURDAY);

			if (dias.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Selecione ao menos um dia.");
				return;
			}

			List<Cronograma> aulas = controller.gerar(txtArquivo.getText(), dataInicio, cargaTotal, cargaAula, dias);

			resultado.setText("");

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for (Cronograma aula : aulas) {
				resultado.append(aula.getData().getDayOfWeek() + " - " + aula.getData().format(fmt) + "\n");
			}

			if (!aulas.isEmpty()) {
				resultado.append("\nInício: " + aulas.get(0).getData().format(fmt));
				resultado.append("\nFim: " + aulas.get(aulas.size() - 1).getData().format(fmt));
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
		}
	}
}