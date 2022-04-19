package university.frontend;

import university.backend.entities.Grades;
import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.services.GradesService;
import university.backend.services.GroupService;
import university.backend.services.StudentService;
import university.backend.services.UniversityService;
import university.backend.validators.DataValidator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentView {
    private JTextField firstName;
    private JTextField lastName;
    private JTextField dateOfBirth;
    private JComboBox<Group> groupCombo;
    private JList<Grades> gradesList;
    private DefaultListModel<Grades> gradesListModel;
    private JButton saveButton;
    private JPanel root;
    private JTextField facNum;
    private JScrollPane scroll;
    private JComboBox<String> specialityComboBox;
    private JTextField pointsField;
    private JButton addGradesButton;
    private JButton removeGradesButton;
    private JButton editGradesButton;
    private Student student;
    private JFrame frame;

    public StudentView(Student student, List<Group> groupList) {
        setGroupComboBox(groupList);
        construct(student);
    }

    public StudentView(Student student, Group group) {
        groupCombo.addItem(group);
        groupCombo.setEnabled(false);
        construct(student);
    }

    private void construct(Student student) {
        this.student = student;
        frame = new JFrame("Student");
        start();
        setData(student);
        gradesList.addListSelectionListener(this::setGrade);
        saveButton.addActionListener(e -> saveStudent());
        addGradesButton.addActionListener(e -> addGrade(getGrade()));
        editGradesButton.addActionListener(e -> editGrade(getGrade()));
        removeGradesButton.addActionListener(e -> removeGrade());
        if (student.getId() != null)
            frame.addWindowFocusListener(new UpdateOnFocus<>((service) -> GradesService.getInstance().findAllForStudent(student.getId()), gradesListModel));
    }

    private void removeGrade() {
        Grades selectedValue = gradesList.getSelectedValue();
        GradesService.getInstance().delete(selectedValue.getId());
        gradesListModel.remove(gradesList.getSelectedIndex());

    }

    private void editGrade(Grades grade) {
        try {
            if (!gradesList.isSelectionEmpty() && grade.getPoints() != null && !grade.getSubjectName().isEmpty()) {
                Grades selectedItem = gradesList.getSelectedValue();
                gradesList.clearSelection();
                selectedItem.setPoints(grade.getPoints());
                selectedItem.setSubjectName(grade.getSubjectName());
                GradesService.getInstance().update(selectedItem);
                gradesListModel.removeElement(selectedItem);
                gradesListModel.addElement(selectedItem);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            e.printStackTrace();
        }
    }

    private void addGrade(Grades grade) {
        try {
            gradesListModel.addElement(grade);
            student.getGradesList().add(grade);
            StudentService.getInstance().saveOrUpdate(student);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            e.printStackTrace();
        }
    }


    private Grades getGrade() {
        DataValidator.validatePoints(pointsField.getText());
        int points = Integer.parseInt(pointsField.getText());
        Grades grades = new Grades(specialityComboBox.getItemAt(specialityComboBox.getSelectedIndex()),
                points);
        pointsField.setText("");
        return grades;
    }

    private void setGrade(ListSelectionEvent e) {
        if (!gradesList.isSelectionEmpty()) {
            Grades selectedValue = gradesList.getSelectedValue();
            specialityComboBox.setSelectedItem(selectedValue.getSubjectName());
            pointsField.setText(selectedValue.getPoints().toString());
        }
    }

    private void start() {
        frame.setContentPane(this.root);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setData(Student data) {
        firstName.setText(data.getFirstName());
        lastName.setText(data.getLastName());
        facNum.setText(data.getFacultyNum());
        String s = "";
        if (data.getDateOfBirth() != null) s = data.getDateOfBirth().toString();
        dateOfBirth.setText(s);
        ComboBoxModel<String> specialitiesList = new DefaultComboBoxModel<>(UniversityService.getInstance().getSpecialities(getGroupId()));
        specialityComboBox.setModel(specialitiesList);

        gradesListModel = new DefaultListModel<>();
        gradesList.setModel(gradesListModel);
    }


    public void setGroupComboBox(List<Group> groupList) {
        ComboBoxModel<Group> groupModel = new DefaultComboBoxModel<>();
        this.groupCombo.setModel(groupModel);
        for (Group group : groupList) {
            this.groupCombo.addItem(group);
        }
    }

    public void getData(Student data) {
        data.setFirstName(firstName.getText());
        data.setLastName(lastName.getText());
        data.setDateOfBirth(LocalDate.parse(dateOfBirth.getText(), DateTimeFormatter.ISO_LOCAL_DATE));
        data.setFacultyNum(facNum.getText());
        Long id = getGroupId();
        data.setGroupId(id);
    }

    private Long getGroupId() {
        Group selectedItem = (Group) groupCombo.getSelectedItem();
        assert selectedItem != null;
        return selectedItem.getId();
    }

    public void validateData() {
        Student s = new Student(
                firstName.getText(),
                lastName.getText(),
                LocalDate.parse(dateOfBirth.getText(), DateTimeFormatter.ISO_LOCAL_DATE),
                facNum.getText());
        DataValidator.validateStudent(s);
    }

    private void saveStudent() {
        try {
            Group groupById = GroupService.getInstance().findById(getGroupId());
            if (student.getId() == null) groupById.getStudents().add(student);
            else student = groupById.findStudent(student.getId());
            getData(student);
            validateData();
            GroupService.getInstance().update(groupById);
            frame.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            e.printStackTrace();
        }
    }
}
