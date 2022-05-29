package code;

/** The Student class shows infos of a student.
 *
 * @author dncdd@aliyun.com
 */
public class Student extends Person {
    private String studentId;
    private double score;

    /**
     * The constructor of Student class.
     * Initialize a Student object
     * @param firstName provides First Name
     * @param lastName provides Last Name
     * @param studentId provides Id
     */
    public Student(String firstName, String lastName, String studentId, double score) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.score = score;
    }

    /**
     * This Accessor method of Student class
     * @return Returns Student id
     */
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getScore () {
        return score;
    }
    @Override
    public String toString() {
        return super.toString() + ", Student ID" + studentId;
    }

}
