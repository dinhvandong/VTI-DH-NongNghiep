import model.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        String pattern = "dd-MM-YYYY";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        // Tai sao List = new ArrayList ma khong phai List = new List
        for(int i = 0;i<3;i++)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhap ho ten:");
            String name = scanner.nextLine();
            System.out.println("Nhap diem so:");
            float score = scanner.nextFloat();
            // Xoa ky tu Enter con lai sau nextFloat()
            scanner.nextLine();
            System.out.println("Nhap ngay thang, nam sinh dd-MM-YYYY:");
            Date dateOfBirth = null;
            try {
                String dateOfBirthStr = scanner.nextLine();
                dateOfBirth = dateFormat.parse(dateOfBirthStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Student studentNew = new Student(name, score, dateOfBirth);
            studentList.add(studentNew);
        }
        for(Student s: studentList){
            System.out.println(s.toString());
        }
        // sap xep theo diem giam dan
        studentList.sort(Comparator.comparing(Student::getScore).reversed());
        System.out.println("Sap xep diem giam dan");
        for(Student s: studentList){
            System.out.println(s.toString());
        }
        // sap xep theo diem giam dan
        studentList.sort(Comparator.comparing(Student::getDateOfBirth).reversed());
        System.out.println("Sap xep tuoi tang dan");
        for(Student s: studentList){
            System.out.println(s.toString());
        }
    }
}