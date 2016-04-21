package com.superduckinvaders.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.superduckinvaders.game.assets.Assets;

/**
 * Screen which displays the sfx and music volumes
 */
public class SettingsScreen implements Screen {

    /**
     * Volume levels.
     */
    private int masterVol, sfxVol, musicVol;

    /**
     * Level progress string.
     */
    private String levelsComplete;

    /**
     * Whether demented mode is active or not.
     */
    private boolean dementedMode = false;

    /**
     * The DuckGame this SettingsScreen belongs to.
     */
    private DuckGame parent;

    /**
     * Stage for containing the button.
     */
    private Stage stage;

    /**
     * Initialises this SettingsScreen.
     *
     * @param parent the game the screen is associated with
     */
    public SettingsScreen(DuckGame parent) {
        this.parent = parent;
    }

    /**
     * Shows this GameScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void show() {
        masterVol = (int) (DuckGame.masterVolume * 100);
        sfxVol = (int) (DuckGame.sfxVolume * 100);
        musicVol = (int) (DuckGame.musicVolume * 100);
        levelsComplete = DuckGame.levelsComplete;
        dementedMode = DuckGame.dementedMode;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle white = new Label.LabelStyle(Assets.font, Color.WHITE);
        final Label masterVolLabel = new Label(Integer.toString(masterVol), white);
        masterVolLabel.setPosition((stage.getWidth() - masterVolLabel.getPrefWidth()) / 2, (stage.getHeight() - masterVolLabel.getPrefHeight()) / 2);
        masterVolLabel.setTouchable(Touchable.disabled);

        final Label title = new Label("Settings", white);
        title.setPosition((stage.getWidth() - title.getPrefWidth()) / 2, (stage.getHeight() - title.getPrefHeight()) / 2 + 200);
        title.setTouchable(Touchable.disabled);

        final Label masterVolTitle = new Label("Master Vol: ", white);
        masterVolTitle.setPosition((stage.getWidth() - masterVolTitle.getPrefWidth()) / 2 - 200, (stage.getHeight() - masterVolTitle.getPrefHeight()) / 2);
        masterVolTitle.setTouchable(Touchable.disabled);

        final Label musicVolTitle = new Label("Music Vol: ", white);
        musicVolTitle.setPosition((stage.getWidth() - musicVolTitle.getPrefWidth()) / 2 - 200, (stage.getHeight() - musicVolTitle.getPrefHeight()) / 2 - 60);
        musicVolTitle.setTouchable(Touchable.disabled);

        final Label sfxVolTitle = new Label("SFX vol: ", white);
        sfxVolTitle.setPosition((stage.getWidth() - sfxVolTitle.getPrefWidth()) / 2 - 200, (stage.getHeight() - sfxVolTitle.getPrefHeight()) / 2 - 120);
        sfxVolTitle.setTouchable(Touchable.disabled);

        Drawable downButton = new TextureRegionDrawable(Assets.downButton);
        Drawable upButton = new TextureRegionDrawable(Assets.upButton);

        Button masterUp = new Button(new Button.ButtonStyle(upButton, upButton, upButton));

        Drawable buttonBack = new TextureRegionDrawable(Assets.backButton);

        Label backLabel = new Label("Escape", white);
        backLabel.setPosition(85, Gdx.graphics.getHeight() - 85);
        backLabel.setTouchable(Touchable.disabled);

        Button backButton = new Button(new Button.ButtonStyle(buttonBack, buttonBack, buttonBack));
        backButton.setPosition(40, Gdx.graphics.getHeight() - 100);
        backButton.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                parent.showStartScreen();
            }
        });

        masterUp.setPosition((stage.getWidth() - masterUp.getPrefWidth()) / 2 + 60, (stage.getHeight() - masterUp.getPrefHeight()) / 2);
        masterUp.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (masterVol < 100) {
                    masterVol += 5;
                }


                applySettings();
                masterVolLabel.setText(Integer.toString(masterVol));
                stage.draw();
            }
        });

        Button masterDown = new Button(new Button.ButtonStyle(downButton, downButton, downButton));
        masterDown.setPosition((stage.getWidth() - masterDown.getPrefWidth()) / 2 - 60, (stage.getHeight() - masterDown.getPrefHeight()) / 2);
        masterDown.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (masterVol > 0) {
                    masterVol -= 5;
                }

                applySettings();

                masterVolLabel.setText(Integer.toString(masterVol));
                stage.draw();
            }
        });


        final Label sfxVolLabel = new Label(Integer.toString(sfxVol), white);
        sfxVolLabel.setPosition((stage.getWidth() - sfxVolLabel.getPrefWidth()) / 2, (stage.getHeight() - sfxVolLabel.getPrefHeight()) / 2 - 120);
        sfxVolLabel.setTouchable(Touchable.disabled);

        Button sfxUp = new Button((new Button.ButtonStyle(upButton, upButton, upButton)));
        sfxUp.setPosition((stage.getWidth() - sfxUp.getPrefWidth()) / 2 + 60, (stage.getHeight() - sfxUp.getPrefHeight()) / 2 - 120);
        sfxUp.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (sfxVol < 100) {
                    sfxVol += 5;
                }

                applySettings();

                sfxVolLabel.setText(Integer.toString(sfxVol));
                stage.draw();
            }
        });

        Button sfxDown = new Button(new Button.ButtonStyle(downButton, downButton, downButton));
        sfxDown.setPosition((stage.getWidth() - sfxDown.getPrefWidth()) / 2 - 60, (stage.getHeight() - sfxDown.getPrefHeight()) / 2 - 120);
        sfxDown.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (sfxVol > 0) {
                    sfxVol -= 5;
                }

                applySettings();

                sfxVolLabel.setText(Integer.toString(sfxVol));
                stage.draw();
            }
        });

        final Label musicVolLabel = new Label(Integer.toString(musicVol), white);
        musicVolLabel.setPosition((stage.getWidth() - musicVolLabel.getPrefWidth()) / 2, (stage.getHeight() - musicVolLabel.getPrefHeight()) / 2 - 60);
        musicVolLabel.setTouchable(Touchable.disabled);

        Button musicUp = new Button((new Button.ButtonStyle(upButton, upButton, upButton)));
        musicUp.setPosition((stage.getWidth() - musicUp.getPrefWidth()) / 2 + 60, (stage.getHeight() - musicUp.getPrefHeight()) / 2 - 60);
        musicUp.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (musicVol < 100) {
                    musicVol += 5;
                }

                applySettings();

                musicVolLabel.setText(Integer.toString(musicVol));
                stage.draw();
            }
        });

        Button musicDown = new Button(new Button.ButtonStyle(downButton, downButton, downButton));
        musicDown.setPosition((stage.getWidth() - musicDown.getPrefWidth()) / 2 - 60, (stage.getHeight() - musicDown.getPrefHeight()) / 2 - 60);
        musicDown.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);
                if (musicVol > 0) {
                    musicVol -= 5;
                }

                applySettings();

                musicVolLabel.setText(Integer.toString(musicVol));
                stage.draw();
            }
        });

        // Added button for enabling/disabling demented mode.
        final Label dementedTitle = new Label("Demented Mode: ", white);
        dementedTitle.setPosition((stage.getWidth() - dementedTitle.getPrefWidth()) / 2 - 200, (stage.getHeight() - dementedTitle.getPrefHeight()) / 2 - 240);
        dementedTitle.setTouchable(Touchable.disabled);


        final Label dementedStatusLabel = new Label(dementedMode ? "ON" : "OFF", white);
        dementedStatusLabel.setPosition((stage.getWidth() - dementedStatusLabel.getPrefWidth()) / 2, (stage.getHeight() - dementedStatusLabel.getPrefHeight()) / 2 - 240);
        dementedStatusLabel.setTouchable(Touchable.disabled);

        final Button dementedButton = new Button(new Button.ButtonStyle(buttonBack, buttonBack, buttonBack));
        dementedButton.setPosition((stage.getWidth() - dementedButton.getPrefWidth()) / 2, (stage.getHeight() - dementedButton.getPrefHeight()) / 2 - 240);
        dementedButton.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                DuckGame.playSoundEffect(Assets.buttonPress, 1);

                // Invert demented mode.
                dementedMode = !dementedMode;

                applySettings();

                dementedStatusLabel.setText(dementedMode ? "ON" : "OFF");
                stage.draw();
            }
        });

        stage.addActor(title);
        stage.addActor(masterVolTitle);
        stage.addActor(sfxVolTitle);
        stage.addActor(musicVolTitle);
        stage.addActor(backButton);
        stage.addActor(backLabel);
        stage.addActor(musicUp);
        stage.addActor(musicDown);
        stage.addActor(musicVolLabel);
        stage.addActor(sfxUp);
        stage.addActor(sfxDown);
        stage.addActor(sfxVolLabel);
        stage.addActor(masterUp);
        stage.addActor(masterDown);
        stage.addActor(masterVolLabel);
        stage.addActor(dementedTitle);
        stage.addActor(dementedButton);
        stage.addActor(dementedStatusLabel);
    }

    /**
     * Main screen loop.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        stage.draw();
    }

    /**
     * Called when the game window is resized.
     *
     * @param width  the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        show();
    }

    /**
     * Checks for escape key press and returns to main menu.
     */
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            parent.showStartScreen();

        }

        stage.draw();
    }

    /**
     * Not used.
     */
    @Override
    public void pause() {
    }

    /**
     * Not used.
     */
    @Override
    public void resume() {
    }

    /**
     * Not used.
     */
    @Override
    public void hide() {
    }

    /**
     * Called to dispose libGDX objects used by this StartScreen.
     */
    @Override
    public void dispose() {
    }

    /**
     * Applies the settings values created here to the settings file.
     */
    public void applySettings() {
        DuckGame.masterVolume = (float) masterVol / 100;
        DuckGame.sfxVolume = (float) sfxVol / 100;
        DuckGame.musicVolume = (float) musicVol / 100;
        DuckGame.levelsComplete = levelsComplete;
        DuckGame.dementedMode = dementedMode;
        DuckGame.saveSettings();
    }
}










