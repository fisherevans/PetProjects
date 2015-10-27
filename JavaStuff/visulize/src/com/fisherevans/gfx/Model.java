package com.fisherevans.gfx;

import com.fisherevans.math.Vector3f;
import com.fisherevans.math.Vector3i;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h13730 on 10/26/2015.
 */
public class Model {
  public final List<Vector3f> vertices = new ArrayList<Vector3f>();
  public final List<Vector3f> normals = new ArrayList<Vector3f>();
  public final List<Face> faces = new ArrayList<Face>();

  public Model() { }

  public static class Face {
    public final Vector3i vertex; // three indices - not a vertex
    public final Vector3i normal;

    public Face(Vector3i vertex, Vector3i normal) {
      this.vertex = vertex;
      this.normal = normal;
    }
  }
}
