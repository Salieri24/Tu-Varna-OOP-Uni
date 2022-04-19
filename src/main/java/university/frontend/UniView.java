package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.entities.Teacher;
import university.backend.entities.University;
import university.backend.services.GroupService;
import university.backend.services.TeacherService;
import university.backend.services.UniversityService;
import university.backend.validators.DataValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class UniView {

    private final DefaultListModel<Group> groups = new DefaultListModel<>();
    private final JFrame frame;
    private JTabbedPane tabbedPane1;
    private JPanel root;
    private JPanel groupsTab;
    private JPanel studentsTab;
    private JTextField uniNameText;
    private JList<Group> groupJList;
    private JButton saveButton;
    private JButton newGroupButton;
    private JButton openGroupButton;

    private JList<Student> studentsList;
    private final DefaultListModel<Student> students = new DefaultListModel<>();
    private JTextField searchStudent;
    private JButton searchStudentButton;
    private JButton openStudentButton;
    private JButton newButton;

    private JList<String> subjectsList;
    private final DefaultListModel<String> subjects = new DefaultListModel<>();
    private JButton addSubjectButton;
    private JButton removeSubjectButton;
    private JTextField subjectName;
    private JList<Teacher> teachersList;
    private DefaultListModel<Teacher> teachers = new DefaultListModel<>();
    private JButton addNewTeacherButton;
    private JButton editTeacherButton;

    private final University university;
    private final GroupService groupService = new GroupService();
    private final UniversityService universityService = new UniversityService();

    public UniView(University selectedValue) {
        this.university = selectedValue;
        frame = new JFrame(this.university.getName());
        Dashboard.setDefaultFrameOptions(frame);
        frame.setContentPane(this.root);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        groups.addAll(groupService.findAllByUniversity(university.getId()));


        this.teachersList.setModel(teachers);
        this.subjectsList.setModel(subjects);
        this.groupJList.setModel(groups);
        this.studentsList.setModel(students);
        this.uniNameText.setText(university.getName());
        this.frame.addWindowFocusListener(new UpdateOnFocus<>(service -> groupService.getAllStudents(university.getId()), students));
        this.frame.addWindowFocusListener(new UpdateOnFocus<>(service -> GroupService.getInstance().findAllByUniversity(university.getId()), groups));
        this.frame.addWindowFocusListener(new UpdateOnFocus<>(service -> TeacherService.getInstance().findAll(),teachers));
        this.newGroupButton.addActionListener(e -> newGroup());
        this.openGroupButton.addActionListener(e -> openSelectedGroup());

        this.saveButton.addActionListener(e -> saveUni());

        this.openStudentButton.addActionListener(e -> openSelectedStudent());
        this.newButton.addActionListener(e -> newStudent());
        this.searchStudentButton.addActionListener(e -> searchStudents());
        this.addSubjectButton.addActionListener(e -> addSubject());
        this.removeSubjectButton.addActionListener(e -> removeSubject());
        this.addNewTeacherButton.addActionListener(e -> newTeacher());
        this.editTeacherButton.addActionListener(e -> editTeacher());
    }

    private void editTeacher() {
        if(!teachersList.isSelectionEmpty())
        {
            Teacher selectedValue = teachersList.getSelectedValue();
            new TeacherView(selectedValue);
        }
    }

    private void newTeacher() {
        new TeacherView(new Teacher());
    }

    private void searchStudents() {
        List<Student> studentsFromGroups = this.groupService.getAllStudentsBySearch(university.getId(), searchStudent.getText());
        this.students.clear();
        this.students.addAll(studentsFromGroups);
    }

    private void addSubject(){
        String text = subjectName.getText();
        if(DataValidator.validateSubject(text)){
            subjects.addElement(text);
            university.addSubject(text);
            universityService.update(university);
        }
    }

    private void removeSubject(){
        if(!subjectsList.isSelectionEmpty()){
            String remove = subjects.remove(subjectsList.getSelectedIndex());
            university.getSubjects().remove(remove);
            universityService.update(university);
        }
    }
    private void newGroup() {
        Group group = new Group();

        group.setUniversityId(university.getId());
        String defaultName = "Group Name";
        group.setName(defaultName);
//        Group save = GroupService.getInstance().persist(group);
        this.university.getGroups().add(group);
        this.openGroup(group);
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

    private void newStudent() {
        Student student = new Student();
        new StudentView(student, groupService.findAllByUniversity(university.getId()));
    }

    private void openSelectedStudent() {
        if (!studentsList.isSelectionEmpty()) {
            Student selectedValue = studentsList.getSelectedValue();
            new StudentView(selectedValue, groupService.findAllByUniversity(university.getId()));
        }
    }

    private void saveUni() {
        String name = uniNameText.getText();
        this.university.setName(name);
        Enumeration<Group> elements = groups.elements();
        ArrayList<Group> uniGroups = new ArrayList<>();
        while (elements.hasMoreElements()) {
            uniGroups.add(elements.nextElement());
        }
        university.setGroups(uniGroups);
        universityService.saveOrUpdate(this.university);
        frame.dispose();
    }
}