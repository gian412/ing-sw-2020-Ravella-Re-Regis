package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayeredCommandPane extends JFrame {

    public LayeredCommandPane() {
        super("Swingo le parole");
        setSize(500, 500);
        JLayeredPane pane = getLayeredPane();

        // Creating buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 20, 190, 230);

        // Creating movePanel
        JPanel movePanel = new JPanel();
        JLabel moveLabel = new JLabel("movePanel"); // Create label
        moveLabel.setBounds(20, 20, 150, 50); // Set label's bounds
        movePanel.add(moveLabel); // Add label to panel
        JButton exitMove = new JButton("Exit"); // Create button
        exitMove.setBounds(90, 20, 100, 50); // Set button's bounds
        exitMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                movePanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        }); // Set button's action listener
        movePanel.add(exitMove); // Add button to panel
        movePanel.setVisible(false); // Set panel's visibility to false
        movePanel.setBounds(20, 20, 300, 300);
        movePanel.setBackground(Color.green);

        // Creating buildPanel
        JPanel buildPanel = new JPanel();
        JLabel buildLabel = new JLabel("buildPanel"); // Create label
        buildLabel.setBounds(20, 20, 150, 50); // Set label's bounds
        buildPanel.add(buildLabel); // Add label to panel
        JButton exitBuild = new JButton("Exit"); // Create button
        exitBuild.setBounds(90, 20, 100, 50); // Set button's bounds
        exitBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buildPanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        }); // Set button's action listener
        buildPanel.add(exitBuild); // Add button to panel
        buildPanel.setVisible(false); // Set panel's visibility to false
        buildPanel.setBounds(20, 20, 300, 300);
        buildPanel.setBackground(Color.yellow);

        // Creating forcePanel
        JPanel forcePanel = new JPanel();
        JLabel forceLabel = new JLabel("forcePanel"); // Create label
        forceLabel.setBounds(20, 20, 150, 50); // Set label's bounds
        forcePanel.add(forceLabel); // Add label to panel
        JButton exitForce = new JButton("Exit"); // Create button
        exitForce.setBounds(90, 20, 100, 50); // Set button's bounds
        exitForce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                forcePanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        }); // Set button's action listener
        forcePanel.add(exitForce); // Add button to panel
        forcePanel.setVisible(false); // Set panel's visibility to false
        forcePanel.setBounds(20, 20, 300, 300);
        forcePanel.setBackground(Color.red);

        JButton move = new JButton("Move");
        move.setBounds(20, 20, 150, 50);
        JButton build = new JButton("Build");
        build.setBounds(20, 90, 150, 50);
        JButton force = new JButton("Force");
        force.setBounds(20, 160, 150, 50);

        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                movePanel.setVisible(true);
                buttonPanel.setVisible(false);
            }
        });
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buildPanel.setVisible(true);
                buttonPanel.setVisible(false);
            }
        });
        force.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                forcePanel.setVisible(true);
                buttonPanel.setVisible(false);
            }
        });

        buttonPanel.add(move);
        buttonPanel.add(force);
        buttonPanel.add(build);

        buttonPanel.setBounds(20, 20, 300, 300);
        buttonPanel.setLayout(null);
        buttonPanel.setVisible(true);

        pane.add(buttonPanel, new Integer(1));
        pane.add(movePanel, new Integer(2));
        pane.add(buildPanel, new Integer(3));
        pane.add(forcePanel, new Integer(4));
    }

}
