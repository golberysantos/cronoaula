package br.com.cronoaula.cronograma.controller;

import br.com.cronoaula.cronograma.model.Cronograma;
import br.com.cronoaula.cronograma.repository.CronogramaRepository;
import br.com.cronoaula.cronograma.service.CronogramaExcelExporter;
import br.com.cronoaula.cronograma.service.CronogramaService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CronogramaController {

    private CronogramaService service;
    private CronogramaRepository repository;

    public CronogramaController() {
        this.service = new CronogramaService();
        this.repository = new CronogramaRepository();
    }

    public List<Cronograma> gerar(
            String caminhoArquivo,
            LocalDate dataInicio,
            int cargaTotal,
            int cargaPorAula,
            Set<DayOfWeek> diasSemana) {

        Set<LocalDate> naoLetivos =
                repository.lerDatasNaoLetivas(caminhoArquivo);

        return service.gerarCronograma(
                dataInicio,
                cargaTotal,
                cargaPorAula,
                diasSemana,
                naoLetivos
        );
    }
    
    
    public void exportarExcel(List<Cronograma> aulas, String caminho) {
        CronogramaExcelExporter.exportar(aulas, caminho);
    }
    
    
}