package ua.andrii.verkholiak;

import ua.andrii.verkholiak.entity.Degree;
import ua.andrii.verkholiak.entity.Lector;
import ua.andrii.verkholiak.service.DepartmentService;
import ua.andrii.verkholiak.service.LectorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class App {

    private static final DepartmentService DEPARTMENT_SERVICE = DepartmentService.getDepartmentService();
    private static final LectorService LECTOR_SERVICE = LectorService.getLectorService();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private static final String MENU_TEXT = "\n1.Who is head of department {department_name}\n" +
            "2.Show {department_name} statistic.\n" +
            "3. Show the average salary for department {department_name}.\n" +
            "4. Show count of employee for {department_name}.\n" +
            "5. Global search by {template}.   \n";
    private static final String ENTER_NAME = "Enter department name: ";
    private static final String DEPARTMENT_NOT_FOUND = "Can't find the %s department ";

    public static void main(String[] args) {
        System.out.println("Simple java project for university");
        int commandNumber;
        while (true) {
            showMenu();
            commandNumber = readCommand();
            if (commandNumber == 0) {
                break;
            }
            showResult(commandNumber);
        }
    }

    private static void showResult(int commandNumber) {
        if (1 == commandNumber) {
            showHeadOfDepartment();
        } else if (2 == commandNumber) {
            showDepartmentStatistic();
        } else if (3 == commandNumber) {
            showAverageSalaryInDepartment();
        } else if (4 == commandNumber) {
            showAmountOfLectorsInDepartment();
        } else if (5 == commandNumber) {
            showByGlobalSearch();
        }
    }

    private static void showHeadOfDepartment() {
        System.out.println(ENTER_NAME);
        String departmentName = readInput();
        if (!isDepartmentExist(departmentName)) {
            System.out.println(String.format(DEPARTMENT_NOT_FOUND, departmentName));
            return;
        }
        Lector headOfDepartment = DEPARTMENT_SERVICE.getHeadOfDepartmentByDepartmentName(departmentName);
        if (headOfDepartment == null || headOfDepartment.getId() == null) {
            System.out.println(String.format("Can't find head of the %s department ", departmentName));
        } else {
            System.out.println(String.format("Head of %s department is %s %s",
                    departmentName, headOfDepartment.getFirstName(), headOfDepartment.getLastName()));
        }
    }

    private static void showDepartmentStatistic() {
        System.out.println(ENTER_NAME);
        String departmentName = readInput();
        if (!isDepartmentExist(departmentName)) {
            System.out.println(String.format(DEPARTMENT_NOT_FOUND, departmentName));
            return;
        }
        Set<Lector> lectors = LECTOR_SERVICE.getAllLectorsByDepartmentName(departmentName);
        System.out.println("assistants - " + countLectorsWithDegree(lectors, Degree.ASSISTANT));
        System.out.println("associate professors - " + countLectorsWithDegree(lectors, Degree.ASSOCIATE_PROFESSOR));
        System.out.println("professors - " + countLectorsWithDegree(lectors, Degree.PROFESSOR));
    }

    private static void showAverageSalaryInDepartment() {
        System.out.println(ENTER_NAME);
        String departmentName = readInput();
        if (!isDepartmentExist(departmentName)) {
            System.out.println(String.format(DEPARTMENT_NOT_FOUND, departmentName));
            return;
        }
        double averageSalary = DEPARTMENT_SERVICE.getAverageSalaryByDepartmentName(departmentName);
        System.out.println("The average salary of " + departmentName + " is " + averageSalary);
    }

    private static void showAmountOfLectorsInDepartment() {
        System.out.println(ENTER_NAME);
        String departmentName = readInput();
        if (!isDepartmentExist(departmentName)) {
            System.out.println(String.format(DEPARTMENT_NOT_FOUND, departmentName));
            return;
        }
        System.out.println(DEPARTMENT_SERVICE.countLectorsInDepartment(departmentName));
    }

    private static void showByGlobalSearch() {
        System.out.println("Enter template: ");
        Set<Lector> lectors = LECTOR_SERVICE.findAllWhoContain(readInput());
        if (lectors.isEmpty()) {
            System.out.println("Can't find anything");
            return;
        }
        lectors.forEach(l -> System.out.print(l.getFirstName() + " " + l.getLastName() + ", "));
    }

    private static boolean isDepartmentExist(String departmentName) {
        return DEPARTMENT_SERVICE.findDepartmentByName(departmentName) != null;
    }

    private static long countLectorsWithDegree(Set<Lector> lectors, Degree degree) {
        return lectors.stream().filter(l -> l.getDegree().equals(degree)).count();
    }

    private static String readInput() {
        while (true) {
            try {
                return BUFFERED_READER.readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int readCommand() {
        while (true) {
            System.out.println("Enter digit from 1 to 5 or 0 to exit");
            try {
                return Integer.parseInt(BUFFERED_READER.readLine().trim());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Sorry, wrong input !!!");
            }
        }
    }

    private static void showMenu() {
        System.out.println(MENU_TEXT);
    }
}
