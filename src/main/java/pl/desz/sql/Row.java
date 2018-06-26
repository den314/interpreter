package pl.desz.sql;

class Row {

    private String name;
    private String surname;

    public Row(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}