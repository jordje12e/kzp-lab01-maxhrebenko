package ua.lpnu.kzp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Читає версію продукту й номер збірки, підставлені Maven під час фільтрування ресурсів. */
public final class VersionInfo {

    private VersionInfo() {
    }

    /**
     * Повертає рядок вигляду {@code <версія>+<номер збірки>},
     * наприклад {@code 1.0.0+local} або {@code 1.0.0+42}.
     *
     * @return поточна версія разом із номером збірки
     */
    public static String current() {
        Properties properties = new Properties();
        try (InputStream stream = VersionInfo.class.getResourceAsStream("/version.properties")) {
            if (stream == null) {
                return "unknown+unknown";
            }
            properties.load(stream);
        } catch (IOException exception) {
            return "unknown+unknown";
        }
        return "%s+%s".formatted(
                properties.getProperty("version", "unknown"),
                properties.getProperty("build", "unknown"));
    }
}
