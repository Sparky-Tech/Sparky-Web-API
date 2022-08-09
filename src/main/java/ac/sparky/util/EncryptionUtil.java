package ac.sparky.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtil {
    private static final byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    public static String decrypt(String encryptedText, String secretKey) {
        try {
            KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, 256);
            AlgorithmParameterSpec algorithmParameterSpec = new PBEParameterSpec(salt, 256);
            SecretKey secret = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(secret.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secret, algorithmParameterSpec);

            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
