package cifrado;

import clases.UserEntity;
import clases.UserPrivilegeType;
import clases.UserStatusType;
import factoria.UserManagerImplementation;
import interfaces.UserInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import static javax.crypto.Cipher.DECRYPT_MODE;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import restful.UserClient;

public class CifradoClient {
    
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cifrado.key");
    private static UserInterface userManager;
    
    /*public static void main(String[] args) {
        String con = "abcd*1234";
        
        UserEntity user = new UserEntity();
        userManager = new UserManagerImplementation();
        UserClient webClientUser = new UserClient();
        
        user.setFullName("Idoia");
        user.setEmail("idoia8.ormaetxea2000@gmail.com");
        user.setPassword(CifradoClient.encrypt(con));
        user.setUserStatus(UserStatusType.ENABLED);
        user.setUserPrivilege(UserPrivilegeType.GRANJERO);
        user.setUsername("idoia8");
        System.out.println();
        UserClient rest = new UserClient();
        
        rest.create(user);
        
       //CifradoClient cf = new CifradoClient();
        System.out.println(CifradoClient.encrypt(con));
        
    }*/

    public static String encrypt(String plaintext) {
        
        byte[] bs = null;
        //PublicKey key;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            //key = readPublicKey("./cifrado/public.key");
            
            cipher.init(Cipher.ENCRYPT_MODE, readPublicKey());
            bs = cipher.doFinal(plaintext.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(CifradoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteArrayToHexString(bs);

    }

    public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(fileReader(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
    
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static PublicKey readPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey pubKey = null;
        try {
            // Obtener los bytes del archivo donde este guardado la llave publica
            byte[] pubKeyBytes = hexStringToByteArray(configFile.getString("PUBLICKEY"));
            //
            X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(pubKeyBytes);
            //
            pubKey = KeyFactory.getInstance("RSA").generatePublic(encPubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(CifradoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pubKey;
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            Logger.getLogger(CifradoClient.class.getName()).log(Level.SEVERE, null, e);
        }
        return ret;
    }
}
