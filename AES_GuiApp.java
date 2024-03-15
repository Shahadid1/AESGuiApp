import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class AES_GuiApp {

    public static void main(String[] args) {
// Create the main frame
        JFrame frame = new JFrame("AES Encryption/Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);
// Create a panel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel,frame);
// Make the frame visible
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel,JFrame frame) {
        panel.setLayout(null);
        JLabel NOTE = new JLabel("NOTEs!: Please be careful to the following restrictions: "); 
        NOTE.setBounds(10, 5, 800, 25);
        panel.add(NOTE);
        
        JLabel SymbolysFormat = new JLabel("1. the following symbols aren't allowed in any text box: < , > , \\ , ' , ; , *, \"");//Note:you may enter a 16 characters for AES-128 OR 24 characters for AES-192 
        SymbolysFormat.setBounds(10, 35, 800, 25);
        panel.add(SymbolysFormat);
        
        JLabel keyFormat = new JLabel("2. you may only enter a 16 characters for AES-128 OR 24 characters for AES-192 "); 
        keyFormat.setBounds(10, 55, 800, 25);
        panel.add(keyFormat);
                
        JLabel keyLabel = new JLabel("Enter AES Key:");
        keyLabel.setBounds(10, 90, 150, 25);
        panel.add(keyLabel);

        JTextField keyText = new JTextField(20);
        keyText.setBounds(160, 90, 200, 25);
        panel.add(keyText);
        
        JLabel inputLabel = new JLabel("Enter Text:");
        inputLabel.setBounds(10, 115, 150, 25);
        panel.add(inputLabel);

        JTextField inputText = new JTextField(20);
        inputText.setBounds(160, 115, 200, 25);
        panel.add(inputText);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(10, 145, 100, 25);
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(120, 145, 100, 25);
        panel.add(decryptButton);
        
        JButton ClearButton = new JButton("Clear");
        ClearButton.setBounds(230, 145, 100, 25);
        panel.add(ClearButton);
        
        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(10, 180, 1000, 60);
        panel.add(resultArea);
        
        // action listeners for encryptButton and decryptButton
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try{
                String key = keyText.getText();
                // validate key and input then Perform AES encryption
                if(!(key.contains("<")||key.contains(">")||key.contains("\\")||key.contains("'")||key.contains(";")||key.contains("*")||key.contains("\""))){
                String input = inputText.getText();
                if(input.length()<1)
                resultArea.setText(null);
                else 
                if(!(input.contains("<")||input.contains(">")||input.contains("\\")||input.contains("'")||input.contains(";")||input.contains("*")||input.contains("\""))){
                String encryptedText = encrypt(input, key);
                 resultArea.setText(encryptedText);// Display the encryption result in the resultArea
                 }
                 // Display a message when text validation goes wrong
                 else
                 JOptionPane.showMessageDialog(frame, "Error: (unallowed symbols).", "Error", JOptionPane.ERROR_MESSAGE);
                 }
                 // Display a message when key validation goes wrong
                 else
                 JOptionPane.showMessageDialog(frame, "Error: (unallowed symbols).", "Error", JOptionPane.ERROR_MESSAGE);
           } catch (Exception ex) {
          // When something goes wrong (e.g., entering a key its length isn't a 16/24 characters)
           JOptionPane.showMessageDialog(frame,"ERORR!, Something went wrong!.", "Error", JOptionPane.ERROR_MESSAGE);
        }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try{
                // validate key and input 
                String key = keyText.getText();
                if(key.length()<1)
                resultArea.setText(null);
                else
                if(!(key.contains("<")||key.contains(">")||key.contains("\\")||key.contains("'")||key.contains(";")||key.contains("*")||key.contains("\"") )){
                String input = inputText.getText();
                if(!(input.contains("<")||input.contains(">")||input.contains("\\")||input.contains("'")||input.contains(";")||input.contains("*")||input.contains("\"")) ){
                // Perform AES decryption
                String decryptedText = decrypt(input, key);
                // Display the decryption result in the resultArea
                resultArea.setText(decryptedText);
                }
                else
                 JOptionPane.showMessageDialog(frame, "Error:(unallowed symbols).", "Error", JOptionPane.ERROR_MESSAGE);
                 }
                 else
                 JOptionPane.showMessageDialog(frame, "Error:(unallowed symbols).", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
          // When something goes wrong (e.g., entering a key its length isn't a 16/24 characters)
           JOptionPane.showMessageDialog(frame,"ERORR!, Something went wrong!.", "Error", JOptionPane.ERROR_MESSAGE);
        }
            }
        });
   ClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try{
                // Display "clear" result in the resultArea
                resultArea.setText(null);
                } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame,"ERORR!.", "Error", JOptionPane.ERROR_MESSAGE);
        }
            }
        });
    }
    
    public static String encrypt(String plainText, String key) throws Exception {
    //creats a SecretKeySpec object using the bytes derived from the provided key string.
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher encrypt_cipher = Cipher.getInstance("AES"); //creats a Cipher object configured to use AES encryption.
        //initializing (encrypt_cipher)object for encryption using (secretKey) object as the encryption key.
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, secretKey); 
        //calling doFinal() method which perform encryption operations with the plainText in byets form, then save it in a bytes array.
        byte[] encryptedBytes = encrypt_cipher.doFinal(plainText.getBytes()); 
        return Base64.getEncoder().encodeToString(encryptedBytes); // calling getEncoder().encodeToString methoed from class Base64 to transform the bytes array to a string.
    }
    public static String decrypt(String encryptedText, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher decrypt_cipher = Cipher.getInstance("AES");
        decrypt_cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = decrypt_cipher.doFinal(Base64.getDecoder().decode(encryptedText));// first decodes the encryptedText from Base64 that transforms it to bytes, then doFinal() method performs decryption operations on the returned bytes and save the result in a bytes array(decryptedBytes).
        return new String(decryptedBytes);// transform the decryptedBytes to a new string and return it.

    }

}
