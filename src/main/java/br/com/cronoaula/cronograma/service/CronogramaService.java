package br.com.cronoaula.cronograma.service;

import br.com.cronoaula.cronograma.model.Cronograma;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CronogramaService {

    public List<Cronograma> gerarCronograma(
            LocalDate dataInicio,
            int cargaTotal,
            int cargaPorAula,
            Set<DayOfWeek> diasSemana,
            Set<LocalDate> naoLetivos) {

        List<Cronograma> aulas = new ArrayList<>();

        int totalAulas = (int) Math.ceil((double) cargaTotal / cargaPorAula);

        LocalDate data = dataInicio;

        while (aulas.size() < totalAulas) {

            if (diasSemana.contains(data.getDayOfWeek())) {

                if (!naoLetivos.contains(data)) {
                    aulas.add(new Cronograma(data));
                }
            }

            data = data.plusDays(1);
        }

        return aulas;
    }
}