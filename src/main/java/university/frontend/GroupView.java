package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.entities.Teacher;
import university.backend.services.GroupService;
import university.backend.services.StudentService;
import university.backend.services.TeacherService;

import javax.swing.*;

public class GroupView {
    private final JFrame frame;
    private final Group group;
    private JPanel root;
    private JButton saveButton;
    private JList<Student> studentsList;
    private JButton newButton;
    private JButton openButton;
//    todo: Add assignTeacher
    private JButton assignTeacherButton;
    private JTextField groupName;
    private JComboBox<Teacher> teachersComboBox;
    private final GroupService groupService;

    public GroupView(Group group) {
        this.group = group;
        frame = new JFrame("Group");
        start();
        this.groupName.setText(group.getName());
        DefaultListModel<Student> students = new DefaultListModel<>();
        students.addAll(this.group.getStudents());
        studentsList.setModel(students);
        groupService = new GroupService();
        saveButton.addActionListener(e -> saveGroup());
        newButton.addActionListener(e -> newStudent());
        openButton.addActionListener(e -> openSelectedStudent());
        assignTeacherButton.addActionListener(e -> assignTeacher());
        DefaultComboBoxModel<Teacher> comboData = new DefaultComboBoxModel<>();
        comboData.addAll(TeacherService.getInstance().findAll());
        teachersComboBox.setModel(comboData);
        frame.addWindowFocusListener(new UpdateOnFocus<>(service -> new StudentService().findAllInGroup(group.getId()), students));
    }

    public void start() {
        frame.setContentPane(this.root);
        Dashboard.setDefaultFrameOptions(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void newStudent(){
        Student s = new Student();
//        this.group.getStudents().add(s);
//        StudentService studentService = new StudentService();
//        studentService.persist(s);
        new StudentView(s,group);
    }
    private void assignTeacher() {
        Teacher teacher = teachersComboBox.getItemAt(teachersComboBox.getSelectedIndex());
        teacher.getGroupList().add(this.group);
        TeacherService.getInstance().update(teacher);
    }
    public void openSelectedStudent(){
        if(!studentsList.isSelectionEmpty()){
            new StudentView(studentsList.getSelectedValue(),group);
        }
    }
    public void saveGroup(){
        group.setName(this.groupName.getText());
//        Iterator<Student> studentIter = students.elements().asIterator();
//        while(studentIter.hasNext())
//        {
//            group.getStudents().add(studentIter.next());
//        }
        groupService.saveOrUpdate(group);
        frame.dispose();
    }
}
