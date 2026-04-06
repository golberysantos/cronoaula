package br.com.cronoaula.appcronoaula;

import br.com.cronoaula.cronograma.view.CronogramaView;

import javax.swing.*;

public class AppCronoAula {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new CronogramaView().setVisible(true);
        });
    }
}