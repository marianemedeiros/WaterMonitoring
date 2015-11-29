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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tools.DB_Direct;
import watermonitoring.JsonParser.Json;
public class GUI extends JFrame implements Observer {
       
    Json infoAPI;
    
    /*save data in db*/
    DB_Direct dB_Direct;
    Date date;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    
    JLabel aviso = new JLabel("Irrigador Ligado, umidade do ar muito baixa!!!");
    
    JLabel cidade = new JLabel("Cidade");
    JTextField txtCidade = new JTextField();
    
    JLabel temperatura = new JLabel("Temperatura (C)");
    JTextField txtTemperatura = new JTextField();

    JLabel umidade = new JLabel("Umidade (%)");
    JTextField txtUmidade = new JTextField();
    
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
    JPanel painelNorte = new JPanel();
    JPanel painelSul = new JPanel();
    JPanel painelCentro = new JPanel();
    boolean controle = false;
    
    public GUI(Json json) throws SQLException {
        date = new Date();
        infoAPI = json;
        this.dB_Direct = new DB_Direct("localhost", "testeCompFis", "root", "", "");
        System.out.println("****************** Status Connection DB: " + dB_Direct.getStatusDaConexao() + "************************");
        gravaInfoAPI();

        try {
            acessaArduino = new AcessaArduino(this); //passa como parâmetro a classe GUI (this) para informar quem é o Observador
            painelNorte.setBackground(Color.magenta);
            painelSul.setBackground(Color.green);
        } catch (Exception e) {
            painelSul.setBackground(Color.red);

        }

        setSize(600, 300);
        setTitle("Controle de Irrigação");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        
        painelNorte.setLayout(new GridLayout(3,1));
        painelCentro.setLayout(new GridLayout(0, 2));
        
        ligaDesligaIrrigador.setOpaque(true);
        ligaDesligaIrrigador.setBackground(Color.gray);

        painelNorte.add(cidade);
        txtCidade.setText(infoAPI.getName());
        painelNorte.add(txtCidade);
        txtCidade.setEditable(false);
        
        painelNorte.add(temperatura);
        txtTemperatura.setText(infoAPI.getMain().getTemp());
        painelNorte.add(txtTemperatura);
        txtTemperatura.setEditable(false);
        
        painelNorte.add(umidade);
        txtUmidade.setText(infoAPI.getMain().getHumidity());
        painelNorte.add(txtUmidade);
        txtUmidade.setEditable(false);
        
        painelCentro.add(new JLabel(""));
        painelCentro.add(new JLabel(""));
        painelCentro.add(sensor1);
        painelCentro.add(txtFsensor1);
        txtFsensor1.setEnabled(false);
        
        painelCentro.add(sensor2);
        painelCentro.add(txtFsensor2);
        txtFsensor2.setEnabled(false);
        
        painelCentro.add(sensor3);
        painelCentro.add(txtFsensor3);
        txtFsensor3.setEnabled(false);
        
        painelCentro.add(media);
        painelCentro.add(txtFMedia);
        txtFMedia.setEnabled(false);
        
        painelCentro.add(new JLabel(""));
        painelCentro.add(new JLabel(""));


        aviso.setVisible(false);
        painelSul.add(aviso);
        painelSul.add(ligaDesligaIrrigador);
        
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
                System.exit(0);
            }
        });
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        String linha = arg.toString();
        String[] s = linha.split(";");
        float media = (Integer.valueOf(s[0])+Integer.valueOf(s[1])+Integer.valueOf(s[2]))/3;
        
        this.txtFsensor1.setText(s[0]);
        this.txtFsensor2.setText(s[1]);
        this.txtFsensor3.setText(s[2]);
        this.txtFMedia.setText(String.valueOf(media));
        
        int liga = verificaLigaDesliga(media);
        if(liga == 1) aviso.setVisible(true);
        
        if(media != 0){
            gravaControle(Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]), media, liga);
        }
    }
    
    private void gravaControle(int s1, int s2, int s3, float media, int l){
         String sql = "INSERT INTO `testeCompFis`.`Controle`\n" +
                        "(`sensor1`, `sensor2`, `sensor3`, `media`, `ligado`, `dia`)\n" +
                        "VALUES(\n" +
                           "\'" + s1 + "\',\n" +
                            "\'" + s2 + "\',\n" +
                            "\'" + s3 + "\',\n" +
                             "\'" + media + "\',\n" +
                              "\'" + l + "\',\n" +
                                "\'" + sdf.format(date) + "\');";
        
        dB_Direct.executaAtualizacaoNoBD(sql);
    }
    
    private void gravaInfoAPI() throws SQLException{
        if (verificaData()){
          date = new Date();
          String sql = "INSERT INTO `testeCompFis`.`InfoAPI`\n" +
                        "(`cidade`, `temperatura`, `umidade`, `dia`)\n" +
                        "VALUES(\n" +
                           "\'" + infoAPI.getName() + "\',\n" +
                            "\'" + infoAPI.getMain().getTemp() + "\',\n" +
                            "\'" + infoAPI.getMain().getHumidity() + "\',\n" +
                            "\'" + sdf.format(date) + "\');";
        
        System.out.println(dB_Direct.executaAtualizacaoNoBD(sql));
        System.out.println(dB_Direct.getMsgErro());
        }
    }

    private boolean verificaData() throws SQLException {
        String select = "SELECT * from InfoAPI;";
        ResultSet r = dB_Direct.executaSelect(select);
        
        while(r.next() && r != null){
            Date dia = r.getDate("dia");
                if(d.format(date).equalsIgnoreCase(dia.toString())){
                    return false;
                }
        }
        return true;
    }

    private int verificaLigaDesliga(float media) {
        if(Integer.valueOf(infoAPI.getMain().getHumidity()) < 30  && media <= 70){
            return 1;
        }
        return 0;
    }
}
