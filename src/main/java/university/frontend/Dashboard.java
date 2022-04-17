package university.frontend;

import university.backend.entities.University;
import university.backend.services.UniversityService;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;
import java.util.List;

public class Dashboard {
    private JPanel mainPanel;

    private JList<University> uniList;
    private JScrollPane uniScroll;
    private JButton openSelectedUni;
    private JButton createNew;
    private JPanel buttonPanel;
    private JTextField searchTextField;
    private JButton searchButton;

    private final UniversityService universityService = new UniversityService();
    private final DefaultListModel<University> uniListModel;

    public Dashboard(JFrame jframe) {
        Dashboard.setDefaultFrameOptions(jframe);
        jframe.setContentPane(mainPanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        uniListModel = new DefaultListModel<>();
        uniListModel.addAll(universityService.findAll());
        uniList.setModel(uniListModel);
        jframe.addWindowFocusListener(new UpdateOnFocus<>(universityService, uniListModel));

        openSelectedUni.addActionListener(e -> openSelectedUni(this));
        createNew.addActionListener(e -> newUni());
        searchTextField.addActionListener(e -> search());
        searchButton.addActionListener(e -> search());
    }

    private void search() {
        String searchText = searchTextField.getText();
        List<University> all;
        all = !searchText.isBlank() && !searchText.isEmpty() ? universityService.findAllByName(searchText) : universityService.findAll();
        uniListModel.clear();
        uniListModel.addAll(all);
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

    public static void setDefaultFrameOptions(JFrame jframe){
        jframe.setSize(600, 400);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}