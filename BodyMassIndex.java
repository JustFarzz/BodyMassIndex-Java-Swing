//saya menggunakan framework javaswing dan java.awt untuk GUInya
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;


//saya memperluas(extended) methodnya menggunakan JFrame
public class BodyMassIndex extends JFrame
{
    //saya membuat variabel akses private untuk input tinggi badan & berat badan
    private JTextField beratTextField;
    private JTextField tinggiTextField;

    //ini untuk tombol pemilihan satuan berat dan tinggi
    private JRadioButton kgCmRadioButton;
    private JRadioButton lbInRadioButton;

    public BodyMassIndex()
    {
        //Setup frame atau GUInya
        setTitle("Body Mass Index");
        setSize(605, 230);

        // Mengatur tata letak frame ke tengah layar
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //icon GUInya
        ImageIcon iconnya = new ImageIcon("images/icon_bmi.png");
        setIconImage(iconnya.getImage());

        //icon labelnya
        JLabel imageLabel = new JLabel();
        ImageIcon inputImage = new ImageIcon("images/image1.png"); // Gantilah dengan path gambar yang sesuai
        imageLabel.setIcon(inputImage);

        //setup Panel/Layout
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //label input (komponen)
        JLabel beratLabel = new JLabel("Berat ");
        JLabel tinggiLabel = new JLabel("Tinggi ");

        kgCmRadioButton = new JRadioButton("kg/cm");
        lbInRadioButton = new JRadioButton("lb/in");

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(kgCmRadioButton);
        unitGroup.add(lbInRadioButton);
        kgCmRadioButton.setSelected(true);

        JButton hitungButton = new JButton("Lihat Hasil");

        //style komponen
        beratTextField = new JTextField(10);
        tinggiTextField = new JTextField(10);
        hitungButton.setFont(new Font("Arial", Font.BOLD, 14));
        hitungButton.setForeground(Color.WHITE);
        hitungButton.setBackground(new Color(0, 102, 204));
        hitungButton.setFocusPainted(false);

        //menghapus background button satuan
        kgCmRadioButton.setOpaque(false);
        lbInRadioButton.setOpaque(false);
        kgCmRadioButton.setFocusPainted(false);
        lbInRadioButton.setFocusPainted(false);



        //mnambahkan komponen ke main Panel
        mainPanel.add(beratLabel);
        mainPanel.add(beratTextField);
        mainPanel.add(tinggiLabel);
        mainPanel.add(tinggiTextField);
        mainPanel.add(kgCmRadioButton);
        mainPanel.add(lbInRadioButton);
        mainPanel.add(hitungButton);
        mainPanel.add(imageLabel);

        //menambahkan main Panel ke dalam frame
        add(mainPanel);

        //fungsi buttonnya(hasil bmi)
        hitungButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                hitungBMI();
            }
        });
    }

    //saya menambahkan class untuk warna gradient(gradiasi)
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //set warna gradient
            Color color1 = new Color(0xFBB040);
            Color color2 = new Color(0xF9ED32);

            //render gradien
            GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    //saya buat fungsi untuk hitungBMI
    private void hitungBMI() {
        try {
            double berat = Double.parseDouble(beratTextField.getText());
            double tinggi = Double.parseDouble(tinggiTextField.getText());

            if (lbInRadioButton.isSelected()) {
                //membuat konversi berat dari pound ke kilogram dan tinggi dari inci ke sentimeter
                berat *= 0.453592;
                tinggi *= 2.54;
            }

            //konversi dari default(meter) ke centimeter
            double tinggiMeter = tinggi / 100.0;
            double bmi = berat / (tinggiMeter * tinggiMeter);

            String kategoriBmi = determinekategoriBmi(bmi);

            //untuk styling popup hasil
            UIManager.put("OptionPane.background", new Color(0xFBB040));
            UIManager.put("Panel.background", new Color(0xFBB040));
            UIManager.put("OptionPane.messageForeground", new Color(0, 0, 0));
            UIManager.put("OptionPane.titleText", new Font("Arial", Font.BOLD, 16));
            UIManager.put("Button.background", new Color(0, 102, 204));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));

            //icon untuk popup hasil
            ImageIcon customIcon = new ImageIcon("images/icon_bmi.png");
            Image scaledIcon = customIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            customIcon.setImage(scaledIcon);

            JOptionPane.showMessageDialog(this,
                    "BMI kamu: " + String.format("%.2f", bmi) + "\nKategori: " + kategoriBmi, "Hasil BMI",
                    JOptionPane.INFORMATION_MESSAGE, customIcon);

        } catch (NumberFormatException ex) {

            //untuk styling popup kesalahan
            UIManager.put("OptionPane.background", new Color(0xFBB040));
            UIManager.put("Panel.background", new Color(0xFBB040));
            UIManager.put("OptionPane.messageForeground", new Color(0, 0, 0));
            UIManager.put("OptionPane.titleText", new Font("Arial", Font.BOLD, 16));
            UIManager.put("Button.background", new Color(0, 102, 204));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));

            JOptionPane.showMessageDialog(this, "Input Salah! Tolong input berat dan tinggi dengan nomor!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //menambahkan kategori bmi (hasil ke-2 atau output)
    private String determinekategoriBmi(double bmi)
    {
        if (bmi < 18.5)
        {
            return "Kekurangan Berat Badan";
        }
        else if (bmi >= 18.5 && bmi <= 24.9)
        {
            return "Berat Badan Normal";
        }
        else if (bmi >= 25 && bmi <= 29.9)
        {
            return "Anda Kelebihan Berat Badan";
        }
        else
        {
            return "Anda Sedang Obesitas";
        }
    }

    //eksekusi guinya
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run()
            {
                new BodyMassIndex().setVisible(true);
            }
        });
    }
}
