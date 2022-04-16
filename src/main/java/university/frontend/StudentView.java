package university.frontend;

import university.backend.entities.Grades;
import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.services.GradesService;
import university.backend.services.StudentService;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentView {
    private JTextField firstName;
    private JTextField lastName;
    private JTextField dateOfBirth;
    private JComboBox<Group> comboBox1;
    private JList<Grades> list;
    private DefaultListModel<Grades> listModel;
    private JButton saveButton;
    private JPanel root;
    private Student student;
    private JFrame frame;

    public StudentView(Student student,List<Group> groupList) {
        setComboBox(groupList);
        construct(student);
    }

    public StudentView(Student student,Group group) {
        comboBox1.addItem(group);
        comboBox1.setEnabled(false);
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
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setData(Student data) {
        firstName.setText(data.getFirstName());
        lastName.setText(data.getLastName());
        String s = "";
        if(data.getDateOfBirth() != null) s = data.getDateOfBirth().toString();
        dateOfBirth.setText(s);
        listModel = new DefaultListModel<>();
        list.setModel(listModel);
    }

    public void setComboBox(List<Group> groupList){
        ComboBoxModel<Group> groupModel = new DefaultComboBoxModel<>();
        this.comboBox1.setModel(groupModel);
        for (Group group : groupList) {
            this.comboBox1.addItem(group);
        }
    }

    public void getData(Student data) {
            data.setFirstName(firstName.getText());
            data.setLastName(lastName.getText());
            data.setDateOfBirth(LocalDate.parse(dateOfBirth.getText(), DateTimeFormatter.ISO_LOCAL_DATE));
    }

    private void saveStudent() {
        try {
        getData(student);
        StudentService studentService = new StudentService();
        studentService.saveOrUpdate(student);
        frame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
