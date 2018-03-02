package criptografia.assimetrica;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAKeyGenParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class Metodos {
    
    private byte[] buf = new byte[1024];
    
    public static void gerarChave() throws Exception {
        KeyPairGenerator kpg = new KeyPairGenerator("RSA") {};
        kpg.initialize(new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4));
        KeyPair par = kpg.genKeyPair();
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new
            File("ChavePublica")));
        oos = new ObjectOutputStream(new FileOutputStream(new File("Chave Privada")));
        oos.writeObject(par.getPrivate());
        oos.close();
    }
    
    public void criptografar(Key chave, InputStream in, OutputStream out) throws Exception {
        Cipher cifra = Cipher.getInstance("RSA");
        cifra.init(Cipher.ENCRYPT_MODE, chave);
        CipherOutputStream cipherOut = new CipherOutputStream(out, cifra);
        
        int numLido = 0;
        while ((numLido = in.read(buf)) >= 0) {
            cipherOut.write(buf, 0, numLido);
        }   
        cipherOut.close();
    }   
    
    public void discriptografar(Key chave, InputStream in, OutputStream out) throws Exception {
        Cipher cifra = Cipher.getInstance("RSA");
        cifra.init(Cipher.DECRYPT_MODE, chave);
        CipherInputStream cipherIn =  new CipherInputStream(in, cifra);
        
        int numLido = 0;
        while((numLido = cipherIn.read(buf)) >= 0) {
            out.write(buf, 0, numLido);
        }
        out.close();
    }
}