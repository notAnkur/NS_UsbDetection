/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nsextdevice;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ankur Anant <hello@ankuranant.dev>
 */

public class LoopThread extends Thread
{
    private javax.swing.JTextArea displayTAT;
   
    private volatile boolean isActive = true;
   
    String[] drive_name;
    File[] usb;
    boolean[] usb_detected;

   public LoopThread(javax.swing.JTextArea displayTAT, File[] usb,String[] drive_name,boolean[] usb_detected)
   {
      this.displayTAT = displayTAT;
      this.drive_name = drive_name;
      this.usb = usb;
      this.usb_detected = usb_detected;
   }

   @Override
   public void run()
   {
        try {
            displayTAT.append("USB detection started\n");
            while(isActive) {
                if(!isActive) break;
                if(Thread.currentThread().isInterrupted()) {
                    isActive = false;
                    displayTAT.append("Thread interrupted\n");
                }
                for ( int i = 0; i < drive_name.length; ++i ) {
                    boolean if_detected;
                    if_detected = usb[i].canRead();
                    if ( if_detected != usb_detected[i] ) {
                        if ( if_detected ) {
                            displayTAT.append("USB "+drive_name[i]+" detected \n");
                        }

                        usb_detected[i] = if_detected;
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
   }
   
    @Override
   public void interrupt() {
        try {
            super.interrupt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
