package services;

import interfaces.Persistencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import models.Producto;
import models.producto.Baldosa;
import models.producto.Pileta;
import models.producto.Pintura;

public class CSVService implements Persistencia <Producto> {
    private final String path;
    
    public CSVService (String path){
        this.path = path;
    }
    
    @Override
    public void guardar(List<Producto> lista) {
        StringBuilder sb = new StringBuilder();
        for (Producto producto : lista) {
            sb.append(producto.getCSV());
            sb.append("\n");          
        }
        
        try (PrintWriter writer = new PrintWriter(new java.io.File(path))) {
            writer.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Producto> cargar() {
        List<Producto> lista = new ArrayList<>();
        
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] v = line.split(",");
                if (v.length < 5) continue;
                
                try {
                int id = Integer.parseInt(v[0]);
                String categoria = v[1];
                String nombre = v[2];
                double precio = Double.parseDouble(v[3]);
                int stock = Integer.parseInt(v[4]);

                switch (categoria) {
                    case "BALDOSAS":
                        lista.add(new Baldosa(id, nombre, precio, stock, v[5], Double.parseDouble(v[6])));
                        break;
                    case "PILETAS":
                        lista.add(new Pileta(id, nombre, precio, stock, Integer.parseInt(v[5]), v[6]));
                        break;
                    case "PINTURAS":
                        lista.add(new Pintura(id, nombre, precio, stock, v[5], Integer.parseInt(v[6])));
                        break;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Línea ignorada por error de formato: " + line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return lista;
}
}
