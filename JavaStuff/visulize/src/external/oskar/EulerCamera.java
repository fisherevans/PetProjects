package external.oskar;
/*
 * Copyright (c) 2013, Oskar Veerhoek
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */

    import org.joml.Matrix4f;
    import org.joml.Vector2d;
    import org.lwjgl.BufferUtils;
    import org.lwjgl.Sys;
    import org.lwjgl.opengl.GL;

    import java.nio.ByteBuffer;
    import java.nio.ByteOrder;
    import java.nio.DoubleBuffer;
    import java.nio.FloatBuffer;

    import static org.lwjgl.glfw.GLFW.glfwGetKey;
    import static org.lwjgl.glfw.GLFW.glfwPollEvents;
    import static org.lwjgl.glfw.GLFW.*;

    import static java.lang.Math.*;
    import static org.lwjgl.opengl.ARBDepthClamp.GL_DEPTH_CLAMP;
    import static org.lwjgl.opengl.GL11.*;

/**
 * A camera set in 3D perspective. The camera uses Euler angles internally, so beware of a gimbal lock.
 *
 * @author Oskar Veerhoek
 */
public final class EulerCamera implements Camera {

  private float x = 0;
  private float y = 0;
  private float z = 0;
  private float pitch = 0;
  private float yaw = 0;
  private float roll = 0;
  private float fov = 90;
  private float aspectRatio = 1;
  private final float zNear;
  private final float zFar;

  private Vector2d lastPosition;

  private long window;

  private EulerCamera(Builder builder) {
    this.x = builder.x;
    this.y = builder.y;
    this.z = builder.z;
    this.pitch = builder.pitch;
    this.yaw = builder.yaw;
    this.roll = builder.roll;
    this.aspectRatio = builder.aspectRatio;
    this.zNear = builder.zNear;
    this.zFar = builder.zFar;
    this.fov = builder.fov;
    this.window = builder.window;
    this.lastPosition = getGLFWPosition();
  }

  private Vector2d getGLFWPosition() {
    Vector2d position = new Vector2d();
    DoubleBuffer xb = BufferUtils.createDoubleBuffer(1), yb = BufferUtils.createDoubleBuffer(1);
    glfwGetCursorPos(window, xb, yb);
    position.set(xb.get(0), yb.get(0));
    return position;
  }

  /**
   * Processes mouse input and converts it in to camera movement.
   *
   * @param mouseSpeed the speed (sensitivity) of the mouse, 1.0 should suffice
   */
  public void processMouse(float mouseSpeed) {
    final float MAX_LOOK_UP = 90;
    final float MAX_LOOK_DOWN = -90;
    Vector2d position = getGLFWPosition();
    Vector2d delta = new Vector2d(position);
    delta.sub(lastPosition);
    lastPosition = position;
    float mouseDX = ((float)delta.x) * mouseSpeed * 0.16f;
    float mouseDY = ((float)delta.y) * mouseSpeed * -0.16f;
    if (yaw + mouseDX >= 360) {
      yaw = yaw + mouseDX - 360;
    } else if (yaw + mouseDX < 0) {
      yaw = 360 - yaw + mouseDX;
    } else {
      yaw += mouseDX;
    }
    if (pitch - mouseDY >= MAX_LOOK_DOWN && pitch - mouseDY <= MAX_LOOK_UP) {
      pitch += -mouseDY;
    } else if (pitch - mouseDY < MAX_LOOK_DOWN) {
      pitch = MAX_LOOK_DOWN;
    } else if (pitch - mouseDY > MAX_LOOK_UP) {
      pitch = MAX_LOOK_UP;
    }
  }

  /**
   * Processes mouse input and converts it into camera movement.
   *
   * @param mouseSpeed the speed (sensitivity) of the mouse, 1.0 should suffice
   * @param maxLookUp the maximum angle in degrees at which you can look up
   * @param maxLookDown the maximum angle in degrees at which you can look down
   */
  public void processMouse(float mouseSpeed, float maxLookUp, float maxLookDown) {
    Vector2d position = getGLFWPosition();
    Vector2d delta = new Vector2d(position);
    delta.sub(lastPosition);
    lastPosition = position;
    float mouseDX = ((float)delta.x) * mouseSpeed * 0.16f;
    float mouseDY = ((float)delta.y) * mouseSpeed * 0.16f;
    if (yaw + mouseDX >= 360) {
      yaw = yaw + mouseDX - 360;
    } else if (yaw + mouseDX < 0) {
      yaw = 360 - yaw + mouseDX;
    } else {
      yaw += mouseDX;
    }
    if (pitch - mouseDY >= maxLookDown && pitch - mouseDY <= maxLookUp) {
      pitch += -mouseDY;
    } else if (pitch - mouseDY < maxLookDown) {
      pitch = maxLookDown;
    } else if (pitch - mouseDY > maxLookUp) {
      pitch = maxLookUp;
    }
  }

