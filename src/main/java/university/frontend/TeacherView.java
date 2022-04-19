package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.Teacher;
import university.backend.services.GroupService;
import university.backend.services.TeacherService;
import university.backend.validators.DataValidator;

import javax.swing.*;
import java.util.List;

public class TeacherView {
    private JFrame frame;
    private Teacher teacher;
    private JTextField textField1;
    private JTextField textField2;
    private JList<Group> list1;
    private DefaultListModel<Group> groups;
    private JButton saveButton;
    private JPanel root;

    public TeacherView(Teacher teacher) {
        frame = new JFrame("Teacher");
        Dashboard.setDefaultFrameOptions(frame);
        frame.setSize(600,300);
        frame.setContentPane(this.root);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        List<Group> groupsByTeacher = GroupService.getInstance().findByTeacherId(teacher.getId());
        this.groups = new DefaultListModel<>();
        this.groups.addAll(groupsByTeacher);
        this.list1.setEnabled(false);
        list1.setModel(this.groups);
        this.teacher = teacher;
        setData(teacher);
        saveButton.addActionListener(e -> saveTeacher());
    }

    private void saveTeacher() {
        getData(teacher);
        TeacherService.getInstance().saveOrUpdate(teacher);
        this.frame.dispose();
    }

    public void setData(Teacher data) {
        textField1.setText(data.getFirstName());
        textField2.setText(data.getLastName());
    }

    public void getData(Teacher data) {
        try {
            DataValidator.validateTeacher(textField1.getText(),textField2.getText());
            data.setFirstName(textField1.getText());
            data.setLastName(textField2.getText());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
        }
    }
}
