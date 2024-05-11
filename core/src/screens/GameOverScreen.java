    package screens;
    
    import btck.com.GameManager;
    import btck.com.MyGdxGame;
    import btck.com.common.io.sound.ConstantSound;
    import btck.com.model.constant.Constants;
    import btck.com.model.constant.GameState;
    import btck.com.model.entity.player.swordman.Swordman;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    
    public class GameOverScreen  implements Screen {
        public final int gameOverWidth = 450;
        public final int gameOverHeight = 100;
        public final int tryAgainWidth = 350;
        public final int tryAgainHeight = 90;
        public final int mainMenuWidth = 350;
        public final int mainMenuHeight = 90;
        public int WIDTH = Gdx.graphics.getWidth();
        public int HEIGHT = Gdx.graphics.getHeight();
        MyGdxGame myGdxGame;
        int gameOverX, gameOverY, tryAgainX, tryAgainY, mainMenuX, mainMenuY;
    
        Texture gameOver;
        Texture tryAgainInactive;
        Texture tryAgainActive;
        Texture mainMenuInactive;
        Texture mainMenuActive;
        public GameOverScreen(MyGdxGame myGdxGame){
            System.out.println(WIDTH);
            System.out.println(HEIGHT);
            this.myGdxGame = myGdxGame;
            gameOver = new Texture(Constants.gameOverImgPath);
            tryAgainActive = new Texture(Constants.tryAgainIconActivePath);
            tryAgainInactive = new Texture(Constants.tryAgainIconInactivePath);
            mainMenuActive = new Texture(Constants.mainMenuIconActivePath);
            mainMenuInactive = new Texture(Constants.mainMenuIconInactivePath);

            ConstantSound.bgm.play();
        }
    
        @Override
        public void show() {
    
        }
    
        @Override
        public void render(float delta) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

            Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            myGdxGame.batch.begin();
    
            gameOverX = (WIDTH - gameOverWidth)/2;
            gameOverY = (HEIGHT - gameOverHeight)/2;
//            System.out.println(gameOverX + " " + gameOverY);

            myGdxGame.batch.draw(gameOver, gameOverX, gameOverY, gameOverWidth, gameOverHeight);

            mainMenuX = (WIDTH - mainMenuWidth)/2;
            mainMenuY = gameOverY - 100;

            if(Gdx.input.getX() < mainMenuX  + mainMenuWidth && Gdx.input.getX() > mainMenuX &&
                    HEIGHT - Gdx.input.getY() < mainMenuHeight + mainMenuY &&
                    HEIGHT - Gdx.input.getY() > mainMenuY) {
                myGdxGame.batch.draw(mainMenuActive, mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight);
                if(Gdx.input.isTouched()){
                    this.dispose();
                    myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
                }
            }
            else{
                myGdxGame.batch.draw(mainMenuInactive, mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight);
            }


            tryAgainX =  (WIDTH - tryAgainWidth)/2;;
            tryAgainY = mainMenuY - 100;
    
            if(Gdx.input.getX() < tryAgainX + tryAgainWidth && Gdx.input.getX() > tryAgainX &&
                    HEIGHT - Gdx.input.getY() < tryAgainHeight + tryAgainY &&
                    HEIGHT - Gdx.input.getY() > tryAgainY) {
                myGdxGame.batch.draw(tryAgainActive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
                if(Gdx.input.isTouched()){
                    this.dispose();
                    ConstantSound.dispose();
                    GameManager.getInstance().setCurrentPlayer(new Swordman());
                    GameManager.getInstance().gameState = GameState.INGAME;
                    myGdxGame.setScreen(new IngameScreen(myGdxGame));
                }
            }
            else{
                myGdxGame.batch.draw(tryAgainInactive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
            }



            myGdxGame.batch.end();
        }
    
        @Override
        public void resize(int width, int height) {
            this.WIDTH = width;
            this.HEIGHT = height;
        }
    
        @Override
        public void pause() {
    
        }
    
        @Override
        public void resume() {
    
        }
    
        @Override
        public void hide() {
    
        }
    
        @Override
        public void dispose() {
            this.gameOver.dispose();
            this.tryAgainActive.dispose();
            this.tryAgainInactive.dispose();
            this.mainMenuActive.dispose();
            this.mainMenuInactive.dispose();
        }
    }
    
