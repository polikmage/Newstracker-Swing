package org.mpo.newstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NewsTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsTrackerApplication.class, args);
    }


    @Bean
    public Executor asyncExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        return executor;
    }


}*/

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NewsTrackerApplication extends JFrame {

    @Bean
    public Executor asyncExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        return executor;
    }

    public NewsTrackerApplication() {

        initUI();
    }

    private void initUI() {

        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        createLayout(quitButton);

        setTitle("Quit button");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(NewsTrackerApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            NewsTrackerApplication ex = ctx.getBean(NewsTrackerApplication.class);
            ex.setVisible(true);
        });
    }
}