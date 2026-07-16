/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.usuarios;

/**
 *
 * @author zulmi
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import modelo.persona.PersonaReniec;

public class DniService {
    private static final String API_URL = "https://api.verificape.com/v2/dni/";
    private static final String API_KEY = "vp_live_c5e575126bb746cfa8859de562cd37b1"; // reemplaza con tu token

    public PersonaReniec validarDni(String dni) {
        try {
            //validar campos para no llamar de  forma inecesaria
            if(dni==null){
                return new PersonaReniec(false,"Ingrese DNI.");
            }
            if(!dni.matches("\\d{8}")){
                return new PersonaReniec(false,
                        "El DNI debe tener 8 dígitos.");
            }
            //Entrar al api con cuenta para hacer el put de Dni
            URL url = new URL(API_URL + dni);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            // Validar que la respuesta es 200(correcta en capa aplicacion)
            int codigo = con.getResponseCode();
            if(codigo!=200){
                return new PersonaReniec(false,
                        "DNI no esta registrado en la base de datos de la RENIEC.");
            }
            //Hacer llamado y completar datos de PersonaReniec
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Parsear JSON con Gson
            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject().getAsJsonObject("data");
            String nombres = json.get("names").getAsString();
            String apellidoPaterno = json.get("paternalSurname").getAsString();
            String apellidoMaterno = json.get("maternalSurname").getAsString();
            PersonaReniec persona = new PersonaReniec();
            persona.setValido(true);
            persona.setDni(dni);
            persona.setNombres(nombres);
            persona.setApellidoPaterno(apellidoPaterno);
            persona.setApellidoMaterno(apellidoMaterno);
            persona.setMensaje("DNI válido.");
            return persona;
        } catch (IOException ex) {
            return new PersonaReniec(false,"Error al validar DNI:\n " + ex.getMessage()
                    + "\n Porfavor comunicar al Bibliotecario :)");
        }
    }
}
