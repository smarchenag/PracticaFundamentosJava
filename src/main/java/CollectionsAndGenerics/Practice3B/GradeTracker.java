package CollectionsAndGenerics.Practice3B;

import java.util.*;

public class GradeTracker {
    private LinkedHashMap<String, List<Double>> gradosEstudiante;
    public GradeTracker() {
        gradosEstudiante = new LinkedHashMap<>();
    }

    public void addGrade(String student, double grade) {
        List<Double> grades = gradosEstudiante.computeIfAbsent(student, v -> new ArrayList<>());
        grades.add(grade);
    }

    public double getAverage(String student) {
        double sum = 0.0;
        if (gradosEstudiante.containsKey(student)) {
            List<Double> grades = gradosEstudiante.get(student);
            sum = grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        } else {
            throw new IllegalArgumentException("No grades found for student " + student);
        }
        return sum;
    }

    public Map.Entry<String, Double> getBestStudent () {
        return gradosEstudiante.entrySet().stream().max(Comparator.comparing(c -> getAverage(c.getKey()))).map(e -> {
            return Map.entry(e.getKey(), getAverage(e.getKey()));
        }).orElseThrow(() -> new IllegalArgumentException("No grades found for student " + getBestStudent().getKey()));
    }

    public LinkedHashMap<String, Double> getAllAverages() {
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, List<Double>> entry : gradosEstudiante.entrySet()) {
            result.put(entry.getKey(), getAverage(entry.getKey()));
        }
        return result;
    }

    public void removeStudent(String student) {
        gradosEstudiante.remove(student);
    }

    @Override
    public String toString() {
        return "GradeTracker{" +
                "gradosEstudiante=" + gradosEstudiante +
                '}';
    }
}
