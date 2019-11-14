package ua.andrii.verkholiak.service;

import ua.andrii.verkholiak.entity.Lector;
import ua.andrii.verkholiak.repository.LectorRepository;

import java.util.Set;

public class LectorService {

    private static LectorService INSTANCE;
    private LectorRepository lectorRepository;

    private LectorService() {
        lectorRepository = LectorRepository.getLectorRepository();
    }

    public static LectorService getLectorService() {
        if (INSTANCE == null) {
            INSTANCE = new LectorService();
        }

        return INSTANCE;
    }

    public Set<Lector> getAllLectorsByDepartmentName(String departmentName) {
        return lectorRepository.findAllByDepartmentName(departmentName);
    }

    public Set<Lector> findAllWhoContain(String template) {
        return lectorRepository.findAllWhoContain(template);
    }
}
