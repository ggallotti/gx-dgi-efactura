package uy.gub.dgi.gx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.PolicyInclude;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FilenameUtils;
import org.apache.rampart.handler.WSSHandlerConstants;

import org.apache.rampart.policy.model.CryptoConfig;
import org.apache.rampart.policy.model.RampartConfig;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;

import com.dgi.efacturaStub.WS_eFacturaStub;
import com.dgi.efacturaStub.WS_eFacturaStub.Data;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACCONSULTARESTADOENVIO;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACCONSULTARESTADOENVIOResponse;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONREPORTE;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONREPORTEResponse;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONSOBRE;
import com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONSOBREResponse;

import uy.gub.dgi.PWCBHandler;
import uy.gub.dgi.Utilities;

public class DGIEfactura {
	enum serviceDGIType {
		EFACRECEPCIONSOBRE, EFACRECEPCIONREPORTE, EFACCONSULTARESTADOENVIO
	}

	/**
	 * @param args
	 */
	private static ConfigurationContext _myConfigContext;	

	private String keyStoreType = "";
	private String keyStoreFile = "";
	private String keyStorePassword = "";
	private String privateKeyPassword = "";
	private String privateKeyAlias = "";
	private Integer errorCode = 0;
	private String errorDsc = "";
	private Properties cryptoProperties;
	private long timeout = 0;
	private boolean _dirty;
	private String _lastWsAddress = "";

	public DGIEfactura() {
		_dirty = true;
	}

