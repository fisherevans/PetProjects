package com.fisherevans;

import external.oskar.Camera;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by h13730 on 10/26/2015.
 */
public class KeyListener extends GLFWKeyCallback {
  private Camera camera;

  public KeyListener(Camera camera) {
    this.camera = camera;
  }

  @Override
  public void invoke(long window, int key, int scancode, int action, int mods) {
    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
      glfwSetWindowShouldClose(window, GL_TRUE);
  }
}
