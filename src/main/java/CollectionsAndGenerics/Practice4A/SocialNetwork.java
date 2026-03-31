package CollectionsAndGenerics.Practice4A;

import java.util.*;

public class SocialNetwork {

    private Map<String, List<String>> friends;

    public SocialNetwork() {
        this.friends = new HashMap<>();
    }

    public void addPerson (String person) {
        friends.putIfAbsent(person, new ArrayList<>());
    }

    public void addFriendship(String person1, String person2) {
        if (!friends.containsKey(person1)) {
            this.addPerson(person1);
        }
        if (!friends.containsKey(person2)) {
            this.addPerson(person2);
        }
        if (!friends.get(person1).contains(person2)) {
            friends.get(person1).add(person2);
        }
        if (!friends.get(person2).contains(person1)) {
            friends.get(person2).add(person1);
        }
    }

    public List<String> getFriends(String person) {
        return friends.get(person);
    }

    public Set<String> getFriendsOfFriends(String person) {
        Set<String> friendsList = new HashSet<>();
        for (String friend : getFriends(person)) {
            for (String fof : getFriends(friend)) {
                if (!fof.equals(person) && !getFriends(person).contains(fof)) {
                    friendsList.add(fof);
                }
            }
        }
        return friendsList;
    }

    public int countConnections() {
        int count = 0;
        for (String person : friends.keySet()) {
            count += friends.get(person).size();
        }
        return count / 2;
    }

    public String mostPopular() {
        return friends.keySet().stream().max(Comparator.comparing(k -> friends.get(k).size())).orElseThrow();
    }
}
