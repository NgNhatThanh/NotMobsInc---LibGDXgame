    package screens;
    
    import btck.com.GameManager;
    import btck.com.MyGdxGame;
    import btck.com.common.io.sound.ConstantSound;
    import btck.com.common.io.Constants;
    import btck.com.model.constant.GameState;
    import btck.com.model.entity.player.ghost.Ghost;
    import btck.com.ui.Button;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;

    public class GameOverScreen  implements Screen {
        public final int gameOverWidth = 450;
        public final int gameOverHeight = 100;
        public final int tryAgainWidth = 320;
        public final int tryAgainHeight = 90;
        public final int mainMenuWidth = 320;
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
            ConstantSound.getInstance().bgmMenu.play();
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

            btnGameOver = new Button (gameOverX, gameOverY, gameOverWidth, gameOverHeight, Constants.GAME_OVER_IMG_PATH, Constants.GAME_OVER_IMG_PATH);
            btnGameOver.draw(myGdxGame.batch);

            tryAgainX =  (WIDTH - tryAgainWidth)/2;;
            tryAgainY = gameOverY - 110;
            btnTryAgain = new Button(tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight, Constants.TRY_AGAIN_ICON_INACTIVE_PATH, Constants.TRY_AGAIN_ICON_ACTIVE_PATH);
            updateTryAgain();

            mainMenuX = (WIDTH - mainMenuWidth)/2;
            mainMenuY = tryAgainY - 90;
            btnMainMenu = new Button(mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight, Constants.MAIN_MENU_ICON_INACTIVE_PATH, Constants.MAIN_MENU_ICON_ACTIVE_PATH);
            updateMainMenu();




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
                ConstantSound.getInstance().bgmMenu.dispose();
                GameManager.getInstance().setCurrentPlayer(new Ghost());
                GameManager.getInstance().gameState = GameState.INGAME;
                // Clear enemies
                GameManager.getInstance().getEnemies().clear();
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
    
