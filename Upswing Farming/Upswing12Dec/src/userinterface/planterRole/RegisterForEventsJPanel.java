/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.planterRole;

import Business.Organization.EndUserOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.EventOrganizerWorkRequest;
import Business.WorkQueue.WorkRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.awt.CardLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kavyaprakash
 */
public class RegisterForEventsJPanel extends javax.swing.JPanel {

    /**
     * Creates new form RegisterForEventsJPanel
     */
    JPanel container;
    UserAccount account;
    EndUserOrganization endUserOrganization;
    ArrayList<WorkRequest> listOfReq;

    RegisterForEventsJPanel(JPanel container, UserAccount account,EndUserOrganization endUserOrganization) {
        initComponents(); //To change body of generated methods, choose Tools | Templates.
        this.container = container;
        this.account = account;
        this.endUserOrganization = endUserOrganization;
        this.listOfReq = endUserOrganization.getWorkQueue().getWorkRequestList();
        populateQATable();
    }
    
    public void populateQATable(){
    DefaultTableModel tbl = (DefaultTableModel) tblEvent.getModel();
        tbl.setRowCount(0);
         for(WorkRequest wk : listOfReq){
            if (wk instanceof EventOrganizerWorkRequest){
                Object[] row = new Object[5];
                row[0] = wk;
                row[1] = ((EventOrganizerWorkRequest) wk).getEventAddress();
                row[2] = ((EventOrganizerWorkRequest) wk).getEventDate();
                row[3] = ((EventOrganizerWorkRequest) wk).getTotalSeats();
                row[4] = ((EventOrganizerWorkRequest) wk).getRemainingSeat();
                tbl.addRow(row);
        }
            
        
        
        
 
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvent = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        jLabel1.setText("REGISTER FOR EVENTS ");

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        tblEvent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Event Name", "Event Address", "Event Date", "Number of Seats", "Number of Remaining Seats"
            }
        ));
        jScrollPane1.setViewportView(tblEvent);

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(btnRegister))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnBack)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegister)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addContainerGap(130, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblEvent.getSelectedRow();

        if (selectedRow < 0){
            JOptionPane.showMessageDialog(null,"Please select a row");
        }
        
        else{
            DefaultTableModel dtm = (DefaultTableModel) tblEvent.getModel();
            
                WorkRequest wr = (WorkRequest)dtm.getValueAt(selectedRow,0);
                                
                for(WorkRequest wk : listOfReq){
                    if (wk instanceof EventOrganizerWorkRequest){
                        
                      if(((EventOrganizerWorkRequest) wk).getEventName().equals(wr.getMessage())){
                        
                          if (((EventOrganizerWorkRequest) wk).getRemainingSeat() > 0){
                                int x = ((EventOrganizerWorkRequest) wk).getRemainingSeat() - 1;                        
                                ((EventOrganizerWorkRequest) wk).setRemainingSeat(x);
                                JOptionPane.showMessageDialog(null,"Successfully Registered");
                                populateQATable();
                                
                                String mobNo = JOptionPane.showInputDialog("Enter Your Mobile Number to get details of the event");
                                
                                if (mobNo != null){
                                sendSMS(mobNo, ((EventOrganizerWorkRequest) wk).getEventName(), ((EventOrganizerWorkRequest) wk).getEventDate());
                                }
                          }
                          
                          else if (((EventOrganizerWorkRequest) wk).getRemainingSeat() < 1){
                                //int x = ((EventOrganizerWorkRequest) wk).getRemainingSeat() - 1;                        
                                //((EventOrganizerWorkRequest) wk).setRemainingSeat(x);
                                JOptionPane.showMessageDialog(null,"No Seats available");
                               // populateQATable();
                               // String mobNo = JOptionPane.showInputDialog("Enter Your Mobile Number to get details of the event");
                                //sendSMS(mobNo, ((EventOrganizerWorkRequest) wk).getEventName(), ((EventOrganizerWorkRequest) wk).getEventDate());
                          }
                         
                        
                }
            }
            }
              
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        container.remove(this);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }//GEN-LAST:event_btnBackActionPerformed
public void sendSMS(String Number, String Name, String Date){
    Twilio.init("ACded94591e66f983b7f73c89336b1c3df", "7980a1f0a1a1f0af739b113351287a6d");
 
            Message message = Message
            .creator(new PhoneNumber(Number), new PhoneNumber("+1 347-947-2698"),
                "Hello..!! your registration is successful..!! Event Name: "+Name+" on Date: "+Date+" Best Regards - UpSwing Farming Team ").create();
 
                System.out.println(message.getSid());
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEvent;
    // End of variables declaration//GEN-END:variables
}
