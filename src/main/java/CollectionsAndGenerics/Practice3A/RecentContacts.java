package CollectionsAndGenerics.Practice3A;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class RecentContacts {

    private final LinkedList<Contact> recentContacts = new LinkedList<>();
    private final int maxSize;

    public RecentContacts(int maxSize) {
        this.maxSize = maxSize;
    }

    public void addContact(Contact c) {
        if (recentContacts.contains(c)){
            recentContacts.remove(c);
            recentContacts.addFirst(c);
        }  else {
            recentContacts.addFirst(c);
        }
        if (recentContacts.size() > maxSize) {
            recentContacts.removeLast();
        }
    }

    public Contact getByPhone(String phone) {
        for (Contact c : recentContacts){
            if (c.getPhone().equals(phone)){
                return c;
            }
        }
        return null;
    }

    public void removeContact(String phone) {
        recentContacts.removeIf(c -> c.getPhone().equals(phone));
    }

    public List<Contact> getAll () {
        ListIterator<Contact> iterator = recentContacts.listIterator();
        List<Contact> contacts = new ArrayList<>();
        while (iterator.hasNext()){
            Contact c = iterator.next();
            contacts.add(c);
        }
        return contacts;
    }
}
