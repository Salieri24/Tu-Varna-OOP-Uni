package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.entities.University;
import university.backend.services.GroupService;
import university.backend.services.StudentService;
import university.backend.services.UniversityService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

public class UniView {

    private final DefaultListModel<Group> groups;
    private final JFrame frame;
    private final DefaultListModel<Student> students;
    private JPanel root;
    private JList<Group> groupJList;
    private JButton saveButton;
    private JTextField uniNameText;
    private JButton newGroupButton;
    private JButton openGroupButton;
    private JTabbedPane tabbedPane1;
    private JPanel groupsTab;
    private JPanel studentsTab;
    private JList<Student> studentsList;
    private JTextField searchStudent;
    private JButton openStudentButton;
    private JButton newButton;
    private JButton searchStudentButton;

    private final University university;
    private final UniversityService universityService = new UniversityService();

    public UniView(University selectedValue) {
        this.university = selectedValue;
        frame = new JFrame(this.university.getName());
        frame.setContentPane(this.root);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        groups = new DefaultListModel<>();
        students = new DefaultListModel<>();
        groups.addAll(this.university.getGroups());

        this.groupJList.setModel(groups);
        this.uniNameText.setText(university.getName());
        this.frame.addWindowFocusListener(new UpdateOnFocus<>(new GroupService(), groups));
        this.frame.addWindowFocusListener(new UpdateOnFocus<>(new StudentService(), students));
        this.newGroupButton.addActionListener(e -> newGroup());
        this.openGroupButton.addActionListener(e -> openSelectedGroup());

        this.openStudentButton.addActionListener(e -> openSelectedStudent());
        this.saveButton.addActionListener(e -> saveUni());

        newButton.addActionListener(e -> searchStudents());
        searchStudentButton.addActionListener(e -> searchStudents());
    }

    private static void searchStudents() {
//                todo: new User with a choice of groups
//        List<Student> studentsFromGroups = this.university.getGroups().stream().flatMap(group -> group.getStudents().stream()).collect(Collectors.toList());
//        this.students.addAll(studentsFromGroups);
    }

    private void saveUni() {
        String name = uniNameText.getText();
        this.university.setName(name);
        Enumeration<Group> elements = groups.elements();
        while (elements.hasMoreElements()) {
            this.university.getGroups().add(elements.nextElement());
        }
        universityService.saveOrUpdate(this.university);
        frame.dispose();
    }

    private void newGroup() {
        this.openGroup(new Group());
    }

    private void openGroup(Group group) {
        GroupView groupView = new GroupView(group);
    }

    private void openSelectedGroup() {
        if (!groupJList.isSelectionEmpty()) {
            Group selectedValue = groupJList.getSelectedValue();
            openGroup(selectedValue);
        }
    }

    private void openSelectedStudent() {
        if (studentsList.isSelectionEmpty()) {
            Student selectedValue = studentsList.getSelectedValue();
            new StudentView(selectedValue, university.getGroups());
        }
    }
}