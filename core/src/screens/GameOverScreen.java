    package screens;
    
    import btck.com.GameManager;
    import btck.com.MyGdxGame;
    import btck.com.model.constant.GameState;
    import btck.com.model.entity.player.Swordman;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    
    public class GameOverScreen  implements Screen {
        public static final int gameOverWidth = 400;
        public static final int gameOverHeight = 380;
        public static final int tryAgainWidth = 400;
        public static final int tryAgainHeight = 380;
        public static int WIDTH = Gdx.graphics.getWidth();
        public static int HEIGHT = Gdx.graphics.getHeight();
        MyGdxGame myGdxGame;
    
    
        Texture gameOver;
        Texture tryAgainInactive;
        Texture tryAgainActive;
        public GameOverScreen(MyGdxGame myGdxGame){
            System.out.println(GameOverScreen.WIDTH);
            System.out.println(GameOverScreen.HEIGHT);
            this.myGdxGame = myGdxGame;
            gameOver = new Texture("GameOverScreen/GameOver.png");
            tryAgainActive = new Texture("GameOverScreen/TryAgainActive.png");
            tryAgainInactive = new Texture("GameOverScreen/TryAgainInactive.png");
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
    
            int gameOverX = (GameOverScreen.WIDTH - gameOverWidth)/2;
            int gameOverY = (GameOverScreen.HEIGHT - gameOverHeight)/2;
//            System.out.println(gameOverX + " " + gameOverY);

            myGdxGame.batch.draw(gameOver, gameOverX, gameOverY, gameOverWidth, gameOverHeight);

            int tryAgainX =  (GameOverScreen.WIDTH - tryAgainWidth)/2;;
            int tryAgainY = gameOverY - 50;
    
            if(Gdx.input.getX() < tryAgainX + tryAgainWidth && Gdx.input.getX() > tryAgainX &&
                    GameOverScreen.HEIGHT - tryAgainHeight < Gdx.input.getY() &&
                    GameOverScreen.HEIGHT - tryAgainHeight > Gdx.input.getY()) {
                myGdxGame.batch.draw(tryAgainActive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
                if(Gdx.input.isTouched()){
                    myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
                }
            }
            else{
                myGdxGame.batch.draw(tryAgainInactive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
            }



            myGdxGame.batch.end();
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
    
        @Override
        public void hide() {
    
        }
    
        @Override
        public void dispose() {
            this.gameOver.dispose();
            this.tryAgainActive.dispose();
            this.tryAgainInactive.dispose();
        }
    }
    
