package view;

/**
 *
 * @author radames
 */

//import db.DB_Direct;
import Arduino.AcessaArduino;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class GUI extends JFrame implements Observer {
    
    /*save data in db*/
//    DB_Direct dB_Direct;
    Date date;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    JLabel sensor1 = new JLabel("Sensor1");
    JTextField txtFsensor1 = new JTextField();
    
    
    JLabel sensor2 = new JLabel("Sensor2");
    JTextField txtFsensor2 = new JTextField();
    
    JLabel sensor3 = new JLabel("Sensor3");
    JTextField txtFsensor3 = new JTextField();

    JLabel media = new JLabel("Media");
    JTextField txtFMedia = new JTextField();
    
    JButton ligaDesligaIrrigador = new JButton("Liga/Desliga");

    Container cp;
    AcessaArduino acessaArduino;
    JPanel painelResposta = new JPanel();
    JPanel painelNorte = new JPanel();
    JPanel painelSul = new JPanel();
    JPanel painelCentro = new JPanel();
    boolean controle = false;

    public GUI() {
  //      this.dB_Direct = new DB_Direct("localhost", "testeCompFis", "root", "", "");
  //      System.out.println("****************** Status Connection DB: " + dB_Direct.getStatusDaConexao() + "************************");
        try {
 //           acessaArduino = new AcessaArduino(this); //passa como parâmetro a classe GUI (this) para informar quem é o Observador
            painelNorte.setBackground(Color.magenta);
            painelSul.setBackground(Color.green);

        } catch (Exception e) {
            painelSul.setBackground(Color.red);

        }

        //System.out.println(ArduinoSerialPortListener.serial_port);
        setSize(1000, 700);
        setTitle("Controle de Irrigação");
        setLocation(2500, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cp = getContentPane();
        cp.setLayout(new BorderLayout());

        painelNorte.setLayout(new GridLayout(1,8));
        painelNorte.add(new JLabel());
        painelNorte.add(sensor1);
        
        painelCentro.setLayout(new GridLayout(5, 3));
        
        ligaDesligaIrrigador.setOpaque(true);
        ligaDesligaIrrigador.setBackground(Color.gray);
 
        painelCentro.add(new JLabel());
        painelCentro.add(sensor1);
        painelCentro.add(txtFsensor1);
        txtFsensor1.disable();
        
        painelCentro.add(new JLabel());
        painelCentro.add(sensor2);
        painelCentro.add(txtFsensor2);
        txtFsensor2.disable();
        
        painelCentro.add(new JLabel());
        painelCentro.add(sensor3);
        painelCentro.add(txtFsensor3);
        txtFsensor3.disable();
        
        painelCentro.add(new JLabel());
        painelCentro.add(media);
        painelCentro.add(txtFMedia);
        txtFMedia.disable();
        
        painelCentro.add(ligaDesligaIrrigador);
        
        painelResposta.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelCentro.add(painelResposta);
        cp.add(painelNorte, BorderLayout.NORTH);
        cp.add(painelCentro, BorderLayout.CENTER);
        cp.add(painelSul, BorderLayout.SOUTH);

        ligaDesligaIrrigador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controle = !controle;
                if (controle) {
                    ligaDesligaIrrigador.setText("Ligado");
                    ligaDesligaIrrigador.setBackground(Color.green);
 //                   acessaArduino.setDataToArduino(acessaArduino.getSerialPort(), "1"); // 0 == mudo; 1 == auto
                } else {
                    ligaDesligaIrrigador.setText("Desligado");
                    ligaDesligaIrrigador.setBackground(Color.red);
 //                   acessaArduino.setDataToArduino(acessaArduino.getSerialPort(), "0"); // 0 == mudo; 1 == auto
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
 //               acessaArduino.setDataToArduino(acessaArduino.getSerialPort(), "0"); // 0 == mudo; 1 == auto
                
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        date = new Date();
        
        
        String sql = "INSERT INTO `testeCompFis`.`Estacionamento`\n" +
                        "(`idEstacionamento`,\n" +
                        "`proximidade`)\n" +
                        "VALUES(\n" +
                           "\'" + sdf.format(date) + "\',\n" +
                          "\'" + arg.toString() + "\');";
        
//        System.out.println(dB_Direct.executaAtualizacaoNoBD(sql));
//        System.out.println(dB_Direct.getMsgErro());
        Float f = Float.valueOf(arg.toString());
//        txtFDist.setText(f.toString());
//        if (arg instanceof Integer) {
//            //foi setado um valor na janela observada....             
//            lbResposta.setText(String.valueOf(((Integer) arg).intValue())); //valor informado na thread
//
////            tf.setText(String.valueOf(contador));
//        } else if (arg instanceof Boolean) {
//            if (((Boolean) arg).booleanValue()) {
//            }
//        }
    }
}
