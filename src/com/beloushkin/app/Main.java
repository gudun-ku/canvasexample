package com.beloushkin.app;

import com.beloushkin.app.core.Window;
import com.beloushkin.app.sandbox.ExampleLayer;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Test window", 960, 640);
        window.addLayer(new ExampleLayer("First example", Color.RED));
        window.addLayer(new ExampleLayer("Second example", Color.BLUE));
    }
}
