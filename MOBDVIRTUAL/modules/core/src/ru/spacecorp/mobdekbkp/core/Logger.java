package ru.spacecorp.mobdekbkp.core;

import java.util.ArrayList;

/**
 * Created by Stepanov_ME on 26.08.2019.
 */
public class Logger {

    private ArrayList<String> strings;

    public Logger() {
        strings = new ArrayList<>();
    }

    public void addLog(String log) {
        strings.add(log);
    }

    public void addFirst(String log) {
        strings.add(0, log);
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();
        strings.forEach(s -> {
            report.append(s).append("\n");
        });
        return report.toString();
    }
}