  /**
   * Processes keyboard input and converts into camera movement.
   *
   * @param delta the elapsed time since the last frame update in milliseconds
   *
   * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
   */
  public void processKeyboard(float delta) {
    if (delta <= 0) {
      throw new IllegalArgumentException("delta " + delta + " is 0 or is smaller than 0");
    }

    boolean keyUp = glfwGetKey(window, GLFW_KEY_UP) == 1 || glfwGetKey(window, GLFW_KEY_W) == 1;
    boolean keyDown = glfwGetKey(window, GLFW_KEY_DOWN) == 1 || glfwGetKey(window, GLFW_KEY_S) == 1;
    boolean keyLeft = glfwGetKey(window, GLFW_KEY_LEFT) == 1 || glfwGetKey(window, GLFW_KEY_A) == 1;
    boolean keyRight = glfwGetKey(window, GLFW_KEY_RIGHT) == 1 || glfwGetKey(window, GLFW_KEY_D) == 1;
    boolean flyUp = glfwGetKey(window, GLFW_KEY_SPACE) == 1;
    boolean flyDown = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == 1;

    if (keyUp && keyRight && !keyLeft && !keyDown) {
      moveFromLook(delta * 0.003f, 0, -delta * 0.003f);
    }
    if (keyUp && keyLeft && !keyRight && !keyDown) {
      moveFromLook(-delta * 0.003f, 0, -delta * 0.003f);
    }
    if (keyUp && !keyLeft && !keyRight && !keyDown) {
      moveFromLook(0, 0, -delta * 0.003f);
    }
    if (keyDown && keyLeft && !keyRight && !keyUp) {
      moveFromLook(-delta * 0.003f, 0, delta * 0.003f);
    }
    if (keyDown && keyRight && !keyLeft && !keyUp) {
      moveFromLook(delta * 0.003f, 0, delta * 0.003f);
    }
    if (keyDown && !keyUp && !keyLeft && !keyRight) {
      moveFromLook(0, 0, delta * 0.003f);
    }
    if (keyLeft && !keyRight && !keyUp && !keyDown) {
      moveFromLook(-delta * 0.003f, 0, 0);
    }
    if (keyRight && !keyLeft && !keyUp && !keyDown) {
      moveFromLook(delta * 0.003f, 0, 0);
    }
    if (flyUp && !flyDown) {
      y += delta * 0.003f;
    }
    if (flyDown && !flyUp) {
      y -= delta * 0.003f;
    }
  }

