import com.fisherevans.gfx.Model;
import com.fisherevans.gfx.OBJLoader;
import com.fisherevans.math.Vector3f;
import external.oskar.Camera;
import external.oskar.EulerCamera;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.io.File;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

  // We need to strongly reference callback instances.
  private GLFWErrorCallback errorCallback;
  private GLFWKeyCallback   keyCallback;

  private static Camera camera;

  // The window handle
  private long window;

  private Model model;
  private static int modelDisplayList;

  private int vertShader, fragShader, shaderProgram;

  public void run() {
    System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

    try {
      init();
      loop();

      // Release window and window callbacks
      glfwDestroyWindow(window);
      keyCallback.release();
    } finally {
      // Terminate GLFW and release the GLFWErrorCallback
      glfwTerminate();
      errorCallback.release();
    }
  }

  private void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( glfwInit() != GL11.GL_TRUE )
      throw new IllegalStateException("Unable to initialize GLFW");

    // Configure our window
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

    int WIDTH = 300;
    int HEIGHT = 300;

    // Create the window
    window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
    if ( window == NULL )
      throw new RuntimeException("Failed to create the GLFW window");

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
          glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
      }
    });

    // Get the resolution of the primary monitor
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    // Center our window
    glfwSetWindowPos(
        window,
        (vidmode.getWidth() - WIDTH) / 2,
        (vidmode.getHeight() - HEIGHT) / 2
    );

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);

    EulerCamera.Builder cameraBuilder = new EulerCamera.Builder();
    cameraBuilder.setWindow(window);
    cameraBuilder.setFieldOfView(75);
    cameraBuilder.setAspectRatio(1f);
    cameraBuilder.setPosition(0, 0, 10);
    camera = cameraBuilder.build();
  }

  private int createShader(String file, int type) {
    try {
      Scanner in = new Scanner(new File(file));
      String text = "";
      while(in.hasNextLine())
        text += in.nextLine() + "\n";
      int shader = ARBShaderObjects.glCreateShaderObjectARB(type);
      ARBShaderObjects.glShaderSourceARB(shader, text);
      ARBShaderObjects.glCompileShaderARB(shader);
      return shader;
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
      return 0;
    }
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
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();
    camera.applyOptimalStates();
    camera.applyPerspectiveMatrix();

    vertShader = createShader("res/shaders/default.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
    fragShader = createShader("res/shaders/default.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    shaderProgram = ARBShaderObjects.glCreateProgramObjectARB();
    ARBShaderObjects.glAttachObjectARB(shaderProgram, vertShader);
    ARBShaderObjects.glAttachObjectARB(shaderProgram, fragShader);
    ARBShaderObjects.glLinkProgramARB(shaderProgram);
    ARBShaderObjects.glValidateProgramARB(shaderProgram);

    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

    // Set the clear color
    //glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    loadModel();
    long last = System.currentTimeMillis(), current;
    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( glfwWindowShouldClose(window) == GL_FALSE ) {
      glfwPollEvents();
      current = System.currentTimeMillis();
      float delta = ((float)(current-last))/1000f;
      if(delta > 0) {
        camera.processKeyboard(delta, 500f);
        camera.processMouse(2.5f);
        last = current;
      }

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      glLoadIdentity();
      camera.applyTranslations();

      //ARBShaderObjects.glUseProgramObjectARB(shaderProgram);
      glColor3f(0f, 0f, 0f);
      glCallList(modelDisplayList);
      //ARBShaderObjects.glUseProgramObjectARB(0);

      glfwSwapBuffers(window); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
    }
  }

  public static void main(String[] args) {
    new HelloWorld().run();
  }

}