package com.tongge.revision_control.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class MypathFace extends JDialog {
    /**
     * <p>Description:[�ֶι�������]</p>
     */
    private static final long serialVersionUID = -7488045944549617606L;
    JTextField textField1 = new JTextField();
    JTextField textField2 = new JTextField();
    JTextField textField3 = new JTextField();
    JTextField textField4 = new JTextField();

    public MypathFace(String title) {
        this.setTitle(title);
        this.setSize(400, 600);
        this.setResizable(false);
        init();
    }

    private void init() {
        this.getContentPane().setLayout(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelCenter = new JPanel();
        JPanel panelBottom = new JPanel();
        this.getContentPane().add(panelTop, BorderLayout.NORTH);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        this.getContentPane().add(panelBottom, BorderLayout.SOUTH);
        this.setModal(true);
        JLabel lable1 = new JLabel();
        lable1.setText("����workspace ·��");
        JLabel lable2 = new JLabel();
        lable2.setText("���� project ·�� ");
        JLabel lable3 = new JLabel();
        lable3.setText("ѡ�� patch�ļ�");
        JLabel lable4 = new JLabel();
        lable4.setText("ѡ�� ���������·��");

        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        button1.setText("��ѡ��");
        button2.setText("��ѡ��");
        button3.setText("��ѡ��");
        button4.setText("��ѡ��");
        panelTop.setLayout(new BorderLayout());
        panelTop.add(lable3, BorderLayout.WEST);
        panelTop.add(textField3, BorderLayout.CENTER);
        panelTop.add(button3, BorderLayout.EAST);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        panel1.setLayout(new BorderLayout());
        panelCenter.add(panel1, BorderLayout.NORTH);
        panelCenter.add(panel2, BorderLayout.CENTER);
        panel2.setLayout(new BorderLayout());
        panelCenter.add(panel3, BorderLayout.SOUTH);
        panel3.setLayout(new BorderLayout());
        JRadioButton rButton = new JRadioButton();
        rButton.setText("��Ҫ");
        panel3.add(rButton, BorderLayout.CENTER);
        panel1.add(lable1, BorderLayout.WEST);
        panel1.add(textField1, BorderLayout.CENTER);
        panel2.add(lable2, BorderLayout.WEST);
        lable2.setSize(180, 23);
        textField2.setSize(230, 23);
        panel2.add(textField2, BorderLayout.CENTER);
        button1.setActionCommand("ѡ��workspace");
        ActionLt actionLt = new ActionLt();
        button1.addActionListener(actionLt);
        button2.setActionCommand("ѡ�� project");
        button2.addActionListener(actionLt);
        button3.setActionCommand("ѡ�� patch�ļ�");
        button3.addActionListener(actionLt);
        button4.setActionCommand("ѡ�� ���������·��");
        button4.addActionListener(actionLt);
        this.addWindowListener(new WindowAdapter() {
            // windowClosing
        });

    }

    class ActionLt implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("ѡ�� patch�ļ�")) {
                JFileChooser chooser = new JFileChooser();
                FileFt fileFt = new FileFt(chooser);
                chooser.setFileFilter(fileFt);
                int returnValue = -1;
                if ((returnValue = chooser.showOpenDialog(null)) == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    textField3.setText(chooser.getSelectedFile().getPath());
                }

            }
            if (e.getActionCommand().equalsIgnoreCase("ѡ�� project")) {

            }
            if (e.getActionCommand().equalsIgnoreCase("ѡ�� patch�ļ�")) {

            }
            if (e.getActionCommand().equalsIgnoreCase("ѡ�� ���������·��")) {

            }

        }

    }

    class FileFt extends FileFilter {
        JFileChooser chooser = null;

        public FileFt(JFileChooser chooser) {
            this.chooser = chooser;
        }

        public boolean accept(File f) {
            if (f.isDirectory() || f.getPath().endsWith(".txt")) {
                return true;
            }
            return false;
        }

        public String getDescription() {
            if (chooser.getSelectedFile() == null) {
                return "";
            } else {
                return chooser.getSelectedFile().getPath();
            }

        }

    }

    /**
     * <p>Description:[����������������]</p>
     * @author��١��� tge0503020211@163.com
     * @return JTextField textField1.
     */
    public JTextField getTextField1() {
        return textField1;
    }

    /**
     * <p>Description:[����������������]</p>
     * @author��١��� tge0503020211@163.com
     * @return JTextField textField2.
     */
    public JTextField getTextField2() {
        return textField2;
    }

    /**
     * <p>Description:[����������������]</p>
     * @author��١��� tge0503020211@163.com
     * @return JTextField textField3.
     */
    public JTextField getTextField3() {
        return textField3;
    }

    /**
     * <p>Description:[����������������]</p>
     * @author��١��� tge0503020211@163.com
     * @return JTextField textField4.
     */
    public JTextField getTextField4() {
        return textField4;
    }

}