  /**
   * Processes keyboard input and converts into camera movement.
   *
   * @param delta the elapsed time since the last frame update in milliseconds
   * @param speed the speed of the movement (normal = 1.0)
   *
   * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
   */
  public void processKeyboard(float delta, float speed) {
    if (delta <= 0) {
      throw new IllegalArgumentException("delta " + delta + " is 0 or is smaller than 0");
    }

    boolean keyUp = glfwGetKey(window, GLFW_KEY_UP) == 1 || glfwGetKey(window, GLFW_KEY_W) == 1;
    boolean keyDown = glfwGetKey(window, GLFW_KEY_DOWN) == 1 || glfwGetKey(window, GLFW_KEY_S) == 1;
    boolean keyLeft = glfwGetKey(window, GLFW_KEY_LEFT) == 1 || glfwGetKey(window, GLFW_KEY_A) == 1;
    boolean keyRight = glfwGetKey(window, GLFW_KEY_RIGHT) == 1 || glfwGetKey(window, GLFW_KEY_D) == 1;
    boolean flyUp = glfwGetKey(window, GLFW_KEY_SPACE) == 1;
    boolean flyDown = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == 1;

    if (keyUp && keyRight && !keyLeft && !keyDown) {
      moveFromLook(speed * delta * 0.003f, 0, -speed * delta * 0.003f);
    }
    if (keyUp && keyLeft && !keyRight && !keyDown) {
      moveFromLook(-speed * delta * 0.003f, 0, -speed * delta * 0.003f);
    }
    if (keyUp && !keyLeft && !keyRight && !keyDown) {
      moveFromLook(0, 0, -speed * delta * 0.003f);
    }
    if (keyDown && keyLeft && !keyRight && !keyUp) {
      moveFromLook(-speed * delta * 0.003f, 0, speed * delta * 0.003f);
    }
    if (keyDown && keyRight && !keyLeft && !keyUp) {
      moveFromLook(speed * delta * 0.003f, 0, speed * delta * 0.003f);
    }
    if (keyDown && !keyUp && !keyLeft && !keyRight) {
      moveFromLook(0, 0, speed * delta * 0.003f);
    }
    if (keyLeft && !keyRight && !keyUp && !keyDown) {
      moveFromLook(-speed * delta * 0.003f, 0, 0);
    }
    if (keyRight && !keyLeft && !keyUp && !keyDown) {
      moveFromLook(speed * delta * 0.003f, 0, 0);
    }
    if (flyUp && !flyDown) {
      y += speed * delta * 0.003f;
    }
    if (flyDown && !flyUp) {
      y -= speed * delta * 0.003f;
    }
  }

  /**
   * Processes keyboard input and converts into camera movement.
   *
   * @param delta the elapsed time since the last frame update in milliseconds
   * @param speedX the speed of the movement on the x-axis (normal = 1.0)
   * @param speedY the speed of the movement on the y-axis (normal = 1.0)
   * @param speedZ the speed of the movement on the z-axis (normal = 1.0)
   *
   * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
   */
  public void processKeyboard(float delta, float speedX, float speedY, float speedZ) {
    if (delta <= 0) {
      throw new IllegalArgumentException("delta " + delta + " is 0 or is smaller than 0");
    }

    boolean keyUp = glfwGetKey(window, GLFW_KEY_UP) == 1 || glfwGetKey(window, GLFW_KEY_W) == 1;
    boolean keyDown = glfwGetKey(window, GLFW_KEY_DOWN) == 1 || glfwGetKey(window, GLFW_KEY_S) == 1;
    boolean keyLeft = glfwGetKey(window, GLFW_KEY_LEFT) == 1 || glfwGetKey(window, GLFW_KEY_A) == 1;
    boolean keyRight = glfwGetKey(window, GLFW_KEY_RIGHT) == 1 || glfwGetKey(window, GLFW_KEY_D) == 1;
    boolean flyUp = glfwGetKey(window, GLFW_KEY_SPACE) == 1;
    boolean flyDown = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == 1;

    if (keyUp && keyRight && !keyLeft && !keyDown) {
      moveFromLook(speedX * delta * 0.003f, 0, -speedZ * delta * 0.003f);
    }
    if (keyUp && keyLeft && !keyRight && !keyDown) {
      moveFromLook(-speedX * delta * 0.003f, 0, -speedZ * delta * 0.003f);
    }
    if (keyUp && !keyLeft && !keyRight && !keyDown) {
      moveFromLook(0, 0, -speedZ * delta * 0.003f);
    }
    if (keyDown && keyLeft && !keyRight && !keyUp) {
      moveFromLook(-speedX * delta * 0.003f, 0, speedZ * delta * 0.003f);
    }
    if (keyDown && keyRight && !keyLeft && !keyUp) {
      moveFromLook(speedX * delta * 0.003f, 0, speedZ * delta * 0.003f);
    }
    if (keyDown && !keyUp && !keyLeft && !keyRight) {
      moveFromLook(0, 0, speedZ * delta * 0.003f);
    }
    if (keyLeft && !keyRight && !keyUp && !keyDown) {
      moveFromLook(-speedX * delta * 0.003f, 0, 0);
    }
    if (keyRight && !keyLeft && !keyUp && !keyDown) {
      moveFromLook(speedX * delta * 0.003f, 0, 0);
    }
    if (flyUp && !flyDown) {
      y += speedY * delta * 0.003f;
    }
    if (flyDown && !flyUp) {
      y -= speedY * delta * 0.003f;
    }
  }

