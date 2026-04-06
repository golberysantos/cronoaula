package br.com.cronoaula.appcronoaula;

import br.com.cronoaula.cronograma.view.CronogramaView;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
public class AppCronoAula {

    public static void main(String[] args) {
    	
    	try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new CronogramaView().setVisible(true);
        });
    }
}