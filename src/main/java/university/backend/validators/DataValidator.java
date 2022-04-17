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
}