  /**
   * Move in the direction you're looking. That is, this method assumes a new coordinate system where the axis you're
   * looking down is the z-axis, the axis to your left is the x-axis, and the upward axis is the y-axis.
   *
   * @param dx the movement along the x-axis
   * @param dy the movement along the y-axis
   * @param dz the movement along the z-axis
   */
  public void moveFromLook(float dx, float dy, float dz) {
    this.z += dx * (float) cos(toRadians(yaw - 90)) + dz * cos(toRadians(yaw));
    this.x -= dx * (float) sin(toRadians(yaw - 90)) + dz * sin(toRadians(yaw));
    this.y += dy * (float) sin(toRadians(pitch - 90)) + dz * sin(toRadians(pitch));
    //float hypotenuseX = dx;
    //float adjacentX = hypotenuseX * (float) Math.cos(Math.toRadians(yaw - 90));
    //float oppositeX = (float) Math.sin(Math.toRadians(yaw - 90)) * hypotenuseX;
    //this.z += adjacentX;
    //this.x -= oppositeX;
    //
    //this.y += dy;
    //
    //float hypotenuseZ = dz;
    //float adjacentZ = hypotenuseZ * (float) Math.cos(Math.toRadians(yaw));
    //float oppositeZ = (float) Math.sin(Math.toRadians(yaw)) * hypotenuseZ;
    //this.z += adjacentZ;
    //this.x -= oppositeZ;
  }

  /**
   * Sets the position of the camera.
   *
   * @param x the x-coordinate of the camera
   * @param y the y-coordinate of the camera
   * @param z the z-coordinate of the camera
   */
  public void setPosition(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Sets GL_PROJECTION to an orthographic projection matrix. The matrix mode will be returned it its previous value
   * after execution.
   */
  public void applyOrthographicMatrix() {
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-aspectRatio, aspectRatio, -1, 1, 0, zFar);
    glPopAttrib();
  }

  /**
   * Enables or disables OpenGL states that will enhance the camera appearance. Enable GL_DEPTH_CLAMP if
   * ARB_depth_clamp is supported
   */
  public void applyOptimalStates() {
    if (GL.getCapabilities().GL_ARB_depth_clamp) {
      glEnable(GL_DEPTH_CLAMP);
    }
  }

  /**
   * Sets GL_PROJECTION to an perspective projection matrix. The matrix mode will be returned it its previous value
   * after execution.
   */
  public void applyPerspectiveMatrix() {
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    Matrix4f matrix = new Matrix4f();
    matrix.perspective((float) Math.toRadians(fov), aspectRatio, zNear, zFar);
    FloatBuffer buffer = ByteBuffer.allocateDirect(4 * 4 * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer();
    matrix.get(buffer);
    glMatrixMode(GL_PROJECTION);
    glLoadMatrixf(buffer);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    //GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
    glPopAttrib();
  }

  /** Applies the camera translations and rotations to GL_MODELVIEW. */
  public void applyTranslations() {
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_MODELVIEW);
    glRotatef(pitch, 1, 0, 0);
    glRotatef(yaw, 0, 1, 0);
    glRotatef(roll, 0, 0, 1);
    glTranslatef(-x, -y, -z);
    glPopAttrib();
  }

  /**
   * Sets the rotation of the camera.
   *
   * @param pitch the rotation around the x-axis in degrees
   * @param yaw the rotation around the y-axis in degrees
   * @param roll the rotation around the z-axis in degrees
   */
  public void setRotation(float pitch, float yaw, float roll) {
    this.pitch = pitch;
    this.yaw = yaw;
    this.roll = roll;
  }

  /** @return the x-coordinate of the camera */
  public float x() {
    return x;
  }

  /** @return y the y-coordinate of the camera */
  public float y() {
    return y;
  }

  /** @return the z-coordinate of the camera */
  public float z() {
    return z;
  }

  /** @return the pitch of the camera in degrees */
  public float pitch() {
    return pitch;
  }

  /** @return the yaw of the camera in degrees */
  public float yaw() {
    return yaw;
  }

  /** @return the roll of the camera in degrees */
  public float roll() {
    return roll;
  }

  /** @return the fov of the camera in degrees in the y direction */
  public float fieldOfView() {
    return fov;
  }

  /**
   * Sets the field of view angle in degrees in the y direction. Note that this.applyPerspectiveMatrix() must be
   * applied in order to see any difference.
   *
   * @param fov the field of view angle in degrees in the y direction
   */
  public void setFieldOfView(float fov) {
    this.fov = fov;
  }

