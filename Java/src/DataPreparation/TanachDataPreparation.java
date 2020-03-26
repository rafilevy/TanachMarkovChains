package DataPreparation;

import Enums.TanachBook;
import org.json.JSONStringer;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TanachDataPreparation {

    public static void DownloadRawData() throws IOException {
        for (TanachBook book : TanachBook.values()) {
            Path destPath = book.getFilePath();
            URL sourceURL = book.getSefariaURL();
            try (InputStream inputStream = sourceURL.openStream()) {
                Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void CleanupTanachFiles() throws IOException {
        for (TanachBook book : TanachBook.values()) {
            Path filePath = book.getFilePath();
            List<String> lines = Files.readAllLines(filePath);
            lines = lines.subList(9 ,lines.size());
            lines = lines.stream().filter((String line) -> !(line.contains("Chapter") || line.isEmpty())).collect(Collectors.toList());
            Files.write(filePath, lines);
        }
    }

    public static List<String> getLines(TanachBook book) throws IOException {
        return Files.readAllLines(book.getFilePath());
    }

    public static List<String> getLines() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        for (TanachBook book : TanachBook.values()) lines.addAll(Files.readAllLines(book.getFilePath()));
        return lines;
    }

    public static void saveTransitionMatrix(Map<String, Map<String, Double>> transitionMatrix) throws IOException {
        String JSON = JSONStringer.valueToString(transitionMatrix);
        Path outputPath = Paths.get("output", "transitionMatrix.json");
        Files.writeString(outputPath, JSON);
    }

    public static void main(String[] args) throws IOException {
        DownloadRawData();
        CleanupTanachFiles();
    }
}
