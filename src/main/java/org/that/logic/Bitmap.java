package org.that.logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Bitmap {

    private final short width;
    private final byte[] data;

    public Bitmap(BufferedImage image) {
        width = (short) image.getWidth();
        data = new byte[(int) Math.ceil(image.getWidth() * image.getHeight() / 8.0)];

        for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
            int x = i % width;
            int y = i / width;
            boolean b = (((image.getRGB(x, y) >> 16) & 0xFF) + ((image.getRGB(x, y) >> 8) & 0xFF) + ((image.getRGB(x, y)) & 0xFF)) > 382;
            if (b) data[i / 8] = (byte) (data[i / 8] | 1);
            if (i % 8 != 7) data[i / 8] = (byte) (data[i / 8] << 1);
        }

    }

    public Bitmap(byte[] stream) throws IOException {
        width = ByteBuffer.wrap(new byte[]{stream[0], stream[1]}).getShort();

        data = new byte[stream.length - 2];
        for (int i = 2; i < stream.length; i++) data[i - 2] = stream[i];
    }

    public byte[] getStream() throws IOException {
        byte[] stream = new byte[2 + data.length];

        byte[] head = ByteBuffer.allocate(Short.BYTES).putShort(width).array();
        stream[0] = head[0];
        stream[1] = head[1];

        for (int i = 2; i < stream.length; i++) stream[i] = data[i - 2];

        return stream;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (byte b : data.clone()) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                line.append(b & 1);
                b = (byte) (b >> 1);
            }
            builder.append(line.reverse());
        }

        builder.replace((builder.length() / width) * width, builder.length(), "");

        for (int i = builder.length(); i > 0; i--) {
            if (i % width == width - 1)
                builder.insert(i + 1, '\n');
        }

        return builder.toString();

    }

    public BufferedImage asImage() {
        BufferedImage image = new BufferedImage(width, (data.length * 8) / width, 2);

        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            byte t = 0;

            for (int j = 0; j < 8; j++) {
                t = (byte) (t + (byte) (b & 1));
                if (j == 7) continue;
                t = (byte) (t << 1);
                b = (byte) (b >> 1);
            }

            for (int j = 0; j < 8; j++) {
                try {
                    int x = (i*8 + j) % width;
                    int y = (i*8 + j) / width;

                    image.setRGB(x, y, (t & 1) == 1 ? Color.WHITE.getRGB() : Color.BLACK.getRGB());

                    t = (byte) (t >> 1);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }

        return image;
    }
}