  @Override
  public void setAspectRatio(float aspectRatio) {
    if (aspectRatio <= 0) {
      throw new IllegalArgumentException("aspectRatio " + aspectRatio + " is 0 or less");
    }
    this.aspectRatio = aspectRatio;
  }

  /** @return the aspect ratio of the camera */
  public float aspectRatio() {
    return aspectRatio;
  }

  /** @return the distance from the camera to the near clipping pane */
  public float nearClippingPane() {
    return zNear;
  }

  /** @return the distance from the camera to the far clipping pane */
  public float farClippingPane() {
    return zFar;
  }

  @Override
  public String toString() {
    return "EulerCamera [x=" + x + ", y=" + y + ", z=" + z + ", pitch=" + pitch + ", yaw=" + yaw + ", " +
        "roll=" + roll + ", fov=" + fov + ", aspectRatio=" + aspectRatio + ", zNear=" + zNear + ", " +
        "zFar=" + zFar + "]";
  }

  /** A builder helper class for the EulerCamera class. */
  public static class Builder {

    private float aspectRatio = 1;
    private float x = 0, y = 0, z = 0, pitch = 0, yaw = 0, roll = 0;
    private float zNear = 0.3f;
    private float zFar = 100;
    private float fov = 90;
    private long window;

    public Builder() {

    }

    /**
     * Sets the aspect ratio of the camera.
     *
     * @param aspectRatio the aspect ratio of the camera (window width / window height)
     *
     * @return this
     */
    public Builder setAspectRatio(float aspectRatio) {
      if (aspectRatio <= 0) {
        throw new IllegalArgumentException("aspectRatio " + aspectRatio + " was 0 or was smaller than 0");
      }
      this.aspectRatio = aspectRatio;
      return this;
    }

    /**
     * Sets the distance from the camera to the near clipping pane.
     *
     * @param nearClippingPane the distance from the camera to the near clipping pane
     *
     * @return this
     *
     * @throws IllegalArgumentException if nearClippingPane is 0 or less
     */
    public Builder setNearClippingPane(float nearClippingPane) {
      if (nearClippingPane <= 0) {
        throw new IllegalArgumentException("nearClippingPane " + nearClippingPane + " is 0 or less");
      }
      this.zNear = nearClippingPane;
      return this;
    }

    /**
     * Sets the distance from the camera to the far clipping pane.
     *
     * @param farClippingPane the distance from the camera to the far clipping pane
     *
     * @return this
     *
     * @throws IllegalArgumentException if farClippingPane is 0 or less
     */
    public Builder setFarClippingPane(float farClippingPane) {
      if (farClippingPane <= 0) {
        throw new IllegalArgumentException("farClippingPane " + farClippingPane + " is 0 or less");
      }
      this.zFar = farClippingPane;
      return this;
    }

    /**
     * Sets the field of view angle in degrees in the y direction.
     *
     * @param fov the field of view angle in degrees in the y direction
     *
     * @return this
     */
    public Builder setFieldOfView(float fov) {
      this.fov = fov;
      return this;
    }

    /**
     * Sets the position of the camera.
     *
     * @param x the x-coordinate of the camera
     * @param y the y-coordinate of the camera
     * @param z the z-coordinate of the camera
     *
     * @return this
     */
    public Builder setPosition(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
    }

    /**
     * Sets the rotation of the camera.
     *
     * @param pitch the rotation around the x-axis in degrees
     * @param yaw the rotation around the y-axis in degrees
     * @param roll the rotation around the z-axis in degrees
     */
    public Builder setRotation(float pitch, float yaw, float roll) {
      this.pitch = pitch;
      this.yaw = yaw;
      this.roll = roll;
      return this;
    }

    public Builder setWindow(long window) {
      this.window = window;
      return this;
    }

    /**
     * Constructs an instance of EulerCamera from this builder helper class.
     *
     * @return an instance of EulerCamera
     *
     * @throws IllegalArgumentException if farClippingPane is the same or less than nearClippingPane
     */
    public EulerCamera build() {
      if (zFar <= zNear) {
        throw new IllegalArgumentException("farClippingPane " + zFar + " is the same or less than " +
            "nearClippingPane " + zNear);
      }
      return new EulerCamera(this);
    }
  }
}