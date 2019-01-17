package apps;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import org.apache.commons.lang3.SerializationUtils;

import models.File;

public class ReadFileSigned {
	
	public static void main(String[] args) {
		File file;
		byte[] signatureFile;
		X509Certificate certificate;
		PublicKey publicKeyUser;
		PublicKey publicKeyIssuer;
		Signature signature;
		
		
		if(args != null
				&& args.length > 0
				&& !args[0].isEmpty()
				&& args[0].contains(".dat"))
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(args[0]))) {
				System.out.println("Empezando el proceso de comprobación del fichero...");
				
				file = (File) ois.readObject();
				signatureFile = (byte[]) ois.readObject();
				certificate = (X509Certificate) ois.readObject();
				
				publicKeyUser = certificate.getPublicKey();
				publicKeyIssuer = certificate.getPublicKey();
				
				certificate.checkValidity();
				certificate.verify(publicKeyIssuer);
				
				// Same on file generation.
				signature = Signature.getInstance("DSA");
				signature.initVerify(publicKeyUser);
				signature.update(SerializationUtils.serialize(file));
				
				if(signature.verify(signatureFile)) {
					System.out.println("FILE");
					System.out.println("Subject: " + file.getSubject());
					System.out.println("Body: " + file.getBody());
					System.out.println("Firma correcta.");
				} else
					System.err.println("Firma incorrecta. Posible archivo modificado!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateExpiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateNotYetValidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			System.err.println("Introduce el nombre del fichero como argumento.");
	}

}
