package Model;

import java.util.Comparator;

public class Item implements Comparable<Item> {
    private int id;
    private int listId;
    private String name;

    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public int getListId() {
        return listId;
    }
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Item o) {
        if (this.listId != o.listId) {
            return Integer.compare(this.listId, o.listId);
        } else {
            int numThis;
            if (this.name == null || this.name.isEmpty() || this.name.replaceAll("\\D+", "").isEmpty()) {
                numThis = Integer.MAX_VALUE;
            } else {
                numThis = Integer.parseInt(this.name.replaceAll("\\D+", ""));
            }

            int numOther;
            if (o.name == null || o.name.isEmpty() || o.name.replaceAll("\\D+", "").isEmpty()) {
                numOther = Integer.MAX_VALUE;
            } else {
                numOther = Integer.parseInt(o.name.replaceAll("\\D+", ""));
            }

            return Integer.compare(numThis, numOther);
        }
    }
}
