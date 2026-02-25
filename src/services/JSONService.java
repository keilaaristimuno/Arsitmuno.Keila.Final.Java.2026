package services;

import interfaces.Persistencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import models.Producto;
import java.util.List;
import java.util.Map;
import models.producto.Baldosa;
import models.producto.Pileta;
import models.producto.Pintura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONService implements Persistencia <Producto> {
    private final String path;
    
    public JSONService(String path) {
        this.path = path;
    }
    
    @Override
    public void guardar(List<Producto> lista) {
         StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < lista.size(); i++) {
            sb.append("    " + lista.get(i).getJSON());
            if (i < lista.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            writer.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /* 
     * @brief Parsea un string JSON a una lista de mapas de strings.
     * @param json El string JSON a parsear.
     * @return Lista de mapas con los datos del JSON.
    */
    public List<Map<String, String>> parseJSON(String json) {
        
        List<Map<String, String>> list = new ArrayList<>();

        json = json.trim();
        json = json.substring(1, json.length() - 1); /* Saca [ ] */
/*el método split con una expresión regular escapando las llaves mediante doble barra invertida. 
     Esto me permitió fragmentar el String del JSON para luego procesar cada entidad de mi inventario por separado */

        String[] products = json.split("\\},\\{"); /* Splitear por llaves */

        for(int i=0; i<products.length; i++){
            String product = products[i];

            if(i==0) product.substring(1); // Saca {
            if(i==products.length-1) product = product.substring(0, product.length()-1); // Saca }

            Map<String, String> map = new HashMap<>();
            String[] pairs = product.split(",");

            for(String pair : pairs){
                String[] kv = pair.split(":", 2);
                String key = kv[0].trim().replace("\"", "");
                String value = kv[1].trim().replace("\"", "");
                map.put(key,value);
            }
            list.add(map);
        }
        return list;
    }
    
    
    @Override
    public List<Producto> cargar() {
        List<Producto> lista = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Map<String, String>>>() {}.getType();
            List<Map<String, String>> maplista = gson.fromJson(reader, tipoLista);
            
            for (Map<String, String> map : maplista) {
                String categoria = map.get("categoria");
                switch (categoria) {
                    case "BALDOSAS" -> lista.add(new Baldosa(map));
                    case "PILETAS" -> lista.add(new Pileta(map));
                    case "PINTURAS" -> lista.add(new Pintura(map));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
