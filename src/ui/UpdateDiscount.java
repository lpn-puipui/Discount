/*
 * Created by JFormDesigner on Mon May 10 01:03:56 CST 2021
 */

package ui;

import Util.HolidayUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 1
 */
public class UpdateDiscount extends JFrame {
    static float discount;
    float realSellingprice;

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
    static String date = df.format(new Date());


    public static void main(String[] args) {
        System.out.println(date);
        if(HolidayUtil.request(date).equals("2")){
            discount = (float) 0.85;
            System.out.println("Holiday------0.85");
        }else if(HolidayUtil.request(date).equals("1")){
            discount = (float) 0.95;
            System.out.println("Resting------0.95");
        }else if(HolidayUtil.request(date).equals("0")){
            discount = (float) 1;
            System.out.println("Working------1");
        }


        new UpdateDiscount();
    }
    public UpdateDiscount() {
        initComponents();
    }
    //ȫ����ѯ
    public Object[][] queryData() {
        //��ΪSwing��Ҳ��һ�������List
        List<Goods> list = new ArrayList<Goods>();
        Connection conn = null;
        String url = "jdbc:oracle:thin:@47.113.217.47:1521:orcl";
        Statement stmt = null;//SQL������ƴSQL
        String sql = "select * from commodity";
        System.out.println("����ִ�е�sql��" + sql);
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//
            conn = DriverManager.getConnection(url, "scott", "tiger");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {//rs.next�����ã����α������ƶ�
                Goods goods = new Goods();//ÿ��ѭ�������user����û�з�����������ôuser����һ����������
                goods.setName(rs.getString("NAME"));
                goods.setSellingprice(rs.getFloat("SELLINGPRICE"));
                goods.setDiscount(rs.getFloat("DISCOUNT"));
                list.add(goods);
            }
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //�ͷ���Դ�����ݿ����Ӻܰ���
            try {
                rs.close();
                stmt.close();
                conn.close();//������
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        data = new Object[list.size()][head.length];
        //�Ѽ���������ݷ���Obejct�����ά����
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < head.length; j++) {
                data[i][0] = list.get(i).getName();
                data[i][1] = list.get(i).getSellingprice();
                data[i][2] = list.get(i).getDiscount();
                realSellingprice = list.get(i).getSellingprice()*list.get(i).getDiscount();
                DecimalFormat decimalFormat=new DecimalFormat(".00");//���췽�����ַ���ʽ�������С������2λ,����0����.
                data[i][3] = decimalFormat.format(realSellingprice);
            }
        }
        return data;
    }

    public void updateDiscount(float discount){
        Connection conn = null;
        String url = "jdbc:oracle:thin:@47.113.217.47:1521:orcl";
        PreparedStatement pstmt = null;//SQL������
        String sql = "update commodity set discount=?";//ռλ��
        System.out.println("����ִ�е�sql��" + sql);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//
            conn = DriverManager.getConnection(url, "scott", "tiger");
            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, (float) discount);
            pstmt.executeUpdate();//�������
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //�ͷ���Դ�����ݿ����Ӻܰ���
            try {
                pstmt.close();
                conn.close();//������
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    private void initComponents() {

        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();
        label2 = new JLabel();
        textField = new JTextField("");

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("\u66f4\u65b0\u6298\u6263");//�����ۿ�
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(610, 400), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("\u81ea\u5b9a\u4e49\u66f4\u65b0");//�Զ������
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(600, 460), button2.getPreferredSize()));

        //---- textField ----
        contentPane.add(textField);
        textField.setBounds(530, 460, 70, 28);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(50, 90, 635, 295);

        //---- label1 ----
        label1.setText("\u4eca\u65e5\u6298\u6263");//�����ۿ�
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 20f));
        contentPane.add(label1);
        label1.setBounds(300, 30, 235, 40);

        //---- label2 ----
        label2.setText("\u8f93\u5165\u9519\u8bef\uff01\u8bf7\u8f93\u51650-1\u4e4b\u95f4\u7684\u4e24\u4f4d\u5c0f\u6570\uff01");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(510, 495), label2.getPreferredSize()));
        label2.setVisible(false);

        //�������ܿ�������
        updateDiscount(discount);
        DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
        Check check = new Check();
        check.setAlwaysOnTop(true);

        //����¼�
        button1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //����
                        updateDiscount(discount);
                        //��ѯ
                        DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        table1.setModel(tableModel);

                        Check check = new Check();
                        check.setAlwaysOnTop(true);
                    }
                }
        );

        button2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        label2.setVisible(false);
                        float i= Float.parseFloat(textField.getText());
                        if(i>=(float) 0&&i<=(float) 1) {
                            discount = Float.parseFloat(textField.getText());
                            System.out.println(discount);

                            //����
                            updateDiscount(discount);
                            //��ѯ
                            DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {
                                public boolean isCellEditable(int row, int column) {
                                    return false;
                                }
                            };
                            table1.setModel(tableModel);

                            Check check = new Check();
                            check.setAlwaysOnTop(true);

                            //�ָ�Ĭ���ۿ�
                            if (HolidayUtil.request(date).equals("2")) {
                                discount = (float) 0.85;
                                System.out.println("Holiday------0.85");
                            } else if (HolidayUtil.request(date).equals("1")) {
                                discount = (float) 0.95;
                                System.out.println("Resting------0.95");
                            } else if (HolidayUtil.request(date).equals("0")) {
                                discount = (float) 1;
                                System.out.println("Working------1");
                            }
                        }else{
                            label2.setVisible(true);
                            textField.setText("");
                        }
                    }
                }
        );

        contentPane.setPreferredSize(new Dimension(715, 515));
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents




        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    private JLabel label2;
    private JTextField textField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private Object[][] data = null;
    private String head[] = {"��Ʒ", "�ۼ�", "�ۿ�","�ۺ��"};
}
