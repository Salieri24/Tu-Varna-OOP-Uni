package university.frontend;

import university.backend.entities.Grades;
import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.services.GradesService;
import university.backend.services.GroupService;
import university.backend.validators.DataValidator;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentView {
    private JTextField firstName;
    private JTextField lastName;
    private JTextField dateOfBirth;
    private JComboBox<Group> groupCombo;
    private JList<Grades> list;
    private DefaultListModel<Grades> listModel;
    private JButton saveButton;
    private JPanel root;
    private JTextField facNum;
    private JScrollPane scroll;
    private Student student;
    private JFrame frame;

    public StudentView(Student student, List<Group> groupList) {
        setComboBox(groupList);
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
        saveButton.addActionListener(e -> saveStudent());
        frame.addWindowFocusListener(new UpdateOnFocus<>(new GradesService(), listModel));
    }

    private void start() {
        frame.setContentPane(this.root);
        Dashboard.setDefaultFrameOptions(frame);
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
        listModel = new DefaultListModel<>();
        list.setModel(listModel);
    }

    public void setComboBox(List<Group> groupList) {
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
        Group selectedItem = (Group) groupCombo.getSelectedItem();
        assert selectedItem != null;
        Long id = selectedItem.getId();
        data.setGroupId(id);
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
            Group groupById = GroupService.getInstance().findById(student.getGroupId());
            if (student.getId() == null) groupById.getStudents().add(student);
            else student = groupById.findStudent(student.getId());
            getData(student);
            validateData();
            GroupService.getInstance().update(groupById);
            frame.dispose();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
