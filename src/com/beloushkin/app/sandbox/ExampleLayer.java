package com.beloushkin.app.sandbox;

import com.beloushkin.app.events.Dispatcher;
import com.beloushkin.app.events.Event;
import com.beloushkin.app.events.types.MouseMotionEvent;
import com.beloushkin.app.events.types.MousePressedEvent;
import com.beloushkin.app.events.types.MouseReleasedEvent;
import com.beloushkin.app.layers.Layer;

import java.awt.*;
import java.util.Random;

public class ExampleLayer extends Layer {

    private String name;
    private Color color;
    private Rectangle box;
    private boolean dragging = false;
    private int px, py; //previous values

    private static final Random random = new Random();

    public ExampleLayer(String name, Color color) {
        this.name = name;
        this.color = color;

        box = new Rectangle(random.nextInt(100) + 150, random.nextInt(100) + 250, 120, 240);
    }

    public void onRender(Graphics g) {
        g.setColor(color);
        g.fillRect(box.x, box.y, box.width, box.height);

        g.setColor(Color.WHITE);
        g.drawString(name,box.x + 15, box.y + 45);
    }


    @Override
    public void onEvent(Event event) {
        //System.out.println(e.hashCode());
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onPressed((MousePressedEvent) e ));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onReleased((MouseReleasedEvent) e ));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMoved((MouseMotionEvent) e ));
    }


    private boolean onPressed(MousePressedEvent e) {

        if (box.contains(new Point(e.getX(),e.getY())))
            dragging = true;

        return dragging;
    }

    private boolean onReleased(MouseReleasedEvent e) {
        dragging = false;
        return false;
    }

    private boolean onMouseMoved(MouseMotionEvent e) {
        if (dragging) {
            box.x += e.getX() - px;
            box.y += e.getY() - py;
        }
        px = e.getX();
        py = e.getY();
        return dragging;
    }
}
