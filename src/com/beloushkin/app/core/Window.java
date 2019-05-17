package com.beloushkin.app.core;

import com.beloushkin.app.events.Event;
import com.beloushkin.app.events.types.MouseMotionEvent;
import com.beloushkin.app.events.types.MousePressedEvent;
import com.beloushkin.app.events.types.MouseReleasedEvent;
import com.beloushkin.app.layers.Layer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Window extends Canvas {

    private BufferStrategy bs;
    private Graphics g;
    private JFrame frame;
    //objects on canvas
    private List<Layer> layers = new ArrayList<Layer>();

    public Window(String name, int width ,int height) {
        setPreferredSize(new Dimension(width,height));
        init(name);

        // listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
                onEvent(event);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
                onEvent(event);
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                MouseMotionEvent event = new MouseMotionEvent(e.getX(), e.getY(), true);
                onEvent(event);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                MouseMotionEvent event = new MouseMotionEvent(e.getX(), e.getY(), false);
                onEvent(event);
            }
        });


        render();
    }

    private void init(String name) {
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void render() {
        if (bs == null) {
            createBufferStrategy(3);
        }

        bs = getBufferStrategy();
        g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, getWidth(), getHeight());
        //all logic is here:
        onRender(g);

        g.dispose();

        bs.show();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //after all - render again
        EventQueue.invokeLater(() -> render());
    }

    private void onRender(Graphics g) {
        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).onRender(g);
        }
    }

    private void onEvent(Event e) {
        for (int i = layers.size() - 1; i >= 0; i--) {
            layers.get(i).onEvent(e);
        }
    }


    public void addLayer(Layer layer) {
        layers.add(layer);
    }
}
