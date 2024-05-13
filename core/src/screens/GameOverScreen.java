    package screens;
    
    import btck.com.GameManager;
    import btck.com.MyGdxGame;
    import btck.com.common.io.sound.ConstantSound;
    import btck.com.model.constant.Constants;
    import btck.com.model.constant.GameState;
    import btck.com.model.entity.player.swordman.Swordman;
    import btck.com.ui.Button;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;

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
        Button btnGameOver;
        Button btnTryAgain;
        Button btnMainMenu;
        public GameOverScreen(MyGdxGame myGdxGame){
            System.out.println(WIDTH);
            System.out.println(HEIGHT);
            this.myGdxGame = myGdxGame;
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

            btnGameOver = new Button (gameOverX, gameOverY, gameOverWidth, gameOverHeight, Constants.gameOverImgPath, Constants.gameOverImgPath);
            btnGameOver.draw(myGdxGame.batch);

            mainMenuX = (WIDTH - mainMenuWidth)/2;
            mainMenuY = gameOverY - 100;
            btnMainMenu = new Button(mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight, Constants.mainMenuIconInactivePath, Constants.mainMenuIconActivePath);
            updateMainMenu();

            tryAgainX =  (WIDTH - tryAgainWidth)/2;;
            tryAgainY = mainMenuY - 100;
            btnTryAgain = new Button(tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight, Constants.tryAgainIconInactivePath, Constants.tryAgainIconActivePath);
            updateTryAgain();


            myGdxGame.batch.end();
        }
        public void updateMainMenu(){
            btnMainMenu.update();
            btnMainMenu.draw(myGdxGame.batch);
            if(btnMainMenu.isClicked()){
                btnMainMenu.setClicked(false);
                this.dispose();
                myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
            }
        }
        public void updateTryAgain(){
            btnTryAgain.update();
            btnTryAgain.draw(myGdxGame.batch);
            if(btnTryAgain.isClicked()){
                btnTryAgain.setClicked(false);
                this.dispose();
                ConstantSound.dispose();
                GameManager.getInstance().setCurrentPlayer(new Swordman());
                GameManager.getInstance().gameState = GameState.INGAME;
                myGdxGame.setScreen(new IngameScreen(myGdxGame));
            }
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
            btnGameOver.dispose();
            btnMainMenu.dispose();
            btnTryAgain.dispose();
        }
    }
    
