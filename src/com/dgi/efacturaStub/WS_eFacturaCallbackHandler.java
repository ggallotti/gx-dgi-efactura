
/**
 * WS_eFacturaCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.dgi.efacturaStub;

    /**
     *  WS_eFacturaCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WS_eFacturaCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WS_eFacturaCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WS_eFacturaCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for eFACRECEPCIONREPORTE method
            * override this method for handling normal response from eFACRECEPCIONREPORTE operation
            */
           public void receiveResulteFACRECEPCIONREPORTE(
                    com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONREPORTEResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eFACRECEPCIONREPORTE operation
           */
            public void receiveErroreFACRECEPCIONREPORTE(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eFACCONSULTARESTADOENVIO method
            * override this method for handling normal response from eFACCONSULTARESTADOENVIO operation
            */
           public void receiveResulteFACCONSULTARESTADOENVIO(
                    com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACCONSULTARESTADOENVIOResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eFACCONSULTARESTADOENVIO operation
           */
            public void receiveErroreFACCONSULTARESTADOENVIO(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eFACRECEPCIONSOBRE method
            * override this method for handling normal response from eFACRECEPCIONSOBRE operation
            */
           public void receiveResulteFACRECEPCIONSOBRE(
                    com.dgi.efacturaStub.WS_eFacturaStub.WS_eFacturaEFACRECEPCIONSOBREResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eFACRECEPCIONSOBRE operation
           */
            public void receiveErroreFACRECEPCIONSOBRE(java.lang.Exception e) {
            }
                


    }
    