/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author epasten
 */
@WebService(serviceName = "wsfdoc")
public class wsfdoc 
{   
    //--------------------------------------------------------------------------------------------------------------------
    //-----------------------------  RUTAS DONDE ESTARAN LOS DOCUMENTOS GENERADOS POR XDOC --------------------------
    //HTP
    //File RutaXdocOK_HTP = new File("\\\\vxdoc\\PDF-DTE\\HTP\\");  //PRODUCCION
    File RutaXdocOK_HTP = new File("\\\\vxdoc\\PDF-DTE\\78192550-0\\"); //QA
    
    //File RutaXdocBAD_HTP = new File("\\\\vxdoc\\errTXT-HTP\\");  //PRODUCCION
    File RutaXdocBAD_HTP = new File("\\\\vxdoc\\errTXTL-HTP-QA\\");  //QA
    
        
    //TAC
    //File RutaXdocOK_TAC = new File("\\\\vxdoc\\PDF-DTE\\TAC\\"); //PRODUCCION
    File RutaXdocOK_TAC = new File("\\\\vxdoc\\PDF-DTE\\78192540-3\\"); //QA   
    
    //File RutaXdocBAD_TAC = new File("\\\\vxdoc\\errTXT-TAC\\"); //PRODUCCION
    File RutaXdocBAD_TAC = new File("\\\\vxdoc\\errTXT-TAC-QA\\"); //QA
    
    
    //HHR
    //File RutaXdocOK_HHR = new File("\\\\vxdoc\\pdf-dte\\HHR\\"); //PRODUCCION
    File RutaXdocOK_HHR = new File("\\\\vxdoc\\pdf-dte\\76281470-6\\"); //QA
               
    //File RutaXdocBAD_HHR = new File("\\\\vxdoc\\errTXT-HHR-PROD\\"); //PRODUCCION
    File RutaXdocBAD_HHR = new File("\\\\vxdoc\\errTXT-HHR-QA\\"); //QA
   
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------
           
