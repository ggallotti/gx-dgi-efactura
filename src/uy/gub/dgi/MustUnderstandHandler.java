package uy.gub.dgi;

import java.util.Iterator;


import org.apache.axiom.soap.SOAPHeader;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;

public class MustUnderstandHandler extends AbstractHandler {

	public InvocationResponse invoke(MessageContext msgContext)
			throws AxisFault {		
		SOAPHeader header = msgContext.getEnvelope().getHeader();
		if( header != null )
		{
		    Iterator<?> blocks = header.examineAllHeaderBlocks();
		    while( blocks.hasNext() )
		    {
		        SOAPHeaderBlock block = (SOAPHeaderBlock)blocks.next();
		        //if( block )
		            block.setProcessed();
		    }
		}
		return InvocationResponse.CONTINUE;
	}
}