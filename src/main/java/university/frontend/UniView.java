package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.University;
import university.backend.services.UniversityService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UniView {

    private JPanel root;
    private JList<Group> list1;
    private JButton saveButton;
    private JTextField uniNameText;
    private JButton newGroupButton;
    private JButton openGroupButton;

    private University university;
    private UniversityService universityService = new UniversityService();

    public UniView(University selectedValue) {
        this.university = selectedValue;
        JFrame frame = new JFrame(this.university.getName());
        start(frame);
        DefaultListModel<Group> groups = new DefaultListModel<>();
        groups.addAll(this.university.getGroups());
        this.list1.setModel(groups);
        this.uniNameText.setText(university.getName());

        this.newGroupButton.addActionListener(this::newGroup);
        this.saveButton.addActionListener(e -> saveUni());
    }

    private void saveUni() {
        String name = uniNameText.getText();
        this.university.setName(name);
//        this.university.setGroups();
        universityService.saveOrUpdate(this.university);
    }

    private void newGroup(ActionEvent actionEvent) {
        Group group = new Group();

    }

    private void start(JFrame frame) {
        frame.setContentPane(this.root);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}