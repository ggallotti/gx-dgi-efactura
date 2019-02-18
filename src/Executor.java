import uy.gub.dgi.Utilities;
import uy.gub.dgi.gx.DGIEfactura;


public class Executor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String XMLpath = "C:\\axis201\\DATOS\\XML_Sobre_Firmado.xml";
		String XMLpath = "E:\\temp\\dgi\\211492540010.xml";
		String content = "";
		DGIEfactura client = null;
		
		XMLpath = Utilities.getRealPath(XMLpath);
		content = readFileContent(XMLpath);

		
		
		client = new DGIEfactura();
		client.setkeyStoreFile("E:\\temp\\dgi\\RUC211492540010.jks");
		client.setkeyStorePassword("toryal341");
		client.setPrivateKeyPassword("toryal341");
		client.setPrivateKeyAlias("le-5ac521d7-6b29-46bf-8232-6ee4e7d994d5");
		client.setKeyStoreType("jks");
		client.setTimeout(10000);
		System.out.println(client.EFACRECEPCIONREPORTE(content,"https://efactura.dgi.gub.uy:6443/ePrueba/ws_eprueba"));
		//System.out.println(client.EFACRECEPCIONREPORTE(content,"http://localhost:8888/efactura"));
		//System.out.println(client.getErrorDsc());
	
		
		XMLpath = "E:\\temp\\dgi\\212093440010.xml";
		XMLpath = Utilities.getRealPath(XMLpath);
		content = readFileContent(XMLpath);

		
		client = new DGIEfactura();
		client.setkeyStoreFile("E:\\temp\\dgi\\RUC212093440010.jks");
		client.setkeyStorePassword("Proinfo824");
		client.setPrivateKeyPassword("Proinfo824");
		client.setPrivateKeyAlias("le-8f1a0344-54dc-4ae7-b117-ffb28a4011ca");
		client.setKeyStoreType("jks");
		client.setTimeout(10000);
		//http://200.40.33.146:6131/ePrueba/ws_eprueba
		
		//System.out.println("RESPUESTA: " + client.EFACRECEPCIONREPORTE(content,"http://200.40.33.146:6131/ePrueba/ws_eprueba"));
		
		//System.out.println("RESPUESTA: " + client.EFACRECEPCIONREPORTE(content,"http://efactura.dgi.gub.uy/ePrueba/ws_eprueba"));
		System.out.println("RESPUESTA: " + client.EFACRECEPCIONREPORTE(content,"https://efactura.dgi.gub.uy:6443/ePrueba/ws_eprueba"));
		//System.out.println("RESPUESTA: " + client.EFACRECEPCIONREPORTE(content,"http://localhost:8888/ePrueba/ws_eprueba"));
	//	System.out.println(client.EFACRECEPCIONREPORTE(content,"http://localhost:8888/efactura"));
		System.out.println(client.getErrorDsc());
		
	}

	protected static String readFileContent(String XMLpath) {
		String content = "";
		try {
			String cannPath = (new java.io.File(XMLpath)).getCanonicalPath();
			java.io.FileReader fr = new java.io.FileReader(cannPath);
			java.io.File f = new java.io.File(cannPath);
			java.io.FileInputStream fis = new java.io.FileInputStream(f);
			byte[] byteArray = new byte[(int)f.length()];
			int offset = 0;
			int numRead = 0;
			while ((offset < byteArray.length) && ((numRead = fis.read(byteArray, offset, byteArray.length-offset)) >= 0) ) {            
				offset += numRead;
			}
			//content = new String(byteArray,"Cp1252"); //  fr.getEncoding()
			content = new String(byteArray); //  fr.getEncoding()
			fis.close();
			fr.close();
		}
		catch (java.io.IOException e)
		{
		
		}
		return content;
	}

}
