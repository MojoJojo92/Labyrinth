package com.lab.labyrinth.graphics;

import java.util.ArrayList;

import com.lab.labyrinth.input.Controller;

public class Render3D extends Render {

	public double zBuffer[];
	public double zBufferWall[];
	public static double cosine, sine, up, bobbing, xx, yy, z;
	public static double forward, sideways;
	public ArrayList<Detection> detectionList = new ArrayList<Detection>();
	private int xPix = 0, yPix = 0;

	public Render3D(ArrayList<Detection> detectionList,int width, int height) {
		super(width, height);
		this.detectionList = detectionList;
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}

	public void floor() {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}

		double ceilingPosition = 16;
		double floorPosition = 8;
		forward = Display.game.controls.z;
		sideways = Display.game.controls.x;
		up = Display.game.controls.y;

		double rotation = Display.game.controls.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y + -height / 2.0) / height;
			if (Controller.walkBobbing) {
				bobbing = Math.sin(Display.game.time / 5.0) * 0.5;
			} else if (Controller.runBobbing) {
				bobbing = (Math.sin(Display.game.time / 5.0) * 0.5) * 2;
			} else if (Controller.crouchBobbing) {
				bobbing = (Math.sin(Display.game.time / 5.0) * 0.1) / 4;
			} else {
				bobbing = 0;
			}
			z = (floorPosition + up + bobbing) / ceiling;
			if (ceiling < 0) {
				z = (ceilingPosition - up - bobbing) / -ceiling;
			}
			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				xx = depth * cosine + z * sine;
				yy = z * cosine - depth * sine;
				xPix = (int) (xx + sideways);
				yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 15) + (yPix & 15) * 16];
				if (z > 400)
					pixels[x + y * width] = 0;
				if(xPix > detectionList.get(detectionList.size()-1).getX() * 8 - 24 && xPix < detectionList.get(detectionList.size()-1).getX()* 8 +1&& yPix > detectionList.get(detectionList.size()-1).getZ() * 8 -1 && yPix < detectionList.get(detectionList.size()-1).getZ()* 8 + 24)
					pixels[x + y * width] = Texture.finish.pixels[(xPix & 15) + (yPix & 15) * 16];
			}
			for (int i = 0; i < detectionList.size()-1; i++)
				detectionList.get(i).detectCollision();
		}
		if(detectionList.get(detectionList.size()-1).detectFinish())
			Display.game.setFinish(true);
	}

	public void walls(double xLeft, double xRight, double zDistanceRight, double zDistanceLeft, double yHeight) {

		double xcLeft = ((xLeft / 2) - (sideways / 16.0)) * 2.0;
		double zcLeft = ((zDistanceLeft / 2) - (forward / 16.0)) * 2.0;
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-(yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		double yCornerBL = ((0.5 - (yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight / 2) - (sideways / 16.0)) * 2.0;
		double zcRight = ((zDistanceRight / 2) - (forward / 16.0)) * 2.0;
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-(yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		double yCornerBR = ((0.5 - (yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		double tex3o = 0;
		double tex4o = 8;
		double clip = 0.5;

		if (rotLeftSideZ < clip && rotRightSideZ < clip) {
			return;
		}

		if (rotLeftSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex3o = tex3o + (tex4o - tex3o) * clip0;
		}

		if (rotRightSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex4o = tex3o + (tex4o - tex3o) * clip0;
		}

		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2.0);

		if (xPixelLeft >= xPixelRight) {
			return;
		}

		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;

		if (xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}

		if (xPixelRightInt > width) {
			xPixelRightInt = width;
		}

		double yPixelLeftTop = yCornerTL / rotLeftSideZ * height + height / 2.0;
		double yPixelLeftBottom = yCornerBL / rotLeftSideZ * height + height / 2.0;
		double yPixelRightTop = yCornerTR / rotRightSideZ * height + height / 2.0;
		double yPixelRightBottom = yCornerBR / rotRightSideZ * height + height / 2.0;

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex3o / rotLeftSideZ;
		double tex4 = tex4o / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {

			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

			if (zBufferWall[x] > zWall) {
				continue;
			}
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) yPixelTop;
			int yPixelBottomInt = (int) yPixelBottom;

			if (yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}

			if (yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (16 * pixelRotationY);

				pixels[x + y * width] = Texture.floor.pixels[(xTexture & 15) + (yTexture & 15) * 16];
				zBuffer[x + y * width] = 1 / zWall * 6;
			}
		}
	}

	public void renderDistancelimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (Display.game.getRenderDistance() / zBuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}