package university.frontend;

import university.backend.entities.University;
import university.backend.services.UniversityService;

import javax.swing.*;

public class Dashboard {
    private JPanel MainPanel;
    private JList<University> uniList;
    private JScrollPane uniScroll;
    private JButton openSelectedUni;
    private JButton createNew;
    private UniversityService universityService = new UniversityService();

    public Dashboard(JFrame jframe) {

        DefaultListModel<University> uniListModel = new DefaultListModel<>();
        uniListModel.addAll(universityService.findAll());
        uniList.setModel(uniListModel);
        jframe.addWindowFocusListener(new UpdateOnFocus<>(universityService, uniListModel));
        openSelectedUni.addActionListener(e -> openSelectedUni(this));
        createNew.addActionListener(e -> newUni());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dashboard");

        Dashboard dashboard = new Dashboard(frame);

        dashboard.universityService.persist(new University("TU-Varna"));

        frame.setSize(400, 200);
        frame.setContentPane(dashboard.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void newUni() {
        openUniversity(new University());
    }

    private static void openUniversity(University selectedValue) {
        UniView universityView = new UniView(selectedValue);
    }

    private static void openSelectedUni(Dashboard dashboard) {
        if (!dashboard.uniList.isSelectionEmpty()) {
            University selectedValue = dashboard.uniList.getSelectedValue();
            openUniversity(selectedValue);
        }
    }

}