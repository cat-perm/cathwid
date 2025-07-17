package lol.notmeower.hwid;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.security.MessageDigest;

public class HWID {

    public static String getHWID() {
        try {
            String toEncrypt = System.getenv("COMPUTERNAME") +
                    System.getProperty("user.name") +
                    System.getenv("PROCESSOR_IDENTIFIER") +
                    System.getenv("PROCESSOR_LEVEL");

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());

            byte[] byteData = md.digest();
            StringBuilder hexString = new StringBuilder();

            for (byte b : byteData) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(text), null);
    }

    public static void showHWIDPopup(String hwid) {
        String message = "Your HWID has been copied to clipboard:\nHWID: " + hwid;
        JOptionPane.showMessageDialog(null, message, "HWID Copied to clipboard", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        String hwid = getHWID();
        copyToClipboard(hwid);
        showHWIDPopup(hwid);
    }
}
