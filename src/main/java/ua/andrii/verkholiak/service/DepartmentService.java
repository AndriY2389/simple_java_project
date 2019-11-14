package ua.andrii.verkholiak.service;

import ua.andrii.verkholiak.entity.Department;
import ua.andrii.verkholiak.entity.Lector;
import ua.andrii.verkholiak.repository.DepartmentRepository;

public class DepartmentService {

    private static DepartmentService INSTANCE;
    private DepartmentRepository departmentRepository;

    private DepartmentService() {
        departmentRepository = DepartmentRepository.getDepartmentRepository();
    }

    public static DepartmentService getDepartmentService() {
        if (INSTANCE == null) {
            INSTANCE = new DepartmentService();
        }

        return INSTANCE;
    }

    public Department findDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    public Lector getHeadOfDepartmentByDepartmentName(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department == null) {
            return null;
        }

        return department.getHeadOfDepartment();
    }

    public double getAverageSalaryByDepartmentName(String departmentName) {
        return departmentRepository.getAverageSalaryForDepartment(departmentName);
    }

    public long countLectorsInDepartment(String departmentName) {
        return departmentRepository.countLectorsInDepartment(departmentName);
    }
}
