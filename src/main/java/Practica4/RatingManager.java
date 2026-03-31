package Practica4;

import java.util.ArrayList;
import java.util.List;

public class RatingManager {
    private final List<Integer> ratings = new ArrayList<>();

    public void addRating(int stars) { ratings.add(stars); }
    public double getAverage() {
        return ratings.isEmpty() ? 0.0 :
                ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public List<Integer> getRatings() {
        return ratings;
    }
}
