/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamapas;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import maps.java.Elevation;
//imports de la libreria de maps
import maps.java.Geocoding;
import maps.java.MapsJava;
import maps.java.Places;
import maps.java.Route;
import maps.java.StaticMaps;
import maps.java.StreetView;
/**
 *
 * @author asortega
 */
public class FXMLDocumentController implements Initializable {
    //aqui se declaran todos los BOTONES que llevara el programa
    
    @FXML
    private Button buscarubicacion;
    @FXML
    private Button guardarcoordenadas;
    @FXML
    private Button guardardireccion;
    @FXML
    private Button mapaestatico;
    @FXML
    private Button mostrarstreetview;
    @FXML
    private Button mostrarindicaciones;
    //labels
    @FXML
    private Label coordenadas;
    @FXML
    private Label direccionlabel;
    @FXML
    private Label indicaciones;
    @FXML
    private TextField ubicacioncaja;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private WebView webView;

    
    
    
    
    //lucio cabañas #1002, Divicion del Norte, chihuahua mexico
    //Monte Everest # 12121, Quintas Carolinas Chihuahua Mexico
    String texto;
    String url = "http://dir.ecc.ion";
    String direccionGeo;
    String puntoA;
    String puntoB = "Monte Everest # 12121, Quintas Carolinas Chihuahua Mexico";
    String direccionStreetView = "lucio cabañas #1002, Divicion del Norte, chihuahua mexico";
    String direccionMapaEstatico = "lucio cabañas #1002, Divicion del Norte, chihuahua mexico";
    String claveAPI ="AIz...";
    double placesLat = 40.4171111;
    double placesLng = -3.7031133;
    
    String concatCoor;
    String concatDir;
    
    WebView vistaweb = new WebView();
    WebEngine engine = vistaweb.getEngine();
    //declara los demas COMPONENTES que llevara
    @FXML
    void initialize() {
    
    
    engine.load("http://dir.ecc.ion:8081mapas/Mapa.html");
    
}
    
    @FXML
    public void buscarDireccion(ActionEvent event){
       //click al boton
       //validar que el campo de texto no este vacio
       //trael la direccion de buscar ubicacion
      //mandar coordenadas a js
        
      
    }
    
    
    
    //aqui se declaran los EVENTOS
    @FXML
    private void cargarmapa(ActionEvent event) {
        initialize();
        
    }
    //se encarga de leer la direccion en la caja
    @FXML
    public void obtenerTexto(ActionEvent event){
       texto = ubicacioncaja.getText();
       System.out.println(texto);
       direccionGeo = texto;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         WebEngine engine = webView.getEngine();
        engine.load("http://dir.ecc.ion:8081/mapas/Mapa.html");
    }    
    
