package com.timing.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timing.ui.group.ConfigGroup;
import com.timing.ui.group.ProgressGroup;

/**
 * @author Shuttle on 15/01/20.
 */

public class MainScreen extends BaseScreen {
    private Camera camera;
    private ProgressGroup progressGroup;
    private ConfigGroup configGroup;

    public MainScreen(SpriteBatch batch, Camera camera) {
        super(batch);
        this.camera = camera;
    }

    @Override
    public void show() {
        super.show();
        this.progressGroup = ProgressGroup.getInstance();
        this.configGroup = ConfigGroup.getInstance();

        stage.addActor(progressGroup);
        stage.addActor(configGroup);
    }

    @Override
    public void createGUI() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        batch.setProjectionMatrix(camera.combined);
        stage.draw();
    }

//    private void connectRender(SpriteBatch batch) {
//        batch.begin();
//        if (texture != null) {
//            batch.draw(texture, Rules.WORLD_WIDTH / 2 - PaintConstants.QR_HEIGHT / 2, Rules.WORLD_HEIGHT / 2 - PaintConstants.QR_HEIGHT / 2);
//        }
//        label.draw(batch, 1.0f);
//        playButton.draw(batch, 1.0f);
//        batch.end();
//        stage.draw();
//    }

    public void update(float dt) {
        stage.act();
    }

    @Override
    public void dispose() {
        super.dispose();
        //Connection.getInstance().dispose();
    }

//    private void connectUpdate(float dt) {
//        setLabelPosition();
//        if (playButton.isPressed() && Gdx.input.justTouched()) {
//            string = randomText();
//            label.setText(string);
//            clickQR();
//            Gdx.input.vibrate(25);
//        }
//
//        if (Gdx.input.justTouched() && (houseButton.isPressed() || personButton.isPressed() || locationButton.isPressed() || shopButton.isPressed() || bankButton.isPressed())) {
//            Gdx.input.vibrate(15);
//        }
//
//        if (Gdx.input.justTouched() && locationButton.isPressed()) {
//            auth();
//        }
//    }

//    private void disconnectUpdate(float dt) {
//        if (Gdx.input.justTouched()) {
//            Connection.getInstance().connect();
//        }
//    }

    private void auth() {
//       Socket socket = Connection.getInstance().getSocket();
//        if (socket.isConnected()) {
//            try {
//                socket.getOutputStream().write(NUMBER.getBytes());
//            } catch (IOException e) {
//                logger.log(Level.WARNING, e.getCause().getMessage());
//            }
//        }
    }

//    private void generateQRCodeImage(String text) throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, PaintConstants.QR_WIDTH, PaintConstants.QR_HEIGHT);
//        Pixmap pixmap = new Pixmap(PaintConstants.QR_WIDTH, PaintConstants.QR_HEIGHT, Pixmap.Format.RGBA8888);
//        for (int i = 0; i < bitMatrix.getHeight(); i++) {
//            for (int j = 0; j < bitMatrix.getWidth(); j++) {
//                if (bitMatrix.get(i, j)) {
//                    pixmap.drawPixel(i, j);
//                }
//                pixmap.setColor(Color.BLACK);
//            }
//        }
//        texture = new Texture(pixmap);
//        pixmap.dispose();
//    }

//    private void clickQR() {
//        try {
//            generateQRCodeImage(string);
//        } catch (WriterException e) {
//            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
//        }
//    }
//
//    private String randomText() {
//        return lorem.getName() + " " + lorem.getLastName();
//    }
//
//    private void setLabelPosition() {
//        label.setPosition(
//                Rules.WORLD_WIDTH / 2 - label.getGlyphLayout().width / 2,
//                3 * Rules.WORLD_HEIGHT / 4
//        );
//    }

    private void disconnectRender(SpriteBatch batch) {
//        batch.begin();
//        loginGroup.draw(batch, 1.0f);
//        batch.end();
    }
}