	public String getKeyStoreType() {
		return keyStoreType;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public String getkeyStoreFile() {
		return keyStoreFile;
	}

	public void setkeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getkeyStorePassword() {
		return keyStorePassword;
	}

	public void setkeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getPrivateKeyPassword() {
		return privateKeyPassword;
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
	}

	public String getPrivateKeyAlias() {
		return privateKeyAlias;
	}

	public void setPrivateKeyAlias(String privateKeyAlias) {
		this.privateKeyAlias = privateKeyAlias;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long milliseconds) {
		timeout = milliseconds;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorDsc() {
		return errorDsc;
	}

	private void init() {
		errorCode = 1;
		errorDsc = "Error desconocido";

		
		cryptoProperties = new Properties();
		cryptoProperties.setProperty("org.apache.ws.security.crypto.provider",
				"org.apache.ws.security.components.crypto.Merlin");
		cryptoProperties.setProperty(
				"org.apache.ws.security.crypto.merlin.keystore.type", "jks");
		if (!getPrivateKeyAlias().equals(""))
			cryptoProperties.setProperty(
					"org.apache.ws.security.crypto.merlin.keystore.alias",
					getPrivateKeyAlias());
		if (!getkeyStorePassword().equals(""))
			cryptoProperties.setProperty(
					"org.apache.ws.security.crypto.merlin.keystore.password",
					getkeyStorePassword());

		cryptoProperties.setProperty(
				"org.apache.ws.security.crypto.merlin.file", getkeyStoreFile());
		System.out.println("Init CryptoProperties : " + getkeyStoreFile());
		PWCBHandler.info.put(getPrivateKeyAlias(), getkeyStorePassword());
	}

	public String EFACRECEPCIONSOBRE(String xml, String wsAddress) {
		init();
		return ExecuteDGIWS(xml, wsAddress.trim(), serviceDGIType.EFACRECEPCIONSOBRE);
	}

	public String EFACRECEPCIONREPORTE(String xml, String wsAddress) {
		init();
		return ExecuteDGIWS(xml, wsAddress.trim(), serviceDGIType.EFACRECEPCIONREPORTE);
	}

	public String EFACCONSULTARESTADOENVIO(String xml, String wsAddress) {
		init();
		return ExecuteDGIWS(xml, wsAddress.trim(),
				serviceDGIType.EFACCONSULTARESTADOENVIO);
	}

	private String ExecuteDGIWS(String xml, String wsAddress,
			serviceDGIType serv) {
		if (!wsAddress.trim().equals("")) {

			Data d = new Data();
			d.setXmlData(xml);

			try {
				initialize(wsAddress);

				//_options.setProperty("cyrpto_props", cryptoProperties);
				//_stub._getServiceClient().setOptions(_options);

				String result = "";
				if (serv == serviceDGIType.EFACRECEPCIONSOBRE)
					result = executeEFACRECEPCIONSOBRE(wsAddress, d);
				else if (serv == serviceDGIType.EFACRECEPCIONREPORTE)
					result = executeEFACRECEPCIONREPORTE(wsAddress, d);
				else
					result = executeEFACCONSULTARESTADOENVIO(wsAddress, d);

				errorCode = 0;
				errorDsc = "";

				return result;
			} catch (FileNotFoundException e) {
				errorCode = 1;
				errorDsc = "No se encontró el archivo security.properties en la ruta:"
						+ Utilities.getContextPath()
						+ "WEB-INF\\classes\\security.properties";
			} catch (AxisFault e) {
				errorCode = 1;
				errorDsc = "AxisFault -";
				errorDsc += addErrMessage(e.getMessage());
				errorDsc += addErrMessage(e.getReason());
				errorDsc += addErrMessage(e.getFaultAction());
				e.printStackTrace();
				/*while (tw != null){
					errorDsc += addErrMessage(e.getMessage());
					tw = tw.getCause();
				}*/

			} catch (RemoteException e) {
				errorCode = 1;
				errorDsc = "Excepción en la comunicación -"
						+ addErrMessage(e.getMessage());
			} catch (java.net.SocketTimeoutException e) {
				errorCode = 1;
				errorDsc = "Read timeout. Setear un mayor timeout o contactar el proveedor del servicio. - "
						+ addErrMessage(e.getMessage());
			} catch (Exception e) {
				errorCode = 1;
				errorDsc = "Error desconocido - "
						+ addErrMessage(e.getMessage());
			}
		} else {
			errorCode = 1;
			errorDsc = "WebService Endpoint address no puede ser vacio";
		}
		return "";
	}

	private void initialize(String wsAddress) throws AxisFault,
			FileNotFoundException, IOException {

		if (_dirty || !_lastWsAddress.equals(wsAddress)) {
			if (_myConfigContext == null) {
				String path = Utilities.getContextPath() + "repository\\";
				path = FilenameUtils.separatorsToSystem(path);
				_myConfigContext = ConfigurationContextFactory
						.createConfigurationContextFromFileSystem(
								path,
								Utilities.getRealPath("conf\\axis2.xml"));
			}
			
			RampartConfig rampartConfig = new RampartConfig();
			
			Properties merlinProp = new Properties();
			merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
			merlinProp.put("org.apache.ws.security.crypto.merlin.file", getkeyStoreFile());
			merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", getkeyStorePassword());
			merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.alias", getPrivateKeyAlias());
						
			_stub = new WS_eFacturaStub(_myConfigContext, wsAddress );
			_stub._getServiceClient().engageModule("rampart");
						
			CryptoConfig sigCryptoConfig = new CryptoConfig();
			sigCryptoConfig.setProvider("org.apache.ws.security.components.crypto.Merlin");
			sigCryptoConfig.setProp(merlinProp);
			
			rampartConfig.setUser(getPrivateKeyAlias());			
			rampartConfig.setUserCertAlias(getPrivateKeyAlias());
			//rampartConfig.setEncryptionUser(getPrivateKeyAlias());
			
			rampartConfig.setPwCbClass("uy.gub.dgi.PWCBHandler");
				
			rampartConfig.setSigCryptoConfig(sigCryptoConfig);
			rampartConfig.setTimestampTTL("300");
									
			StAXOMBuilder builder;
			Options options = _stub._getServiceClient().getOptions();
			
			try {
				builder = new StAXOMBuilder(Utilities.getRealPath("conf\\policy.xml"));
				Policy policy = PolicyEngine.getPolicy(builder.getDocumentElement());										
				policy.addAssertion(rampartConfig);
				options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, policy);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			_lastWsAddress = wsAddress;
											
			HttpMethodParams methodParams = new HttpMethodParams();
			DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(
					0, false);
			methodParams.setParameter(HttpMethodParams.RETRY_HANDLER,
					retryHandler);
			options.setProperty(HTTPConstants.HTTP_METHOD_PARAMS, methodParams);	
		
										
			options.setTo(new EndpointReference(wsAddress));
		
			
			if (timeout > 0)
				options.setTimeOutInMilliSeconds(timeout);
			else
				options.setTimeOutInMilliSeconds(80000);
			
			_dirty = false;
		}
	}

	private String addErrMessage(String message) {
		if (message != null)
			return message + " - ";
		return "";
	}

	private WS_eFacturaStub _stub;

	private String executeEFACCONSULTARESTADOENVIO(String wsAddress, Data d)
			throws AxisFault, RemoteException {
		WS_eFacturaEFACCONSULTARESTADOENVIO sobre = new WS_eFacturaEFACCONSULTARESTADOENVIO();

		sobre.setDatain(d);

		WS_eFacturaEFACCONSULTARESTADOENVIOResponse response = _stub
				.eFACCONSULTARESTADOENVIO(sobre);

		return response.getDataout().getXmlData();
	}

	private String executeEFACRECEPCIONREPORTE(String wsAddress, Data d)
			throws AxisFault, RemoteException {

		WS_eFacturaEFACRECEPCIONREPORTE sobre = new WS_eFacturaEFACRECEPCIONREPORTE();

		sobre.setDatain(d);

		WS_eFacturaEFACRECEPCIONREPORTEResponse response = _stub
				.eFACRECEPCIONREPORTE(sobre);

		return response.getDataout().getXmlData();
	}

	private String executeEFACRECEPCIONSOBRE(String wsAddress, Data d)
			throws AxisFault, RemoteException {
		
		WS_eFacturaEFACRECEPCIONSOBRE sobre = new WS_eFacturaEFACRECEPCIONSOBRE();

		sobre.setDatain(d);

		WS_eFacturaEFACRECEPCIONSOBREResponse response = _stub
				.eFACRECEPCIONSOBRE(sobre);

		String val = response.getDataout().getXmlData();
		return val;
	}

	@Override
	public void finalize() throws Throwable {
		try {
			_stub._getServiceClient().disengageModule("rampart");
			_stub._getServiceClient().cleanupTransport();
			_stub._setServiceClient(null);
			cryptoProperties.clear();
			_myConfigContext.shutdownModulesAndServices(); //ADDED GG 17/04/2015
			_myConfigContext.cleanupContexts();
			_myConfigContext.terminate();
			_myConfigContext = null;
			//_stub.cleanup();

		} catch (Throwable t) {
			throw t;
		} finally {
			super.finalize();
		}

	}

	
}
