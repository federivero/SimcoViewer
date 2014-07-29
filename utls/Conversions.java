/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.utls;

/**
 *
 * @author fede
 */
public class Conversions {
 
    
    public static String hexToASCII(String hex){
        if (hex.startsWith("0x")){
            hex = hex.substring(2);
        }
        if(hex.length()%2 != 0){
            System.err.println("requires EVEN number of chars");
             return null;
        }
        StringBuilder sb = new StringBuilder();
        //Convert Hex 0232343536AB into two characters stream.
        for( int i=0; i < hex.length()-1; i+=2 ){
            /* Grab the hex in pairs*/
            String output = hex.substring(i, (i + 2));
            /* Convert Hex to Decimal */
            int decimal = Integer.parseInt(output, 16);
            sb.append((char)decimal);
        }
        return sb.toString();
    }

    public static String asciiToHex(String ascii){
        StringBuilder hex = new StringBuilder();
        for (int i=0; i < ascii.length(); i++) {
            hex.append(StringUtils.pad(Integer.toHexString(ascii.charAt(i)), 2, '0', true));
        }      
        return "0x" + hex.toString().toUpperCase();
    }
    
   private static String zero_pad_bin_char(String bin_char){
        int len = bin_char.length();
        if(len == 8) return bin_char;
        String zero_pad = "0";
        for(int i=1;i<8-len;i++) zero_pad = zero_pad + "0"; 
        return zero_pad + bin_char;
    }
   
    public static String asciiToBinary(String pt){
        return hex_to_binary(plaintext_to_hex(pt));
    }
    
    private static String binary_to_plaintext(String bin){
        return hex_to_plaintext(binary_to_hex(bin));
    }
    
    public static String plaintext_to_hex(String pt) {
        String hex = "";
        for(int i=0;i<pt.length();i++){
            String hex_char = Integer.toHexString(pt.charAt(i));
            if(i==0) hex = hex_char;
            else hex = hex + hex_char;
        }
        return hex;  
    }
    
    private static String binary_to_hex(String binary) {
        String hex = "";
        String hex_char;
        int len = binary.length()/8;
        for(int i=0;i<len;i++){
            String bin_char = binary.substring(8*i,8*i+8);
            int conv_int = Integer.parseInt(bin_char,2);
            hex_char = Integer.toHexString(conv_int);
            if(i==0) hex = hex_char;
            else hex = hex+hex_char;
        }
        return hex;
    }
    
    private static String hex_to_binary(String hex) {
        String hex_char,bin_char,binary;
        binary = "";
        int len = hex.length()/2;
        for(int i=0;i<len;i++){
            hex_char = hex.substring(2*i,2*i+2);
            int conv_int = Integer.parseInt(hex_char,16);
            bin_char = Integer.toBinaryString(conv_int);
            bin_char = zero_pad_bin_char(bin_char);
            if(i==0) binary = bin_char; 
            else binary = binary+bin_char;
            //out.printf("%s %s\n", hex_char,bin_char);
        }
        return binary;
    }
    private static String hex_to_plaintext(String hex) {
        String hex_char;
        StringBuilder plaintext = new StringBuilder();
        char pt_char;
        int len = hex.length()/2;
        for(int i=0;i<len;i++){
            hex_char = hex.substring(2*i,2*i+2);
            pt_char = (char)Integer.parseInt(hex_char,16);
            plaintext.append(pt_char);
            //out.printf("%s %s\n", hex_char,bin_char);
        }
        return plaintext.toString();
    }
    
}