    //aqui se declaran los METODOS
    
    
    public static void error(String funcionError) {
        System.err.println("Algo ocurrió, no se pudo ejecutar la función: " + funcionError);
    }
    
    
    @FXML
    public void guardarDireccion(ActionEvent event){ //muestra las direcciones
        Geocoding ObjGeocod = new Geocoding();
        try {

            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccionGeo);
            
            System.out.println("Los resultados obtenidos para la búsqueda de dirección de "
                    + resultadoCD.x + "," + resultadoCD.y + " son:");
            ArrayList<String> resultadoCI = ObjGeocod.getAddress(resultadoCD.x, resultadoCD.y);
            for(int item=0; item < 1; ++item) {
               String imprecion = resultadoCI.get(0);
                System.out.println(imprecion);
            //Aqui se agregan las direcciones
                
               direccionlabel.setText(imprecion);
            }
        } catch (Exception e) {
            error("Codificación geográfica");
        }
    }
    
    
    
    @FXML
    public void geocoding(ActionEvent event) { // muestra las coordenadas
        Geocoding ObjGeocod = new Geocoding();
        try {

            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccionGeo);
            System.out.println("Las coordenadas de " + direccionGeo + ", son: "
                    + resultadoCD.x + "," + resultadoCD.y);
            //mi adicion
            concatCoor = "Lat: " + resultadoCD.x + "Lng: " + resultadoCD.y;
            coordenadas.setText(concatCoor);
            //se asigna al punto a la ubicacion que se busco primero
            
            
           
        } catch (Exception e) {
            error("Codificación geográfica");
        }
    }
    
    
    @FXML
    public void status(ActionEvent event) {
        System.out.println("Sensor activo: " + MapsJava.getSensor());
        System.out.println("Idioma: " + MapsJava.getLanguage());
        MapsJava.setRegion("mx");
        System.out.println("Región: " + MapsJava.getRegion());
        System.out.println("Clave: " + MapsJava.getKey());

        System.out.println("Ahora vamos a comprobar el registro de peticiones");
        String[][] registroPeticiones = MapsJava.getStockRequest();
        for (int i = 0; i < registroPeticiones.length; i++) {
            System.out.println("Petición " + i);
            for (int j = 0; j < registroPeticiones[0].length; j++) {
                System.out.print(registroPeticiones[i][j] + "\t");
            }
        }
    }
    @FXML
    public void Teide(ActionEvent event) {

        Elevation ObjElevat = new Elevation();
        try {
            double resultadoAlt = ObjElevat.getElevation(28.2725, -16.6425);
            System.out.println("La altitud del punto 28.2725,-16.6425 es: " + String.valueOf(resultadoAlt) + " metros");
            System.out.println("La resolución de dicha altitud es de :" + String.valueOf(ObjElevat.getResolution()) + " metros");
        } catch (Exception e) {
            error("Elevación");
        }
    }
    @FXML
    public void ruta(ActionEvent event) {
        puntoA ="lucio cabañas #1002, Divicion del Norte, chihuahua mexico";
        if (puntoA != null){
            
        
        Route ObjRout = new Route();
        try {
            String[][] resultado = ObjRout.getRoute(puntoA, puntoB, null, Boolean.TRUE, Route.mode.driving, Route.avoids.nothing);
            for (int i = 0; i < resultado.length; i++) {
                //System.out.println("Tramo " + i + ":");
                //String tramo = "Tramo " + i + ":";
                
                
                
                for (int j = 0; j < resultado[0].length; j++) {
                    System.out.print(resultado[i][j] + "\t");
                    String concatTramo = " ";
                    concatTramo = concatTramo + resultado[i][j] + " ";
                    indicaciones.setText(concatTramo);
                }
                System.out.println("");
            }
        } catch (Exception e) {
            error("Ruta");
        }
    }else{
            System.out.println("primero debe buscar una ubicacion");
    }
    }
    @FXML
    public void streetview(ActionEvent event){
        StreetView ObjStreet=new StreetView();
        try {
            Image imgResultado=ObjStreet.getStreetView(direccionStreetView, new Dimension(300,300),
                    90, 100, -100);
            System.out.println("La URL asociada a la imagen es: " + MapsJava.getLastRequestURL());
        } catch (Exception e) {
            error("Street View");
        }
    }
    @FXML
    public void MapaEstatico(ActionEvent event){
        StaticMaps ObjStatMap=new StaticMaps();
        try {
            Image resultadoMapa=ObjStatMap.getStaticMap(direccionMapaEstatico, 14,new Dimension(300,300),
                    1, StaticMaps.Format.png, StaticMaps.Maptype.terrain);
            System.out.println("La URL asociada al mapa es: " + MapsJava.getLastRequestURL());
        } catch (Exception e) {
            error("Mapas estáticos");
        }
    }
    @FXML
    public void places(ActionEvent event){
        MapsJava.setKey(claveAPI);
        System.out.println("Bien, ya hemos puesto la clave. Ahora estamos comprobando si es correcta..");
        if("OK".equals(MapsJava.APIkeyCheck(claveAPI))){
             System.out.println("La clave es correcta. Ya estamos buscando locales "
                + "cercanos a \"40.4171111,-3.7031133\"...\n");
            Places ObjPlace=new Places();
            try {
                String[][] resultado=ObjPlace.getPlaces(placesLat, placesLng, 
                        3000, "", "", Places.Rankby.prominence, null);

                for(int i=0;i<resultado.length;i++){
                    System.out.println("Place " + i + ":");
                    for(int j=0;j<resultado[0].length;j++){
                        System.out.print(resultado[i][j] + "\t");
                    }
                    System.out.println("");
                }
            } catch (Exception e) {
                error("Place");
            }
            
        }else{
            System.out.println("Lo sentimos, la clave no es correcta :(");
        }
        
        
    }
    //ARREGLAR AQUI!
    @FXML
    public void leerArchivo(ActionEvent event) throws UnsupportedEncodingException{
        
       texto = ubicacioncaja.getText();
       System.out.println(texto);
       direccionGeo = texto;
       
        engine.setJavaScriptEnabled(true);
        
        Geocoding ObjGeocod = new Geocoding();
        
        File file = new File("C:/Users/usuario/Desktop/mapa.html");
        System.out.println(file.exists() + " file exitence");
        try {
            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccionGeo);
            //asigna lat y lang a variables
            double lt = resultadoCD.x;
            double lg = resultadoCD.y;
            engine.load(file.toURI().toURL().toString());
            if (engine != null) 
                {
                    engine.executeScript("mandarCoordenadas("+lt+","+lg+")");
                }
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error de la url");
        }
        
        
        
    }
    
    
    
    
}
