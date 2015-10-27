package com.fisherevans.gfx;

import com.fisherevans.math.Vector3f;
import external.oskar.Camera;
import external.oskar.EulerCamera;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by h13730 on 10/26/2015.
 */
public class ModelViewer {
  private GLFWErrorCallback errorCallback;
  private GLFWKeyCallback keyCallback;

  private long window;

  private Model model;
  private static int modelDisplayList;
  private Camera camera;

  public void run() {
    try {
      init();
      //loadModel();
      loop();
      glfwDestroyWindow(window);
      keyCallback.release();
    } finally {
      glfwTerminate();
      errorCallback.release();
    }
  }

  private void init() {
    glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

    if (glfwInit() != GL11.GL_TRUE)
      throw new IllegalStateException("Unable to initialize GLFW");

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

    int WIDTH = 300;
    int HEIGHT = 300;

    window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window");

    glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
          glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
      }
    });

    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(
        window,
        (vidmode.getWidth() - WIDTH) / 2,
        (vidmode.getHeight() - HEIGHT) / 2
    );

    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    glfwShowWindow(window);

    EulerCamera.Builder cameraBuilder = new EulerCamera.Builder();
    camera = cameraBuilder.build();
    camera.applyPerspectiveMatrix();
  }

  private void loadModel() {
    File file = new File("res/model/bunny/bunny.obj");
    try {
      model = OBJLoader.loadModel(file);
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    modelDisplayList = glGenLists(1);
    glNewList(modelDisplayList, GL_COMPILE);
    {
      glBegin(GL_TRIANGLES);
      Vector3f n1, n2, n3, v1, v2, v3;
      for (Model.Face face : model.faces) {
        n1 = model.normals.get(face.normal.x - 1);
        n2 = model.normals.get(face.normal.y - 1);
        n3 = model.normals.get(face.normal.z - 1);
        v1 = model.vertices.get(face.vertex.x - 1);
        v2 = model.vertices.get(face.vertex.y - 1);
        v3 = model.vertices.get(face.vertex.z - 1);
        glNormal3f(n1.x, n1.y, n1.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glNormal3f(n2.x, n2.y, n2.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glNormal3f(n3.x, n3.y, n3.z);
        glVertex3f(v3.x, v3.y, v3.z);
      }
      glEnd();
    }
    glEndList();
  }

  private void loop() {
    GL.createCapabilities();

    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

    //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    //glCallList(modelDisplayList);

    while (glfwWindowShouldClose(window) == GL_FALSE) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glfwSwapBuffers(window);
      glfwPollEvents();
    }
  }

  public static void main(String[] args) {
    new ModelViewer().run();
  }
}
