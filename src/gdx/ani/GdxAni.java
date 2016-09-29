package gdx.ani;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.audio.Music;

public class GdxAni extends ApplicationAdapter {

    private Texture img, isShot, isFalling;
    private SpriteBatch batch;
    private TextureAtlas textureAtlas1;
    private Animation[] aniBird = new Animation[3];
    private Animation[] anidog = new Animation[5];
    private float elapsedTime = 0;
    int x1, y1, nDir = 1, x2, y2, nDir2 = 1, nJump;
    int Speed;
    boolean wasShot;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("background.png");
        isShot = new Texture("isShot.png");
        isFalling = new Texture("isFalling.png");
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("duckRight.atlas"));
        aniBird[0] = new Animation(1 / 10f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("duckLeft.atlas"));
        aniBird[1] = new Animation(1 / 10f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("duckUp.atlas"));
        aniBird[2] = new Animation(1 / 10f, textureAtlas1.getRegions());

        textureAtlas1 = new TextureAtlas(Gdx.files.internal("dogRight.atlas"));
        anidog[0] = new Animation(1 / 10f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("dogLeft.atlas"));
        anidog[1] = new Animation(1 / 10f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("dogJumpRight.atlas"));
        anidog[2] = new Animation(1 / 5f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("dogJumpLeft.atlas"));
        anidog[3] = new Animation(1 / 5f, textureAtlas1.getRegions());
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("dogLaugh.atlas"));
        anidog[4] = new Animation(1 / 10f, textureAtlas1.getRegions());

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas1.dispose();

    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        nDir2 = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nDir = 1;
            x1 += 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nDir = 2;
            x1 -= 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y1 -= 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nDir = 3;
            y1 += 4;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (isHit(x1, y1)) {
                nDir = 6;
                wasShot = true;

            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Music dogSound = Gdx.audio.newMusic(Gdx.files.internal("Duck Hunt.mp3"));
            dogSound.play();

        }


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nDir2 = 1;
            x2 += 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nDir2 = 2;
            x2 -= 4;
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W & Input.Keys.D)) {
//            if (y2 <= 40) {
//                nJump = 1;
//
//                nDir2 = 3;
//            }
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W & Input.Keys.A)) {
//
//            if (y2 <= 40) {
//                nJump = 1;
//
//                nDir2 = 4;
//            }
//
//        }

        if (wasShot == true) {
            y1 -= Speed;
            Speed++;
            nDir = 7;
            if (y1 < 10) {
                wasShot = false;
                Speed = 10;
            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {


            if (y2 <= 40) {
                nJump = 1;
                // nDir2 = 3;
            } else {
                nJump = 0;
            }

        }


        if (nJump == 1) {

            //We're ascending. Decrease the Y coordinate of the sprite by the speed.
            y2 += Speed;
            Speed--; //The character needs to jump smoothly, so the speed should decrease as he ascends.
            if (Speed <= 0) {
                nJump = 0; //If speed is <= 0, then the character should fall down.
                Speed = 0;
            }
        } else if (nJump == 0) {
            //We're descending. Increase the Y coordinate by the speed (at first, it's 0).
            y2 -= Speed;
            Speed++; //Increase the speed, so the character falls gradually faster.
            if (y2 <= 10) {
                //If we reached the original Y coordinate, we hit the ground. Mark the character as standing.

                y2 = 10;
                Speed = 20;
            }
        }


        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //sprite.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (nDir == 1) {
            batch.draw(aniBird[0].getKeyFrame(elapsedTime, true), x1, y1);
        }
        if (nDir == 2) {
            batch.draw(aniBird[1].getKeyFrame(elapsedTime, true), x1, y1);
        }
        if (nDir == 3) {
            batch.draw(aniBird[2].getKeyFrame(elapsedTime, true), x1, y1);
        }

        if (nDir2 == 1) {
            batch.draw(anidog[0].getKeyFrame(elapsedTime, true), x2, y2);
        }
        if (nDir2 == 2) {
            batch.draw(anidog[1].getKeyFrame(elapsedTime, true), x2, y2);
        }
        if (nDir2 == 3) {
            batch.draw(anidog[2].getKeyFrame(elapsedTime, true), x2, y2);
        }
        if (nDir2 == 4) {
            batch.draw(anidog[3].getKeyFrame(elapsedTime, true), x2, y2);
        }
        if (nDir2 == 5) {
            batch.draw(anidog[4].getKeyFrame(elapsedTime, true), x2, y2);
        }
        if (nDir == 6) {
            batch.draw(isShot, x1, y1);
        }
        if (nDir == 7) {
            batch.draw(isFalling, x1, y1);
        }


        batch.end();
    }

    boolean isHit(int x1, int y1) {
        int nH = 100, nW = 100;
        int mX = 0, mY = 0;

        Gdx.input.getX(mX);
        Gdx.input.getY(mY);

        if (mX >= x1 && mX <= x1 + nW
                && mY >= y1 && mY <= y1 + nH) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}