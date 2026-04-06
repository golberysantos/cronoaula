package br.com.cronoaula.cronograma.model;

import java.time.LocalDate;

public class Cronograma {

    private LocalDate data;

    public Cronograma(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }
}