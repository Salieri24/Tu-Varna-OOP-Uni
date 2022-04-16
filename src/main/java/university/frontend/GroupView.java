package university.frontend;

import university.backend.entities.Group;
import university.backend.entities.Student;
import university.backend.services.GroupService;
import university.backend.services.StudentService;

import javax.swing.*;
import java.util.Iterator;

public class GroupView {
    private final JFrame frame;
    private final DefaultListModel<Student> students;
    private Group group;
    private JPanel root;
    private JButton saveButton;
    private JList<Student> studentsList;
    private JButton newButton;
    private JButton openButton;
//    todo: Add assignTeacher
    private JButton assignTeacherButton;
    private JTextField groupName;
    private final GroupService groupService;

    public GroupView(Group group) {
        this.group = group;
        frame = new JFrame("Group");
        start();
        this.groupName.setText(group.getName());
        students = new DefaultListModel<>();
        students.addAll(this.group.getStudents());
        studentsList.setModel(students);
        groupService = new GroupService();
        saveButton.addActionListener(e -> saveGroup());
        newButton.addActionListener(e -> newStudent());
        openButton.addActionListener(e -> openSelectedStudent());
        frame.addWindowFocusListener(new UpdateOnFocus<>(new StudentService(), students));
    }

    public void start() {
        frame.setContentPane(this.root);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    public void newStudent(){
        Student s = new Student();
        new StudentView(s,group);
    }
    public void openSelectedStudent(){
        if(!studentsList.isSelectionEmpty()){
            new StudentView(studentsList.getSelectedValue(),group);
        }
    }
    public void saveGroup(){
        group.setName(this.groupName.getText());
        Iterator<Student> studentIter = students.elements().asIterator();
        while(studentIter.hasNext())
        {
            group.getStudents().add(studentIter.next());
        }
        groupService.saveOrUpdate(group);
        frame.dispose();
    }
}
