package apps;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import controllers.FileController;
import models.File;

public class GenerateFile {
	
	public static void main(String[] args) {
		FileController fileCtrl = new FileController(new File());
		fileCtrl.readAll();
		// Introducing FileOutputStream & ObjectOutputStream
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileCtrl.getFile().getSubject().toLowerCase()+".dat"))) {
			oos.writeObject(fileCtrl.getFile());
			System.out.println("Fichero generado como " + fileCtrl.getFile().getSubject()+ ".dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
