package uy.gub.dgi;

import java.security.cert.X509Certificate;

import org.apache.rampart.handler.WSDoAllReceiver;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.RequestData;

@SuppressWarnings("deprecation")
class MyWSDoAllReceiver extends WSDoAllReceiver {

	protected boolean verifyTrust(X509Certificate[] arg0, RequestData arg1)
			throws WSSecurityException {
		// return super.verifyTrust(arg0, arg1);
		return true;
	}
}
