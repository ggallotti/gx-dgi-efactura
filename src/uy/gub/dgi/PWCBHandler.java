package uy.gub.dgi;

import java.io.IOException;
import java.util.Hashtable;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class PWCBHandler implements CallbackHandler {

	public static Hashtable info = new Hashtable<String, String>(); // WA for
																	// passing
																	// password
																	// from
																	// DGIEfactura.
																	// EGIEfactura
																	// cannot be
																	// static.

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			String id = pwcb.getIdentifier();
			String password = (String) info.get(id);

			if (password != null /* || "service".equals(id) */) {
				System.out.println("Setting User-Password: " + id + " - " +  password);
				pwcb.setPassword(password);
			}else
			{
				System.err.println("Password not found for Id: " + id);
			}
		}
	}

}