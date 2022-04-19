import com.formdev.flatlaf.FlatDarculaLaf;
import university.backend.entities.University;
import university.backend.services.UniversityService;
import university.frontend.Dashboard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame("Dashboard");
//        UniversityService universityService = new UniversityService();
//        universityService.persist(new University("TU-Varna"));
        Dashboard dashboard = new Dashboard(frame);

    }
}