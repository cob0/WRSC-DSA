package apps;
import models.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.lang3.SerializationUtils;

public class SignFile {
	
	public static void main(String[] args)  {
		KeyStore keyStore;
		PrivateKey privateKeyIssuer; // Private key of the user
		Signature signature;
		X509Certificate certificate;
		File file;
		ObjectOutputStream oos;
		
		if(args != null
				&& args.length > 0
				&& !args[0].isEmpty()
				&& args[0].contains(".dat"))
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(args[0]))) {
				System.out.println("Empezando proceso para firmar el documento...");
				
				keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				keyStore.load(new FileInputStream("almacen.keystore"), "changeit".toCharArray());
				privateKeyIssuer = (PrivateKey) keyStore.getKey("cobo", "changeit".toCharArray());
				System.out.println("Clave privada cargada para firmar.");
				
				// Until 1024 bytes
				signature = Signature.getInstance("DSA");
				signature.initSign(privateKeyIssuer);
				file = (File) ois.readObject();
				// SerializationUtils.serialize convierte un objeto a byte[]
				signature.update(SerializationUtils.serialize(file));
				System.out.println("Fichero introducido para firmar.");
				
				certificate = (X509Certificate) keyStore.getCertificate("cobo");
				System.out.println("Certificado cargado.");
				
				oos = new ObjectOutputStream(new FileOutputStream(file.getSubject() + "_signed.dat"));
				oos.writeObject(file);
				oos.writeObject(signature.sign());
				oos.writeObject(certificate);
				System.out.println("Fichero firmado generado.");
				
				oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			System.err.println("Introduce el nombre del fichero como argumento.");
	}

}
