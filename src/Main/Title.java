package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Title {
    private JPanel titlePanel;
    JButton Startbtn;
    private JLabel MainLabel;
    JButton Rulebtn;
    private Game gamePanel;


    public Title(){

        Startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //setPreferredSize(new Dimension(600,600));
        //setBackground(Color.blue);
    }


    public JPanel getTitlePanel(){
        return titlePanel;
    }


    private class Rulebtn implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private class Loadbtn implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
