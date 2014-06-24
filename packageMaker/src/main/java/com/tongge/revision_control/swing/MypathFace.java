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
     * <p>Description:[字段功能描述]</p>
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
        lable1.setText("设置workspace 路径");
        JLabel lable2 = new JLabel();
        lable2.setText("设置 project 路径 ");
        JLabel lable3 = new JLabel();
        lable3.setText("选择 patch文件");
        JLabel lable4 = new JLabel();
        lable4.setText("选择 升级包存放路径");

        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        button1.setText("请选择");
        button2.setText("请选择");
        button3.setText("请选择");
        button4.setText("请选择");
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
        rButton.setText("需要");
        panel3.add(rButton, BorderLayout.CENTER);
        panel1.add(lable1, BorderLayout.WEST);
        panel1.add(textField1, BorderLayout.CENTER);
        panel2.add(lable2, BorderLayout.WEST);
        lable2.setSize(180, 23);
        textField2.setSize(230, 23);
        panel2.add(textField2, BorderLayout.CENTER);
        button1.setActionCommand("选择workspace");
        ActionLt actionLt = new ActionLt();
        button1.addActionListener(actionLt);
        button2.setActionCommand("选择 project");
        button2.addActionListener(actionLt);
        button3.setActionCommand("选择 patch文件");
        button3.addActionListener(actionLt);
        button4.setActionCommand("选择 升级包存放路径");
        button4.addActionListener(actionLt);
        this.addWindowListener(new WindowAdapter() {
            // windowClosing
        });

    }

    class ActionLt implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("选择 patch文件")) {
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
            if (e.getActionCommand().equalsIgnoreCase("选择 project")) {

            }
            if (e.getActionCommand().equalsIgnoreCase("选择 patch文件")) {

            }
            if (e.getActionCommand().equalsIgnoreCase("选择 升级包存放路径")) {

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
     * <p>Description:[方法功能中文描述]</p>
     * @author：佟广恩 tge0503020211@163.com
     * @return JTextField textField1.
     */
    public JTextField getTextField1() {
        return textField1;
    }

    /**
     * <p>Description:[方法功能中文描述]</p>
     * @author：佟广恩 tge0503020211@163.com
     * @return JTextField textField2.
     */
    public JTextField getTextField2() {
        return textField2;
    }

    /**
     * <p>Description:[方法功能中文描述]</p>
     * @author：佟广恩 tge0503020211@163.com
     * @return JTextField textField3.
     */
    public JTextField getTextField3() {
        return textField3;
    }

    /**
     * <p>Description:[方法功能中文描述]</p>
     * @author：佟广恩 tge0503020211@163.com
     * @return JTextField textField4.
     */
    public JTextField getTextField4() {
        return textField4;
    }

}
