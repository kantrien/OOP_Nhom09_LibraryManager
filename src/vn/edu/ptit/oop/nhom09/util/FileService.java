import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

package vn.edu.ptit.oop.nhom09.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileService {

    private FileService() {
    }

    public static List<String[]> readCSV(String filePath)
            throws IOException {

        Path path = Paths.get(filePath);
        List<String[]> rows = new ArrayList<>();

        if (!Files.exists(path)) {
            return rows;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                rows.add(parseCSVLine(line));
            }
        }

        return rows;
    }

    public static void writeCSV(
            String filePath, List<String[]> rows)
            throws IOException {

        Path path = Paths.get(filePath);
        Path parent = path.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path, StandardCharsets.UTF_8)) {

            for (String[] row : rows) {
                writer.write(toCSVLine(row));
                writer.newLine();
            }
        }
    }

    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    value.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(value.toString());
                value.setLength(0);
            } else {
                value.append(c);
            }
        }

        values.add(value.toString());
        return values.toArray(new String[0]);
    }

    private static String toCSVLine(String[] row) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < row.length; i++) {
            if (i > 0) {
                result.append(',');
            }

            String value = row[i] == null ? "" : row[i];

            if (value.contains(",") || value.contains("\"")
                    || value.contains("\n") || value.contains("\r")) {
                result.append('"');
                result.append(value.replace("\"", "\"\""));
                result.append('"');
            } else {
                result.append(value);
            }
        }

        return result.toString();
    }
}

public static void saveObject(
        String filePath, Object object) throws IOException {

    Path path = Paths.get(filePath);
    Path parent = path.toAbsolutePath().getParent();

    if (parent != null) {
        Files.createDirectories(parent);
    }

    try (ObjectOutputStream out = new ObjectOutputStream(
            Files.newOutputStream(path))) {
        out.writeObject(object);
    }
}

public static Object loadObject(
        String filePath)
        throws IOException, ClassNotFoundException {

    Path path = Paths.get(filePath);

    try (ObjectInputStream in = new ObjectInputStream(
            Files.newInputStream(path))) {
        return in.readObject();
    }
}

