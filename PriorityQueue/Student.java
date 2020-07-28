package PriorityQueue;
@SuppressWarnings("unchecked")
public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = new Integer(parseInt);
    }

    @Override
    public int compareTo(Student student) {
        return this.marks.compareTo(student.getMarks());
    }

    public String getName() {
        return name;
    }

    public Integer getMarks() {
        return marks;
    }

    public Student(Student s) {
        this.name = s.getName();
        this.marks = s.getMarks();
    }

    @Override
    public String toString() {
        return "Student{name='" + this.name + "', marks=" + this.marks.toString() + "}";
    }
}