    /**
     * Web service operation
     * @param login
     * @param factCabecera
     * @param factLineas
     * @return 
     * @throws java.io.IOException 
     */
    @WebMethod(operationName = "Procesar_doc_Request")
    public Status Procesar_doc_Request
        (   @WebParam(name = "login") Login login, 
            @WebParam(name = "factCabecera") FactCabecera factCabecera, 
            @WebParam(name = "factReferencia") FactReferencia factReferencia,
            @WebParam(name = "detalle") Detalle detalle
        ) throws IOException 
    {     
        
       //<editor-fold defaultstate="collapsed" desc="Lectura y escritura de variables de entrada"> 
       System.out.println("---- LOGIN ----");  
       System.out.println(login.usuario.toString() +" "+ login.clave.toString());
       
       System.out.println("---- DOCUMENTO ----"); 
       System.out.println(factCabecera.TipoDTE.toString()); 
       System.out.println(factCabecera.Folio.toString());
       System.out.println(factCabecera.FchEmis.toString());
       System.out.println(factCabecera.FchVenc);
       System.out.println(factCabecera.IdVendedor);
       System.out.println(factCabecera.OrdenDeCompra); //se esta inicializando vacio
       System.out.println(factCabecera.FechaOC.toString());
       System.out.println(factCabecera.Tag802);
       System.out.println(factCabecera.NumReserva.toString());
       System.out.println(factCabecera.TermPagoDias.toString());
       System.out.println(factCabecera.TermPagoGlosa.toString());
       System.out.println(factCabecera.IndServicio.toString());
       System.out.println(factCabecera.Impresora.toString());       
       
       System.out.println("---- EMISOR ----"); //EMISOR
       System.out.println(factCabecera.RUTEmisor.toString());
       System.out.println(factCabecera.RznSoc.toString());
       System.out.println(factCabecera.GiroEmis.toString());
       System.out.println(factCabecera.DirOrigen.toString());
       System.out.println(factCabecera.CiudadOrigen.toString());       
       System.out.println(factCabecera.Acteco.toString());
       
       System.out.println("---- RECEPTOR ----"); //RECEPTOR
       System.out.println(factCabecera.RUTRecep.toString());
       System.out.println(factCabecera.cdgInRecep.toString());
       System.out.println(factCabecera.RznSocRecep.toString());
       System.out.println(factCabecera.GiroRecep.toString());
       System.out.println(factCabecera.DirRecep.toString()); 
       System.out.println(factCabecera.CiudadRecep.toString());
       System.out.println(factCabecera.Contacto.toString());
       System.out.println(factCabecera.CorreoRecep.toString());
       System.out.println(factCabecera.DirPostal.toString());
       
       System.out.println("---- MONTOS ----"); //MONTOS
       System.out.println(factCabecera.mntNeto.toString());
       System.out.println(factCabecera.MntExe.toString());
       System.out.println(factCabecera.TasaIVA.toString());
       System.out.println(factCabecera.IVA.toString());      
       System.out.println(factCabecera.MntTotal.toString());
       
       System.out.println("---- MONEDA MONTOS ----"); 
       System.out.println(factCabecera.TpoMoneda.toString());
       System.out.println(factCabecera.TpoCambio.toString());
       System.out.println(factCabecera.MntExeOtrMnda.toString());
       System.out.println(factCabecera.MntRetencion.toString());
       System.out.println(factCabecera.MntTotOtrMnda.toString());
       
       System.out.println("---- REFERENCIAS ----"); 
       System.out.println(factReferencia.TipoDocRef.toString());
       System.out.println(factReferencia.FolioRef.toString());
       System.out.println(factReferencia.FechaDocRef.toString());
       System.out.println(factReferencia.IDRazon.toString());
       System.out.println(factReferencia.RazonDesc.toString());
       //</editor-fold>//Esto es para validar en glassfish si los paramentros los esta leyendo el metodo 
        
             
       Status status = new Status();      
       String nombreArchivo = "";
       try
       {
        /*Aca se inicializan las variables a utilizar para rescatar la informacion de los parametros.*/
        //<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
        int a = 0;
        String fin = "";
        String TipoDTE = ""; 
        String RznSoc = "";
        String RUTEmisor = "";
        String DirOrigen = "";
        String CmnaOrigen = "";
        String CiudadOrigen = "";
        String Acteco = "";
        String GiroEmis = "";
        String cdgInRecep = "";
        String RznSocRecep = "";
        String RUTRecep = "";
        String GiroRecep = "";
        String CorreoRecep = "";
        String CiudadRecep = "";
        String CmnaRecep = "";
        String DirRecep = "";
        String Contacto = "";
        String TermPagoGlosa = "";
        String TermPagoDias = "";
        String FchEmis = "";
        String FchVenc = "";
        String Folio = "";
        String IVA = "";
        String MntTotal = "";
        String TasaIVA = ""; 
        String mntNeto = "";                                      
        String TpoCambio = "";
        String MntExeOtrMnda = "";
        String MntTotOtrMnda = "";        
        String IndServicio = "";
        String DirPostal = "";
        String TpoMoneda = "";
        String MntExe = "";        
        String IdVendedor = "";
        String MntRetencion = "";
        String TipoDocRef = "";
        String FolioRef = "";
        String FechaDocRef = "";
        String IDRazon = "";
        String RazonDesc = "";      
        String NumReserva = "";
        String Impresora = "";
        String Pasaporte = "";
        String OrdenDeCompra = "";
        String Tag802 = "";
        String FechaOC = "";
        String var1 = "";
        String Hotel = "";
        String Observaciones = "";
        //</editor-fold>//Declaracion de variables
                 
        /*------------------------ Validaciones de parametros -------------------------------*/ 
        
        /*DOCUMENTO*/
        //<editor-fold defaultstate="collapsed" desc="TipoDTE">
        if(factCabecera.TipoDTE.equals(null) || factCabecera.TipoDTE.equals(""))
        {
            TipoDTE = "Err";
        }
        else
        {            
            if(factCabecera.TipoDTE.length() > 3)
            {
                TipoDTE = factCabecera.TipoDTE.substring(0,3); 
            }
            else 
            {
                TipoDTE = factCabecera.TipoDTE;
            }
        }
        //</editor-fold>//Campo obligatorio      
        // <editor-fold defaultstate="collapsed" desc="FchEmis">
        if(factCabecera.FchEmis.equals(null) || factCabecera.FchEmis.equals(""))
        {
            FchEmis = "Err";
        }
        else
        {                   
            if(factCabecera.FchEmis.length() > 10)
            {
                FchEmis = factCabecera.FchEmis.substring(0,10);
            }
            else
            {
                FchEmis = factCabecera.FchEmis;
            }
        }
        // </editor-fold>//Campo obligatorio 
        // <editor-fold defaultstate="collapsed" desc="FchVenc">
        if(factCabecera.FchVenc.equals(null) || factCabecera.FchVenc.equals(""))
        {
            FchVenc = "Err";
        }
        else
        {
            if(factCabecera.FchVenc.length() > 10)
            { 
                FchVenc = factCabecera.FchVenc.substring(0,10);
            }
            else
            {
                FchVenc = factCabecera.FchVenc;
            }
        }
        
        // </editor-fold>//Campo obligatorio
        // <editor-fold defaultstate="collapsed" desc="Folio">
        if(factCabecera.Folio.equals(null) || factCabecera.Folio.equals(""))
        {
            Folio = "Err";
        }
        else
        {
            if(factCabecera.Folio.length() > 10)
            {
                Folio = factCabecera.Folio.substring(0,10);
            }
            else
            {
                Folio = factCabecera.Folio;
            }
        }
        
        // </editor-fold>//Campo obligatorio 
        IndServicio = "4";
        // <editor-fold defaultstate="collapsed" desc="IdVendedor">
        if(factCabecera.IdVendedor.length() > 20)
        {
            OrdenDeCompra = factCabecera.IdVendedor.substring(0, 20);
        }
        else
        {
            OrdenDeCompra = factCabecera.IdVendedor;
        }
        // </editor-fold>//Campo obligatorio/opcional        
        // <editor-fold defaultstate="collapsed" desc="OrdenDeCompra">
        if(factCabecera.OrdenDeCompra.length() > 20)
        {
            OrdenDeCompra = factCabecera.OrdenDeCompra.substring(0,20);
        }
        else
        {
            OrdenDeCompra = factCabecera.OrdenDeCompra;
        }
        // </editor-fold>//Campo obligatorio/opcional        
        // <editor-fold defaultstate="collapsed" desc="Tag802">
        if(factCabecera.Tag802.length() > 20)
        {
            Tag802 = factCabecera.Tag802.substring(0,20);
        }
        else
        {
            Tag802 = factCabecera.Tag802;
        }
        // </editor-fold>//Campo obligatorio/opcional
        
        FechaOC = factCabecera.FechaOC;

        
        /*EMISOR*/
        //<editor-fold defaultstate="collapsed" desc="RUTEmisor">
        if(factCabecera.RUTEmisor.equals(null) || factCabecera.RUTEmisor.equals(""))
        {
            RUTEmisor = "Err";            
        }
        else
        {
            if(factCabecera.RUTEmisor.length() > 10)
            {
                RUTEmisor = factCabecera.RUTEmisor.substring(0,10);
            }
            else
            {
                RUTEmisor = factCabecera.RUTEmisor;
            }
        }
        // </editor-fold>//Campo obligatorio    
       
        if(RUTEmisor.equals("78192550-0")) //HTP
        {
            RznSoc = "HOTEL TERMAS DE PUYEHUE LTDA";
            DirOrigen = "RUTA 215 KM 76";
            CmnaOrigen = "VITACURA";
            CiudadOrigen = "SANTIAGO";
            Acteco = "521112";
            GiroEmis = "HOTELES ALMACENES Y RESTAURANT";
        } 
        else if (RUTEmisor.equals("78192540-3")) //TAC
        {
            RznSoc = "TURISMO Y CABANAS AGUAS CALIENTES LTDA";
            DirOrigen = "CAMINO ANTILANCA KM 4 PARQUE NACIONAL PUYEHUE";
            CmnaOrigen = "PUYEHUE";
            CiudadOrigen = "OSORNO";
            Acteco = "551010";
            GiroEmis = "ADMINISTRACION DE CABAÑAS, ALMACEN, RESTAURANT";
        }
        else if (RUTEmisor.equals("76281470-6")) //HHR
        {
            RznSoc = "HOTEL HANGAROA LIMITADA";
            DirOrigen = "AV PNT SN";
            CmnaOrigen = "ISLA DE PASCUA";
            CiudadOrigen = "VALPARAISO";
            Acteco = "551010";
            GiroEmis = "HOTELES ALMACENES RESTAURANT Y SERVICIOS";
        }
                               
        /*RECEPTOR*/
        if(TipoDTE.equals("39") || TipoDTE.equals("41"))
        {
            // <editor-fold defaultstate="collapsed" desc="cdgInRecep">
        if(factCabecera.cdgInRecep.equals(null) || factCabecera.cdgInRecep.equals(""))
        {
            cdgInRecep = "";            
        }
        else
        {
            if(factCabecera.cdgInRecep.length() > 20)
            {
                cdgInRecep = factCabecera.cdgInRecep.substring(0, 20).replaceAll("(\n|\r)", "");
            }
            else
            {
                cdgInRecep = factCabecera.cdgInRecep.replaceAll("(\n|\r)", "");
            }            
        }
        // </editor-fold>//OPCIONAL                       
            // <editor-fold defaultstate="collapsed" desc="RznSocRecep">
        if(factCabecera.RznSocRecep.equals(null) || factCabecera.RznSocRecep.equals(""))
        {
            RznSocRecep = "";
        }
        else
        {            
            if(factCabecera.RznSocRecep.length() > 65)
            {
                RznSocRecep = factCabecera.RznSocRecep.substring(0, 65).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
            else
            {
                RznSocRecep = factCabecera.RznSocRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//OPCIONAL        
            // <editor-fold defaultstate="collapsed" desc="RUTRecep">
        if(TipoDTE.equals("110") || TipoDTE.equals("112"))
        {
            RUTRecep = "55555555-5";             
        }
        else if(TipoDTE.equals("39") || TipoDTE.equals("41"))
        {
            RUTRecep = "66666666-6";            
        }
        else if(factCabecera.RUTRecep.equals(null) || factCabecera.RUTRecep.equals(""))
        {
            RUTRecep = "";
        }
        else
        {
            if(factCabecera.RUTRecep.length() > 10)
            {
                RUTRecep = factCabecera.RUTRecep.substring(0,10);
            }
            else
            {
                RUTRecep = factCabecera.RUTRecep;
            }
        }
        // </editor-fold>//OPCIONAL                
            // <editor-fold defaultstate="collapsed" desc="GiroRecep">
        if(factCabecera.GiroRecep.equals(null) || factCabecera.GiroRecep.equals(""))
        {
            GiroRecep = "";
        }
        else
        {
            if(factCabecera.GiroRecep.length() > 40)
            {
                GiroRecep = factCabecera.GiroRecep.substring(0, 40).replace('ñ', 'n').replace('Ñ', 'N');    
            }
            else
            {
                GiroRecep = factCabecera.GiroRecep.replace('ñ', 'n').replace('Ñ', 'N');
            }  
        }
        // </editor-fold>//OPCIONAL   
            // <editor-fold defaultstate="collapsed" desc="DirRecep">
        if(factCabecera.DirRecep.equals(null) || factCabecera.DirRecep.equals(""))
        {
            DirRecep = "";        
        }
        else
        {
            if(factCabecera.DirRecep.length() > 60)
            {
                DirRecep = factCabecera.DirRecep.substring(0, 60).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");            
            }
            else
            {
                DirRecep = factCabecera.DirRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//OPCIONAL
            // <editor-fold defaultstate="collapsed" desc="DirPostal">
        if(factCabecera.DirPostal.length() > 25)
        {
            DirPostal = factCabecera.DirPostal.substring(0, 25);
        }
        else
        {
            DirPostal = factCabecera.DirPostal;
        }
        // </editor-fold>//OPCIONAL
            // <editor-fold defaultstate="collapsed" desc="CiudadRecep">
        if(factCabecera.CiudadRecep.equals(null) || factCabecera.CiudadRecep.equals(""))
        {
            CiudadRecep = "";
        }
        else
        {
            if(factCabecera.CiudadRecep.length() > 20)
            {
                CiudadRecep = factCabecera.CiudadRecep.substring(0,20).replace('ñ', 'n').replace('Ñ', 'N');
            }
            else
            {
                CiudadRecep = factCabecera.CiudadRecep.replace('ñ', 'n').replace('Ñ', 'N');
            }
        }
        // </editor-fold>//OPCIONAL        
            // <editor-fold defaultstate="collapsed" desc="CmnaRecep">
        if(factCabecera.CmnaRecep.equals(null) || factCabecera.CmnaRecep.equals(""))
        {
            CmnaRecep = "";
        }
        else
        {
            if(factCabecera.CmnaRecep.length() > 20)
            {
                CmnaRecep = factCabecera.CmnaRecep.substring(0,20).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
            else
            {
                CmnaRecep = factCabecera.CmnaRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//OPCIONAL        
            // <editor-fold defaultstate="collapsed" desc="Contacto">
        if(factCabecera.Contacto.equals(null) || factCabecera.Contacto.equals(""))
        {
            Contacto = "";
        }
        else
        {
            if(factCabecera.Contacto.length() > 65)
            {
                Contacto = factCabecera.Contacto.substring(0,65).replace('ñ', 'n').replace('Ñ', 'N');
            }
            else
            {
                Contacto = factCabecera.Contacto.replace('ñ', 'n').replace('Ñ', 'N');
            }
        }
        // </editor-fold>//OPCIONAL3
            // <editor-fold defaultstate="collapsed" desc="CorreoRecep">
        CorreoRecep = factCabecera.CorreoRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
        // </editor-fold>// OPCIONAL 
        }
        else
        {
            // <editor-fold defaultstate="collapsed" desc="cdgInRecep">
        if(factCabecera.cdgInRecep.equals(null) || factCabecera.cdgInRecep.equals(""))
        {
            cdgInRecep = "Err";            
        }
        else
        {
            if(factCabecera.cdgInRecep.length() > 20)
            {
                cdgInRecep = factCabecera.cdgInRecep.substring(0, 20).replaceAll("(\n|\r)", "");
            }
            else
            {
                cdgInRecep = factCabecera.cdgInRecep.replaceAll("(\n|\r)", "");
            }            
        }
        // </editor-fold>//Campo obligatorio                       
            // <editor-fold defaultstate="collapsed" desc="RznSocRecep">
        if(factCabecera.RznSocRecep.equals(null) || factCabecera.RznSocRecep.equals(""))
        {
            RznSocRecep = "Err";
        }
        else
        {            
            if(factCabecera.RznSocRecep.length() > 65)
            {
                RznSocRecep = factCabecera.RznSocRecep.substring(0, 65).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
            else
            {
                RznSocRecep = factCabecera.RznSocRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio        
            // <editor-fold defaultstate="collapsed" desc="RUTRecep">
        if(TipoDTE.equals("110") || TipoDTE.equals("112"))
        {
            RUTRecep = "55555555-5";             
        }
        else if(TipoDTE.equals("39"))
        {
            RUTRecep = "66666666-6";            
        }
        else if(factCabecera.RUTRecep.equals(null) || factCabecera.RUTRecep.equals(""))
        {
            RUTRecep = "Err";
        }
        else
        {
            if(factCabecera.RUTRecep.length() > 13)
            {
                RUTRecep = factCabecera.RUTRecep.substring(0,13).replaceAll("(\n|\r)", "");
            }
            else
            {
                RUTRecep = factCabecera.RUTRecep.replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio                
            // <editor-fold defaultstate="collapsed" desc="GiroRecep">
        if(factCabecera.GiroRecep.equals(null) || factCabecera.GiroRecep.equals(""))
        {
            GiroRecep = "Err";
        }
        else
        {
            if(factCabecera.GiroRecep.length() > 40)
            {
                GiroRecep = factCabecera.GiroRecep.substring(0, 40).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");    
            }
            else
            {
                GiroRecep = factCabecera.GiroRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }  
        }
        // </editor-fold>//Campo obligatorio   
            // <editor-fold defaultstate="collapsed" desc="DirRecep">
        if(factCabecera.DirRecep.equals(null) || factCabecera.DirRecep.equals(""))
        {
            DirRecep = "Err";        
        }
        else
        {
            if(factCabecera.DirRecep.length() > 60)
            {
                DirRecep = factCabecera.DirRecep.substring(0, 60).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");            
            }
            else
            {
                DirRecep = factCabecera.DirRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio
            // <editor-fold defaultstate="collapsed" desc="DirPostal">
        if(factCabecera.DirPostal.length() > 25)
        {
            DirPostal = factCabecera.DirPostal.substring(0, 25).replaceAll("(\n|\r)", "");
        }
        else
        {
            DirPostal = factCabecera.DirPostal.replaceAll("(\n|\r)", "");
        }
        // </editor-fold>//OPCIONAL
            // <editor-fold defaultstate="collapsed" desc="CiudadRecep">
        if(factCabecera.CiudadRecep.equals(null) || factCabecera.CiudadRecep.equals(""))
        {
            CiudadRecep = "Err";
        }
        else
        {
            if(factCabecera.CiudadRecep.length() > 20)
            {
                CiudadRecep = factCabecera.CiudadRecep.substring(0,20).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
            else
            {
                CiudadRecep = factCabecera.CiudadRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio        
            // <editor-fold defaultstate="collapsed" desc="CmnaRecep">
        if(factCabecera.CmnaRecep.equals(null) || factCabecera.CmnaRecep.equals(""))
        {
            CmnaRecep = "Err";
        }
        else
        {
            if(factCabecera.CmnaRecep.length() > 20)
            {
                CmnaRecep = factCabecera.CmnaRecep.substring(0,20).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
            else
            {
                CmnaRecep = factCabecera.CmnaRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio        
            // <editor-fold defaultstate="collapsed" desc="Contacto">
        if(factCabecera.Contacto.equals(null) || factCabecera.Contacto.equals(""))
        {
            Contacto = "Err";
        }
        else
        {
            if(factCabecera.Contacto.length() > 65)
            {
                Contacto = factCabecera.Contacto.substring(0,65).replace('ñ', 'n').replace('Ñ', 'N');
            }
            else
            {
                Contacto = factCabecera.Contacto.replace('ñ', 'n').replace('Ñ', 'N');
            }
        }
        // </editor-fold>//Campo obligatorio
            // <editor-fold defaultstate="collapsed" desc="CorreoRecep">
        CorreoRecep = factCabecera.CorreoRecep.replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");
        // </editor-fold>// OPCIONAL 
        }
        
        /*MEDIO DE PAGO*/
        if(TipoDTE.equals("39") || TipoDTE.equals("41"))
        {
            // <editor-fold defaultstate="collapsed" desc="TermPagoGlosa">
        if(factCabecera.TermPagoGlosa.equals(null) || factCabecera.TermPagoGlosa.equals(""))
        {
            TermPagoGlosa = "";
        }
        else
        {
            if(factCabecera.TermPagoGlosa.length() > 65)
            {
                TermPagoGlosa = factCabecera.TermPagoGlosa.substring(0,65).replaceAll("(\n|\r)", "");
            }
            else
            {
                TermPagoGlosa = factCabecera.TermPagoGlosa.replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//OPCIONAL  
            // <editor-fold defaultstate="collapsed" desc="TermPagoDias">
        if(factCabecera.TermPagoDias.length() > 3)
        {
            TermPagoDias = factCabecera.TermPagoDias.substring(0,3).replaceAll("(\n|\r)", "");
        }
        else
        {
            TermPagoDias = factCabecera.TermPagoDias.replaceAll("(\n|\r)", "");
        }
        // </editor-fold>//OPCIONAL
        }
        else
        {
            // <editor-fold defaultstate="collapsed" desc="TermPagoGlosa">
        if(factCabecera.TermPagoGlosa.equals(null) || factCabecera.TermPagoGlosa.equals(""))
        {
            TermPagoGlosa = "Err";
        }
        else
        {
            if(factCabecera.TermPagoGlosa.length() > 65)
            {
                TermPagoGlosa = factCabecera.TermPagoGlosa.substring(0,65).replaceAll("(\n|\r)", "");
            }
            else
            {
                TermPagoGlosa = factCabecera.TermPagoGlosa.replaceAll("(\n|\r)", "");
            }
        }
        // </editor-fold>//Campo obligatorio  
            // <editor-fold defaultstate="collapsed" desc="TermPagoDias">
        if(factCabecera.TermPagoDias.length() > 3)
        {
            TermPagoDias = factCabecera.TermPagoDias.substring(0,3);
        }
        else
        {
            TermPagoDias = factCabecera.TermPagoDias;
        }
        // </editor-fold>//OPCIONAL
        }
                
        /*TOTALES*/
        
        if(TipoDTE.equals("33") || TipoDTE.equals("39"))
        {
            IVA = factCabecera.IVA.replace(',', '.');
            TasaIVA = factCabecera.TasaIVA.replace(',', '.'); 
            
            if(factCabecera.mntNeto.equals("0") || factCabecera.mntNeto.equals("") || factCabecera.mntNeto.equals(null)) 
            {
                 mntNeto = "SIN AFECTO";
            }
            else
            {
                mntNeto = factCabecera.mntNeto.replace(',', '.');
            }
        }
        else
        {
            IVA = factCabecera.IVA.replace(',', '.');
            TasaIVA = factCabecera.TasaIVA.replace(',', '.');
            mntNeto = factCabecera.mntNeto.replace(',', '.');
        }
        
        
        MntTotal = factCabecera.MntTotal.replace(',', '.');  

        
        if(TipoDTE.equals("110") || TipoDTE.equals("112") || TipoDTE.equals("34") || TipoDTE.equals("41"))
        {
            if(factCabecera.MntExe.equals(null) || factCabecera.MntExe.equals("") || factCabecera.MntExe.equals("0"))
            {            
                MntExe = MntTotal;
            }
            else
            {
                MntExe = factCabecera.MntExe.replace(',', '.');
            }
        }
        else
        {
            MntExe = factCabecera.MntExe.replace(',', '.');;
        }                
        
        
        MntRetencion = factCabecera.MntRetencion.replace(',', '.');                          
        TpoMoneda = factCabecera.TpoMoneda;
        
        
        if(TipoDTE.equals("110") || TipoDTE.equals("112"))
        {                        
            // <editor-fold defaultstate="collapsed" desc="TpoCambio">
            if(factCabecera.TpoCambio.equals(null) || factCabecera.TpoCambio.equals(""))
            {
                TpoCambio = "Err";
            }
            else
            {
                System.out.println("ENTRE AL TIPO DE CAMBIO");
                TpoCambio = factCabecera.TpoCambio.replace(',', '.');
                System.out.println("PASEEEEEE");
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE
            // <editor-fold defaultstate="collapsed" desc="MntExeOtrMnda">
            if(factCabecera.MntExeOtrMnda.equals(null) || factCabecera.MntExeOtrMnda.equals("") || factCabecera.MntExeOtrMnda.equals("0"))
            {
                MntExeOtrMnda = Integer.toString(Integer.parseInt(TpoCambio)* Integer.parseInt(MntTotal)).replace(',', '.'); //"Err";
            }
            else
            {
                MntExeOtrMnda = factCabecera.MntExeOtrMnda.replace(',', '.');
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE
            // <editor-fold defaultstate="collapsed" desc="MntTotOtrMnda">
            if(factCabecera.MntTotOtrMnda.equals(null) || factCabecera.MntTotOtrMnda.equals(""))
            {
                MntTotOtrMnda = Integer.toString(Integer.parseInt(TpoCambio)* Integer.parseInt(MntTotal)).replace(',', '.'); //"Err"; 
            }
            else
            {
                MntTotOtrMnda = factCabecera.MntTotOtrMnda.replace(',', '.'); 
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE
        }
        else
        {
            TpoCambio = "";
            MntExeOtrMnda = "";
            MntTotOtrMnda = ""; 
        }
                                        
        /*REFERENCIA*/ 
        if(TipoDTE.equals("112") || TipoDTE.equals("61"))
        {
            // <editor-fold defaultstate="collapsed" desc="TipoDocRef">
            if(factReferencia.TipoDocRef.equals(null) || factReferencia.TipoDocRef.equals(""))
            {
                TipoDocRef = "Err";
            }
            else
            {
                if(factReferencia.TipoDocRef.length() > 3) 
                {
                    TipoDocRef = factReferencia.TipoDocRef.substring(0, 3);
                }
                else
                {
                    TipoDocRef = factReferencia.TipoDocRef;
                }
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE            
            // <editor-fold defaultstate="collapsed" desc="FolioRef">
            if(factReferencia.FolioRef.equals(null) || factReferencia.FolioRef.equals(""))
            {
                FolioRef = "Err";
            }
            else
            {
                if(factReferencia.FolioRef.length() > 10)
                {
                    FolioRef = factReferencia.FolioRef.substring(0, 10);
                }
                else
                {
                    FolioRef = factReferencia.FolioRef;
                }
                
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE   
            // <editor-fold defaultstate="collapsed" desc="FechaDocRef">
            if(factReferencia.FechaDocRef.equals(null) || factReferencia.FechaDocRef.equals(""))
            {
                FechaDocRef = "Err";
            }
            else
            {
                if(factReferencia.FechaDocRef.length() > 10)
                {
                    FechaDocRef = factReferencia.FechaDocRef.substring(0, 10);
                }
                else
                {
                    FechaDocRef = factReferencia.FechaDocRef;
                }
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE 
            // <editor-fold defaultstate="collapsed" desc="IDRazon">
            if(factReferencia.IDRazon.equals(null) || factReferencia.IDRazon.equals(""))
            {
                IDRazon = "Err";
            }
            else
            {
                if(factReferencia.IDRazon.length() > 65)
                {
                    IDRazon = factReferencia.IDRazon.substring(0, 65);
                }
                else
                {
                    IDRazon = factReferencia.IDRazon;
                }
            }
            // </editor-fold>//Campo obligatorio si se cumple condicion de TIPO DTE 
            // <editor-fold defaultstate="collapsed" desc="RazonDesc">
            if(factReferencia.RazonDesc.length() > 65)
            {
                RazonDesc = factReferencia.RazonDesc.substring(0, 65).replaceAll("(\n|\r)", "");
            }
            else
            {
                RazonDesc = factReferencia.RazonDesc.replaceAll("(\n|\r)", "");
            }
            // </editor-fold>//OPCIONAL
        }
        else
        {
            TipoDocRef = "";
            FolioRef = "";
            FechaDocRef = "";
            IDRazon = "";
            RazonDesc = "";
         
        }
        
        /*PERSONALIZADO*/
        NumReserva = factCabecera.NumReserva.replaceAll("(\n|\r)", "");
        Impresora = factCabecera.Impresora;
        Pasaporte = factCabecera.Pasaporte.replaceAll("(\n|\r)", "");   
        
        if(factCabecera.Observaciones.length() > 200)
        {
            Observaciones = factCabecera.Observaciones.substring(0, 100).replace('Ñ', 'N').replace('ñ', 'n').replaceAll("(\n|\r)", "");
        }
        else
        {
            Observaciones = factCabecera.Observaciones.replace('Ñ', 'N').replace('ñ', 'n').replaceAll("(\n|\r)", "");
        }
        
        System.out.println("VALIDACION DE IMPRESORAS...");
        // <editor-fold defaultstate="collapsed" desc="VALIDACION IMPRESORAS">        
        if(RUTEmisor.equals("78192550-0")) //HTP
        {
            Hotel = "HTP";
            
            if(Impresora.contains("DESKTOP-V6TV2N1"))
            {
                Impresora = "1";
            }
            else if (Impresora.contains("HTPPC005"))
            {
                Impresora = "2";        
            }
            else if (Impresora.contains("ACTIVIDADES-HTP"))
            {
                Impresora = "3";        
            }
            else if (Impresora.contains("BARES-PC"))
            {
                Impresora = "4";        
            }
            else if (Impresora.contains("HTPTPVTRONCOS2"))
            {
                Impresora = "5";        
            }
            else if (Impresora.contains("HTPPC0006"))
            {
                Impresora = "6";        
            }
            else if (Impresora.contains("VWINHOTELMAIN"))
            {
                Impresora = "7";        
            }                         
            else if (Impresora.contains("RECEPCION3"))
            {
                Impresora = "8";        
            }
            else if (Impresora.contains("DESKTOP-NG8V699"))
            {
                Impresora = "8";        
            }
            else if (Impresora.contains("DESKTOP-NG9B89A"))
            {
                Impresora = "8";        
            }                        
        }
        else if (RUTEmisor.equals("78192540-3")) //TAC
        {
            Hotel = "TAC";
            
            if(Impresora.contains("DESKTOP-LROERD4")) //RECEPCION 1    
            {
                Impresora = "1";
            }
            else if (Impresora.contains("RECEPCION-TAC1")) //RECEPCION 2
            {
                Impresora = "2";        
            }
            else if (Impresora.contains("DESKTOP-F81S0DR")) //QUINCHO(COMANDAS)
            {
                Impresora = "3";        
            }
            else if (Impresora.contains("DESKTOP-VQG2S8O")) //QUINCHO TPV CAJA   
            {
                Impresora = "4";        
            }
            else if (Impresora.contains("BOUTIQUE-HP")) //CAFETERIA 1
            {
                Impresora = "5";        
            }
            else if (Impresora.contains("BOUTIQUE-TAC")) //CAFETERIA 2
            {
                Impresora = "6"; //10.10.19.235       
            }
            else if (Impresora.contains("TAC-TPV-0001")) //SPA
            {
                Impresora = "7";        
            }                         
            else if (Impresora.contains("TAC-MCAMPOS")) //CONTROL INTERNO
            {
                Impresora = "8";        
            }
            else if (Impresora.contains("QUIOSCOPAL-PC")) //KIOSCO
            {
                Impresora = "9";        
            } 
            else if (Impresora.contains("VWINHOTELMAIN")) //SERVIDOR
            {
                Impresora = "10";        
            } 
        }
        else if (RznSoc.contains("HANGAROA"))
        {
            Hotel = "HHR";
            
            if(Impresora.contains("VWINHOTEL-HHR")) //SERVIDOR 
            {
                Impresora = "2";
            }
            else if (Impresora.contains("HPMGUZMAN")) //VAIKOA
            {
                Impresora = "1";        
            }
            else if (Impresora.contains("HHRPC002")) //POERAVA
            {
                Impresora = "3";        
            }
            else if (Impresora.contains("HOTELHHR-HP")) //SPA
            {
                Impresora = "4";        
            }
            else if (Impresora.contains("HHRPC001")) //KALOA
            {
                Impresora = "5";        
            }
            else if (Impresora.contains("LAPTOP-CPJ621M0")) //RECEPCION
            {
                Impresora = "6";        
            }
            else if (Impresora.contains("LAPTOP-N8Q0IDA1")) //RECEPCION
            {
                Impresora = "7";        
            }                                                           
        }  
        // </editor-fold>//ACA SE LE ASIGNA UN NUMERO DE IMPRESION AL EQUIPO UTILIZADO
        
        float Propina = 0;
        float Descuentos = 0;
        a = detalle.Linea.size();/* Entrega la cantidad de lineas*/
                        
        
        // VALIDACION DE TOTALES
//        System.out.println("TEST VALIDACION TOTAL");
//        int ssTotal = 0;
//        ssTotal = (Integer.parseInt(mntNeto) + Integer.parseInt(MntExe) + Integer.parseInt(IVA));// - Descuentos;
//        System.out.println(ssTotal);
//        System.out.println(MntTotal);
//        System.out.println("FIN TEST VALIDACION TOTAL");
                                      
        System.out.println("INICIO DE VALIDACION DE ERRORES ANTES DE CREAR TXT");
        
        if(TipoDTE.equals("Err") || FchEmis.equals("Err") || FchVenc.equals("Err") || Folio.equals("Err") || //DOCUMENTO
           RznSoc.equals("Err") || RUTEmisor.equals("Err") || DirOrigen.equals("Err") || CmnaOrigen.equals("Err") || CiudadOrigen.equals("Err") || Acteco.equals("Err") || GiroEmis.equals("Err") || //EMISOR
           cdgInRecep.equals("Err") || RznSocRecep.equals("Err") || RUTRecep.equals("Err") || GiroRecep.equals("Err") || DirRecep.equals("Err") || CiudadRecep.equals("Err") || CmnaRecep.equals("Err") || Contacto.equals("Err") || //RECEPTOR
           TermPagoGlosa.equals("Err") || //FORMA PAGO
           TpoCambio.equals("Err") || MntExeOtrMnda.equals("Err") || MntTotOtrMnda.equals("Err") || //MONTOS TIPO CAMBIO
           TipoDocRef.equals("Err") || FolioRef.equals("Err") || FechaDocRef.equals("Err") || IDRazon.equals("Err")) //REFERENCIA
        {
            status.status = "ERROR";
            status.descripcion = "VERIFICAR CAMPO VACIO"; // El codigo 1 corresponde a error por campos vacios en campos obligatorios
            status.id = "0";
        }
        else if (a == 0)
        {
            status.status = "ERROR";
            status.descripcion = "SIN LINEAS DE DOCUMENTO"; // El codigo 2 corresponde documento sin ninguna linea de detalle
            status.id = "0";
        }
        else if(mntNeto.equals("SIN AFECTO"))
        {
            status.status = "ERROR";
            status.descripcion = "SIN MONTOS AFECTOS"; 
            status.id = "0";
        }
        else 
        {       
            System.out.println("COMIENZO DE CREACION DE TXT");
            
                  for(int i = 1; i <= a; i++) /*Se recorre el ArrayList para extraer la info segun la cantidad de lineas*/
                  {
                        /*Verifica cuales son las lineas que corresponden a propinas y las suma para obtener el total de las propinas*/
                        if(detalle.Linea.get(i-1).getVlrCodigo().equals("900_") || detalle.Linea.get(i-1).getVlrCodigo().equals("9999") || detalle.Linea.get(i-1).getVlrCodigo().equals("0700") || detalle.Linea.get(i-1).getVlrCodigo().equals("2700") || detalle.Linea.get(i-1).getVlrCodigo().equals("4700"))
                        {
                            //Propina += Integer.parseInt(detalle.Linea.get(i-1).getMontoItem());
                            Propina += Float.parseFloat(detalle.Linea.get(i-1).getMontoItem()); 
                        }
                        
                        if(detalle.Linea.get(i-1).getNumbItem().contains("Dto."))
                        {
                            //Descuentos += Integer.parseInt(detalle.Linea.get(i-1).getMontoItem().replace("-", ""));
                            Descuentos += Float.parseFloat(detalle.Linea.get(i-1).getMontoItem().replace("-", ""));
                        }                        
                  }
                                    
                   /* Se crea la estructura del documento txt (cabecera)*/
                   fin = "//////////////////////////////////////////////////////Header" + "\r\n";
                  fin += "                                         :////////////////////////////DATOS EMISOR//////////////////////////////////"+ "\r\n";
                  fin += "NOMBREEMISOR                             :" + RznSoc + "\r\n";
                  fin += "RUTEMISOR                                :" + RUTEmisor + "\r\n";
                  fin += "CALLEEMISOR                              :" + DirOrigen + "\r\n";
                  fin += "COMUNAEMISOR                             :" + CmnaOrigen + "\r\n";
                  fin += "CIUDADEMISOR                             :" + CiudadOrigen + "\r\n";
                  fin += "ACTECO                                   :" + Acteco + "\r\n";
                  fin += "GIROEMISOR                               :" + GiroEmis + "\r\n";
                  fin += "                                         :////////////////////////////DATOS RECEPTOR/////////////////////////////////" + "\r\n";
                  fin += "CODIGORECEPTOR                           :" + cdgInRecep + "\r\n";
                  fin += "NOMBRERECEPTOR                           :" + RznSocRecep + "\r\n";
                  fin += "RUTRECEPTOR                              :" + RUTRecep + "\r\n";
                  fin += "GIRORECEPTOR                             :" + GiroRecep + "\r\n";
                  fin += "CORREORECEPTOR                           :" + CorreoRecep + "\r\n";
                  fin += "CODIGOPOSTALRECEPTOR                     :" + DirPostal + "\r\n";
                  fin += "                                         :////////////////////////////DIRECCION FACTURACION/////////////////////////////////" + "\r\n";
                  fin += "CALLE                                    :" + DirRecep + "\r\n";
                  fin += "CIUDAD                                   :" + CiudadRecep + "\r\n";
                  fin += "COMUNA                                   :" + CmnaRecep + "\r\n";
                  fin += "CODIGOPOSTAL                             :" + DirPostal + "\r\n";
                  fin += "PAIS                                     :" + Contacto + "\r\n";
                  fin += "                                         :////////////////////////////DISPONIBLE/////////////////////////////////" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "                                         :////////////////////////////MEDIO DE PAGO/////////////////////////////////" + "\r\n";
                  fin += "NROPAGO                                  :" + TermPagoGlosa + "\r\n";
                  fin += "NOMBREPAGO                               :" + TermPagoDias + "\r\n";
                  fin += "                                         :////////////////////////////DOCUMENTO/////////////////////////////////" + "\r\n";
                  fin += "TIPO DTE                                 :" + TipoDTE + "\r\n";
                  fin += "ORDEN DE COMPRA                          :" + OrdenDeCompra + "\r\n";
                  fin += "TAG802                                   :" + Tag802 + "\r\n";
                  fin += "FECHA ORDEN DE COMPRA                    :" + FechaOC + "\r\n";
                  fin += "VENDEDOR                                 :" + IdVendedor + "\r\n";
                  fin += "SERVICIO                                 :" + IndServicio + "\r\n";
                  fin += "FECHAEMISDOC                             :" + FchEmis + "\r\n";
                  fin += "FECHAVENCDOC                             :" + FchVenc + "\r\n";
                  fin += "FOLIO                                    :" + Folio + "\r\n";
                  fin += "                                         :////////////////////////////TOTALES/////////////////////////////////" + "\r\n";
                  fin += "MONTOIVA                                 :" + IVA + "\r\n";
                  fin += "MONTOTOTAL                               :" + MntTotal + "\r\n";
                  fin += "DESCUENTOS                               :" + Descuentos + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "TASAIVA                                  :" + TasaIVA + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "MONTOEXENTO                              :" + MntExe + "\r\n";
                  fin += "MONTONETO                                :" + mntNeto + "\r\n";
                  fin += "MONTORETENCION                           :" + MntRetencion + "\r\n";
                  fin += "TIPOMONEDA                               :" + TpoMoneda + "\r\n";
                  fin += "TIPOCAMBIO                               :" + TpoCambio + "\r\n";
                  fin += "MONTOEXENTOOTRAMONEDA                    :" + MntExeOtrMnda + "\r\n";
                  fin += "MONTOTOTALOTRAMONEDA                     :" + MntTotOtrMnda + "\r\n";
                  fin += "                                         :////////////////////////////REFERENCIA/////////////////////////////////" + "\r\n";     
                  fin += "TIPODOCUMENTO                            :" + TipoDocRef + "\r\n";
                  fin += "FOLIO                                    :" + FolioRef + "\r\n";
                  fin += "FECHA                                    :" + FechaDocRef + "\r\n";
                  fin += "CODIGORAZON                              :" + IDRazon + "\r\n";
                  fin += "DESCRIPCIONRAZON                         :" + RazonDesc + "\r\n";
                  fin += "                                         :////////////////////////////PERSONALIZADOS/////////////////////////////////" + "\r\n";
                  fin += "NUMERODERESERVA                          :" + NumReserva + "\r\n";
                  fin += "IMPRESORA                                :" + Impresora + "\r\n";
                  fin += "PASAPORTE                                :" + Pasaporte + "\r\n";
                  fin += "PROPINA                                  :" + Propina + "\r\n";
                  fin += "OBSERVACIONES                            :" + Observaciones + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
                  fin += "disponible                               :" + "" + "\r\n";
              
                  /*Se crea la seccion del documento que contiene las lineas de la factura (detalle)*/
                  fin += "//////////////////////////////////////////////////////DOCUMENT LINES" + "\r\n";
                  fin += String.format("%-65s","CODIGO")+String.format("%-65s","CANTIDAD")+String.format("%-65s","PRECIO")+String.format("%-65s","TOTAL")+String.format("%-65s","EXENTO")+String.format("%-65s","DESCRIPCION")+String.format("%-85s","GLOSAADICIONAL")+ "\r\n";          
                  
                  for(int i = 1; i <= a; i++) /*Se recorre el ArrayList para extraer la info segun la cantidad de lineas*/
                  {
                      if(detalle.Linea.get(i-1).getVlrCodigo().equals("900_") || detalle.Linea.get(i-1).getVlrCodigo().equals("9999") || detalle.Linea.get(i-1).getVlrCodigo().equals("0700") || detalle.Linea.get(i-1).getVlrCodigo().equals("2700") || detalle.Linea.get(i-1).getNumbItem().contains("Dto.") || detalle.Linea.get(i-1).getVlrCodigo().equals("4700"))
                        {
                      
                        }
                      else
                      {
                          fin += String.format("%-65s",(detalle.Linea.get(i-1).getTpoCodigo()+ detalle.Linea.get(i-1).getVlrCodigo())).substring(0, 65);       /*CODIGO*/
                          fin += String.format("%-65s",detalle.Linea.get(i-1).getQtyItem()).substring(0, 65);                                                 /*CANTIDAD*/
                          fin += String.format("%-65s",detalle.Linea.get(i-1).getPrcItem()).substring(0, 65).replace(',', '.');                                /*PRECIO*/
                          fin += String.format("%-65s",detalle.Linea.get(i-1).getMontoItem()).substring(0, 65).replace(',', '.');                              /*TOTAL*/
                          fin += String.format("%-65s",detalle.Linea.get(i-1).getIndExe()).substring(0, 65);                                                  /*EXENTO*/
                          fin += String.format("%-65s",detalle.Linea.get(i-1).getNumbItem()).substring(0, 65).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "");          /*DESCRIPCION*/
                          fin += String.format("%-85s",detalle.Linea.get(i-1).getDscItem()).substring(0, 85).replace('ñ', 'n').replace('Ñ', 'N').replaceAll("(\n|\r)", "") + "\r\n"; /*GLOSAADICIONAL*/  
                      }
                  }    
        
            nombreArchivo = TipoDTE+"_"+Folio+"_"+Hotel;
              
            Scanner scanner = new Scanner(System.in);
            
            File nuevo = null;
            
            System.out.println("FIN DE CREACION DE TXT");
            
            /* Se define la ruta donde se creara el archivo*/
            if(Hotel.equals("HTP"))
            {                                
                nuevo = new File("\\\\vxdoc\\inTXTtoXML-HTP-QA\\"+ nombreArchivo +".txt"); //QA
                //nuevo = new File("\\\\vxdoc\\inTXTtoXML-HTP\\"+ nombreArchivo +".txt"); //PRODUCCION
                
                FileWriter escribe = new FileWriter(nuevo, false);  /*Se crea el archivo*/    
                          
                escribe.write(fin); /* Se escribe en el documento txt creado anteriormente todo el contenido creado*/
                escribe.close();
                       
                status.status = "OK";
                status.descripcion = "APROBADO";//nuevo.toString();//
                status.id = nombreArchivo;     
                
                System.out.println("SE GUARDA ARCHIVO Y SE TERMINA METODO 1");
            }
            else if(Hotel.equals("TAC"))
            {                                
                nuevo = new File("\\\\vxdoc\\inTXTtoXML-TAC-QA\\"+ nombreArchivo +".txt"); //QA
                //nuevo = new File("\\\\vxdoc\\inTXTtoXML-TAC\\"+ nombreArchivo +".txt"); //PRODUCCION
                
                FileWriter escribe = new FileWriter(nuevo, false);  /*Se crea el archivo*/    
                          
                escribe.write(fin); /* Se escribe en el documento txt creado anteriormente todo el contenido creado*/
                escribe.close();
                       
                status.status = "OK";
                status.descripcion = "APROBADO";
                status.id = nombreArchivo;   
                
                System.out.println("SE GUARDA ARCHIVO Y SE TERMINA METODO 1");
            }
            else if(Hotel.equals("HHR"))
            {
                nuevo = new File("\\\\vxdoc\\inTXTtoXML-HHR-QA\\"+ nombreArchivo +".txt"); //QA
                //nuevo = new File("\\\\vxdoc\\inTXTtoXML-HHR-PROD\\"+ nombreArchivo +".txt"); //PRODUCCION
                
                FileWriter escribe = new FileWriter(nuevo, false);  /*Se crea el archivo*/    
                          
                escribe.write(fin); /* Se escribe en el documento txt creado anteriormente todo el contenido creado*/
                escribe.close();
                       
                status.status = "OK";
                status.descripcion = "APROBADO";
                status.id = nombreArchivo; 
                
                System.out.println("SE GUARDA ARCHIVO Y SE TERMINA METODO 1");
            }
            else
            {
                status.status = "ERROR";
                status.descripcion = "Error en rut de emisor";//nuevo.toString();
                status.id = nombreArchivo; 
                
                System.out.println("ERROR METODO 1 EN RUT DE EMISOR");
                
            }
        }
        
        /*return status;*/
        }
        catch(IOException ex)
        {            
            status.status = "ERROR";
            status.descripcion = ex.toString();//"4";//
            status.id = "0";  
            
            System.out.println(ex.toString());
        }
        return status;
    }  
        
    /**
     *
     * @param id
     * @return
     */
    @WebMethod(operationName = "GetDocument_request")
    public Status_Document GetDocument_request
        (@WebParam(name = "id") String id) 
        {
            System.out.println("INICIO METODO 2 RECUPERAR DOCUMENTO");
            
            Status_Document status = new Status_Document();
                       
           /*Debe ingresar el ID del documento y este metodo debera retornar el estado del documento y la url de donde se encuentra este (segun su estado)*/
                                   
            String et = null;
            String ur = null;      
            
            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            
            File archivos1[] = RutaXdocOK_HTP.listFiles();            
            File archivos2[] = RutaXdocBAD_HTP.listFiles();
                      
            File archivos3[] = RutaXdocOK_TAC.listFiles();
            File archivos4[] = RutaXdocBAD_TAC.listFiles();
            
            File archivos5[] = RutaXdocOK_HHR.listFiles();
            File archivos6[] = RutaXdocBAD_HHR.listFiles();
            
            String bueno[] = id.split("_");
                        
            String pdf = "T_"+bueno[0]+"_F_"+bueno[1]+"_";
            String txt = id + ".txt";
            String xml = id + ".xml";
            String Hotel = bueno[2];
            
            System.out.println("NOMBRE DOCUMENTOS:");
            System.out.println(pdf);
            System.out.println(txt);
            System.out.println(xml);
            
            System.out.println("CONSULTANDO CARPETAS...");
            
            //Falta declarar bien el codigo que se utilizara de los hoteles para el nombre del archivo
            if(Hotel.equals("HTP"))
            {
                if(archivos1 != null)
                {               
                   for(int i = 0; i < archivos1.length;i++)
                   {                   
                        if(archivos1[i].getName().toString().startsWith("HTP_" + pdf.toString())) // QA
                        //if(archivos1[i].getName().toString().startsWith(pdf.toString())) // PRODUCCION
                        {
                            et = "Aprobado!";
                            ur = RutaXdocOK_HTP.toString() + '\\' + archivos1[i].getName().toString();
                            i=archivos1.length;
                            
                            System.out.println("ENCONTRO PDF CORRECTO!");                            
                        }                    
                    }               
                   if(et == null)
                   {
                       if(archivos2 != null)
                       {
                           for(int i = 0; i < archivos2.length;i++)
                           {
                              if(archivos2[i].getName().toString().equals(txt))
                              {
                                    et = "Error en XDoc";
                                    ur = RutaXdocBAD_HTP.toString() + '\\' + archivos2[i].getName().toString();
                                    i=archivos2.length;
                                  
                                    System.out.println("ERROR EN XDOC! PRIMER IF");
                                  
                                    //Importante ir a leer el archivo de error de XDOC.                                                                                                 

                                    try
                                    {                                                                            
                                        Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\" + txt); //QA
                                        Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\" + xml); //QA
                                        Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\log\\"); //QA
                                        
                                        //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                        //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                        //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\log\\"); //PRODUCCION
                                                                                                                             
                                        Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                        Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                        System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                                    }
                                    catch (IOException e) 
                                    {
                                        System.out.println("ERROR:" + e.toString());
                                    }                                                                                                      
                              }
                           }                       
                           if(et == null)
                           {
                                et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                                ur = "";
                                System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           }
                       }
                       else
                       {
                           et = "El documento no existe. Intente en 2 segundos";
                           ur = "";
                           System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           //Status de error: El documento no existe. Intente en 2 segundos.
                       }                   
                   }                                                           
                }
                else if(archivos2 != null)
                {
                    for(int i = 0; i < archivos2.length;i++)
                    {
                        if(archivos2[i].getName().toString().equals(txt))
                        {
                            et = "Error en XDoc";
                            ur = RutaXdocBAD_HTP.toString() + '\\' + archivos2[i].getName().toString();
                            i=archivos2.length;                            
                            
                            System.out.println("ERROR EN XDOC! SEGUNDO IF");
                            
                            try
                            {                                                                            
                                Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\" + txt); //QA
                                Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\" + xml); //QA
                                Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-TERMAS-PUYEHUE\\instances\\01\\errTXT\\log\\"); //QA
                                
                                //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HTP\\instances\\01\\errTXT\\log\\"); //PRODUCCION                                                               
                                                                                                                             
                                Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                            }
                            catch (IOException e) 
                            {
                                System.out.println("ERROR:" + e.toString());
                            }      
                        }
                    }                       
                    if(et == null)
                    {
                        et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                        ur = "";
                        System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                    }                                                          
                }
                else
                {
                    et = "El documento no existe. Intente en 2 segundos";
                    ur = "";
                    System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                    //Status de error: El documento no existe. Intente en 2 segundos.
                }
                
                
                status.status = et;
                status.url = ur;
                
                
            }
            else if (Hotel.equals("TAC"))
            {
                if(archivos3 != null)
                {               
                   for(int i = 0; i < archivos3.length;i++)
                   {                   
                        if(archivos3[i].getName().toString().startsWith("TAC_" + pdf.toString())) // aca debe estar TAC
                        {
                            et = "Aprobado!";
                            ur = RutaXdocOK_TAC.toString() + '\\' + archivos3[i].getName().toString();
                            i=archivos3.length;
                            System.out.println("ENCONTRO PDF CORRECTO!");
                        }                    
                    }               
                   if(et == null)
                   {
                       if(archivos4 != null)
                       {
                           for(int i = 0; i < archivos4.length;i++)
                           {
                              if(archivos4[i].getName().toString().equals(txt))
                              {
                                  et = "Error en XDoc";
                                  ur = RutaXdocBAD_TAC.toString() + '\\' + archivos4[i].getName().toString();
                                  i=archivos4.length;
                                  //Importante ir a leer el archivo de error de XDOC.
                                  
                                  System.out.println("ERROR EN XDOC! PRIMER IF");
                                  
                                    //Importante ir a leer el archivo de error de XDOC.                                                                                                 

                                    try
                                    {                                                                            
                                        Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\" + txt); //QA
                                        Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\" + xml); //QA
                                        Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\log\\"); //QA
                                        
                                        //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                        //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                        //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\log\\"); //PRODUCCION
                                                                                                                             
                                        Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                        Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                        System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                                    }
                                    catch (IOException e) 
                                    {
                                        System.out.println("ERROR:" + e.toString());
                                    }                                                                
                              }
                           }                       
                           if(et == null)
                           {
                                et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                                ur = "";
                                System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           }
                       }
                       else
                       {
                           et = "El documento no existe. Intente en 2 segundos";
                           ur = "";
                           System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           //Status de error: El documento no existe. Intente en 2 segundos.
                       }                   
                   }                                                                    
                }
                else if(archivos4 != null)
                {
                    for(int i = 0; i < archivos4.length;i++)
                    {
                        if(archivos4[i].getName().toString().equals(txt))
                        {
                            et = "Error en XDoc";
                            ur = RutaXdocBAD_TAC.toString() + '\\' + archivos4[i].getName().toString();
                            i=archivos4.length;
                            //Importante ir a leer el archivo de error de XDOC.
                            
                            System.out.println("ERROR EN XDOC! SEGUNDO IF");
                                  
                                    //Importante ir a leer el archivo de error de XDOC.                                                                                                 

                                    try
                                    {                                                                            
                                        Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\" + txt); //QA
                                        Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\" + xml); //QA
                                        Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-AGUAS-CALIENTES\\instances\\01\\errTXT\\log\\"); //QA
                                        
                                        //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                        //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                        //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-TAC\\instances\\01\\errTXT\\log\\"); //PRODUCCION
                                                                                                                             
                                        Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                        Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                        System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                                    }
                                    catch (IOException e) 
                                    {
                                        System.out.println("ERROR:" + e.toString());
                                    }              

                        }
                    }                       
                    if(et == null)
                    {
                        et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                        ur = "";
                        System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                    }
                }
                else
                {
                    et = "El documento no existe. Intente en 2 segundos";
                    ur = "";
                    //Status de error: El documento no existe. Intente en 2 segundos.
                    System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                }           
                
                
                status.status = et;
                status.url = ur;   
                
            }
            else if (Hotel.equals("HHR"))
            {
                if(archivos5 != null)
                {               
                   for(int i = 0; i < archivos5.length;i++)
                   {                   
                        if(archivos5[i].getName().toString().startsWith("HHR_" + pdf.toString()))
                        {
                            et = "Aprobado!";
                            ur = RutaXdocOK_HHR.toString() + '\\' + archivos5[i].getName().toString();
                            i=archivos5.length;
                            System.out.println("ENCONTRO PDF CORRECTO");
                        }                    
                    }               
                   if(et == null)
                   {
                       if(archivos6 != null)
                       {
                           for(int i = 0; i < archivos6.length;i++)
                           {
                              if(archivos6[i].getName().toString().equals(txt))
                              {
                                  et = "Error en XDoc";
                                  ur = RutaXdocBAD_HHR.toString() + '\\' + archivos6[i].getName().toString();
                                  i=archivos6.length;
                                  //Importante ir a leer el archivo de error de XDOC.
                                  
                                  System.out.println("ERROR EN XDOC! PRIMER IF");
                                  
                                  try
                                    {                                                                            
                                        Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\" + txt); //QA
                                        Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\" + xml); //QA
                                        Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\log\\"); //QA
                                        
                                        //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                        //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                        //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\log\\"); //PRODUCCION
                                                                                                                             
                                        Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                        Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                        System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                                    }
                                    catch (IOException e) 
                                    {
                                        System.out.println("ERROR:" + e.toString());
                                    }                                                                                                         
                              }
                           }                       
                           if(et == null)
                           {
                                et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                                ur = "";
                                System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           }
                       }
                       else
                       {
                           et = "El documento no existe. Intente en 2 segundos";
                           ur = "";
                           System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                           //Status de error: El documento no existe. Intente en 2 segundos.
                       }                   
                   }                                                                    
                }
                else if(archivos6 != null)
                {
                    for(int i = 0; i < archivos6.length;i++)
                    {
                        if(archivos6[i].getName().toString().equals(txt))
                        {
                            et = "Error en XDoc";
                            ur = RutaXdocBAD_HHR.toString() + '\\' + archivos6[i].getName().toString();
                            i=archivos6.length;
                            //Importante ir a leer el archivo de error de XDOC.
                            
                            System.out.println("ERROR EN XDOC! SEGUNDO IF");
                            
                            //Importante ir a leer el archivo de error de XDOC.                                                                                                 

                                    try
                                    {                                                                            
                                        Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\" + txt); //QA
                                        Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\" + xml); //QA
                                        Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\QA-HANGAROA\\instances\\01\\errTXT\\log\\"); //QA
                                        
                                        //Path RutaXdocBAD_ORI1_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\" + txt); //PRODUCCION
                                        //Path RutaXdocBAD_ORI2_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\" + xml); //PRODUCCION
                                        //Path RutaXdocBAD_DES_HTP = Paths.get("C:\\Program Files (x86)\\Signature\\xDoc CL\\Stores\\areas\\FE-HHR\\instances\\01\\errTXT\\log\\"); //PRODUCCION
                                                                                                                             
                                        Files.move(RutaXdocBAD_ORI1_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".txt"), StandardCopyOption.REPLACE_EXISTING); 
                                        Files.move(RutaXdocBAD_ORI2_HTP, RutaXdocBAD_DES_HTP.resolve(id +"_"+ date.format(now)+ "__" + hour.format(now).replace(":", ";") + ".xml"), StandardCopyOption.REPLACE_EXISTING); 
                                      
                                        System.out.println("SE MUEVE ARCHIVO CON ERROR A CARPETA LOG...");
                                      
                                    }
                                    catch (IOException e) 
                                    {
                                        System.out.println("ERROR:" + e.toString());
                                    }     
                            
                            
                        }
                    }                       
                    if(et == null)
                    {
                        et = "El archivo no ha sido recepcionado. Intente en 2 segundos";
                        ur = "";
                        System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                    }
                }
                else
                {
                    et = "El documento no existe. Intente en 2 segundos";
                    ur = "";
                    System.out.println("EL ARCHIVO NO HA SIDO RECEPCIONADO...");
                    //Status de error: El documento no existe. Intente en 2 segundos.
                }             
                     
                
                status.status = et;
                status.url = ur;                               
                        
            }
            else
            {
                status.status = "Error nombre doc";
                status.url = "";
                System.out.println("ERROR EN NOMBRE DE HOTEL");
            }             
            
            System.out.println(ur);
            System.out.println("FIN DEL PROCEDO METODO 2");
            
            return status; 
            
        }        
}
