package university.backend.validators;

import university.backend.entities.Student;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class DataValidator {
    private static final Pattern nameRegex = Pattern.compile("[a-zA-Z-]+");
    private static final Pattern justNumbers = Pattern.compile("[0-9]+");
    public static void validateStudent(Student student){
        String firstName = student.getFirstName();
        if(!firstName.matches(nameRegex.pattern())) throw new RuntimeException("Invalid first name");
        String lastName = student.getLastName();
        if(!lastName.matches(nameRegex.pattern())) throw new RuntimeException("Invalid last name");
        String facultyNum = student.getFacultyNum();
        if(!facultyNum.matches(justNumbers.pattern())) throw new RuntimeException("Invalid faculty number. Must contain only letters");
        LocalDate dateOfBirth = student.getDateOfBirth();
        if(dateOfBirth.getYear()<1900 && dateOfBirth.isAfter(LocalDate.now())) throw new RuntimeException("Invalid birth date");
    }

    public static boolean validateSubject(String text) {
        return true;
    }
    public static void validatePoints(String text){
        if(!text.matches(justNumbers.pattern())) throw new RuntimeException("Invalid first name");
        int i = Integer.parseInt(text);
        if(i>100 || i<0) throw new RuntimeException("Invalid points. They should be between 0 and 100");
    }

    public static void validateTeacher(String text, String text1) {
        if(!text.matches(nameRegex.pattern())) throw new RuntimeException("Invalid first name");
        if(!text1.matches(nameRegex.pattern())) throw new RuntimeException("Invalid last name");
    }
}
