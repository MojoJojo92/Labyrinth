package com.lab.labyrinth.graphics;

import java.util.ArrayList;

public class Render3D extends Render {

	private double zBuffer[];
	private double zBufferWall[];
	private static double cosine, sine, up, bobbing, xx, yy, z;
	public static double forward, sideways;
	private ArrayList<Detection> detectionList;
	private double ceilingPosition, floorPosition, ceiling, depth, rotation;
	private int xPix, yPix;

	private double xcLeft, zcLeft, rotLeftSideX, yCornerTL, yCornerBL, rotLeftSideZ;
	private double xcRight, zcRight, rotRightSideX, yCornerTR, yCornerBR, rotRightSideZ;
	private double tex3o, tex4o, clip, clip0, tex1, tex2, tex3, tex4, xPixelLeft, xPixelRight, yPixelTop, yPixelBottom;
	private double yPixelLeftTop, yPixelLeftBottom, yPixelRightTop, yPixelRightBottom;
	private double pixelRotationX, pixelRotationY, zWall;
	private int xPixelLeftInt, xPixelRightInt, yPixelTopInt, yPixelBottomInt, xTexture, yTexture;

	private int colour, brightness, r, g, b;

	public Render3D(ArrayList<Detection> detectionList, int width, int height) {
		super(width, height);
		this.detectionList = new ArrayList<Detection>();
		this.detectionList = detectionList;
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
		ceilingPosition = 16;
		floorPosition = 8;
		xPix = 0;
		yPix = 0;
	}

	public void floor() {
		for (int x = 0; x < width; x++)
			zBufferWall[x] = 0;
		forward = Display.game.getControls().getZ();
		sideways = Display.game.getControls().getX();
		up = Display.game.getControls().getY();
		rotation = Display.game.getControls().getRotation();
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			ceiling = (y + -height / 2.0) / height;
			setBobbing();
			z = (floorPosition + up + bobbing) / ceiling;
			if (ceiling < 0)
				z = (ceilingPosition - up - bobbing) / -ceiling;
			for (int x = 0; x < width; x++) {
				depth = (x - width / 2.0) / height;
				depth *= z;
				xx = depth * cosine + z * sine;
				yy = z * cosine - depth * sine;
				xPix = (int) (xx + sideways);
				yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 15) + (yPix & 15) * 16];
				if (z > 400)
					pixels[x + y * width] = 0;
				if (playerIn(detectionList.get(detectionList.size() - 1).getX() * 8 - 24, detectionList.get(detectionList.size() - 1).getX() * 8 + 1, detectionList.get(detectionList.size() - 1).getZ() * 8 - 1, detectionList.get(detectionList.size() - 1).getZ() * 8 + 24))
					pixels[x + y * width] = Texture.finish.pixels[(xPix & 15) + (yPix & 15) * 16];
			}
			for (int i = 0; i < detectionList.size() - 1; i++)
				detectionList.get(i).detectCollision();
		}
		if (detectionList.get(detectionList.size() - 1).detectFinish())
			Display.game.setFinish(true);
	}

	private boolean playerIn(int x, int y, int z, int v) {
		if (xPix < x)
			return false;
		if (xPix > y)
			return false;
		if (yPix < z)
			return false;
		if (yPix > v)
			return false;
		return true;
	}

	private void setBobbing() {
		if (Display.game.getControls().isWalkBobbing())
			bobbing = Math.sin(Display.game.getTime() / 5.0) * 0.5;
		else if (Display.game.getControls().isRunBobbing())
			bobbing = (Math.sin(Display.game.getTime() / 5.0) * 0.5) * 2;
		else if (Display.game.getControls().isCrouchBobbing())
			bobbing = (Math.sin(Display.game.getTime() / 5.0) * 0.1) / 4;
		else
			bobbing = 0;
	}

	public void walls(double xLeft, double xRight, double zDistanceRight, double zDistanceLeft, double yHeight) {

		xcLeft = ((xLeft / 2) - (sideways / 16.0)) * 2.0;
		zcLeft = ((zDistanceLeft / 2) - (forward / 16.0)) * 2.0;
		rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		yCornerTL = ((-(yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		yCornerBL = ((0.5 - (yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		xcRight = ((xRight / 2) - (sideways / 16.0)) * 2.0;
		zcRight = ((zDistanceRight / 2) - (forward / 16.0)) * 2.0;
		rotRightSideX = xcRight * cosine - zcRight * sine;
		yCornerTR = ((-(yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		yCornerBR = ((0.5 - (yHeight / 2)) - (-up / 16.0) + (bobbing / 16.0)) * 2.0;
		rotRightSideZ = zcRight * cosine + xcRight * sine;

		tex3o = 0;
		tex4o = 8;
		clip = 0.5;

		if (rotLeftSideZ < clip && rotRightSideZ < clip)
			return;

		clip();

		if (xPixelLeft >= xPixelRight)
			return;

		xAdjustment();

		yPixelLeftTop = yCornerTL / rotLeftSideZ * height + height / 2.0;
		yPixelLeftBottom = yCornerBL / rotLeftSideZ * height + height / 2.0;
		yPixelRightTop = yCornerTR / rotRightSideZ * height + height / 2.0;
		yPixelRightBottom = yCornerBR / rotRightSideZ * height + height / 2.0;

		tex1 = 1 / rotLeftSideZ;
		tex2 = 1 / rotRightSideZ;
		tex3 = tex3o / rotLeftSideZ;
		tex4 = tex4o / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {

			pixelRotationX = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			zWall = (tex1 + (tex2 - tex1) * pixelRotationX);

			if (zBufferWall[x] > zWall)
				continue;
			zBufferWall[x] = zWall;

			xTexture = (int) ((tex3 + tex4 * pixelRotationX) / zWall);
			yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotationX;
			yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotationX;

			yAdjustment();

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				yTexture = (int) (16 * pixelRotationY);
				pixels[x + y * width] = Texture.floor.pixels[(xTexture & 15) + (yTexture & 15) * 16];
				zBuffer[x + y * width] = 1 / zWall * 6;
			}
		}
	}

	private void clip() {
		if (rotLeftSideZ < clip) {
			clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex3o = tex3o + (tex4o - tex3o) * clip0;
		}

		if (rotRightSideZ < clip) {
			clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex4o = tex3o + (tex4o - tex3o) * clip0;
		}

		xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
		xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2.0);
	}

	private void xAdjustment() {
		xPixelLeftInt = (int) xPixelLeft;
		xPixelRightInt = (int) xPixelRight;

		if (xPixelLeftInt < 0)
			xPixelLeftInt = 0;

		if (xPixelRightInt > width)
			xPixelRightInt = width;
	}

	private void yAdjustment() {
		yPixelTopInt = (int) yPixelTop;
		yPixelBottomInt = (int) yPixelBottom;

		if (yPixelTopInt < 0)
			yPixelTopInt = 0;

		if (yPixelBottomInt > height)
			yPixelBottomInt = height;
	}

	public void renderDistancelimiter() {
		for (int i = 0; i < width * height; i++) {
			colour = pixels[i];
			brightness = (int) (Display.game.getRenderDistance() / zBuffer[i]);

			if (brightness < 0)
				brightness = 0;

			if (brightness > 255)
				brightness = 255;

			r = (colour >> 16) & 0xff;
			g = (colour >> 8) & 0xff;
			b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}