package code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FunctionalCode {

    public static void main (String[] args) {
        ArrayList<Student> arrayList = new ArrayList<Student>();
        arrayList.add(new Student("a", "A", "1", 1.1));
        arrayList.add(new Student("b", "B", "2", 8.2));
        arrayList.add(new Student("c", "C", "3", 3.3));
        System.out.println(arrayList.stream()
                .filter(Objects::nonNull)
                .map(Student::getScore)
                .max(Double::compareTo)
                .orElse(-1.0));
        System.out.println("-----------------");
        arrayList.stream().
                filter(Objects::nonNull).
                map(Student::getScore).
                forEach(System.out::println);
        System.out.println("-----------------");
        Object instance = new FunctionalCode().getTopScore(arrayList);
        System.out.println(instance);
        System.out.println("-----------------");
        Object instanceFC = new FunctionalCode().getTopsScoreFC(arrayList);
        System.out.println(instanceFC);
    }


    public Double getTopsScoreFC (List<Student> students) {
        return students.stream()
                .filter(Objects::nonNull)
                .map(Student::getScore)
                .max(Double::compareTo)
                .orElse(-1.0);


    }

    public Double getTopScore(List<Student> students) {
        Double topScore = 0.0;
        for (Student s : students) {
            if (s == null) continue;
            topScore = Math.max(topScore, s.getScore());
        }
        return topScore;
    }



}
