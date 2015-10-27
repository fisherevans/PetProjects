package com.fisherevans.gfx;

import com.fisherevans.math.Vector3f;
import com.fisherevans.math.Vector3i;

import java.io.*;

/**
 * Created by h13730 on 10/26/2015.
 */
public class OBJLoader {
  public static Model loadModel(File file) throws FileNotFoundException, IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    Model model = new Model();
    String line;
    while((line = reader.readLine()) != null) {
      String[] split = line.split(" ");
      if (line.startsWith("v")) {
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);
        if(line.startsWith("v "))
          model.vertices.add(new Vector3f(x, y, z));
        else if(line.startsWith("vn "))
          model.normals.add(new Vector3f(x, y, z));
      } else if(line.startsWith("f ")) {
        String[] xSplit = split[1].split("//");
        String[] ySplit = split[2].split("//");
        String[] zSplit = split[3].split("//");
        Vector3i vertexIndices = new Vector3i(Integer.parseInt(xSplit[0]),
            Integer.parseInt(ySplit[0]),
            Integer.parseInt(zSplit[0]));
        Vector3i normalIndices = new Vector3i(Integer.parseInt(xSplit[1]),
            Integer.parseInt(ySplit[1]),
            Integer.parseInt(zSplit[1]));
        model.faces.add(new Model.Face(vertexIndices, normalIndices));
      }
    }
    reader.close();
    return model;
  }
}
