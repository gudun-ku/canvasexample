package com.beloushkin.app.sandbox;

import com.beloushkin.app.events.Event;
import com.beloushkin.app.layers.Layer;

public class ExampleLayer extends Layer {

    @Override
    public void onEvent(Event e) {
        System.out.println(e.hashCode());
    }
}
