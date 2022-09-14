package testes;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author gelvazio
 */
//public class MainFrame extends javax.swing.JFrame {
//
//    private JLabel countLabel1 = new JLabel("0");
//    private JLabel statusLabel = new JLabel("Task not completed.");
//    private JButton startButton = new JButton("Start");
//
//    public MainFrame(String title) {
//        super(title);
//
//        setLayout(new GridBagLayout());
//
//        countLabel1.setFont(new Font("serif", Font.BOLD, 28));
//
//        GridBagConstraints gc = new GridBagConstraints();
//
//        gc.fill = GridBagConstraints.NONE;
//
//        gc.gridx = 0;
//        gc.gridy = 0;
//        gc.weightx = 1;
//        gc.weighty = 1;
//        add(countLabel1, gc);
//
//        gc.gridx = 0;
//        gc.gridy = 1;
//        gc.weightx = 1;
//        gc.weighty = 1;
//        add(statusLabel, gc);
//
//        gc.gridx = 0;
//        gc.gridy = 2;
//        gc.weightx = 1;
//        gc.weighty = 1;
//        add(startButton, gc);
//
//        startButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                start();
//            }
//        });
//
//        setSize(200, 400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//    }
//
//    private void start() {
//        SwingWorker worker = new SwingWorker() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                // Simulate doing something useful.
//                for (int i = 0; i <= 10; i++) {
//                    Thread.sleep(1000);
//                    System.out.println("Running " + i);
//                    countLabel1.setText(Integer.toString(i));
//                }
//
//                return null;
//            }
//        };
//
//        worker.execute();
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                new MainFrame("SwingWorker Demo");
//            }
//        });
//    }
//}

// Barra de progresso

//package testes;

import java.awt.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;

public class MainFrame extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {

    private final JProgressBar progressBar;
    private final JButton startButton;
    private final JTextArea taskOutput;
    private Task task;

    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {
                    System.out.println("Erro:" + ignore.getMessage());
                }
                
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("Done!\n");
        }
    }

    public MainFrame() {
        super(new BorderLayout());

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    /**
     * Invoked when the user presses the start button.
     * @param evt
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // Instances of javax.swing.SwingWorker are not reusuable, so
        // we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Invoked when task's progress property changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
        } 
    }


    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ProgressBarDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new MainFrame();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}