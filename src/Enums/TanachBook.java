package Enums;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum TanachBook {
    Genesis ("Genesis", BookType.Torah), Exodus ("Exodus", BookType.Torah), Leviticus ("Leviticus", BookType.Torah), Numbers ("Numbers", BookType.Torah),
    Deuteronomy ("Deuteronomy", BookType.Torah), Daniel ("Daniel", BookType.Writings), Ecclesiastes ("Ecclesiastes", BookType.Writings),
    Esther ("Esther", BookType.Writings), Ezra ("Ezra", BookType.Writings), I_Chronicles ("I%20Chronicles", BookType.Writings),
    II_Chronicles ("II%20Chronicles", BookType.Writings), Job ("Job", BookType.Writings), Lamentations ("Lamentations", BookType.Writings),
    Nehemiah ("Nehemiah", BookType.Writings), Proverbs ("Proverbs", BookType.Writings), Psalms ("Psalms", BookType.Writings),
    Ruth ("Ruth", BookType.Writings), Song_of_Songs ("Song%20of%20Songs", BookType.Writings);

    private final String str;
    private final BookType type;
    TanachBook(String str, BookType type) {
        this.str = str;
        this.type = type;
    }

    public static String[] strings() {
        String[] strings = new String[values().length];
        int i = 0;
        for (TanachBook book : values()) {
            strings[i++] = book.str;
        }
        return strings;
    }

    @Override
    public String toString() {
        return str;
    }

    public Path getFilePath() {
        return Paths.get("data/Tanach", this.toString() + ".txt");
    }

    public URL getSefariaURL() throws MalformedURLException {
        return new URL("https://raw.githubusercontent.com/Sefaria/Sefaria-Export/master/txt/Tanakh/"+this.type.toString()+"/"+this.toString()+"/Hebrew/Tanach%20with%20Text%20Only.txt");
    }
}

enum BookType {
    Torah ("Torah"), Writings ("Writings"), Prophets ("Prophets");

    private final String str;
    BookType(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}

